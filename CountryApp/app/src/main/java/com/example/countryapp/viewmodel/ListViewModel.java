package com.example.countryapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countryapp.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    // 사용자에게 보여줄 국가 데이터
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();
    // 국가 데이터를 가져오는 것에 성공했는지를 알려주는 데이터
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<>();
    // 로딩 중인지를 나타내는 데이터
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    // 뷰에서 데이터를 가져오기 위해 호출하는 함수
    public void refresh(){
        fetchCountries();
    }

    private void fetchCountries(){

        // 리스트에 넣을 임의의 데이터
        CountryModel countryModel1 = new CountryModel("Korea", "Seoul", "");
        CountryModel countryModel2 = new CountryModel("China", "Beijing", "");
        CountryModel countryModel3 = new CountryModel("Japan", "Tokyo", "");

        List<CountryModel> list = new ArrayList<>();
        list.add(countryModel1);
        list.add(countryModel2);
        list.add(countryModel3);
        list.add(countryModel1);
        list.add(countryModel2);
        list.add(countryModel3);
        list.add(countryModel1);
        list.add(countryModel2);
        list.add(countryModel3);

        // LiveData에 데이터를 넣어줌
        // 데이터를 관찰하는 뷰에게 전달됨.
        countries.setValue(list);
        countryLoadError.setValue(false);
        loading.setValue(false);

    }
}
