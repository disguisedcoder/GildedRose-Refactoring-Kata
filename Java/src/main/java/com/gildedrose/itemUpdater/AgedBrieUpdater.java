package com.gildedrose.itemUpdater;

import com.gildedrose.Item;

class AgedBrieUpdater implements ItemUpdater {
    public void update(Item item) {
        item.sellIn--;
        // Increases by 1 before sell-by date, 2 after
        incrementQuality(item, item.sellIn < 0 ? 2 : 1);
    }

    private void incrementQuality(Item item, int amount) {
        // Quality is capped at 50
        item.quality = Math.min(50, item.quality + amount);
    }
}
