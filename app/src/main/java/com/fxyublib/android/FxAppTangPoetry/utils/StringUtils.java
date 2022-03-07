package com.fxyublib.android.FxAppTangPoetry.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    static public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
