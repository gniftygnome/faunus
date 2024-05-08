package cybercat5555.faunus.mixin;

import cybercat5555.faunus.core.PotionRegistry;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionUtil.class)
public class PotionColorMixin {

    @Inject(method = "getColor(Lnet/minecraft/potion/Potion;)I", at = @At("HEAD"), cancellable = true)
    private static void get(Potion potion, CallbackInfoReturnable<Integer> cir) {
        if (potion.equals(PotionRegistry.SWAM_CLAWLER_POTION)) {
            cir.setReturnValue(49257);
        }
    }
}
