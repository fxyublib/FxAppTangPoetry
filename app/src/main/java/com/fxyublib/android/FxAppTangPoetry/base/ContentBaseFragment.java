package com.fxyublib.android.FxAppTangPoetry.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.fxyublib.android.FxAppTangPoetry.DataBaseHelper;
import com.fxyublib.android.FxAppTangPoetry.R;
import com.fxyublib.android.FxAppTangPoetry.utils.ActivityUtils;
import com.fxyublib.android.FxAppTangPoetry.utils.TextToSpeechUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ContentBaseFragment extends Fragment {
    private static final String TAG = "ContentBaseFragment";

    private DataBaseHelper mDbHelper;
    public SQLiteDatabase mDbMgr;
    public String mSearchSQL = "";
    private String mFileName = "";

    public List<PoetryInfo> mList = new ArrayList<PoetryInfo>();
    private ImageView imageView;
    private TextToSpeechUtils mTextSpeech;

    private ProgressDialog progressDialog;
    private Thread mThreadLoadApps = new Thread(){
        @Override
        public void run() {
            getData();
            hander.sendEmptyMessage(0); // 下载完成后发送处理消息
            progressDialog.dismiss();   // 关闭进度条对话框
        }
    };

    private Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    //smAdapter.notifyDataSetChanged(); //发送消息通知ListView更新
                    //list.setAdapter(smAdapter); // 重新设置ListView的数据适配器
                    ListView lv = (ListView) getActivity().findViewById(R.id.list);
                    lv.setAdapter(new ContentListAdapter(getActivity(), mList, Common._font, Common._font2));
                    break;
                default:
                    //do something
                    break;
            }
        }
    };

    public ContentBaseFragment(){
        super();
    }

    public ContentBaseFragment(String filename, String sql){
        super();
        mFileName = filename;
        mSearchSQL = sql;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
        setHasOptionsMenu(true);

        Log.e(TAG, "filename: " + mFileName);
        Log.e(TAG, "sql: " + mSearchSQL);
        mDbHelper = new DataBaseHelper(getActivity(), mFileName);
        mDbMgr = mDbHelper.getDataBasePtr();

        if(mDbMgr != null) {
            progressDialog = ProgressDialog.show(getActivity(), "请稍等", "正在刷新数据...",true);
            mThreadLoadApps.start();
        }

        //
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY)%7;
        int imageIdArray[] = {R.mipmap.navigation_1, R.mipmap.navigation_2
                , R.mipmap.navigation_3, R.mipmap.navigation_4
                , R.mipmap.bg_main, R.mipmap.bg1, R.mipmap.bg2};
        imageView = (ImageView) getActivity().findViewById(R.id.iv_pressure_bg);
        imageView.setAlpha(0.3f);
        imageView.setImageDrawable(getResources().getDrawable(imageIdArray[hour], null));
        mTextSpeech = new TextToSpeechUtils();
        mTextSpeech.init(getActivity());

        ListView lv = (ListView) getActivity().findViewById(R.id.list);
        //lv.setAdapter(new MyAapter(getActivity()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "onItemClick", Toast.LENGTH_SHORT).show();
                boolean ret = ActivityUtils.isFastDoubleClick();
                if(ret) {
                    PoetryInfo poetry = mList.get(position);
                    if(mTextSpeech.isSpeaking()) {
                        mTextSpeech.stop();
                    }
                    else {
                        mTextSpeech.speak(poetry.getContent());
                    }
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
                //Toast.makeText(getActivity(), "onItemLongClick", Toast.LENGTH_SHORT).show();
                PoetryInfo poetry = mList.get(position);
                String strDetail = poetry.getComment()
                        + "\n\n-----------------------------------\n"
                        + poetry.getTranslation()
                        + "\n\n-----------------------------------\n"
                        + poetry.getAppreciation() ;
                Common.showDialog_Description(getActivity(), strDetail);
                return true;
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_tangcontent, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
        mTextSpeech.close();
    }

    public void showDialog_search(Context context) {
        /*final View dialgoView = View.inflate(context, R.layout.dialog_search, null); //初始化输入对话框的布局
        //   设置登录密码
        AlertDialog.Builder pwdBuilder = new AlertDialog.Builder(context);
        pwdBuilder.setTitle("输入密码后用手机号登陆");
        pwdBuilder.setView(dialgoView);
        //因为点击取消关闭dialog，我直接这样写
        pwdBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //现在builder中这样写确定按钮
        pwdBuilder.setPositiveButton("确定",null);
        //创建dialog
        final AlertDialog pwdDialog=pwdBuilder.create();
        //dialog点击其他地方不关闭
        pwdDialog.setCancelable(true);
        pwdDialog.show();
        //创建dialog点击监听OnClickListener
        pwdDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果想关闭dialog直接加上下面这句代码就行
                pwdDialog.cancel();
            }
        });*/

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        final View dialogView = View.inflate(context, R.layout.dialog_search, null);
        dialog.setView(dialogView);
        dialog.show();

        Button btnLogin = (Button) dialogView.findViewById(R.id.btn_login);
        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = (EditText) dialogView.findViewById(R.id.et_name);
                String text = etName.getText().toString();
                RadioGroup radiogroup = (RadioGroup)dialogView.findViewById(R.id.radiogroup1);
                int checkId = radiogroup.getCheckedRadioButtonId();
                RadioButton radio1 = (RadioButton) dialogView.findViewById(R.id.radiobutton1);
                RadioButton radio2 = (RadioButton) dialogView.findViewById(R.id.radiobutton2);
                RadioButton radio3 = (RadioButton) dialogView.findViewById(R.id.radiobutton3);

                int type = 0;
                if(radio1.getId() == checkId) {
                    type = 0;
                }
                else if(radio2.getId() == checkId) {
                    type = 1;
                }
                else if(radio3.getId() == checkId) {
                    type = 2;
                }

                //dialog.cancel();
                dialog.dismiss();

                Log.e(TAG, "查询：" + text);
                Log.e(TAG, "类型：" + String.valueOf(checkId));

                getSearchSQL(text, type);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void getSearchSQL(String searchText, int type) {
        //TODO
    }

    public void getData() {
        //TODO
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.e(TAG, "onCreateOptionsMenu()");
        menu.clear();
        if (getChildFragmentManager().getBackStackEntryCount() == 0) {
            inflater.inflate(R.menu.search, menu);
        }
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "onOptionsItemSelected" + this.isVisible());

        if(item.getItemId() == R.id.action_search && this.isVisible()) {
            showDialog_search(getActivity());
        }

        return super.onOptionsItemSelected(item);
    }
}
