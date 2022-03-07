package com.fxyublib.android.FxAppTangPoetry.base;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fxyublib.android.FxAppTangPoetry.R;
import com.fxyublib.android.FxAppTangPoetry.base.PoetryInfo;

import java.util.ArrayList;
import java.util.List;

public class ContentListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    List<PoetryInfo> list = null;
    private Typeface mNameFont;
    private Typeface mContentFont;

    public ContentListAdapter(Context context, List<PoetryInfo> list, Typeface font1, Typeface font2){
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
        this.list = list;
        this.mNameFont = font1;
        this.mContentFont = font2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //由系统调用，返回一个view对象作为listview的条目
    /*
     * position：本次getView方法调用所返回的view对象在listView中处于第几个条目，position的值就为多少
     * */
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewholder = null;
        if(view == null){
            LayoutInflater inflate=LayoutInflater.from(context);
            view=inflate.inflate(R.layout.listview_item,null);
            viewholder = new ViewHolder();
            viewholder.tv_title= (TextView)view.findViewById(R.id.tv_title);
            viewholder.tv_content = (TextView)view.findViewById(R.id.tv_content);
            viewholder.tv_id = (TextView)view.findViewById(R.id.tv_id);
            view.setTag(viewholder);

            viewholder.tv_title.setTypeface(mNameFont);
            viewholder.tv_content.setTypeface(mContentFont);
        }
        else {
            viewholder = (ViewHolder)view.getTag();
        }

        PoetryInfo poetry = list.get(position);
        String title = poetry.getName() + " - 《" + poetry.getTitle() + "》 " ;
        viewholder.tv_title.setText(title);
        viewholder.tv_content.setText(poetry.getContent());
        viewholder.tv_id.setText(String.format("%d", position+1));
        return view;
    }

    private class ViewHolder {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_id;
    }
}

