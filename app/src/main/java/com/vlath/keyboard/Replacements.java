package com.vlath.keyboard;

import java.util.Map;
import java.util.HashMap;

public class Replacements {
    
    static HashMap<String, String> data = new HashMap<String, String>(); 
    
    public Replacements() {}

    public static HashMap<String, String> getData() {
        data.put("i", "I");
        data.put("yoy", "you");
        return data;
    }
}
