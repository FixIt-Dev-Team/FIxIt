package com.example.fixit;

import java.io.Serializable;
import java.util.ArrayList;

public class imgUrl implements Serializable {

    ArrayList<String> arr;

    String imgID;

    imgUrl(){
        arr= new ArrayList<>();
    }

    public void setipnut(String input){
        arr.add(input);
    }

    public void setArr(ArrayList<String> arr){
        this.arr = arr;
    }

    public ArrayList<String> getArr() {
        return arr;
    }

    public String getImgID() {
        return imgID;
    }

    public void setImgID(String imgID) {
        this.imgID = imgID;
    }
}
