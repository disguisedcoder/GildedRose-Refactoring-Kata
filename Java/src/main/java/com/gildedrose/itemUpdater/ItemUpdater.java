package com.gildedrose.itemUpdater;

import com.gildedrose.Item;

public interface ItemUpdater {
    void update(Item item);

    static ItemUpdater getUpdater(Item item) {
        if (item.name.equals("Aged Brie")) return new AgedBrieUpdater();
        if (item.name.equals("Sulfuras, Hand of Ragnaros")) return new SulfurasUpdater();
        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) return new BackstagePassUpdater();
        if (item.name.startsWith("Conjured")) return new ConjuredItemUpdater();
        return new NormalItemUpdater();
    }
}
