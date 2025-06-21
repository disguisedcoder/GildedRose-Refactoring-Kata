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
    /**
     * Aged Brie increases in quality by 1 each day before its sell-by date,
     * by 2 each day after the date (double increase),
     * and quality never exceeds 50.
     */
    @Test
    void agedBrie_quality_increases() {
        // Arrange: Aged Brie with sellIn = 2 and quality = 0
        Item[] items = { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);

        // Day 1: sellIn > 0, quality increments by 1
        app.updateQuality();
        assertEquals(1, items[0].sellIn,   "sellIn should decrease by 1 after update");
        assertEquals(1, items[0].quality,  "quality should increase by 1 when sellIn > 0");

        // Day 2: sellIn still >= 0, quality increments by 1 again
        app.updateQuality();
        assertEquals(0, items[0].sellIn,   "sellIn should decrease to 0");
        assertEquals(2, items[0].quality,  "quality should increase by 1 when sellIn == 0");

        // Day 3: sellIn < 0, quality increments by 2 (double increase)
        app.updateQuality();
        assertEquals(-1, items[0].sellIn,  "sellIn becomes negative after passing sell date");
        assertEquals(4, items[0].quality,  "quality should increase by 2 when sellIn < 0");
    }

    /**
     * Aged Brie quality is capped at 50, even if increase rules would push it higher.
     */
    @Test
    void agedBrie_quality_max_50() {
        // Arrange: Aged Brie already at max quality
        Item[] items = { new Item("Aged Brie", 5, 50) };
        GildedRose app = new GildedRose(items);

        // Act: updateQuality should not increase beyond 50
        app.updateQuality();
        assertEquals(50, items[0].quality, "quality cannot exceed 50");
    }

    /**
     * Sulfuras is a legendary item: its sellIn and quality never change.
     */
    @Test
    void sulfuras_never_changes() {
        // Arrange: Sulfuras always has quality = 80
        Item[] items = { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);

        // Act: updateQuality should leave it untouched
        app.updateQuality();

        // Assert: sellIn and quality remain constant
        assertEquals(0, items[0].sellIn,  "sellIn should not change for Sulfuras");
        assertEquals(80, items[0].quality, "quality should not change for Sulfuras");
    }

    /**
     * Duplicate test to emphasize Sulfuras immutability (optional).
     */
    @Test
    void sulfuras_never_changes_again() {
        Item[] items = { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertEquals(0, items[0].sellIn,  "sellIn should stay the same on each call");
        assertEquals(80, items[0].quality, "quality remains constant at 80");
    }

    /**
     * Backstage passes increase in quality as their sellIn approaches:
     * - +1 when more than 10 days left
     * - +2 when 6–10 days left
     * - +3 when 1–5 days left
     * After the concert (sellIn <= 0), quality drops to 0.
     */
    @Test
    void backstage_passes_quality_steps_and_drop() {
        // Arrange: four scenarios for different sellIn values
        Item[] items = {
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20), // >10 days
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20), // =10 days
            new Item("Backstage passes to a TAFKAL80ETC concert", 5,  20), // =5 days
            new Item("Backstage passes to a TAFKAL80ETC concert", 0,  20)  // =0 days (concert)
        };
        GildedRose app = new GildedRose(items);

        // Act & Assert for each case
        app.updateQuality();
        // Case 1: >10 days, quality +1
        assertEquals(21, items[0].quality, "+1 quality when sellIn > 10");

        // Case 2: 10 days, quality +2
        assertEquals(9,  items[1].sellIn,  "sellIn decrements by 1");
        assertEquals(22, items[1].quality, "+2 quality when sellIn <= 10 && > 5");

        // Case 3: 5 days, quality +3
        assertEquals(4,  items[2].sellIn,  "sellIn decrements by 1");
        assertEquals(23, items[2].quality, "+3 quality when sellIn <= 5 && > 0");

        // Case 4: concert day passed, quality drops to 0
        assertEquals(-1, items[3].sellIn, "sellIn decrements below zero");
        assertEquals(0,  items[3].quality, "quality drops to 0 after concert");
    }
    /**
     * A normal item with expired sellIn should never have negative quality.
     */
    @Test
    void normalItem_quality_never_negative_even_after_expiry() {
        // Arrange: Normal item already past sell date with zero quality
        Item[] items = { new Item("Normal Item", -1, 0) };
        GildedRose app = new GildedRose(items);

        // Act: updateQuality should not drop quality below 0
        app.updateQuality();

        // Assert: quality stays at 0
        assertEquals(0, items[0].quality,
            "Quality must remain at 0 even after expiry for normal items");
    }

    /**
     * A conjured item degrades twice as fast but must not go below zero.
     */
    @Test
    void conjured_quality_never_negative() {
        // Arrange: Conjured item at sellIn 0 with quality 1
        Item[] items = { new Item("Conjured Mana Cake", 0, 1) };
        GildedRose app = new GildedRose(items);

        // Act: updateQuality applies double degradation but floors at 0
        app.updateQuality();

        // Assert: quality floors at 0
        assertEquals(0, items[0].quality,
            "Conjured item quality should never become negative");
    }

    /**
     * Backstage passes quality must cap at 50 even when multiple increments apply.
     */
    @Test
    void backstage_quality_never_exceeds_50() {
        // Arrange: Backstage passes within 5 days at quality 49
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49) };
        GildedRose app = new GildedRose(items);

        // Act: updateQuality would add up to +3 but should cap
        app.updateQuality();

        // Assert: quality capped at 50
        assertEquals(50, items[0].quality,
            "Backstage passes quality must not exceed 50");
    }

    /**
     * Expired Aged Brie increases twice as fast but still should not exceed 50.
     */
    @Test
    void agedBrie_expired_increases_double_but_caps() {
        // Arrange: Aged Brie past its sell date with quality 49
        Item[] items = { new Item("Aged Brie", -1, 49) };
        GildedRose app = new GildedRose(items);

        // Act: updateQuality applies double increase but floors at 50
        app.updateQuality();

        // Assert: quality capped at 50
        assertEquals(50, items[0].quality,
            "Expired Aged Brie quality should cap at 50 even with double increment");
    }

    /**
     * An unrecognized item should be treated as a normal item.
     */
    @Test
    void unknownItem_treated_as_normal() {
        // Arrange: Unknown item behaves like a standard item
        Item[] items = { new Item("FooBar", 3, 6) };
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert: quality -1, sellIn -1
        assertEquals(5, items[0].quality,
            "Unknown items should decrease quality by 1 before sellDate");
        assertEquals(2, items[0].sellIn,
            "Unknown items should decrease sellIn by 1");
    }

    /**
     * Backstage passes transition through quality increments:
     * +1 when sellIn > 10, +2 when 6-10, +3 when 1-5.
     */
    @Test
    void backstage_transitions_from_plus1_to_plus2_to_plus3() {
        // Arrange: Backstage passes with 12 days remaining and initial quality 10
        Item[] items = { new Item("Backstage passes to a TAFKAL80ETC concert", 12, 10) };
        GildedRose app = new GildedRose(items);

        // Day 1: sellIn 12->11, quality +1
        app.updateQuality();
        assertEquals(11, items[0].sellIn,
            "Day 1: sellIn should decrement by 1");
        assertEquals(11, items[0].quality,
            "Day 1: quality should increase by +1 when sellIn > 10");

        // Day 2: 11->10, quality +1
        app.updateQuality();
        assertEquals(10, items[0].sellIn,
            "Day 2: sellIn should decrement by 1");
        assertEquals(12, items[0].quality,
            "Day 2: quality should increase by +1 when sellIn still > 10 before update");

        // Day 3: 10->9, quality +2
        app.updateQuality();
        assertEquals(9, items[0].sellIn,
            "Day 3: sellIn should decrement by 1");
        assertEquals(14, items[0].quality,
            "Day 3: quality should increase by +2 when sellIn <= 10 and > 5");

        // Simulate down to 5 days left
        for (int i = 0; i < 4; i++) {
            app.updateQuality();
        }
        assertEquals(5, items[0].sellIn,
            "After simulation: sellIn should reach 5");
        assertEquals(22, items[0].quality,
            "Backstage passes quality should reflect +3 increments when sellIn <= 5");
    }
}
