package com.malkinfo.editingrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.malkinfo.editingrecyclerview.model.ProData
import com.malkinfo.editingrecyclerview.model.UserData
import com.malkinfo.editingrecyclerview.model.myProData
import com.malkinfo.editingrecyclerview.view.myTaskAdapter

class MyProViewMainActivity : AppCompatActivity() {
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter:myTaskAdapter
    private lateinit var proList:ArrayList<ProData>
    private lateinit var myProList:ArrayList<myProData>
    private lateinit var myPro:ArrayList<ProData>
    private lateinit var mDatabase: DatabaseReference
    val user = Firebase.auth.currentUser
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var uid: String



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
        var proReview: String

        mDatabase.child("myPros").get().addOnSuccessListener {
            for (pro in it.children) {
                proName = pro.child("proName").value.toString()
                proEmail = pro.child("proEmail").value.toString()
                proDescription = pro.child("proDescription").value.toString()
                proReview = pro.child("proReview").value.toString()
                myProList.add(myProData(proName, proEmail, proDescription, proReview,
                    pro.child("proRating").value.toString().toFloat()
                )
                )
            }
            userList.add(UserData(name, email, uid, false))
            println("-----" + myProList + "-----")
            recv = findViewById(R.id.mRecycler)
            /**set Adapter*/
            userAdapter = myTaskAdapter(this, mDatabase, myProList, proList, UserData(name, email, uid, false), userList)

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