package cybercat5555.faunus.core.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

public interface FeedableEntity {

    void feedEntity(PlayerEntity player, ItemStack stack);

    boolean canFedWithItem(ItemStack stack);

    boolean hasBeenFed();

    TagKey<Item> getBreedingItemsTag();
}
