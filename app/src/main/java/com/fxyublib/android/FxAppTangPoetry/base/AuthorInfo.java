package com.fxyublib.android.FxAppTangPoetry.base;

public class AuthorInfo extends BaseInfo {
    private String desc;

    public void setDynasty(String dynasty) { this.setContent(dynasty);}
    public String getDynasty() {
        return this.getContent();
    }

    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AuthorInfo() {
        super();
    }

    public AuthorInfo(String name, String dynasty, String desc) {
        super(name, dynasty);
        setDesc(desc);
    }
}
