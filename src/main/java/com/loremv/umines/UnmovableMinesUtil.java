package com.loremv.umines;

import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UnmovableMinesUtil {
    public static <T> List<T> pickRandom(List<T> list, int n, RandomSource random) {
        List<T> shuffled = new ArrayList<>(list);
        Collections.shuffle(shuffled, new Random(random.nextLong()));
        return shuffled.subList(0, Math.min(n, shuffled.size()));
    }
}
