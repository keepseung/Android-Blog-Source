package com.keepseung.viewmodeldatabinding

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(startingTotal: Int) :ViewModel() {
    // MutableLiveData를 은닉하기
    private var count = MutableLiveData<Int>()
    // 다른 클래스가 접근할 수 있는 데이터
    val countData : LiveData<Int>
        get() = count
    // 초기 값 설정하기
    init {
        count.value = startingTotal
    }
    
    @Bindable
    val inputValue = MutableLiveData<String>()

    // 덧셈 결과 값 수정하기
    fun getUpdatedCount(plusCount: Int){
        count.value = (count.value)?.plus(plusCount)
    }
}