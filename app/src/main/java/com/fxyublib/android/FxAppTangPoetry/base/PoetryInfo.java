package com.fxyublib.android.FxAppTangPoetry.base;

public class PoetryInfo extends BaseInfo{
    private String title;
    private String comment;
    private String translation;
    private String appreciation;
    private String type;
    private String dynasty;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTranslation() {
        return translation;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getAppreciation() {
        return appreciation;
    }
    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) { this.type = type;  }

    public void setDynasty(String dynasty) { this.dynasty = dynasty;}
    public String getDynasty() {
        return this.dynasty;
    }

    @Override
    public String toString() {
        return getName()+" "+title+" "+getContent();
    }

    public PoetryInfo(String name, String title, String content, String comment, String translation, String appreciation) {
        super(name, content);
        this.title = title;

        this.comment = comment;
        this.translation = translation;
        this.appreciation = appreciation;
    }

    public PoetryInfo() {
        super();
    }
}
