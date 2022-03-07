package com.fxyublib.android.FxAppTangPoetry.base;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.fxyublib.android.FxAppTangPoetry.DataBaseHelper;
import com.fxyublib.android.FxAppTangPoetry.R;

import java.util.ArrayList;
import java.util.List;

public class AuthorBaseFragment extends ListFragment {
    private static final String TAG = "AuthorBaseFragment";

    private DataBaseHelper mDbHelper;
    public SQLiteDatabase mDbMgr;
    public String mSearchSQL = "";
    private String mFileName = "";

    private ArrayAdapter<String> adapter;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    public LayoutInflater mInflater;
    final String[] from = {"title","info"};
    final int[] to = new int[]{R.id.title, R.id.info};
    private boolean isFirstVisible = true;
    public List<BaseInfo> _list = new ArrayList<BaseInfo>();

    public AuthorBaseFragment(){
        super();
    }

    public AuthorBaseFragment(String filename, String sql){
        super();
        mFileName = filename;
        mSearchSQL = sql;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = getActivity().getLayoutInflater();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = mInflater.inflate(R.layout.fragment_tangauthor,container,false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint: " + String.valueOf(isVisibleToUser));

        if (isVisibleToUser) {
            if(isFirstVisible) {
                isFirstVisible = false;
                refreshData(0);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: " + String.valueOf(hidden));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
        setHasOptionsMenu(true);
        manager = this.getFragmentManager();
        mDbHelper = new DataBaseHelper(getActivity(), mFileName);
        mDbMgr = mDbHelper.getDataBasePtr();

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long id) {
                //Toast.makeText(getActivity(), "onItemLongClick", Toast.LENGTH_SHORT).show();
                AuthorInfo a = (AuthorInfo)_list.get(position);
                String strDetail = a.getDesc();
                if(strDetail != null && strDetail.isEmpty() == false) {
                    Common.showDialog_Description(getActivity(), strDetail);
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @Nullable MenuInflater inflater) {
        Log.e(TAG, "onCreateOptionsMenu()");
        menu.clear();
    }

    public void refreshData(int position){
        AuthorListAdapter adapter = new AuthorListAdapter(R.layout.listview_item_author, this.getActivity());
        adapter.setData(getData(position));
        adapter.setFont(Common._font);
        this.setListAdapter(adapter);
        //this.setListAdapter(new SimpleAdapter(this.getActivity(),getData(position),R.layout.listview_item_author,from,to));
    }

    public List<BaseInfo> getData(int position){
        return _list;
    }

}
