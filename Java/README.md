# Gilded Rose starting position in Java

## Run the TextTest Fixture from Command-Line

```
./gradlew -q text
```

### Specify Number of Days

For e.g. 10 days:

```
./gradlew -q text --args 10
```

You should make sure the gradle commands shown above work when you execute them in a terminal before trying to use TextTest (see below).


## Run the TextTest approval test that comes with this project

There are instructions in the [TextTest Readme](../texttests/README.md) for setting up TextTest. What's unusual for the Java version is there are two executables listed in [config.gr](../texttests/config.gr) for Java. The first uses Gradle wrapped in a python script. Uncomment these lines to use it:

    executable:${TEXTTEST_HOME}/Java/texttest_rig.py
    interpreter:python

The other relies on your CLASSPATH being set correctly in [environment.gr](../texttests/environment.gr). Uncomment these lines to use it instead:

    executable:com.gildedrose.TexttestFixture
    interpreter:java


## Test Strategy Plan

This test plan outlines the scenarios and edge cases covered by our unit tests:

### 1. Normal Items
- **Before sell-by date**: quality −1 per day, sellIn −1
- **After sell-by date**: quality −2 per day, sellIn −1
- **Quality never < 0**

### 2. Aged Brie
- quality +1 per day when sellIn ≥ 0
- quality +2 per day when sellIn < 0
- **Cap**: quality ≤ 50

### 3. Sulfuras, Hand of Ragnaros
- quality remains constant at 80
- sellIn and quality **never change**

### 4. Backstage passes to a TAFKAL80ETC concert
- **sellIn > 10**: quality +1
- **6 ≤ sellIn ≤ 10**: quality +2
- **1 ≤ sellIn ≤ 5**: quality +3
- **sellIn ≤ 0**: quality = 0
- **Cap**: quality ≤ 50

### 5. Conjured Items
- quality degrades **twice as fast** as normal items
- quality never < 0

### 6. Unknown Items
- treated like normal items (quality −1 / −2, sellIn −1)

### 7. Boundary & Transition Tests
- starting quality at 0 and 50 → remains within [0,50]
- immediate expired items (sellIn < 0) → apply post-expiry rules on first update
- multi-day simulation to verify Backstage passes increments (+1 → +2 → +3)
