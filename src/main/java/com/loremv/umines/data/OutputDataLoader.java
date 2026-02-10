package com.loremv.umines.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class OutputDataLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private static volatile OutputData SNAPSHOT = OutputData.EMPTY;

    public OutputDataLoader() {
        super(GSON, "output");
    }

    public OutputData getSnapshot() {
        return SNAPSHOT;
    }

    public void loadResources() {
        Map<ResourceLocation, JsonElement> data = ResourceScanner.scanResources("output");
        this.apply(data, null, null);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons,
                         @Nullable ResourceManager manager,
                         @Nullable ProfilerFiller profiler)
    {
        Map<String, OutputDetail> parsed = new HashMap<>();
        for (var entry : jsons.entrySet()) {
            var detail = GSON.fromJson(entry.getValue(), OutputDetail.class);
            var name = entry.getKey().getPath();
            parsed.put(name, detail);
        }
        SNAPSHOT = new OutputData(parsed);
    }
}
