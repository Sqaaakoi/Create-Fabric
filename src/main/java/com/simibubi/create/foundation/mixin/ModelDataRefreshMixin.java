package com.simibubi.create.foundation.mixin;

//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import com.simibubi.create.content.schematics.SchematicWorld;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraftforge.client.model.ModelDataManager;

//@Environment(EnvType.CLIENT) // fabric: forge mixin, unneeded
//@Mixin(ModelDataManager.class)
//public class ModelDataRefreshMixin {

//	/**
//	 * Normally ModelDataManager will throw an exception if a tile entity tries
//	 * to refresh its model data from a world the client isn't currently in,
//	 * but we need that to not happen for tile entities in fake schematic
//	 * worlds, so in those cases just do nothing instead.
//	 */
//	@Inject(at = @At("HEAD"), method = "requestModelDataRefresh", cancellable = true, remap = false)
//	private static void requestModelDataRefresh(BlockEntity te, CallbackInfo ci) {
//		if (te != null) {
//			Level world = te.getLevel();
//			if (world != Minecraft.getInstance().level && world instanceof SchematicWorld)
//				ci.cancel();
//		}
//	}

//}
