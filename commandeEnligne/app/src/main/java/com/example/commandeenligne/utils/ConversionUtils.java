package com.example.commandeenligne.utils;

import java.util.Date;

public class ConversionUtils {
    // Convertit une Date en Long (timestamp)
    public static Long dateToLong(Date date) {
        return date != null ? date.getTime() : null;
    }

    // Convertit un Long (timestamp) en Date
    public static Date longToDate(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    // Convertit un int en Long
    public static Long intToLong(Integer value) {
        return value != null ? value.longValue() : null;
    }

    // Convertit un Long en int, avec gestion des valeurs nulles et dépassement
    public static Integer longToInt(Long value) {
        if (value == null) return null;
        if (value > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (value < Integer.MIN_VALUE) return Integer.MIN_VALUE;
        return value.intValue();
    }
}
