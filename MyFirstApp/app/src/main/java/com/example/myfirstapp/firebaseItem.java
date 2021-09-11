package com.example.myfirstapp;

public class firebaseItem {
    private String name;
    private String path;
    private String URL;
    private boolean isFile;
    private boolean isSelected = false;

    public firebaseItem(String name, String path, String URL, boolean isFile)
    {
        this.name=name;
        this.path=path;
        this.URL=URL;
        this.isFile=isFile;
    }

    public boolean getIsFile(){return this.isFile;}

    public String getName(){return this.name;}

    public String getPath(){return this.path;}

    public String getURL(){return this.URL;}

    public  boolean getSelected(){ return this.isSelected; }

    public void setSelected(boolean isSelected) { this.isSelected = isSelected; }

    public void setURL(String URL){ this.URL = URL; }


}
