package com.meliksah.banka.app.gen.util;

import com.meliksah.banka.app.gen.enums.GenErrorMessage;
import com.meliksah.banka.app.gen.exception.exceptions.GenBusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberUtilTest {

    @Test
    void shouldGetRandomNumber() {
        int charCount = 5;
        Long randomNumber = NumberUtil.getRandomNumber(charCount);
        assertEquals(5, String.valueOf(randomNumber).length());
    }

    @Test
    void shouldGetRandomNumberWhenCharCountIsZero() {
        int charCount = 0;
        Long randomNumber = NumberUtil.getRandomNumber(charCount);
        assertNull(randomNumber);
    }

    @Test
    void shouldGetRandomNumberWhenCharCountTooLong() {
        int charCount = 50;
        assertThrows(NumberFormatException.class, () -> NumberUtil.getRandomNumber(charCount));
    }

    @Test
    void shouldGetRandomNumberAsString() {
        int charCount = 5;
        String randomNumber = NumberUtil.getRandomNumberAsString(charCount);
        assertEquals(5, randomNumber.length());
    }

    @Test
    void shouldNotGetRandomNumberAsStringWhenCharCountIsNegative() {
        int charCount = -1;
        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> NumberUtil.getRandomNumberAsString(charCount));
        assertEquals(genBusinessException.getBaseErrorMessage(), GenErrorMessage.VALUE_NOT_BE_NEGATIVE);
    }

    @Test
    void shouldGetRandomNumberAsStringWhenCharCountIsZero() {
        int charCount = 0;
        String randomNumber = NumberUtil.getRandomNumberAsString(charCount);
        assertEquals("", randomNumber);
    }
}