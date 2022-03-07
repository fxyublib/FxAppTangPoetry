package com.fxyublib.android.FxAppTangPoetry.ui.Song;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fxyublib.android.FxAppTangPoetry.R;
import com.fxyublib.android.FxAppTangPoetry.base.Common;
import com.fxyublib.android.FxAppTangPoetry.base.ContentBaseFragment;
import com.fxyublib.android.FxAppTangPoetry.base.PoetryInfo;

public class ContentFragment extends ContentBaseFragment {
    private static final String TAG = ".Song.ContentFragment";
    private static final String mFullSQL = "select poet_name,content,name,dynasty,type,shangxi,yiwen,zhushi from poem order by poet_name desc";

    public ContentFragment() {
        super("songpoem.db", mFullSQL);
    }

    public ContentFragment(String filename, String sql) {
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
                String author = cursor.getString(cursor.getColumnIndex("poet_name"));
                String title = cursor.getString(cursor.getColumnIndex("name"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String dynasty = cursor.getString(cursor.getColumnIndex("dynasty"));
                String type = cursor.getString(cursor.getColumnIndex("type"));

                String shangxi = cursor.getString(cursor.getColumnIndex("shangxi"));
                String yiwen = cursor.getString(cursor.getColumnIndex("yiwen"));
                String zhushi = cursor.getString(cursor.getColumnIndex("zhushi"));

                content = content.replace("<br>", "\n");
                content = content.replace("</p>", "");
                content = content.replace("<p>", "\n");
                content = content.replace("</span>", "");
                content = content.replace("<span style=\"font-family:SimSun;\">", "");

                PoetryInfo poetry = new PoetryInfo(author, title, content, zhushi, yiwen, shangxi);
                poetry.setDynasty(dynasty);
                poetry.setType(type);

                mList.add(poetry);
            } while (cursor.moveToNext());

            Common.sortPoetry(mList);
        }
        cursor.close();
    }

    @Override
    public void getSearchSQL(String searchText, int type) {
        if (TextUtils.isEmpty(searchText)) {
            Toast.makeText(getActivity(), "查询的内容不能为空", Toast.LENGTH_SHORT).show();
            mSearchSQL = mFullSQL;
            getData();
            return;
        }

        String strType = "";
        if (type == 0) {
            strType = "poet_name";
        } else if (type == 1) {
            strType = "name";
        } else if (type == 2) {
            strType = "content";
        }

        mSearchSQL = "select * from poem where " + strType + " like '%" + searchText + "%' order by poet_name";
        getData();
    }

}

