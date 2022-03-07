package com.fxyublib.android.FxAppTangPoetry;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class WaitingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_waiting);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WaitingActivity.this.finish();
                Toast.makeText(WaitingActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
        },1000);
    }
}

