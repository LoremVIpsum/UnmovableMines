package com.loremv.umines;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AltOreLoader {


    public static HashMap<String,String> MAPPED = new HashMap<>();

    public static void loadMapped()
    {
        File file = new File("./config/umines/ores.txt");

        try {
            if(!file.exists())
            {
                FileUtils.writeLines(file,List.of());
            }
            List<String> maps  = FileUtils.readLines(file,"utf-8");
            for (String map : maps) {
                String[] v = map.split("=");
                MAPPED.put(v[0],v[1]);
                //System.out.println("Adding ore definition: {}->{}", v[0], v[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
