package com.fxyublib.android.FxAppTangPoetry.ui.Song;

import android.database.Cursor;
import com.fxyublib.android.FxAppTangPoetry.base.AuthorBaseFragment;
import com.fxyublib.android.FxAppTangPoetry.base.AuthorInfo;
import com.fxyublib.android.FxAppTangPoetry.base.BaseInfo;
import com.fxyublib.android.FxAppTangPoetry.base.Common;
import java.util.List;

public class AuthorFragment extends AuthorBaseFragment {
    private static final String TAG = ".Song.AuthorFragment";
    private static final String mFullSQL = "select name, dynasty,desc_lpcolumn from poet order by name desc";

    public AuthorFragment(){
        super("songpoem.db", mFullSQL);
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
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String dynasty = cursor.getString(cursor.getColumnIndex("dynasty"));
                String desc = cursor.getString(cursor.getColumnIndex("desc_lpcolumn"));

                AuthorInfo a = new AuthorInfo(name, dynasty, desc);
                _list.add(a);
            } while (cursor.moveToNext());

            Common.sort(_list);
        }
        cursor.close();
        return _list;
    }
}


