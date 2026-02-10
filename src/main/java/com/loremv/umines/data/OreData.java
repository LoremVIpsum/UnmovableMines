package com.loremv.umines.data;

import java.util.Map;

public record OreData(Map<String, OreDetail> ores) {
    public static final OreData EMPTY = new OreData(Map.of());
}
