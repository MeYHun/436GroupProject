package com.malkinfo.editingrecyclerview.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.malkinfo.editingrecyclerview.R
import com.malkinfo.editingrecyclerview.model.ProData



class TaskAdapter(val c:Context, val mdatabase: DatabaseReference, val proList:ArrayList<ProData>):RecyclerView.Adapter<TaskAdapter.UserViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        var proName:TextView
        var proEmail:TextView
        var proDescription:TextView
        var mMenus:ImageView
        var counter:Int

        init {
            proName = itemView.findViewById<TextView>(R.id.mTitle)
            proEmail = itemView.findViewById<TextView>(R.id.mSubTitle)
            proDescription = itemView.findViewById<TextView>(R.id.mDescription)
            mMenus = itemView.findViewById<ImageView>(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
            counter = 0
        }
        private fun popupMenus(v:View) {
            val position = proList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.select->{
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Select")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure you want to select this Pro")
                            .setPositiveButton("Yes"){
                                    dialog,_->
//                                mdatabase.add(ProData(proList[adapterPosition].proName,proList[adapterPosition].proEmail, proList[adapterPosition].proDescription))
//                                println(myPro)
                                mdatabase.child("myPros").child("Pro"+counter.toString()).setValue(
                                    ProData(proList[adapterPosition].proName, proList[adapterPosition].proEmail, proList[adapterPosition].proDescription)
                                )
                                notifyDataSetChanged()
                                Toast.makeText(c,"Pro Selected",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.UserViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val proView = inflater.inflate(R.layout.list_item, parent, false)
        // Return a new holder instance
        return UserViewHolder(proView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: TaskAdapter.UserViewHolder, position: Int) {
        viewHolder.proName.text = proList[position].proName.toString()
        viewHolder.proEmail.text = proList[position].proEmail.toString()
        viewHolder.proDescription.text = proList[position].proDescription.toString()
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return proList.size
    }


}
