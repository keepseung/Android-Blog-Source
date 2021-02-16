package com.keepseung.livedatadatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.keepseung.livedatadatabinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        // liveData를 binding에서 사용하기 위해
        // 뷰 모델 객체에 실제 lifecycleowner를 현재 activity로 지정해줘야 함
        binding.lifecycleOwner = this
        // 뷰 모델을 바인딩 변수로 사용함
        binding.myViewModel = viewModel

    }
}
