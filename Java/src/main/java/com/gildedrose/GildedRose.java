package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        // Loop through each item in the inventory
        for (int i = 0; i < items.length; i++) {
            // Check if the item is neither "Aged Brie" nor "Backstage passes"
            if (!items[i].name.equals("Aged Brie")
                && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                // Handle normal items
                if (items[i].quality > 0) {
                    // "Sulfuras" is legendary and never decreases in quality
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;  // Decrease quality by 1
                    }
                }
            } else {
                // Handle "Aged Brie" and "Backstage passes"
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;  // Increase quality by 1

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        // If there are 10 days or less until the concert, quality increases by an extra 1
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                        // If there are 5 days or less until the concert, quality increases by yet another 1
                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            // "Sulfuras" does not have to be sold, so its sellIn does not decrease
            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;  // Decrease sellIn by 1 for all other items
            }

            // Once the sellIn date has passed
            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        // For normal items, quality degrades twice as fast after expiration
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;  // Decrease quality by 1 again
                            }
                        }
                    } else {
                        // "Backstage passes" quality drops to 0 after the concert
                        items[i].quality = 0;
                    }
                } else {
                    // "Aged Brie" increases in quality twice as fast after expiration
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}


