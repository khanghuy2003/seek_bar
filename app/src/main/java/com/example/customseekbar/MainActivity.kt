package com.example.customseekbar

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    val a1bc = ""
    val ab212 = ""
    val ab12 = ""
    val ab122 = ""
    val a2bc = ""
    val ab1c = ""
    val ab321c = ""
    val abc = ""
    val a4bc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        findViewById<MySeekBarView>(R.id.sb).setSeekbarListener(object : MySeekBarView.WhenSeekBarChangeListener{
            override fun onSeekBarChange(progress: Float) {
                Log.d("TAGGGG", "onSeekBarChange: ${String.format(Locale.getDefault(),"%.02f", progress)}")
                Log.d("TAGGGG", "onSeekBarChange: ${String.format(Locale.getDefault(),"%.02f", progress)}")
                Log.d("TAGGGG", "onSeekBarChange: ${String.format(Locale.getDefault(),"%.02f", progress)}")
                Log.d("TAGGGG", "onSeekBarChange: ${String.format(Locale.getDefault(),"%.02f", progress)}")
                Log.d("TAGGGG", "onSeekBarChange: ${String.format(Locale.getDefault(),"%.02f", progress)}")
                Log.d("TAGGGG", "onSeekBarChange: ${String.format(Locale.getDefault(),"%.02f", progress)}")
                Log.d("TAGGGG", "onSeekBarChange: ${String.format(Locale.getDefault(),"%.02f", progress)}")
                Log.d("TAGGGG", "onSeekBarChange: ${String.format(Locale.getDefault(),"%.02f", progress)}")
            }
        })


        Log.d("TAGGGG", "onSeekBarChange: ")
        Log.d("TAGGGG", "onSeekBarChange: ")
        Log.d("TAGGGG", "onSeekBarChange: ")
        Log.d("TAGGGG", "onSeekBarChange: ")
        Log.d("TAGGGG", "onSeekBarChange: ")
    }

    private fun abc() {

    }

    private fun htest() {

    }

}