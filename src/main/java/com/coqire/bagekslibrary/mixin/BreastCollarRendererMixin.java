package com.coqire.bagekslibrary.mixin;

import com.alaharranhonor.swem.forge.client.render.SWEMHorseRenderer;
import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.items.tack.BreastCollarItem;
import java.util.Arrays;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

@Mixin(SWEMHorseRenderer.class)
public abstract class BreastCollarRendererMixin extends ExtendedGeoEntityRenderer<SWEMHorseEntity> {
    private static final String[] BREAST_COLLAR_SHOW_BONE_NAMES = new String[]{};
    private static final String[] BREAST_COLLAR_HIDE_BONE_NAMES = new String[]{};


    //Needed in every mixinclass
    protected BreastCollarRendererMixin(EntityRendererProvider.Context renderManager, AnimatedGeoModel<SWEMHorseEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
    @Inject(
            at = {@At("HEAD")},
            method = {"checkBreastCollarForRendering"},
            cancellable = true,
            remap = false
    )
    private void checkBreastCollarForRendering(SWEMHorseEntity entity, CallbackInfo info) {
        info.cancel();
        ItemStack stack = entity.getBreastCollar();
        if (stack.getItem() instanceof BreastCollarItem) {
            Arrays.stream(BREAST_COLLAR_SHOW_BONE_NAMES).forEach((n) -> {
                this.showBone(n, entity);
            });
        } else {
            Arrays.stream(BREAST_COLLAR_HIDE_BONE_NAMES).forEach((n) -> {
                this.hideBone(n, entity);
            });
        }

    }
    public void hideBone(String boneName, SWEMHorseEntity entity) {
        this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelLocation(entity)).getBone(boneName).ifPresent((bone) -> {
            bone.setHidden(true);
        });
    }
    public void showBone(String boneName, SWEMHorseEntity entity) {
        this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelLocation(entity)).getBone(boneName).ifPresent((bone) -> {
            bone.setHidden(false);
        });
    }
}
