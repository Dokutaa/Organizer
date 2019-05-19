package com.example.organizer.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.organizer.R
import com.example.organizer.support.loadImage
import com.example.organizer.database.DailyActivity

class FullAdapter : RecyclerView.Adapter<FullAdapter.FullViewHolder>() {

    private var activitiesList = mutableListOf<DailyActivity>()

    fun setItems(activities: MutableList<DailyActivity>) {
        activitiesList.addAll(activities)
        notifyDataSetChanged()
    }

    fun clearItems() {
        activitiesList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.full_activity_layout, parent, false)
        return FullViewHolder(view)
    }

    override fun onBindViewHolder(holder: FullViewHolder, position: Int) {
        val activity = activitiesList[position]

        val timeTextView = holder.itemView.findViewById<TextView>(R.id.full_time_text_view)
        val addressItem = holder.itemView.findViewById<TextView>(R.id.address_item_view)
        val addressTextView = holder.itemView.findViewById<TextView>(R.id.full_address_text_view)
        val activityTextView = holder.itemView.findViewById<TextView>(R.id.full_activity_text_view)
        val notesTextView = holder.itemView.findViewById<TextView>(R.id.full_notes_text_view)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.full_image_view)

        timeTextView.text = activity.time
        presenceCheck(activity.address, ", , , Apt. No ", addressTextView)
        if (activity.address == ", , , Apt. No ") {
            addressItem.visibility = View.GONE
        }
        activityTextView.text = activity.activity
        presenceCheck(activity.notes, "", notesTextView)
        presenceCheck(activity.img, "", imageView)

    }

    override fun getItemCount(): Int = activitiesList.size

    private fun presenceCheck(item: String, evalStr: String, view: View) {

        if (view is TextView) {
            if (item == evalStr) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
                view.text = item
            }
        } else if (view is ImageView) {
            if (item == evalStr) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
                loadImage(item, view)
            }
        } else {
            throw error("Incorrect view for checking")
        }

    }

    inner class FullViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}