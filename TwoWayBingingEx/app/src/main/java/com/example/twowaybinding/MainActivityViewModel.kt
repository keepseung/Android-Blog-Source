package com.example.twowaybinding

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

    val inputText = MutableLiveData<String>()

    // 초기 값 설정하기
    init {
        count.value = startingTotal
    }

    // 덧셈하
    fun getUpdatedCount(){
        // EditText의 값을 가져오기
        val plusCount:Int =  inputText.value!!.toInt()
        count.value = (count.value)?.plus(plusCount)
    }
}