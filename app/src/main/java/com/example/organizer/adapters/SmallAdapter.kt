package com.example.organizer.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.organizer.R
import com.example.organizer.activities.FullListActivity
import com.example.organizer.database.DailyActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.stringify
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class SmallAdapter : RecyclerView.Adapter<SmallAdapter.SmallViewHolder>() {

    var activitiesList = mutableListOf<DailyActivity>()

    fun setItems(activities: MutableList<DailyActivity>) {
        activitiesList.addAll(activities)
        notifyDataSetChanged()
    }

    fun clearItems() {
        activitiesList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.small_activity_layout, parent, false)
        return SmallViewHolder(view)
    }

    @ImplicitReflectionSerializer
    override fun onBindViewHolder(holder: SmallViewHolder, position: Int) {

        val activity = activitiesList[position]
        val timeTextView = holder.itemView.findViewById<TextView>(R.id.small_time_text_view)
        val activityTextView = holder.itemView.findViewById<TextView>(R.id.small_activity_text_view)
        val notesTextView = holder.itemView.findViewById<TextView>(R.id.small_notes_text_view)

        timeTextView.text = activity.time
        activityTextView.text = activity.activity
        notesTextView.text = activity.notes

        holder.itemView.onClick {
            GlobalScope.launch(Dispatchers.Main) {
                val activitiesListJson = async(Dispatchers.IO) {
                    Json.stringify(activitiesList)
                }.await()
                holder.itemView.context.startActivity<FullListActivity>("activitiesListJson" to activitiesListJson)
            }
        }

    }

    override fun getItemCount(): Int = activitiesList.size

    inner class SmallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}