package com.warmdelightapp.Model;

public class delivery {
    String name,price,imageurl,qty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public delivery(String name, String price, String imageurl, String qty) {
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
        this.qty = qty;
    }

    public delivery() {
    }
}
