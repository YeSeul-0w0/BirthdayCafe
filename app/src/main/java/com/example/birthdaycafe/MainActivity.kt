package com.example.birthdaycafe

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.birthdaycafe.Adapter.ListAdapter
import com.example.birthdaycafe.data.cafeData
import com.example.birthdaycafe.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    var customToolbar: Toolbar? = null
    var customTab: TabLayout? = null
    lateinit var cafeAdapter: ListAdapter
    val cafeData = mutableListOf<cafeData>()

    val db = Firebase.firestore

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
        // customToolbar = binding.toolbar
        // setSupportActionBar(customToolbar)
        // getSupportActionBar()?.setTitle("Dreamcather");

        customTab = binding.members
        customTab!!.addTab(customTab!!.newTab().setText("다미"))
        customTab!!.addTab(customTab!!.newTab().setText("한동"))



        getBirthday("Dami")
        initRecycler("Dami")

        customTab!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                println(tab!!.text)
                var deliver: String = ""
                when(tab!!.text){
                    "한동" -> deliver = "Handong"
                    "다미" -> deliver = "Dami"
                }
                getBirthday(deliver)
                initRecycler(deliver)
                // 작성
            }
        })
    }

    private fun getBirthday(hero: String){
        val dayTextView = binding.day
        val loadDay = db.collection("$hero").document("birthday")
        loadDay.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val temp = document.data
                    val year = temp!!.getValue("year").toString()
                    val month = temp.getValue("month").toString()
                    val day = temp.getValue("day").toString()
                    dayTextView.setText("탄생일: $year-$month-$day")
                } else {
                    dayTextView.setText("no data")
                    Log.d("Null", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "get failed with ", exception)
            }
    }

    private fun initRecycler(docName:String ){
        cafeAdapter = ListAdapter(this)
        val rv = binding.listUp
        rv.adapter = cafeAdapter
        db.collection("$docName")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val temp = document.data
                    println()
                    if (!document.id.equals("birthday")){
                        val cafeLocated = temp.getValue("located").toString()
                        val cafeStation = temp.getValue("station").toString()
                        val cafeTime = temp.getValue("time").toString()
                        cafeData.apply {
                            add(cafeData(name=document.id, located = cafeLocated, station = cafeStation, time = cafeTime))
                        }
                    }
                }
                cafeAdapter.datas = cafeData
                cafeAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "Error getting documents: ", exception)
            }


    }
}