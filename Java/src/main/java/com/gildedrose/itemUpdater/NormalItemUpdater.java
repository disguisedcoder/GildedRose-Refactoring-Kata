package com.gildedrose.itemUpdater;

import com.gildedrose.Item;

class NormalItemUpdater implements ItemUpdater {
    public void update(Item item) {
        decrementSellIn(item);
        decrementQuality(item, item.sellIn < 0 ? 2 : 1);
    }

    protected void decrementSellIn(Item item) {
        item.sellIn--;
    }

    protected void decrementQuality(Item item, int amount) {
        item.quality = Math.max(0, item.quality - amount);
    }
}
