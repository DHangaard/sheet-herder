package app.enums;

public enum Ability
{
    CHARISMA("CHA"),
    CONSTITUTION("CON"),
    DEXTERITY("DEX"),
    INTELLIGENCE("INT"),
    STRENGTH("STR"),
    WISDOM("WIS");

    private final String value;

    Ability(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static Ability fromValue(String value)
    {
        for (Ability ability : values())
        {
            if (ability.value.equals(value))
            {
                return ability;
            }
        }
        throw new IllegalArgumentException("Unknown ability score value: " + value);
    }
}
