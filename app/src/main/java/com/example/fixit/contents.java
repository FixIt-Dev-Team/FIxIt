package com.example.fixit;

public class contents {
    String ID;
    String title;
    String content;

    public contents(String ID, String title, String content) {
        this.ID = ID;
        this.title = title;
        this.content = content;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
