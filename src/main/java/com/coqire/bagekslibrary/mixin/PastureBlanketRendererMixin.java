package com.coqire.bagekslibrary.mixin;

import com.alaharranhonor.swem.forge.entities.horse.SWEMHorseEntity;
import com.alaharranhonor.swem.forge.client.render.SWEMHorseRenderer;
import java.util.Arrays;
import com.alaharranhonor.swem.forge.items.tack.PastureBlanketItem;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

@Mixin({SWEMHorseRenderer.class})
public abstract class PastureBlanketRendererMixin extends ExtendedGeoEntityRenderer<SWEMHorseEntity> {
    private static final String[] PASTURE_BLANKET_SHOW_BONE_NAMES = new String[]{};
    private static final String[] PASTURE_BLANKET_HIDE_BONE_NAMES = new String[]{};
    private static final String[] PASTURE_BLANKET_BONE_NAMES = new String[]{};

    private String boneName;

    //Needed in every mixinclass
    // UNSURE IF THIS WORKS YET
    protected PastureBlanketRendererMixin(EntityRendererProvider.Context renderManager, AnimatedGeoModel<SWEMHorseEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
    @Inject(
            at = @At(value = "HEAD"),
            method = "checkPastureBlanketForRendering",
            cancellable = true,
            remap = false
    )
    private void checkPastureBlanketForRendering(SWEMHorseEntity entity, CallbackInfo info) {
        info.cancel();

        ItemStack stack = entity.getSWEMArmor();
        LivingEntity pLivingEntity = null;
        EquipmentSlot pSlot = null;
        ItemStack itemstack = pLivingEntity.getItemBySlot(pSlot);
        if (stack.getItem() instanceof PastureBlanketItem) {
            Arrays.stream(PASTURE_BLANKET_BONE_NAMES).forEach((boneName) -> {
                this.showBone(boneName, entity);
            });
            this.hideBone("BreastLeft", entity);
            this.hideBone("BreastRight", entity);
        } else {
            Arrays.stream(PASTURE_BLANKET_BONE_NAMES).forEach((boneName) -> {
                this.hideBone(boneName, entity);
            });
            this.showBone("BreastLeft", entity);
            this.showBone("BreastRight", entity);
        }


        this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelLocation(entity)).getBone(boneName).ifPresent((bone) -> {
            bone.setHidden(true);
        });
    }

    private void hideBone(String boneName, SWEMHorseEntity entity) {
    }

    public void showBone(String boneName, SWEMHorseEntity entity) {
        this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelLocation(entity)).getBone(boneName).ifPresent((bone) -> {
            bone.setHidden(false);
        });
    }
}
    

