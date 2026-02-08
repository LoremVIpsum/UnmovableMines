package com.loremv.umines;

import com.loremv.umines.blocks.MiningBE;
import com.loremv.umines.blocks.MiningBlock;
import com.loremv.umines.blocks.ProcessingBE;
import com.loremv.umines.blocks.ProcessingBlock;
import com.loremv.umines.items.ChemicalDustItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> MINING_BLOCK = BLOCKS.register("mining_block", ()->new MiningBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> PROCESSING_BLOCK = BLOCKS.register("processing_block", ()->new ProcessingBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noLootTable()));


    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> CHEMICAL_DUST_ITEM = ITEMS.register("chemical_dust",()->new ChemicalDustItem(new Item.Properties()));

    public static boolean haveOresLoaded = OreUtils.registerOres(ITEMS);



    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,MODID);
    public static final Supplier<BlockEntityType<MiningBE>> MINING_BE = BLOCK_ENTITY_TYPES.register("mining_block_entity",()->
            BlockEntityType.Builder.of(MiningBE::new,MINING_BLOCK.get()).build(null));
    public static final Supplier<BlockEntityType<ProcessingBE>> PROCESSOR_BE = BLOCK_ENTITY_TYPES.register("processor_block_entity",()->
            BlockEntityType.Builder.of(ProcessingBE::new,PROCESSING_BLOCK.get()).build(null));

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public UnmovableMines()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        AltOreLoader.loadMapped();

        BLOCKS.register(modEventBus);

        ITEMS.register(modEventBus);

        BLOCK_ENTITY_TYPES.register(modEventBus);


    }



}
