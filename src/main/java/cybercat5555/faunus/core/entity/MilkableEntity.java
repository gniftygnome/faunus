package cybercat5555.faunus.core.entity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

public interface MilkableEntity {
    static final int MILK_RECHARGE_TIME = 20 * 60;

    int milkRechargeTime = 0;

    void milk();

    boolean canBeMilked();

    boolean hasBeenMilked();

    ItemStack getMilkItem();
}