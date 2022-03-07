package com.fxyublib.android.FxAppTangPoetry.base;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fxyublib.android.FxAppTangPoetry.R;
import com.fxyublib.android.FxAppTangPoetry.base.AuthorInfo;

import java.util.List;

public class AuthorListAdapter extends BaseAdapter {

    private List<BaseInfo> list;
    private Context context;
    private int item;
    private LayoutInflater inflater;
    private Typeface font;

    public AuthorListAdapter(int item, Context context) {
        this.list = null;
        this.item = item;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<BaseInfo> list) {
        this.list = list;
    }

    public void setFont(Typeface font) {
        this.font = font;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.info);

            convertView.setTag(holder);
        } else {
            // viewHolder被复用
            holder = (ViewHolder) convertView.getTag();
        }

        AuthorInfo a = (AuthorInfo)list.get(position);
        String name = a.getName();
        String content = a.getDynasty();
        String number = content + (position + 1) + "";

        holder.tv_name.setText(name);
        holder.tv_name.setTypeface(this.font);
        holder.tv_content.setText( number);

        //holder.tv_name.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
        //holder.tv_content.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);// 第一个参数：字体类型、第二个参数字体风格

        // 为listView设置隔行变色
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.item_selector);
        } else {
            convertView.setBackgroundResource(R.drawable.item_selector1);
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_name;
        private TextView tv_content;
    }
}

