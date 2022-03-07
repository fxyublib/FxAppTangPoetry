package com.fxyublib.android.FxAppTangPoetry.ui.Tang;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.fxyublib.android.FxAppTangPoetry.base.MyTabFragmentAdapter;
import com.fxyublib.android.FxAppTangPoetry.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = ".Tang.MainFragment";
    private View mViewContent;
    private TabLayout mTablayout;
    private ViewPager mViewpager;

    public MainFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        mViewContent = inflater.inflate(R.layout.fragment_tang,container,false);
        initConentView(mViewContent);
        initData();

        return mViewContent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }

    public void initConentView(View viewContent) {
        this.mTablayout = (TabLayout) viewContent.findViewById(R.id.my_tablayout);
        this.mViewpager = (ViewPager) viewContent.findViewById(R.id.my_viewpager);
    }

    public void initData() {
        //创建一个viewpager的adapter
        MyTabFragmentAdapter adapter = new MyTabFragmentAdapter(getFragmentManager());

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new ContentFragment());
        fragments.add(new AuthorFragment());

        String[] titlesArr = {"内容", "作者"};
        adapter.setTitlesArr(titlesArr);
        adapter.setFragments(fragments);
        this.mViewpager.setAdapter(adapter);
        this.mViewpager.setOffscreenPageLimit(3);
        this.mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedTab(position);
                Log.e(TAG, "onPageSelected: " + String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //将TabLayout和ViewPager关联起来
        this.mTablayout.setupWithViewPager(this.mViewpager);
        this.mTablayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void selectedTab(int position){
    }

}