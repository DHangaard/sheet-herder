package app.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ContentHashing
{
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int HEX_CHARS_PER_BYTE = 2;
    private static final int NIBBLE_SHIFT = 4;
    private static final int NIBBLE_MASK = 0xF;
    private static final int HEXADECIMAL_RADIX = 16;
    private static final String COLLECTION_DELIMITER = ",";
    private static final String MAP_ENTRY_SEPARATOR = ":";

    private ContentHashing()
    {
    }

    public static String sha256Hex(String input)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
            return toHex(hashBytes);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalStateException(HASH_ALGORITHM + " algorithm not available", e);
        }
    }

    private static String toHex(byte[] bytes)
    {
        StringBuilder hex = new StringBuilder(bytes.length * HEX_CHARS_PER_BYTE);
        for (byte b : bytes)
        {
            hex.append(Character.forDigit((b >> NIBBLE_SHIFT) & NIBBLE_MASK, HEXADECIMAL_RADIX));
            hex.append(Character.forDigit(b & NIBBLE_MASK, HEXADECIMAL_RADIX));
        }
        return hex.toString();
    }

    public static String normalize(String value)
    {
        return value == null ? "" : value.trim();
    }

    // Use when field is (String) natural key or used for identity/matching
    public static String normalizeLower(String value)
    {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    public static String joinSorted(Collection<String> values)
    {
        if (values == null || values.isEmpty()) return "";
        return values.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.joining(COLLECTION_DELIMITER));
    }

    public static <E> String joinSortedMapped(Collection<E> values, Function<E, String> mapper)
    {
        if (values == null || values.isEmpty()) return "";
        return values.stream()
                .filter(Objects::nonNull)
                .map(mapper)
                .filter(Objects::nonNull)
                .map(String::trim)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.joining(COLLECTION_DELIMITER));
    }

    public static <K extends Enum<K>> String joinSortedEnumMap(Map<K, Integer> map)
    {
        if (map == null || map.isEmpty()) return "";
        return map.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().name()))
                .map(entry -> entry.getKey().name() + MAP_ENTRY_SEPARATOR + (entry.getValue() == null ? "" : entry.getValue()))
                .collect(Collectors.joining(COLLECTION_DELIMITER));
    }
}