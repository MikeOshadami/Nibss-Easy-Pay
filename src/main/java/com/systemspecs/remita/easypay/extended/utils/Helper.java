package com.systemspecs.remita.easypay.extended.utils;

import com.systemspecs.remita.easypay.extended.config.EasypayProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class Helper {

    @Getter
    private final EasypayProperties properties;

    public Helper(EasypayProperties properties) {
        this.properties = properties;
    }

    public static String formatXML(String originalXML) {

        return originalXML
            .replaceAll("&gt;", ">").replaceAll("&lt;", "<")
            .replaceAll("><", ">\n<").replaceAll("xmlns", "\nxmlns");
    }

    public static String getRandomNumber() {
        String x = "";
        final SecureRandom rnd = new SecureRandom();

        x += rnd.nextInt(90000) + 100000;
        x += rnd.nextInt(90000) + 100000;

        return x;
    }

    public static String randomUUIDString() {
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }

    public static String randomStringWithLenght(int length) {
        String randomStr = UUID.randomUUID().toString();
        while (randomStr.length() < length) {
            randomStr += UUID.randomUUID().toString();
        }
        return randomStr.substring(0, length);
    }

    public static final boolean isDouble(String amount) {
        //Pattern pattern =Pattern.compile(“^[-+]?\\d+(\\.{0,1}(\\d+?))?$”);
        Pattern pattern = Pattern.compile("^[-+]?\\d+(\\.{0,1}(\\d{0,2}))?$");
        Matcher matcher = pattern.matcher(amount);
        boolean result = matcher.matches();
        return result;

    }

    public static String convertKoboToNaira(String amountInKobo) {
        if (!(amountInKobo.equals("")) || !(amountInKobo.equals(null))) {
            Double amountInNaira = (Integer.valueOf(amountInKobo) / 100.00);
            return String.valueOf(amountInNaira);
        }
        return "";
    }

    public String generateNIBSS_SessionID() {
        return generateNIBSS_SessionID(properties.getInstitutionCode());
    }

    public String generateNIBSS_SessionID(String institutionCode) {
        /*
         Char 1 – 6: Senders Financial Institution’s code (999049)
         Char 7 – 18: Date and time (in the format yymmddHHmmss – HH is 24 hour clock) (160915131900)
         Char 19 – 30: 12 – character unique number (either serial # or random number) (000000000001)
         */

        final String _INSTITUTION_CODE = institutionCode;
        log.info("_INSTITUTION_CODE {} ", _INSTITUTION_CODE);
        return _INSTITUTION_CODE + getCurrentTimeStamp() + getRandomNumber();
    }

    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmss");
        String strDate = sdfDate.format(new Date());

        return strDate;
    }


}
