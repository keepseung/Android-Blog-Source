package com.anushka.viewmodeldemo1

import androidx.lifecycle.ViewModel

class MainActivityViewModel(startingTotal: Int) :ViewModel() {
    private var count = 0

    init {
        count = startingTotal
    }

    fun getCurrentCount():Int{
        return count
    }

    fun getUpdatedCount():Int{
        return ++count
    }

    fun getUpdatedCount(plusCount: Int):Int{
        count+= plusCount
        return count
    }
}