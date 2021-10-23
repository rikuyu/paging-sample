package com.example.pagingsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pagingsample.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val mainFragment = MainFragment()
        transaction.add(R.id.container, mainFragment).commit()
    }
}