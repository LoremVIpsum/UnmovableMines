package com.loremv.umines.mixin;

import com.loremv.umines.items.ItemWithChemical;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class FruityLookingOresMixin {
	@Inject(at = @At("HEAD"), method = "render")
	private void init(ItemStack stack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci) {
		if(stack.getItem() instanceof ItemWithChemical item)
		{
			RandomSource random = RandomSource.create(item.getElementName().chars().distinct().sum());
			poseStack.scale(random.nextInt(10,100)/100f,random.nextInt(10,100)/100f,random.nextInt(10,100)/100f);
		}
	}
}