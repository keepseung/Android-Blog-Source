package com.example.twowaybinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.twowaybinding.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // ViewModel을 생성할 Factory 생성 + cvh
        viewModelFactory = MainActivityViewModelFactory(1234)
        // Main ViewModel 생성
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        // liveData를 binding에서 사용하기 위해
        // 뷰 모델 객체에 실제 lifecycle owner를 현재 activity로 지정해줘야 함
        binding.lifecycleOwner = this

       // 뷰 모델을 바인딩 변수로 사용함
        binding.myViewModel = viewModel

    }
}
