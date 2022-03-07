package com.fxyublib.android.FxAppTangPoetry;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.fxyublib.android.FxAppTangPoetry.base.Common;
import com.fxyublib.android.FxAppTangPoetry.utils.ActivityUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mHandler.postDelayed(new Runnable(){
            @Override
            public void run() {
                ShowWaitingActivity();
            }
        }, 1000);//延迟3s，时间自己定*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                ShowSnackbar(view, "已删除一个会话");
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_tang, R.id.nav_song, R.id.nav_classics,
                R.id.nav_favorite, R.id.nav_setting, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        updateAppIcon();

        Common._font = Typeface.createFromAsset(getAssets(),"fonts/ztx.ttf");
        Common._font2 = Typeface.createFromAsset(getAssets(),"fonts/rz.ttf");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case  R.id.action_settings :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void ShowSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("撤销", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {

                switch (event) {
                    case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                    case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                    case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                    case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                        Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        break;
                    case Snackbar.Callback.DISMISS_EVENT_ACTION:
                        Toast.makeText(MainActivity.this, "撤销了删除操作", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
                Log.i(TAG, "onShown");
            }
        }).show();
    }

    public void updateAppIcon() {
        ActivityUtils.mPackageManager = getApplicationContext().getPackageManager();
        ActivityUtils.mActivity = this;

        Calendar c = Calendar.getInstance();
        int week = c.get(Calendar.DAY_OF_MONTH)%5 + 1;
        String strTag = String.format("com.fxyublib.android.FxAppTangPoetry.tag_%d", week);
        //System.out.println(strTag);
        ActivityUtils.changeIconState(this, getComponentName(), strTag);
    }

    public void ShowWaitingWindow(){
        final PopupWindow popupWindow = new PopupWindow();
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_waiting,null);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        },2000);
    }

    public void ShowWaitingActivity(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,WaitingActivity.class);
        startActivity(intent);
    }
}
