package com.example.birthdaycafe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.birthdaycafe.databinding.ActivityLoadingBinding

class LoadingActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoadingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent( this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 500)
    }
}