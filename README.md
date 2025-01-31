# Magic Wands Minecraft Mod

This mod adds magic wands and spells! The spells can be put in sequences to create unique combinations and powerful wands. There are destructive and utility spells and wands can be used for many purposes

Download from [Github Releases](https://github.com/Dimitris-Toulis/magic-mod/releases/) or [Modrinth](https://modrinth.com/mod/magic-wands)

## Features

### Blocks
- **Wand Editor**: Allows adding a spell to a wand.
  - Make sure the wand has enough capacity and the spell tier is not higher than the wand tier!
  ![image](https://github.com/user-attachments/assets/fa64ff35-83ad-4e55-8133-448bd83a1671)
  ![image](https://github.com/user-attachments/assets/cd36ce45-a684-4c0c-afd0-f936b8b82f2a)

- **Magic Nullifier**: Magic is forbidden!
  - Attempting to cast a wand near it will strike the player with a lightning bolt and force the wand to be dropped.
  - Any nearby wand with spells will be struck by lightning and the spells will be removed and dropped to the ground
    
   ![image](https://github.com/user-attachments/assets/a122d51b-e622-4c24-93dd-3ecfbfbf5aed)

- **Magic Ore**: Some magic dust is hidden in this ore, ready to be used! Find it between y=32 and y=-64


### Items
- **Magic Wand**: The main item!
  - There are 3 tiers: Basic, Powerful, Ultimate
  - Each tier has lower base cooldown, recharge time and more spell capacity than the previous
    
   ![image](https://github.com/user-attachments/assets/0a54493b-a46d-47b6-bcff-29b2cc60b401)
   ![image](https://github.com/user-attachments/assets/8452b6a4-5015-41ad-9a25-cec13c3f90d4)
   ![image](https://github.com/user-attachments/assets/08851803-ccb9-4d9a-a590-fc304c70d51c)
  
- **Magic Dust**: Dust infused with magical properties! Used to craft spells, wands and more
- **Concentrated Magic Dust**: Dust with higher magical potential! Used to craft high-tier spells and wands
  
  ![image](https://github.com/user-attachments/assets/6c565cac-ba55-4d53-8960-235e4e7be9e1)
- **Magic Essence**: Pure magic that is ready to be used to harness all magic! Used to craft an ultimate magic wand
  
  ![image](https://github.com/user-attachments/assets/bb8f0b3f-902a-474f-becf-323eff96a9c7)


### Cast Mechanics
- When a wand is used it casts the next spell (which is highlighted with purple in the inventory tooltip)
- Then, it cools down for a base delay (of the wand) plus the cast delay of the spell
- After casting all spells it will recharge for the maximum of the wand recharge time and the cooldown
- Some spells may alter the casting order and these rules

### Spells
- **Fireball Spell** (Tier 1): Creates a fireball than explodes and sets blocks on fire. Power scales with wand tier
  
  ![image](https://github.com/user-attachments/assets/cb00ada3-9d0d-428f-bd7e-d35f6436c754)
- **Exploding Arrow Spell** (Tier 1): Shoots an arrow that explodes on impact. Power scales with wand tier
  
  ![image](https://github.com/user-attachments/assets/ab376466-fcbc-4565-b3ab-96175b0500fc)
- **Reduce Recharge Spell** (Tier 1): Reduces the recharge time by 7 ticks. Can stack

  ![image](https://github.com/user-attachments/assets/0c9f444b-217c-4b22-8c72-b0b9778807eb)
- **Random Potion Spell** (Tier 1): Throws a random splash potion

  ![image](https://github.com/user-attachments/assets/f8abf159-11b8-4292-b943-0d1124d370a7)
- **Continue Spell** (Tier 1): After the previous spell is cast, the next spell is cast immediately. The total cooldown is the sum of the spell cooldowns

  ![image](https://github.com/user-attachments/assets/582d7a70-0ea1-4a83-ad1a-1d19cc07243c)
- **Teleport Spell** (Tier 2): Teleports the player to block pointed by the mouse if it is within 5 times the interaction range. Otherwise, it teleports the player 5 blocks ahead

  ![image](https://github.com/user-attachments/assets/49b01d24-69cc-4bd7-9fb4-5f9a66e9af4e)
- **Cast All Spell** (Tier 2): Casts all spells in wand, waiting for the cooldown between them. If a continue spell is found, casting stops after it finishes

  ![image](https://github.com/user-attachments/assets/329a9f7e-4f33-4ba2-b6da-2a7b49df7344)
- **No Cooldown Spell** (Tier 3): Removes all cooldown for the remaining spells. Recharge time is unaffected

  ![image](https://github.com/user-attachments/assets/30aba32b-89c2-4b1a-88d9-50cce22dc71d)
- **Lightning Bolt Spell** (Tier 3): Casts a lightning bolt at the block pointed by the mouse if it is within 15 times the interaction range. Otherwise, it casts it 5 blocks from the player

  ![image](https://github.com/user-attachments/assets/fec5c88d-55a0-4ec7-879d-f02d4aa9b054)






