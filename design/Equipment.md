# Equipment Design

Each piece of equipment has the following chase properties:
1. Item Base:
    also the type of item, such as plate body armor, simple dagger.
2. Stat modifiers:
    items may provide additional bonuses to stats when equipped
3. Bond:
    represents how much skill a hero has accrued by using the equipment 

While a hero is wearing equipment, **bond** is built between the hero and the equipment. Bonds function like proficiency which improves the item's properties while equipped by that particular hero. Bonds do not transfer between heroes or items; a bond is a unique pairing of hero and item. 

The level of an item determines how many bonding levels it has. An item lower than level 10 will not have any bonding levels. At level 10, items have 5 bonding levels. At level 20, 10 bonding levels. At level 25, an item will have 10 bonding levels and can become *soulbound*.  

**Soulbinding**

At bonding-level 10, the hero can *soulbind* the item. This permanently locks the item to that hero. The hero can switch items that are soulbound, but the soulbdoun item can no longer be transferred to other heroes. Soulbinding unlocks *eldritch crafting*.

**Eldritch Crafting**

When an item becomes soulbound, a hero unlocks eldritch crafting with that item. This allows for creating new and interesting abilities that extend the functionality of the item to some cosmic power. Elditch abilities are unique per hero and item and are extremely powerful. 


**How is bond leveled?**

Heroes will gain bonding levels while gaining experience using that item (from killing enemies or completing jobs). The first 5 levels don't take as long as the last 5. It takes about as long to get from 5-6 as it does from 1-5.  

### References

*Equipment chase*

The player wants to find a great item base with great crafted stat modifiers, with max level bond, and soulbound with great eldritch sockets. Doing all this will take a lot of time per item, but will encourage the player to invest in powerful items with somewhat deterministic outcomes. 

*As an example of good pacing for bond-leveling:*

> A hero gets a level 10 item. By the time they find an upgrade item, they are roughly at level 3 bonding which would make it a tough choice for the player to choose. Basically a level 10 item with 3 bonding should be only slightly worse than a level 13 item, with 5 bonding be better than a level 14 item. It should fall off after bonding 3.

| Item Level | 10 | 11 | 12 | 13 | 14 | 15 |
|---|---|---|---|---|---|---|
| 10 |  | 1 | 2 | 3 | 5 |  |
| 11 |  |  | 1 | 2 | 3 | 5 |
| 12 |  |  |  | 1 | 2 | 3 |
| 13 |  |  |  |  | 1 | 2 |
| 14 |  |  |  |  |  | 1 |


