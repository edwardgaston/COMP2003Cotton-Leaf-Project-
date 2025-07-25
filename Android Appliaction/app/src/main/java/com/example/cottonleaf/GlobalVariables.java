package com.example.cottonleaf;
import java.util.ArrayList;

public class GlobalVariables {
    private static GlobalVariables instance;
    private ArrayList<String> imagePaths = new ArrayList<>();

    private GlobalVariables() {}

    public static synchronized GlobalVariables getInstance() {
        if (instance == null) {
            instance = new GlobalVariables();
        }
        return instance;
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
}

