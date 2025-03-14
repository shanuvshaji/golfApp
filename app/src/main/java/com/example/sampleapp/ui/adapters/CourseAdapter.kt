package com.example.sampleapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.R
import com.example.sampleapp.domain.model.CourseDomain

class CourseAdapter() :
    ListAdapter<CourseDomain,CourseAdapter.CourseViewHolder>(GolfCourseDiffCallback) {

    companion object{
        val GolfCourseDiffCallback = object : DiffUtil.ItemCallback<CourseDomain>() {
            override fun areItemsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val clubName: TextView = view.findViewById(R.id.search_item_club_name)
        private val courseName: TextView = view.findViewById(R.id.search_item_course_name)
        private val address: TextView = view.findViewById(R.id.search_item_address)

        fun bind(course: CourseDomain) {
            clubName.text = course.clubName
            courseName.text = course.courseName
            address.text = course.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_golf_search, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    fun updateCourses(newCourses: List<CourseDomain>?) {
        if (newCourses != null) {
          submitList(newCourses)
        }
    }

}
