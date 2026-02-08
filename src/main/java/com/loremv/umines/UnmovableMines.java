package com.loremv.umines;

import com.loremv.umines.blocks.MiningBE;
import com.loremv.umines.blocks.MiningBlock;
import com.loremv.umines.blocks.ProcessingBE;
import com.loremv.umines.blocks.ProcessingBlock;
import com.loremv.umines.items.ChemicalDustItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(UnmovableMines.MODID)
public class UnmovableMines
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "umines";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> MINING_BLOCK = BLOCKS.registerBlock("mining_block", a->new MiningBlock(a.strength(-1.0F, 3600000.0F).noLootTable()));
    public static final DeferredBlock<Block> PROCESSING_BLOCK = BLOCKS.registerBlock("processing_block", a->new ProcessingBlock(a.strength(-1.0F, 3600000.0F).noLootTable()));


    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> CHEMICAL_DUST_ITEM = ITEMS.register("chemical_dust",()->new ChemicalDustItem(new Item.Properties()));

    public static boolean haveOresLoaded = OreUtils.registerOres(ITEMS);

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.umines")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CHEMICAL_DUST_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(CHEMICAL_DUST_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());


    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,MODID);
    public static final Supplier<BlockEntityType<MiningBE>> MINING_BE = BLOCK_ENTITY_TYPES.register("mining_block_entity",()->
            BlockEntityType.Builder.of(MiningBE::new,MINING_BLOCK.get()).build(null));
    public static final Supplier<BlockEntityType<ProcessingBE>> PROCESSOR_BE = BLOCK_ENTITY_TYPES.register("processor_block_entity",()->
            BlockEntityType.Builder.of(ProcessingBE::new,PROCESSING_BLOCK.get()).build(null));

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public UnmovableMines(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        AltOreLoader.loadMapped();

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        BLOCK_ENTITY_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        //NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == EXAMPLE_TAB.getKey())
        {
            for(String key: OreUtils.REGISTRY.keySet())
            {
                event.accept(OreUtils.REGISTRY.get(key));
            }
        }
    }

}
