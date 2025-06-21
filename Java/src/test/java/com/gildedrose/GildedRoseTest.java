package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    /**
     * Before the sell-by date has passed, a normal item's quality should
     * decrease by 1 each day, and its sellIn value should decrease by 1.
     */
    @Test
    void normalItem_quality_decreases_by_1_before_sellDate() {
        // Arrange: create a normal item with sellIn > 0 and quality > 0
        Item[] items = new Item[]{new Item("Normal Item", 10, 20)};
        GildedRose app = new GildedRose(items);

        // Act: update quality by one day
        app.updateQuality();

        // Assert: sellIn and quality each decreased by 1
        assertEquals(9, items[0].sellIn);
        assertEquals(19, items[0].quality);
    }

    /**
     * After the sell-by date has passed (sellIn <= 0), a normal item's quality
     * should degrade twice as fast (i.e., by 2), and sellIn decreases by 1.
     */
    @Test
    void normalItem_quality_decreases_by_2_after_sellDate() {
        // Arrange: create a normal item with sellIn = 0
        Item[] items = new Item[]{new Item("Normal Item", 0, 20)};
        GildedRose app = new GildedRose(items);

        // Act: update quality by one day
        app.updateQuality();

        // Assert: sellIn decreased by 1, quality decreased by 2
        assertEquals(-1, items[0].sellIn);
        assertEquals(18, items[0].quality);
    }

    /**
     * The quality of an item is never negative. Even after update, quality should
     * remain at 0 if it was already 0.
     */
    @Test
    void normalItem_quality_never_negative() {
        // Arrange: create a normal item with zero quality
        Item[] items = new Item[]{new Item("Normal Item", 5, 0)};
        GildedRose app = new GildedRose(items);

        // Act: update quality by one day
        app.updateQuality();

        // Assert: sellIn still decreases by 1, but quality stays at 0
        assertEquals(4, items[0].sellIn);
        assertEquals(0, items[0].quality);
    }

}
