package com.loremv.umines.data;

import java.util.Map;

public record OutputData(Map<String, OutputDetail> outputs) {
    public static final OutputData EMPTY = new OutputData(Map.of());
}
