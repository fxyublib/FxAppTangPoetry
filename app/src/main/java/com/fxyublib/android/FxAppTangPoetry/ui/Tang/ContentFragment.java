package com.fxyublib.android.FxAppTangPoetry.ui.Tang;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.fxyublib.android.FxAppTangPoetry.R;
import com.fxyublib.android.FxAppTangPoetry.base.Common;
import com.fxyublib.android.FxAppTangPoetry.base.ContentBaseFragment;
import com.fxyublib.android.FxAppTangPoetry.base.PoetryInfo;
import com.fxyublib.android.FxAppTangPoetry.utils.StringUtils;

public class ContentFragment extends ContentBaseFragment {
    private static final String TAG = ".Tang.ContentFragment";
    private static final String mFullSQL = "select * from xxtstb order by author desc , titel asc";

    public ContentFragment(){
        super("xxtsdb.db", mFullSQL);
    }

    public ContentFragment(String filename, String sql){
        super(filename, sql);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
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
    }

    @Override
    public void getData() {
        mList.clear();
        //Cursor cursor = db.query("xxtstb", null, null, null, null, null, "author desc");
        //Cursor cursor = db.query("xxtstb", null, "author like '%?%'", new String[]{"张"}, null, null, "author desc");
        Cursor cursor = mDbMgr.rawQuery(mSearchSQL, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("author"));
                String title = cursor.getString(cursor.getColumnIndex("titel"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                content = content.replace("&", "");
                String comment = cursor.getString(cursor.getColumnIndex("comment"));
                String translation = cursor.getString(cursor.getColumnIndex("translation"));
                String appreciation = cursor.getString(cursor.getColumnIndex("appreciation"));
                //Log.i(TAG, "name:"+", "+"title:" + title + ", "+ content);

                if(StringUtils.isNumeric(name.substring(0, 1))) continue;
                PoetryInfo poetry = new PoetryInfo(name, title, content, comment, translation, appreciation);
                mList.add(poetry);
            } while (cursor.moveToNext());

            Common.sortPoetry(mList);
        }
        cursor.close();
    }

    @Override
    public void getSearchSQL(String searchText, int type) {
        if (TextUtils.isEmpty(searchText) ) {
            Toast.makeText(getActivity(), "查询的内容不能为空", Toast.LENGTH_SHORT).show();
            mSearchSQL = mFullSQL;
            getData();
            return;
        }

        String strType = "";
        if(type == 0) {
            strType = "author";
        }
        else if(type == 1) {
            strType = "titel";
        }
        else if(type == 2) {
            strType = "content";
        }

        mSearchSQL = "select * from xxtstb where "+strType+" like '%"+searchText+"%' order by author desc, titel asc";
        getData();
    }

}