package com.wso2telco.tip.balance.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yasith on 1/26/17.
 */
public class Validator {

    public static final Pattern VALID_TEL_MSISDN_PATTERN =
            Pattern.compile("^(tel:\\+)([0-9]){11}$");

    public static final Pattern VALID_MSISDN_PATTERN =
            Pattern.compile("^(94)([0-9]){9}$");


    public static boolean validateTelMsisdn(String msisdn){
        Matcher matcher = VALID_TEL_MSISDN_PATTERN.matcher(msisdn);
        return matcher.find();
    }

    public static boolean validateMsisdn(String msisdn){
        Matcher matcher = VALID_MSISDN_PATTERN.matcher(msisdn);
        return matcher.find();
    }

}
