package com.autowrite.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertyUtil {
    private static String PROPERTIES_FILE = "C:\\globals.properties";
    
    public static void main(String args[]) {
        PropertyUtil o = new PropertyUtil();
      
        getProperty("BANNER_BASE_DIR");
        
        System.out.println("BANNER_BASE_DIR" + ":[" +  getProperty("BANNER_BASE_DIR") + "]");
        System.out.println("UPLOAD_BASE_DIR" + ":[" +  getProperty("UPLOAD_BASE_DIR") + "]");
       
    }
    

    /**
     * 특정 키값을 반환한다.
     */
    public static String getProperty(String keyName) {
        String value = null;
  
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(PROPERTIES_FILE);
            props.load(new java.io.BufferedInputStream(fis));
            value = props.getProperty(keyName).trim();
            fis.close();
        } catch (java.lang.Exception e) {
            System.out.println(e.toString());
        }
            return value;
    }
 
 
    /**
     * 특정 키 이름으로 값을 설정한다.
     */
    public void setProperty(String keyName, String value) {
        try {
            Properties props = new Properties();
            FileInputStream fis  = new FileInputStream(PROPERTIES_FILE);
            props.load(new java.io.BufferedInputStream(fis));
            props.setProperty(keyName, value);
            props.store(new FileOutputStream(PROPERTIES_FILE), "");
            fis.close();
        } catch(java.lang.Exception e) {
            System.out.println(e.toString());
        }
    }
}
