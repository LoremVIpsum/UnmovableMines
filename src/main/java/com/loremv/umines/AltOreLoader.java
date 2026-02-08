package com.loremv.umines;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AltOreLoader {


    public static HashMap<String,String> MAPPED = new HashMap<>();

    public static void loadMapped()
    {
        File file = new File(FabricLoader.getInstance().getConfigDir().toString() + "/umines/ores.txt");
        try {
            List<String> maps  = FileUtils.readLines(file,"utf-8");
            for (String map : maps) {
                String[] v = map.split("=");
                MAPPED.put(v[0],v[1]);
                UnmovableMines.LOGGER.info("Adding ore definition: {}->{}", v[0], v[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
