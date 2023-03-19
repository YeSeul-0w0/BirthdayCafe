package com.example.birthdaycafe

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.birthdaycafe.Adapter.ListAdapter
import com.example.birthdaycafe.data.cafeData
import com.example.birthdaycafe.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.time.LocalDate


class MainActivity : AppCompatActivity() {
    var customToolbar: Toolbar? = null
    var customTab: TabLayout? = null
    lateinit var cafeAdapter: ListAdapter
    val cafeData = mutableListOf<cafeData>()

    val db = Firebase.firestore
    val storage = Firebase.storage
    val storageRef = storage.reference

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
        customTab!!.addTab(customTab!!.newTab().setText("test_name1"))
        customTab!!.addTab(customTab!!.newTab().setText("test_name2"))

        getBirthday("Member1")
        updateRecycler("Member1")

        customTab!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                var deliver: String = ""
                when(tab!!.text){
                    "test_name1" -> deliver = "Member1"
                    "test_name2" -> deliver = "Member2"
                }
                getBirthday(deliver)
                updateRecycler(deliver)
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
                    dayTextView.setText("$year-$month-$day")
                } else {
                    dayTextView.setText("no data")
                    Log.d("Null", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Error", "get failed with ", exception)
            }
    }

    private fun updateRecycler(docName:String){
        val recyclerView = binding.listUp
        cafeAdapter = ListAdapter(this)
        cafeData.clear()
        recyclerView.adapter = cafeAdapter

        val test= binding.listUp

        db.collection("$docName")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val temp = document.data
                    if (!document.id.equals("birthday")){
                        val cafeLocated = temp.getValue("located").toString()
                        val cafeStation = temp.getValue("station").toString()
                        val cafeTime = temp.getValue("time").toString()
                        cafeData.apply {
                            add(cafeData(name =document.id, located = cafeLocated, station = cafeStation, time = cafeTime))
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