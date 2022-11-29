package com.malkinfo.editingrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.malkinfo.editingrecyclerview.model.TaskData
import com.malkinfo.editingrecyclerview.model.ProData
import com.malkinfo.editingrecyclerview.view.TaskAdapter

class ProViewMainActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<TaskData>
    private lateinit var userAdapter:TaskAdapter
    private lateinit var proList:ArrayList<ProData>
    private lateinit var myPro:ArrayList<ProData>
    private lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**set List*/
        mDatabase = Firebase.database.reference
        userList = ArrayList()
        proList = ArrayList()
        myPro = ArrayList()
        var proName:String
        var proEmail:String
        var proDescription:String

        mDatabase.child("Pros").get().addOnSuccessListener{
            for (pro in it.children){
                proName = pro.child("proName").value.toString()
                proEmail = "Email: " + pro.child("proEmail").value.toString()
                proDescription = "Description: " + pro.child("proDescription").value.toString()
                proList.add(ProData(proName, proEmail, proDescription))
            }
            println("-----"+proList+"-----")
            addsBtn = findViewById(R.id.reviewbtn)
            recv = findViewById(R.id.mRecycler)
            /**set Adapter*/
            userAdapter = TaskAdapter(this, mDatabase, proList)
            /**setRecycler view Adapter*/
            recv.layoutManager = LinearLayoutManager(this)

            recv.adapter = userAdapter
            addsBtn.setOnClickListener {review()}
        }

        private fun review(){

        }

//        proList.add(ProData("Helen Keller",
//            "Email: hkeller@terpmail.umd.edu",
//            "Description: I am the best teacher for def and blind." +
//                    " I have those disabilities and am fairly confident that no one can teach better than I can"))
//        proList.add(ProData("Lionel Messi",
//            "Email: lmessi@terpmail.umd.edu",
//            "Description: I am the best soccer player" +
//                " I not only have talent but also have good work ethics. With me, you can learn what the best people with best talent can achieve"))
        /**set find Id*/

        /**set Dialog*/

    }

}