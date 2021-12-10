package com.example.microservices.galleryservice.model;

import java.util.List;

public class Gallery {
    private int id;
    private List<Object> images;
    
    public int getId() {
        return id;
    }
    public List<Object> getImages() {
        return images;
    }
    public void setImages(List<Object> images) {
        this.images = images;
    }
    public void setId(int id) {
        this.id = id;
    }
}
