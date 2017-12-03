package com.my.evc.test;

import java.util.Locale;
import java.util.ResourceBundle;

public class L10N {
    static String bundleName = "Message";
    static String key = "key";
    public static void main(String args[]) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, new Locale("zh", "CN"));
        String cancel = bundle.getString(key);
        System.out.println(cancel);
         
        bundle = ResourceBundle.getBundle(bundleName, Locale.US);
        cancel = bundle.getString(key);
        System.out.println(cancel);
         
        bundle = ResourceBundle.getBundle(bundleName, Locale.getDefault());
        cancel = bundle.getString(key);
        System.out.println(cancel);
 
        bundle = ResourceBundle.getBundle(bundleName, Locale.GERMAN);
        cancel = bundle.getString(key);
        System.out.println(cancel);
    }
}
