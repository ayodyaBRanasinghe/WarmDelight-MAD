package com.warmdelightapp.Model;

public class address {String number,street,suburb,town,district;


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public address(String number, String street,String suburb,String town, String district) {
        this.number = number;
        this.street = street;
        this.district = district;
        this.suburb = suburb;
        this.town= town;

    }

    public address() {
    }
}
