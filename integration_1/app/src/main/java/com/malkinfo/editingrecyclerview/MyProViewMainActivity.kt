package com.malkinfo.editingrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.malkinfo.editingrecyclerview.model.ProData
import com.malkinfo.editingrecyclerview.model.TaskData
import com.malkinfo.editingrecyclerview.model.myProData
import com.malkinfo.editingrecyclerview.view.TaskAdapter
import com.malkinfo.editingrecyclerview.view.myTaskAdapter

class MyProViewMainActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<TaskData>
    private lateinit var userAdapter:myTaskAdapter
    private lateinit var proList:ArrayList<ProData>
    private lateinit var myProList:ArrayList<myProData>
    private lateinit var myPro:ArrayList<ProData>
    private lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mymain)
        /**set List*/
        mDatabase = Firebase.database.reference
        userList = ArrayList()
        proList = ArrayList()
        myProList = ArrayList()
        myPro = ArrayList()
        var proName: String
        var proEmail: String
        var proDescription: String

        mDatabase.child("myPros").get().addOnSuccessListener {
            for (pro in it.children) {
                proName = pro.child("proName").value.toString()
                proEmail = "Email: " + pro.child("proEmail").value.toString()
                proDescription = "Description: " + pro.child("proDescription").value.toString()
                proList.add(ProData(proName, proEmail, proDescription))
            }
            println("-----" + proList + "-----")
            recv = findViewById(R.id.mRecycler)
            /**set Adapter*/
            userAdapter = myTaskAdapter(this, mDatabase, myProList, proList)
            /**setRecycler view Adapter*/
            recv.layoutManager = LinearLayoutManager(this)

            recv.adapter = userAdapter
        }

    }
//    private lateinit var addsBtn:FloatingActionButton
//    private lateinit var recv:RecyclerView
//    private lateinit var userList:ArrayList<TaskData>
//    private lateinit var userAdapter:TaskAdapter
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_mymain)
//        /**set List*/
//        userList = ArrayList()
//        /**set find Id*/
//
//        recv = findViewById(R.id.mRecycler)
//        /**set Adapter*/
//        userAdapter = TaskAdapter(this,userList)
//        /**setRecycler view Adapter*/
//        recv.layoutManager = LinearLayoutManager(this)
//        recv.adapter = userAdapter
//        /**set Dialog*/
//        addsBtn.setOnClickListener { addInfo() }
//
//    }
//
//    private fun addInfo() {
//        val inflter = LayoutInflater.from(this)
//        val v = inflter.inflate(R.layout.add_item,null)
//        /**set view*/
//        val userName = v.findViewById<EditText>(R.id.taskName)
//        val userNo = v.findViewById<EditText>(R.id.taskDescription)
//
//        val addDialog = AlertDialog.Builder(this)
//
//        addDialog.setView(v)
//        addDialog.setPositiveButton("Ok"){
//                dialog,_->
//            val names = userName.text.toString()
//            val description = userNo.text.toString()
//            userList.add(TaskData("Task: $names","Description : $description"))
//            userAdapter.notifyDataSetChanged()
//            Toast.makeText(this,"Adding User Information Success",Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        }
//        addDialog.setNegativeButton("Cancel"){
//                dialog,_->
//            dialog.dismiss()
//            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
//
//        }
//        addDialog.create()
//        addDialog.show()
//    }
    /**ok now run this */

}