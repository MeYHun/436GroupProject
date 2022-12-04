package com.malkinfo.editingrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.malkinfo.editingrecyclerview.model.UserData
import com.malkinfo.editingrecyclerview.model.ProData
import com.malkinfo.editingrecyclerview.model.myProData
import com.malkinfo.editingrecyclerview.view.TaskAdapter

class ProViewMainActivity : AppCompatActivity() {
    private lateinit var addsBtn: Button
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter:TaskAdapter
    private lateinit var proList:ArrayList<ProData>
    private lateinit var myProList: ArrayList<myProData>
    private lateinit var myPro:ArrayList<ProData>
    private lateinit var mDatabase: DatabaseReference
    val user = Firebase.auth.currentUser
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var uid: String





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user?.let {
            name = user.displayName.toString()
            email = user.email.toString()
            uid = user.uid
        }

        /**set List*/
        mDatabase = Firebase.database.reference
        userList = ArrayList()
        proList = ArrayList()
        myProList = ArrayList()
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
            userList.add(UserData(name, email, uid, false))

            println("-----"+proList+"-----")
            addsBtn = findViewById(R.id.reviewbtn)
            recv = findViewById(R.id.mRecycler)
            /**set Adapter*/
            userAdapter = TaskAdapter(this, mDatabase, myProList, proList, UserData(name, email, uid, false) ,userList)
            /**setRecycler view Adapter*/
            recv.layoutManager = LinearLayoutManager(this)

            recv.adapter = userAdapter
            addsBtn.setOnClickListener {review()}
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

    private fun review() {
        var intent = Intent(this,MyProViewMainActivity::class.java)
        startActivity(intent)
    }


}