package com.malkinfo.editingrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.malkinfo.editingrecyclerview.model.TaskData
import com.malkinfo.editingrecyclerview.model.ProData
import com.malkinfo.editingrecyclerview.view.TaskAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<TaskData>
    private lateinit var userAdapter:TaskAdapter
    private lateinit var proList:ArrayList<ProData>
    private lateinit var myPro:ArrayList<ProData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**set List*/
        userList = ArrayList()
        proList = ArrayList()
        myPro = ArrayList()
        proList.add(ProData("Helen Keller",
            "Email: hkeller@terpmail.umd.edu",
            "Description: I am the best teacher for def and blind." +
                    " I have those disabilities and am fairly confident that no one can teach better than I can"))
        proList.add(ProData("Lionel Messi",
            "Email: lmessi@terpmail.umd.edu",
            "Description: I am the best soccer player" +
                " I not only have talent but also have good work ethics. With me, you can learn what the best people with best talent can achieve"))
        /**set find Id*/
        recv = findViewById(R.id.mRecycler)
        /**set Adapter*/
        userAdapter = TaskAdapter(this, myPro, proList)
        /**setRecycler view Adapter*/
        recv.layoutManager = LinearLayoutManager(this)

        recv.adapter = userAdapter
        /**set Dialog*/

    }

}