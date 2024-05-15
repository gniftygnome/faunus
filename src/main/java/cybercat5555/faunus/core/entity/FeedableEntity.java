package cybercat5555.faunus.core.entity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

public interface FeedableEntity {

    void feedEntity(ItemStack stack);

    boolean canFedWithItem(ItemStack stack);

    boolean hasBeenFed();

    TagKey<Item> getBreedingItemsTag();
}
