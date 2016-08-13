package com.holler.holler_dao.util;

import java.util.Properties;
import java.util.Set;

/**
 * Created by pravina on 13/08/16.
 */
public class HollerProperties {
    private static final HollerProperties instance;
    static {
        instance = new HollerProperties();
    }

    private Properties finalProperties = new Properties();
    private Properties hollerProps = null;

    private HollerProperties(){
        try{
            loadPropertiesFromApp();
            String configDir = System.getProperty("holler.config.dir");
            if(CommonUtil.notNullAndEmpty(configDir)){
                hollerProps = CommonUtil.getPropertiesFromAbsolutePath(configDir + "/holler.properties");
            }
            if(CommonUtil.isNotNull(hollerProps)){
                Set<Object> keySet = hollerProps.keySet();
                for(Object key : keySet){
                    finalProperties.put(key.toString().trim(), hollerProps.get(key).toString().trim());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadPropertiesFromApp(){
        try{
            Properties appProps = CommonUtil.getPropertiesFromClasspath("holler.properties");
            Set<Object> keySet = appProps.keySet();
            for(Object key : keySet){
                finalProperties.put(key, appProps.get(key));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static HollerProperties getInstance(){
        return instance;
    }

    public String getValue(String key) {
        return finalProperties.getProperty(key);
    }

    public Properties getFinalProperties(){
        return finalProperties;
    }
}
