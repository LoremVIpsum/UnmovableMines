package com.loremv.umines;

import com.loremv.umines.blocks.MiningBE;
import com.loremv.umines.blocks.MiningBlock;
import com.loremv.umines.blocks.ProcessingBE;
import com.loremv.umines.blocks.ProcessingBlock;
import com.loremv.umines.items.ChemicalDustItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionTypeRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnmovableMines implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("umines");

	public static final ItemGroup TAB = FabricItemGroup.builder()
			.icon(() -> new ItemStack(Items.COPPER_INGOT))
			.displayName(Text.of("Unmovable Mines"))
			.build();

	public static final ChemicalDustItem CHEMICAL_DUST = new ChemicalDustItem(new Item.Settings());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registries.ITEM_GROUP, new Identifier("umines", "tab"), TAB);
		Registry.register(Registries.ITEM,new Identifier("umines","chemical_dust"),CHEMICAL_DUST);
		registerBlocks();
		OreUtils.registerOres();
		AltOreLoader.loadMapped();
		LOGGER.info("Hello Fabric world!");


	}


	public static final MiningBlock MINING_BLOCK = new MiningBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(-1.0F, 3600000.0F).dropsNothing());
	public static final ProcessingBlock PROCESSING_BLOCK = new ProcessingBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(-1.0F, 3600000.0F).dropsNothing());

	private void registerBlocks()
	{

		Registry.register(Registries.BLOCK,new Identifier("umines","mining_block"), MINING_BLOCK);
		Registry.register(Registries.BLOCK,new Identifier("umines","processing_block"),PROCESSING_BLOCK);


	}

	public static BlockEntityType<MiningBE> INFORMATION_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier("umines", "mining_block_entity"),
			FabricBlockEntityTypeBuilder.create(MiningBE::new, MINING_BLOCK).build()
	);

	public static BlockEntityType<ProcessingBE> PROCESSING_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier("umines", "processing_block_entity"),
			FabricBlockEntityTypeBuilder.create(ProcessingBE::new,PROCESSING_BLOCK).build()
	);
}