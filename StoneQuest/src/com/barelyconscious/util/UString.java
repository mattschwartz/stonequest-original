package com.barelyconscious.util;

import org.apache.commons.lang3.StringUtils;

public final class UString {

    public static String clamp(String str, int minLength, int maxLength) {
        if (StringUtils.isNotBlank(str)) {
            int clamp = Math.max(
                Math.min(maxLength, str.length()),
                minLength);
            return str.substring(0, clamp);
        }
        return str;
    }

    private UString() {}
}
