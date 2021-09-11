package com.example.myfirstapp;

public class imageItem {
    private String data;
    private String id;
    private String file_name;
    private boolean isSelected = false;
    public imageItem(String data, String id, String file_name)
    {
        this.data = data;
        this.id = id;
        this.file_name = file_name;
    }

    public String getData()
    {
        return this.data;
    }

    public String getId(){
        return this.id;
    }

    public String getFileName(){
        return this.file_name;
    }

    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public  boolean getSelected(){
        return this.isSelected;
    }
}
