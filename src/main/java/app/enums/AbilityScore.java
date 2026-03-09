package app.enums;

public enum AbilityScore
{
    CHARISMA("CHA"),
    CONSTITUTION("CON"),
    DEXTERITY("DEX"),
    INTELLIGENCE("INT"),
    STRENGTH("STR"),
    WISDOM("WIS");

    private final String value;

    AbilityScore(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static AbilityScore fromValue(String value)
    {
        for (AbilityScore abilityScore : values())
        {
            if (abilityScore.value.equals(value))
            {
                return abilityScore;
            }
        }
        throw new IllegalArgumentException("Unknown ability score value: " + value);
    }
}
