package com.malkinfo.editingrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.malkinfo.editingrecyclerview.model.ProData
import com.malkinfo.editingrecyclerview.model.UserData
import com.malkinfo.editingrecyclerview.model.myProData
import com.malkinfo.editingrecyclerview.view.TaskAdapter
import com.malkinfo.editingrecyclerview.view.myTaskAdapter

class MyProViewMainActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
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
        var proName: String
        var proEmail: String
        var proDescription: String
        var proReview: String

        mDatabase.child("Users").child(uid).child("myPros").get().addOnSuccessListener {
            for (pro in it.children) {
                proName = pro.child("proName").value.toString()
                proEmail = pro.child("proEmail").value.toString()
                proDescription = pro.child("proDescription").value.toString()
                proReview = pro.child("proReview").value.toString()
//                Log.d("debug", "HIHIHIHIHIHIHIHI"+ ()
                myProList.add(myProData(proName, proEmail, proDescription, proReview,
                    pro.child("proRating").value.toString().toFloat()
                )
                )
            }


            userList.add(UserData(name, email, uid, false))
            println("-----" + myProList + "-----")
            recv = findViewById(R.id.mRecycler)
            /**set Adapter*/
            userAdapter = myTaskAdapter(this, mDatabase, myProList, proList, UserData(name, email, uid, false),userList)

            /**setRecycler view Adapter*/
            recv.layoutManager = LinearLayoutManager(this)

            recv.adapter = userAdapter
        }

    }

    /**ok now run this */

}