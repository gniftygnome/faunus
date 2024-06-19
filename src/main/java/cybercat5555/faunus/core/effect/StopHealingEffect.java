package cybercat5555.faunus.core.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class StopHealingEffect extends StatusEffect {

    private float health;

    public StopHealingEffect() {
        super(StatusEffectCategory.HARMFUL, 0x7f7f7f);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        this.health = entity.getHealth();

        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getHealth() >= this.health) {
            entity.setHealth(this.health);
            entity.hurtTime = -1;
        } else {
            this.health = entity.getHealth();
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
