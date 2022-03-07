package com.fxyublib.android.FxAppTangPoetry.ui.Tang;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fxyublib.android.FxAppTangPoetry.DataBaseHelper;
import com.fxyublib.android.FxAppTangPoetry.R;
import com.fxyublib.android.FxAppTangPoetry.base.AuthorBaseFragment;
import com.fxyublib.android.FxAppTangPoetry.base.AuthorInfo;
import com.fxyublib.android.FxAppTangPoetry.base.BaseInfo;
import com.fxyublib.android.FxAppTangPoetry.base.Common;
import com.fxyublib.android.FxAppTangPoetry.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AuthorFragment extends AuthorBaseFragment {
    private static final String TAG = ".Tang.AuthorFragment";
    private static final String mFullSQL = "select author from (select DISTINCT replace(author,' ','') as author from xxtstb  ) group by author order by author desc";

    public AuthorFragment(){
        super("xxtsdb.db", mFullSQL);
    }

    public AuthorFragment(String filename, String sql){
        super(filename, sql);
    }

    @Override
    public List<BaseInfo> getData(int position){
        _list.clear();
        Cursor cursor = mDbMgr.rawQuery(mSearchSQL, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("author"));
                if(StringUtils.isNumeric(name.substring(0, 1))) continue;

                AuthorInfo a = new AuthorInfo(name, "", "");
                _list.add(a);
            } while (cursor.moveToNext());

           Common.sort(_list);
        }
        cursor.close();
        return _list;
    }
}
