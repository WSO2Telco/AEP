package com.wso2telco.tip.util;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yasith on 1/26/17.
 */
public class Validator {

    public static final Pattern VALID_MSISDN_PATTERN =
            Pattern.compile("^(tel:\\+)([0-9]){11}$");

    public static boolean validateMsisdn(String msisdn){
        Matcher matcher = VALID_MSISDN_PATTERN.matcher(msisdn);
        return matcher.find();
    }

    public static boolean validateUrl(String url){
        if(!url.startsWith("http")){
            url = "http://" + url;
        }
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }
}
