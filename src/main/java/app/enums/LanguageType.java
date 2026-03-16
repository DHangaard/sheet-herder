package app.enums;

public enum LanguageType
{
    STANDARD("Standard"),
    EXOTIC("Exotic");

    private final String value;

    LanguageType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static LanguageType fromValue(String value)
    {
        for (LanguageType languageType : values())
        {
            if (languageType.value.equals(value))
            {
                return languageType;
            }
        }
        throw new IllegalArgumentException("Unknown language type value: " + value);
    }
}
