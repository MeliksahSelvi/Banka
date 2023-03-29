package com.meliksah.banka.app.gen.util;

import com.meliksah.banka.app.gen.enums.GenErrorMessage;
import com.meliksah.banka.app.gen.exception.exceptions.GenBusinessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateUtilTest {

    private static SimpleDateFormat formatterLocalDate;
    private static SimpleDateFormat formatterDateTime;

    @BeforeAll
    static void setUp() {
        formatterLocalDate = new SimpleDateFormat("dd-MM-yyyy");
        formatterDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    @Test
    void shouldConvertToLocalDate() throws ParseException {
        Date date = formatterLocalDate.parse("21-08-1997");
        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(21, localDate.getDayOfMonth());
        assertEquals(8, localDate.getMonthValue());
        assertEquals(1997, localDate.getYear());
    }

    /*
     *Burada şunu ifade ettik. convertToLocalDate Parametre olarak null verildiğinde GenBusinessException hatası bekliyorum.
     * Eğer convertToLocalDate methodundan GenBusinessException hatası dönerse testimiz beklediğimiz gibi başarılıdır.
     * Beklediğimiz hata gelmezse veya Farklı bir hata alırsak testimiz başarısızdır.Burada test ettiğimiz şey convertToLocalDate
     * methoduna null parametresi geldiğinde bizim beklediğimiz gibi GenBusinessException gelecek mi?Methodumuzun isimlendirmesini de
     * ona göre verdik.assertEquals methodunda ise hata mesajını da kontrol edebiliyoruz.İkisi de çalışıyor.
     * */
    @Test
    void shouldNotConvertToLocalDateWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> DateUtil.convertToLocalDate(null));
        assertEquals(genBusinessException.getBaseErrorMessage(), GenErrorMessage.DATE_NOT_TO_BE_CONVERTED);
    }

    @Test
    void shouldConvertToLocalDateWhen29Feb() throws ParseException {
        Date date = formatterLocalDate.parse("29-02-2016");
        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(29, localDate.getDayOfMonth());
        assertEquals(2, localDate.getMonthValue());
        assertEquals(2016, localDate.getYear());
    }

    @Test
    void shouldConvertToLocalDateWhen01Jan() throws ParseException {
        Date date = formatterLocalDate.parse("01-01-2023");
        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(1, localDate.getDayOfMonth());
        assertEquals(1, localDate.getMonthValue());
        assertEquals(2023, localDate.getYear());
    }

    @Test
    void shouldConvertToLocalDateWhen31Dec() throws ParseException {
        Date date = formatterLocalDate.parse("31-12-2023");
        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(31, localDate.getDayOfMonth());
        assertEquals(12, localDate.getMonthValue());
        assertEquals(2023, localDate.getYear());
    }

    @Test
    void shouldConvertToLocalDateTime() throws ParseException {
        Date date = formatterDateTime.parse("21-08-1997 10:11:12");
        LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(date);

        assertEquals(21, localDateTime.getDayOfMonth());
        assertEquals(8, localDateTime.getMonthValue());
        assertEquals(1997, localDateTime.getYear());
        assertEquals(10, localDateTime.getHour());
        assertEquals(11, localDateTime.getMinute());
        assertEquals(12, localDateTime.getSecond());
    }

    @Test
    void shouldNotConvertToLocalDateTimeWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> DateUtil.convertToLocalDateTime(null));
        assertEquals(genBusinessException.getBaseErrorMessage(), GenErrorMessage.DATE_NOT_TO_BE_CONVERTED);
    }

    @Test
    void shouldConvertToLocalDateTimeWhenTimeIs000000() throws ParseException {
        Date date = formatterDateTime.parse("21-08-1997 00:00:00");
        LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(date);

        assertEquals(21, localDateTime.getDayOfMonth());
        assertEquals(8, localDateTime.getMonthValue());
        assertEquals(1997, localDateTime.getYear());
        assertEquals(0, localDateTime.getHour());
        assertEquals(0, localDateTime.getMinute());
        assertEquals(0, localDateTime.getSecond());
    }

    @Test
    void shouldConverttoLocalDateTimeWhenTimeIs235959() throws ParseException {
        Date date = formatterDateTime.parse("21-08-1997 23:59:59");
        LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(date);

        assertEquals(21, localDateTime.getDayOfMonth());
        assertEquals(8, localDateTime.getMonthValue());
        assertEquals(1997, localDateTime.getYear());
        assertEquals(23, localDateTime.getHour());
        assertEquals(59, localDateTime.getMinute());
        assertEquals(59, localDateTime.getSecond());
    }

    @Test
    void shouldConvertToDate() {
        LocalDate localDate = LocalDate.of(1997, 8, 21);

        Date date = DateUtil.convertToDate(localDate);

        String format = formatterLocalDate.format(date);

        assertEquals("21-08-1997", format);
    }

    @Test
    void shouldNotConvertToDateWhenParameterIsNull() {

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class, () -> DateUtil.convertToDate(null));

        assertEquals(genBusinessException.getBaseErrorMessage(), GenErrorMessage.DATE_NOT_TO_BE_CONVERTED);
    }

}