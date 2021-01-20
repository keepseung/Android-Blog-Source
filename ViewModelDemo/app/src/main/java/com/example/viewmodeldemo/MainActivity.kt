package com.anushka.viewmodeldemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodeldemo.R
import com.example.viewmodeldemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModelFactory = MainActivityViewModelFactory(1234)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.countText.text = viewModel.getCurrentCount().toString()
        binding.button.setOnClickListener {

            binding.countText.text = viewModel.getUpdatedCount(Integer.parseInt(binding.countEt.text.toString())).toString()
        }
    }
}
