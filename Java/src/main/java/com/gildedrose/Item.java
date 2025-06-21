package com.gildedrose;

public class Item {

    /** The display name of the item, e.g., "Aged Brie" or "foo". */
    public String name;

    /** The number of days we have to sell the item. */
    public int sellIn;

    /** The quality of the item, typically between 0 and 50 (except for Sulfuras which is 80). */
    public int quality;

    /**
     * Creates a new Item.
     *
     * @param name    The name of the item.
     * @param sellIn  Days remaining to sell the item.
     * @param quality The initial quality of the item.
     */
    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    /**
     * Returns a readable string representation of the item,
     * e.g., "Aged Brie, 5, 10".
     *
     * @return A string in the format "name, sellIn, quality".
     */
    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
