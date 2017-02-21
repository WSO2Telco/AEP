package com.wso2telco.tip.balance.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yasith on 11/1/16.
 */
public class ResourceLoader {

    public static String getRemoteUrl() {
        return remoteUrl;
    }



    private static String remoteUrl;

    static {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            InputStream propertiesInputStream = ResourceLoader.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(propertiesInputStream);

            remoteUrl = prop.getProperty("remoteUrl");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
