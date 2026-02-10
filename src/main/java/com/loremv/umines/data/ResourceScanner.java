package com.loremv.umines.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.loremv.umines.UnmovableMines.MODID;

public class ResourceScanner {
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Scans a directory in mod resources and returns all JSON files as a map.
     * Path format: "data/mymod/ores" -> scans /data/mymod/ores/*.json
     * ResourceLocation keys will be "mymod:ores/filename" (without .json)
     */
    public static Map<ResourceLocation, JsonElement> scanResources(String dataPath) {

        Map<ResourceLocation, JsonElement> results = new HashMap<>();
        String resourcePath = "/data/" + MODID + "/" + dataPath;

        try {
            URL dirUrl = ResourceScanner.class.getResource(resourcePath);
            if (dirUrl == null) {
                LOGGER.warn("Resource directory not found: {}", resourcePath);
                return results;
            }

            URI uri = dirUrl.toURI();

            // Handle both IDE (file://) and jar (jar://) environments
            if (uri.getScheme().equals("jar")) {
                scanJar(uri, resourcePath, MODID, results);
            } else {
                scanFilesystem(Paths.get(uri), MODID, results);
            }

        } catch (Exception e) {
            LOGGER.error("Failed to scan resource directory: {}", resourcePath, e);
        }

        return results;
    }

    private static void scanJar(URI jarUri, String resourcePath,
                                String modId,
                                Map<ResourceLocation, JsonElement> results) throws Exception {

        try (FileSystem fs = FileSystems.newFileSystem(jarUri, Map.of())) {
            Path dirPath = fs.getPath(resourcePath);
            collectJsonFiles(dirPath, modId, results);
        }
    }

    private static void scanFilesystem(Path dirPath, String modId,
                                       Map<ResourceLocation, JsonElement> results) throws Exception {
        collectJsonFiles(dirPath, modId, results);
    }

    private static void collectJsonFiles(Path dirPath, String modId,
                                         Map<ResourceLocation, JsonElement> results) throws Exception {

        if (!Files.exists(dirPath)) return;

        try (Stream<Path> paths = Files.walk(dirPath)) {
            paths.filter(p -> p.toString().endsWith(".json"))
                    .forEach(p -> loadJsonFile(p, dirPath, modId, results));
        }
    }

    private static void loadJsonFile(Path file, Path baseDir,
                                     String modId,
                                     Map<ResourceLocation, JsonElement> results) {
        try (Reader reader = Files.newBufferedReader(file)) {
            JsonElement json = JsonParser.parseReader(reader);

            // Build ResourceLocation from relative path
            // e.g. "galena.json" -> "mines:ores/galena"
            String relative = baseDir.relativize(file)
                    .toString()
                    .replace('\\', '/')
                    .replace(".json", "");

            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modId, relative);
            results.put(id, json);

        } catch (Exception e) {
            LOGGER.error("Failed to parse JSON file: {}", file, e);
        }
    }
}
