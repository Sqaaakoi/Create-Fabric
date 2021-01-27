package com.simibubi.create.content.contraptions.base;

import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.foundation.render.InstancedTileRenderDispatcher;
import com.simibubi.create.foundation.render.instancing.InstancedModel;
import com.simibubi.create.foundation.render.instancing.InstancedTileRenderRegistry;
import com.simibubi.create.foundation.render.instancing.RotatingData;
import net.minecraft.tileentity.TileEntityType;

public class ShaftlessCogInstance extends SingleRotatingInstance {
    public static void register(TileEntityType<? extends KineticTileEntity> type) {
        InstancedTileRenderRegistry.instance.register(type, ShaftlessCogInstance::new);
    }

    public ShaftlessCogInstance(InstancedTileRenderDispatcher modelManager, KineticTileEntity tile) {
        super(modelManager, tile);
    }

    @Override
    protected InstancedModel<RotatingData> getModel() {
        return AllBlockPartials.SHAFTLESS_COGWHEEL.renderOnRotating(modelManager, tile.getBlockState());
    }
}
