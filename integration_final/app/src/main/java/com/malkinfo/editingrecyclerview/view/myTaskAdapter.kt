package com.malkinfo.editingrecyclerview.view

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.malkinfo.editingrecyclerview.R
import com.malkinfo.editingrecyclerview.model.ProData
import com.malkinfo.editingrecyclerview.model.UserData
import com.malkinfo.editingrecyclerview.model.myProData
import org.w3c.dom.Text


class myTaskAdapter(val c:Context, val mdatabase: DatabaseReference, val myProList: ArrayList<myProData>, val proList:ArrayList<ProData>, val user: UserData, val userList:ArrayList<UserData>):RecyclerView.Adapter<myTaskAdapter.UserViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        var proName:TextView
        var proEmail:TextView
        var proDescription:TextView
        var proReview:TextView
        var proRating:TextView
        var mMenus:ImageView
        var counter:Int

        init {
            proName = itemView.findViewById<TextView>(R.id.mTitle1)
            proEmail = itemView.findViewById<TextView>(R.id.mSubTitle1)
            proDescription = itemView.findViewById<TextView>(R.id.mDescription1)
            proReview = itemView.findViewById<TextView>(R.id.mReview1)
            proRating = itemView.findViewById<TextView>(R.id.mRating1)
            mMenus = itemView.findViewById<ImageView>(R.id.mMenus1)
            mMenus.setOnClickListener { popupMenus(it) }
            counter = 0
        }

        private fun popupMenus(v:View) {
            val position = myProList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.review_show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.review -> {

                        val v = LayoutInflater.from(c).inflate(R.layout.add_item, null)
                        val review = v.findViewById<EditText>(R.id.proReview)
                        val rating = v.findViewById<RatingBar>(R.id.proRating)

                        Log.d("debug", "before review ${review.text} , rating ${rating.rating}")
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok") { dialog, _ ->
                                Log.d("debug", "positive button clicked")

                                myProList[adapterPosition].proReview = review.text.toString()
                                myProList[adapterPosition].proRating = rating.rating
                                myProList[adapterPosition].proName = position.proName
                                myProList[adapterPosition].proDescription = position.proDescription
                                myProList[adapterPosition].proEmail = position.proEmail


                                Log.d("debug", myProList[adapterPosition].toString())

                                mdatabase.child("Users").child(user.UID).child("myPros").child(myProList[adapterPosition].proName).setValue(
                                    myProData(myProList[adapterPosition].proName,
                                        myProList[adapterPosition].proEmail,
                                        myProList[adapterPosition].proDescription,
                                        myProList[adapterPosition].proReview,
                                        myProList[adapterPosition].proRating)
                                )

//                                        myProList[adapterPosition].proRating.toString()+myProList[adapterPosition].proReview)
//                                mdatabase.child("myPros").child("myPro" + adapterPosition.toString())
//                                    .setValue(
//                                        myProData(
//                                            myProList[adapterPosition].proName,
//                                            myProList[adapterPosition].proEmail,
//                                            myProList[adapterPosition].proDescription,
//                                            myProList[adapterPosition].proReview,
//                                            myProList[adapterPosition].proRating





                                notifyDataSetChanged()
                                Log.d("debug2", "Inside positive button2"+proRating.toString()+proReview)
                                Toast.makeText(c, "User Information is Edited", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                                Log.d("debug", "review2 ${review.text} , rating ${rating.rating}")
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                Log.d("debug", "negative button clicked")
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    else -> true
                }
            }


            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }


    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myTaskAdapter.UserViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val proView = inflater.inflate(R.layout.review_list_item, parent, false)
        // Return a new holder instance
        return UserViewHolder(proView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: myTaskAdapter.UserViewHolder, position: Int) {
        viewHolder.proName.text = myProList[position].proName.toString()
        viewHolder.proEmail.text = myProList[position].proEmail.toString()
        viewHolder.proDescription.text = myProList[position].proDescription.toString()
        viewHolder.proReview.text = "Review: " +myProList[position].proReview
        viewHolder.proRating.text = "Rating: " +myProList[position].proRating.toString()
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return myProList.size
    }


}