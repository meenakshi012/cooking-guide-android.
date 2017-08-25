package com.example.rgukt.cooking;

/**
 * Created by rgukt on 3/25/2017.
 */
public class fooditem {
    private String name;
    private byte[] image;

    public fooditem(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
