package com.gildedrose.itemUpdater;

import com.gildedrose.Item;

class ConjuredItemUpdater extends NormalItemUpdater {
    @Override
    public void update(Item item) {
        decrementSellIn(item);
        decrementQuality(item, item.sellIn < 0 ? 4 : 2);
    }
}
