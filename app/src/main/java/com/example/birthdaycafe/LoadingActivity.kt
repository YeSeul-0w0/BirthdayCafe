package com.example.birthdaycafe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.birthdaycafe.databinding.ActivityLoadingBinding
import java.util.*

class LoadingActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoadingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent: Intent = Intent(applicationContext,MainActivity::class.java)

        Timer().schedule(object:TimerTask(){
            override fun run(){
                startActivity(intent)
                finish()
            }
        },2000)
    }
}