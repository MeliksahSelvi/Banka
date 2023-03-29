package com.meliksah.banka.app.gen.util;

import com.meliksah.banka.app.gen.enums.GenErrorMessage;
import com.meliksah.banka.app.gen.exception.exceptions.GenBusinessException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

public class NumberUtil {

    public static String getRandomNumberAsString(int charCount) {
        if (charCount < 0) {
            throw new GenBusinessException(GenErrorMessage.VALUE_NOT_BE_NEGATIVE);
        }
        String randomNumeric = RandomStringUtils.randomNumeric(charCount);
        return randomNumeric;
    }

    public static Long getRandomNumber(int charCount) {
        String randomNumeric = getRandomNumberAsString(charCount);

        Long randomLong = null;
        if (StringUtils.hasText(randomNumeric)) {
            randomLong = Long.valueOf(randomNumeric);
        }
        return randomLong;
    }
}
