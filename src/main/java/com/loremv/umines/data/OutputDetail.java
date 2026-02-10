package com.loremv.umines.data;

import javax.annotation.Nonnull;
import java.util.List;

public record OutputDetail(
        @Nonnull List<String> items,
        @Nonnull String fallbackType) {
}
