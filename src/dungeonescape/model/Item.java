package dungeonescape.model;

/**
 * One item the player can collect.
 * effectValue means: HP restored (POTION) or attack bonus (WEAPON). 0 for others.
 */
public class Item {

    private int id;
    private String name;
    private ItemType type;
    private String description;
    private int value;
    private int effectValue;

    public Item(int id, String name, ItemType type, String description,
                int value, int effectValue) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.value = value;
        this.effectValue = effectValue;
    }

    public int getId()             { return id; }
    public String getName()        { return name; }
    public ItemType getType()      { return type; }
    public String getDescription() { return description; }
    public int getValue()          { return value; }
    public int getEffectValue()    { return effectValue; }

    // Setters are needed for the linked list's "update" operation
    public void setDescription(String description) { this.description = description; }
    public void setEffectValue(int effectValue)    { this.effectValue = effectValue; }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + description;
    }
}