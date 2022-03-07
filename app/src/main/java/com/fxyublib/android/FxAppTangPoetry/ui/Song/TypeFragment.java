package com.fxyublib.android.FxAppTangPoetry.ui.Song;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fxyublib.android.FxAppTangPoetry.R;
import com.fxyublib.android.FxAppTangPoetry.base.AuthorBaseFragment;
import com.fxyublib.android.FxAppTangPoetry.base.AuthorInfo;
import com.fxyublib.android.FxAppTangPoetry.base.BaseInfo;
import com.fxyublib.android.FxAppTangPoetry.base.Common;
import com.fxyublib.android.FxAppTangPoetry.utils.PinyinUtils4;

import java.util.List;

public class TypeFragment extends AuthorBaseFragment {
    private static final String TAG = ".Song.TypeFragment";
    private static final String mFullSQL = "select type from poem group by type order by type";

    public TypeFragment(){
        super("songpoem.db", mFullSQL);
    }

    public TypeFragment(String filename, String sql){
        super(filename, sql);
    }

    @Override
    public List<BaseInfo> getData(int position){
        _list.clear();
        String[] oldArr = {"一","二","三","四","五","六","七","八","九","十","十一","十二"};
        String[] newArr = {"a","b","c","d","e","f","g","h","i","j","k","l"};

        Cursor cursor = mDbMgr.rawQuery(mSearchSQL, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("type"));
                for (int i = oldArr.length-1 ; i>=0; i--) {
                    name = name.replace(oldArr[i],newArr[i]);
                }

                AuthorInfo a = new AuthorInfo(name, "", "");
                _list.add(a);
            } while (cursor.moveToNext());

            Common.sort(_list);

            for(int i = 0;i < _list.size();i++){
                AuthorInfo a = (AuthorInfo)_list.get(i);
                String name = a.getName();
                for (int j = oldArr.length-1 ; j>=0; j--) {
                    name = name.replace(newArr[j], oldArr[j]);
                }
                a.setName(name);
                _list.set(i, a);
            }

        }
        cursor.close();
        return _list;
    }
}

