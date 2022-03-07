package com.fxyublib.android.FxAppTangPoetry.base;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.fxyublib.android.FxAppTangPoetry.R;
import com.fxyublib.android.FxAppTangPoetry.utils.PinyinUtils4;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Common {
    public static Typeface _font;
    public static Typeface _font2;

    public static void sort(List<BaseInfo> list) {
        Comparator cmp = new ChineseCharComp();
        Collections.sort(list, cmp);
    }

    public static void sortAuthor(List<AuthorInfo> list) {
        Comparator cmp = new ChineseCharComp();
        Collections.sort(list, cmp);
    }

    public static void sortPoetry(List<PoetryInfo> list) {
        Comparator cmp = new ChineseCharComp();
        Collections.sort(list, cmp);
    }

    private static class ChineseCharComp implements Comparator {
        public int compare(Object o1, Object o2) {
            Collator myCollator = Collator.getInstance(java.util.Locale.CHINA);

            //PinyinUtils.getSpells(title)
            //PinyinUtils2.getAllFirstLetter(title)
            //PinyinUtils3.cn2py(title)
            String s1 = PinyinUtils4.getPingYin(((BaseInfo)o1).getName());
            String s2 = PinyinUtils4.getPingYin(((BaseInfo)o2).getName());
            int ret = myCollator.compare(s1, s2);
            return Integer.compare(ret, 0);
            //return (ret < 0) ? -1 : ((ret == 0) ? 0 : 1);
        }
    }

    static void showDialog_Description(final Context context, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        final View dialogView = View.inflate(context, R.layout.dialog_mutitext, null);
        dialog.setView(dialogView);
        dialog.show();

        TextView tv = (TextView) dialogView.findViewById(R.id.tv_content);
        tv.setText(text);
        tv.setTypeface(Common._font2);
    }
}
