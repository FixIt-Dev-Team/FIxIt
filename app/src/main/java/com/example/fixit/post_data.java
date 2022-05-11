package com.example.fixit;

import java.io.Serializable;
import java.util.ArrayList;

public class post_data implements Serializable {

    private String ID;
    private String imgID;
    private String Title;
    private String User_name;
    private String Uid;
    private String posting_doc;

    public post_data(){

    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPosting_doc(String posting_doc) {
        this.posting_doc = posting_doc;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getID() {
        return ID;
    }

    public String getPosting_doc() {
        return posting_doc;
    }

    public String getTitle() {
        return Title;
    }

    public String getUid() {
        return Uid;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setImgID(String imgID) {
        this.imgID = imgID;
    }

    public String getImgID() {
        return imgID;
    }
}
