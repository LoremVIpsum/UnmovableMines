package com.loremv.umines.data;

import com.loremv.umines.UnmovableMines;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.util.*;

public class DynamicContentManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final OreDataLoader oreDataLoader;
    private final OutputDataLoader outputDataLoader;
    private Map<String, ItemStack> concreteOutputMapping;

    public DynamicContentManager() {
        oreDataLoader = new OreDataLoader();
        outputDataLoader = new OutputDataLoader();
        concreteOutputMapping = Map.of();
    }

    public void reloadConcreteOutputMapping(RegistryAccess.Frozen registryAccess) {
        concreteOutputMapping = new HashMap<>();
        Registry<Item> itemRegistry = registryAccess.registryOrThrow(Registries.ITEM);
        for (var entry : getOutputData().outputs().entrySet()) {
            String key = entry.getKey();
            List<String> candidates = entry.getValue().items();

            Optional<Item> resolved = candidates.stream()
                    .map(candidate -> resolveCandidate(candidate, itemRegistry))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findFirst();

            if (resolved.isPresent()) {
                ItemStack stack = resolved.get().getDefaultInstance();
                concreteOutputMapping.put(key, stack);
            }
        }
    }

    private ItemStack getChemicalDust(String type) {
        var stack = UnmovableMines.CHEMICAL_DUST_ITEM.get().getDefaultInstance();
        CompoundTag compound = new CompoundTag();
        compound.putString("material", type);
        stack.setTag(compound);
        return stack;
    }

    private Optional<Item> resolveCandidate(String candidate, Registry<Item> itemRegistry) {
        if (candidate.startsWith("#")) {
            TagKey<Item> tag = ItemTags.create(
                    ResourceLocation.parse(candidate.substring(1)));
            return itemRegistry.getTag(tag)
                    .flatMap(holders -> holders.stream()
                            .map(Holder::value)
                            .findFirst());
        } else {
            ResourceLocation loc = ResourceLocation.tryParse(candidate);
            if (loc == null) {
                LOGGER.warn("Invalid item id: {}", candidate);
                return Optional.empty();
            }
            return itemRegistry.containsKey(loc)
                    ? Optional.ofNullable(itemRegistry.get(loc))
                    : Optional.empty();
        }
    }

    public ConcreteOreEntry getConcreteEntry(OreEntry entry) {
        ItemStack stack = concreteOutputMapping.get(entry.name());
        if (stack == null) {
            stack = getChemicalDust(entry.name());
        }
        return new ConcreteOreEntry(stack, entry.rolls());
    }

    public void registerReloadListener() {

    }

    public void loadResources() {
        oreDataLoader.loadResources();
        outputDataLoader.loadResources();
    }

    public OreData getOreData() {
        return oreDataLoader.getSnapshot();
    }

    public OutputData getOutputData() {
        return outputDataLoader.getSnapshot();
    }

    public List<String> getOreNames() {
        return getOreData().ores().keySet().stream().toList();
    }
}
