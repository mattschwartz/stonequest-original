# Item design 

An item has the following descriptors: id, name, description. 

**Item Property**: can modify stats and traits when an item is used or equipped. Other type of on-use/on-equip effects may be found as well.
- WeaponDamageProperty(minDamage, maxDamage, weaponSped)

**Item Requirement**: can be hero level requirements, trait requireemnts, stat requirements, that determine whether or not a hero can use/equip the item

**Item Tags**: describe how an item functions in the game world
    - Consumable, Potable, Edible
    - Craftable, water source, heat source, etc
    - Equipment, helm, body, etc
    - Resource, natural, cloth, ore, etc
    - Stackable

**Quality**: improves the values on the item with higher quality

```json
{
    "id": 1,
    "name": "Iron Shield",
    "description": "A basic shield made of iron",
    "sprite": "BASIC_SHIELD",
    "level": 1,
    "properties": [
	{
	    "__type": "StatProperty",
	    "stat": "ARMOR",
	    "value": 5
	}
    ],
    "tags": ["EQUIPMENT_OFFHAND"]
}
```

## Resources

Worlds has the following classes of resources:
1. Natural
2. Eldritch
3. Tech
4. Synthetic

### Natural
1. Minerals
    - Silicon: found from sand, quartz, chalcedony, amethyst, agate, volcanic rocks
    - Aluminum: Bauxite, (less commonly) cryolite, feldspar, mica, and kaolinite
    - Gold, Silver
    - Tin, Copper, Zinc, Nickel, Lithium
    - Iron, Carbon
2. Precious stones
    - Quartz, chalcedony, amethyst, agate
    - Ruby, Sapphire, Diamond
3. Wood
    - Oak, Maple, Yew
4. Obsidian/Volcanic rock
5. Plants
    - Cotton, silk, wool
6. Animal
    - Leather
    - Bone
    - Meat: Beef/poultry/edible stuff
    - Intestines/sinew/craftable stuff

### Eldritch
1. Worldblood

### Tech
1. Circuitry
    - Motherboards, graphics cards, memory, CPU
    - Networking like wifi, bluetooth
2. Storage
    - SSD/HDD
3. Power Supplies
4. Peripherals (input/output)
    - Screens
    - Mouse, keyboards, controllers, capacitive touch

### Synthetic
1. Acids
    - Hydrochloric acid used to refine silicon
    - Sodium hydroxide (caustic soda) used to refine bauxite into aluminum
2. Reducers
    - Hydrogen, magnesium used to refine silicon



# Items

## Weapons

**Bows**
- Short Bow
    - 5-13 damage
    - 0.67s
- Hunter's bow
    - 2-6 damage
    - 1.0s
- Long bow
    - 8-33 damage
    - 0.8s
- Composite bow
    - 16-34 damage
    - 0.8s
- Short battle bow
    - 5-11 damage
    - 0.9s
- Long battle bow
    - 3-18 dmg
    - 1.0s
- Short war bow
    - 6-14 dmg
    - 0.8s
- Long war bow
    - 3-23 damage
    - 1.0s

**Crossbows**
- Light xbow
    - 6-9 dmg
    - 1.2s
- Heavy xbow
    - 14-26 dmg
    - 1.5s
- Repeating xbow
    - 6-12 dmg
    - 1.0s

**Maces**
- Club
    - 6-8 damage
    - 0.7s
- War hammer
    - 13-31 damage
    - 0.7s
- Bladed mace
    - 19-32
    - 0.7s
- Scepter
    - 11-17 damage
    - 0.8s
- Maul
    - 27-49
    - 0.77s
- Mallet
    - 16-33 dmg
    - 0.77s

**Daggers**
- Skinning knnife
- Stiletto
- Flaying knife
- Poignard
- Gutting knife
- Kris
- Skean

**Axes**
- Hatchet
- Broad axe
- Arming axe
- War axe
- Double axe

**Sword**
- Copper sword
- Sabre
- Broad sword
- War sword
- Cutlass
- Battle sword
- Gladius
- Foil
- Rapier

**2-H sword**
- Longsword
- Bastard sword
- Greatsword

## Armor

**Belts**
- Belt
- Heavy belt
- Plated belt

**Shields**
- Buckler
- Round Shield
- Large shield
- Kite shield
- Tower shield

**Body armor**
- Leather armor
- Studded leather armor
- Ring mail
- Scale mail
- Breast plate
- Chain mail
- Light plate
- Plate Mail

**Boots**
- Hide boots
- Heavy leather boots
- Chain boots
- Light plated boots
- Greaves

**Gloves**
- Hide gloves
- Heavy leather gloves
- Chain gloves
- Light gauntlets
- Plate gauntlets

**Helmets**
- Cap
- Helm
- Full helm
