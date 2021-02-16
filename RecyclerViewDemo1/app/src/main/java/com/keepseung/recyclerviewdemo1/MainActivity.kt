package com.keepseung.recyclerviewdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val fruitsList = listOf(Fruit("mango","aa")
            ,Fruit("apple","aa")
            ,Fruit("banana","aa")
            ,Fruit("goo","aa")
            ,Fruit("dsa","aa"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.adapter = MyRecyclerViewAdapter(fruitsList) { selectedFruitItem:Fruit->listItemClicked(selectedFruitItem)}
    }
    private fun listItemClicked(fruit: Fruit){
        Toast.makeText(this@MainActivity,
                "fruit name is ${fruit.name}",Toast.LENGTH_LONG).show()
    }
}