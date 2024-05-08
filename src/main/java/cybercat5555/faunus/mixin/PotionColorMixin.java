package cybercat5555.faunus.mixin;

import cybercat5555.faunus.core.PotionRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Collection;

@Mixin(PotionUtil.class)
public class PotionColorMixin {

    @Inject(method = "getColor(Ljava/util/Collection;)I", at = @At("HEAD"), cancellable = true)
    private static void get(Collection<StatusEffectInstance> effects, CallbackInfoReturnable<Integer> cir) {

        if (effects.containsAll(swampCrawlerEffects)) {
            cir.setReturnValue(3114217);
        }
    }

    private static final Collection<StatusEffectInstance> swampCrawlerEffects = Arrays.asList(
        new StatusEffectInstance(StatusEffects.RESISTANCE, 1200),
        new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1200),
        new StatusEffectInstance(StatusEffects.LUCK, 1200),
        new StatusEffectInstance(StatusEffects.HUNGER, 1200),
        new StatusEffectInstance(StatusEffects.WEAKNESS, 1200)
    );
}
