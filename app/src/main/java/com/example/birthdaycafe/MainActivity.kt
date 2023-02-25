package com.example.birthdaycafe

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.birthdaycafe.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate


class MainActivity : AppCompatActivity() {
    var customToolbar: Toolbar? = null
    var customTab: TabLayout? = null

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
        customToolbar = binding.toolbar
        setSupportActionBar(customToolbar)
        getSupportActionBar()?.setTitle("$getMonth"+"월");

        customTab = binding.members
        customTab!!.addTab(customTab!!.newTab().setText("테스트"))

        db.collection("March")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("CC", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("CC", "Error getting documents: ", exception)
            }

//        val docRef = db.collection("March").document("Dami")
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d("TT", "DocumentSnapshot data: ${document.data}")
//                } else {
//                    Log.d("TT", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TT", "get failed with ", exception)
//            }
    }
}