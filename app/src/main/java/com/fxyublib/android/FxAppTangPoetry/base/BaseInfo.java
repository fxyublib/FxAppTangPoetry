package com.fxyublib.android.FxAppTangPoetry.base;

import com.fxyublib.android.FxAppTangPoetry.ui.Song.AuthorFragment;
import com.fxyublib.android.FxAppTangPoetry.utils.PinyinUtils4;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BaseInfo {
    private String name;
    private String content;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return name +","+ content;
    }

    public BaseInfo(String name, String content) {
        super();
        this.name = name;
        this.content = content;
    }

    public BaseInfo() {
        super();
        this.name = "";
        this.content = "";
    }

}
