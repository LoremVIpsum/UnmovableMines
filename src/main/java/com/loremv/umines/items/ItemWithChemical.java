package com.loremv.umines.items;


import com.loremv.umines.UnmovableMines;
import com.loremv.umines.data.Records;
import com.loremv.umines.data.DynamicContentManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class ItemWithChemical extends Item {
    private final String elementName;
    private final Random random;
    private final DynamicContentManager dynamicContentManager;
    public ItemWithChemical(
            Properties settings,
            DynamicContentManager dynamicContentManager,
            String elementName) {
        super(settings);
        this.elementName = elementName;
        this.random = new Random();
        this.dynamicContentManager = dynamicContentManager;
    }

    public String getElementName() {
        return elementName;
    }

    public static Optional<ItemWithChemical> byElementName(String elementName) {
        return UnmovableMines.ORE_ITEMS.stream()
                .map(RegistryObject::get)
                .filter(item -> Objects.equals(item.elementName, elementName))
                .findFirst();
    }

    private List<Records.ConcreteOreEntry> getEntries() {
        var output = dynamicContentManager.getOreData().ores().get(elementName);
        return output.produces().stream().map(e -> e.toConcrete(dynamicContentManager)).toList();
    }

    public List<ItemStack> getOutputItems() {
        return getEntries().stream().map(Records.ConcreteOreEntry::item).toList();
    }

    public Records.ConcreteOreEntry getNextDrop() {
        List<Records.ConcreteOreEntry> possibilities = getEntries();
        if (possibilities.isEmpty()) return null;
        int totalWeight = possibilities.stream().mapToInt(Records.ConcreteOreEntry::rolls).sum();

        int r = random.nextInt(totalWeight);
        for (Records.ConcreteOreEntry drop : possibilities) {
            r -= drop.rolls();
            if (r < 0) return drop;
        }
        throw new IllegalStateException("No drop was chosen.");  // Should be unreachable.
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            @Nullable Level p_41422_,
            @NotNull List<Component> tooltip,
            @NotNull TooltipFlag p_41424_) {
        super.appendHoverText(stack, p_41422_, tooltip, p_41424_);
        for (var dropStack : getOutputItems()) {
            tooltip.add(dropStack.getHoverName());
        }
    }
}
