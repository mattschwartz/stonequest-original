package com.barelyconscious.game.dal;

import com.barelyconscious.game.entity.item.Item;

/**
 * Accessor for item prototype data.
 */
public interface ItemDataManager {

    Item getItem(int itemId);
}
