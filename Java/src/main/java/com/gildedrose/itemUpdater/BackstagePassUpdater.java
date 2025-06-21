package com.gildedrose.itemUpdater;

import com.gildedrose.Item;

class BackstagePassUpdater implements ItemUpdater {
    public void update(Item item) {
        item.sellIn--;
        if (item.sellIn < 0) {
            item.quality = 0;
        } else if (item.sellIn < 5) {
            incrementQuality(item, 3);
        } else if (item.sellIn < 10) {
            incrementQuality(item, 2);
        } else {
            incrementQuality(item, 1);
        }
    }

    private void incrementQuality(Item item, int amount) {
        item.quality = Math.min(50, item.quality + amount);
    }
}
