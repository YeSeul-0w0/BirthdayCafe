package com.example.birthdaycafe

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.birthdaycafe.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import java.time.LocalDate


class MainActivity : AppCompatActivity() {
    var customToolbar: Toolbar? = null
    var customTab: TabLayout? = null

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // get month
        val nowDate: LocalDate = LocalDate.now()
        val getMonth: Int = nowDate.monthValue

        // set toolbar title
        customToolbar = binding.toolbar
        setSupportActionBar(customToolbar)
        getSupportActionBar()?.setTitle("$getMonth"+"월");

        customTab = binding.members
        customTab!!.addTab(customTab!!.newTab().setText("테스트"))

    }
}