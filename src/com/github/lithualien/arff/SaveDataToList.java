package com.github.lithualien.arff;

public class SaveDataToList {

    private String attribute;
    private int pos;

    public SaveDataToList() {

    }

    public SaveDataToList(int pos, String attribute) {
        this.attribute = attribute;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
