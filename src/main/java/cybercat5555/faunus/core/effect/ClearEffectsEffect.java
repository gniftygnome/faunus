package cybercat5555.faunus.core.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ClearEffectsEffect extends StatusEffect {

    public ClearEffectsEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x7f7f7f);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.clearStatusEffects();
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.clearStatusEffects();
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
