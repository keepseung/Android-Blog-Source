package com.example.countryapp.model;

import com.google.gson.annotations.SerializedName;

public class CountryModel {
    // 나라 이름을 가짐
    String countryName;
    // 나라 수도 이름을 가짐
    String capital;
    // 나라 국기 이미지 url을 가
    String flag;

    public CountryModel(String countryName, String capital, String flag) {
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }
}
