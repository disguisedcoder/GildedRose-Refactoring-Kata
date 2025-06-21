package com.gildedrose;

import com.gildedrose.itemUpdater.ItemUpdater;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemUpdater.getUpdater(item).update(item);
        }
    }
}

