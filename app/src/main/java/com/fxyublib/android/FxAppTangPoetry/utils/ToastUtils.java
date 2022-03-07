package com.fxyublib.android.FxAppTangPoetry.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static Toast toast;
    public static void showMsg(Context context, String msg){
        if(toast==null){
            toast= Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }
}
