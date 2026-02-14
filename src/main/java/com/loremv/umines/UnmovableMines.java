package com.loremv.umines;

import com.loremv.umines.blocks.MiningBE;
import com.loremv.umines.blocks.MiningBlock;
import com.loremv.umines.blocks.ProcessingBE;
import com.loremv.umines.blocks.ProcessingBlock;
import com.loremv.umines.data.DynamicContentManager;
import com.loremv.umines.data.OreDataLoader;
import com.loremv.umines.data.OutputDataLoader;
import com.loremv.umines.items.ChemicalDustItem;
import com.loremv.umines.items.ItemWithChemical;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(UnmovableMines.MODID)
public class UnmovableMines
{
    public static final String MODID = "umines";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final DynamicContentManager DYNAMIC_CONTENT_MANAGER = new DynamicContentManager();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<Block> MINING_BLOCK = BLOCKS.register("mining_block", ()->new MiningBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noLootTable()));
    public static final RegistryObject<Block> PROCESSING_BLOCK = BLOCKS.register("processing_block", ()->new ProcessingBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noLootTable()));

    public static final RegistryObject<Item> PROCESSING_BLOCK_ITEM =
            ITEMS.register("processing_block",
                    () -> new BlockItem(
                            PROCESSING_BLOCK.get(),
                            new Item.Properties()
                    )
            );
    public static final RegistryObject<Item> MINING_BLOCK_ITEM =
            ITEMS.register("mining_block",
                    () -> new BlockItem(
                            MINING_BLOCK.get(),
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> CHEMICAL_DUST_ITEM = ITEMS.register("chemical_dust",()->new ChemicalDustItem(new Item.Properties()));

    public static List<RegistryObject<ItemWithChemical>> ORE_ITEMS;

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,MODID);
    public static final Supplier<BlockEntityType<MiningBE>> MINING_BE = BLOCK_ENTITY_TYPES.register("mining_block_entity",()->
            BlockEntityType.Builder.of(MiningBE::new,MINING_BLOCK.get()).build(null));
    public static final Supplier<BlockEntityType<ProcessingBE>> PROCESSOR_BE = BLOCK_ENTITY_TYPES.register("processor_block_entity",()->
            BlockEntityType.Builder.of(ProcessingBE::new,PROCESSING_BLOCK.get()).build(null));

    public UnmovableMines(FMLJavaModLoadingContext ctx)
    {
        IEventBus modEventBus = ctx.getModEventBus();
        DYNAMIC_CONTENT_MANAGER.loadResources();
        ORE_ITEMS = DYNAMIC_CONTENT_MANAGER.getOreData().ores().entrySet().stream().map(entry -> {
            var name = entry.getKey();
            return ITEMS.register(name, () -> new ItemWithChemical(
                    new Item.Properties(),
                    UnmovableMines.getDynamicContentManager(),
                    name));
        }).toList();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }

    public static DynamicContentManager getDynamicContentManager() {
        return DYNAMIC_CONTENT_MANAGER;
    }
}
