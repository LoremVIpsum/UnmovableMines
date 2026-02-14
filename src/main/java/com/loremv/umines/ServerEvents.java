package com.loremv.umines;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnmovableMines.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        UnmovableMines.getDynamicContentManager().reloadConcreteOutputMapping(server.registryAccess());
    }
}
