package com.loremv.umines.data;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class Records {
    public record OreData(Map<String, OreDetail> ores) {
        public static final OreData EMPTY = new OreData(Map.of());
    }

    public record OreDetail(List<OreEntry> produces) {
    }

    public record OreEntry(String name, int rolls) {
        public ConcreteOreEntry toConcrete(DynamicContentManager dynamicContentManager) {
            return dynamicContentManager.getConcreteEntry(this);
        }
    }

    public record OutputData(Map<String, OutputDetail> outputs) {
        public static final OutputData EMPTY = new OutputData(Map.of());
    }

    public record OutputDetail(
            @Nonnull List<String> items,
            @Nonnull String fallbackType) {
    }

    public record ConcreteOreEntry(ItemStack item, int rolls) {
    }
}
