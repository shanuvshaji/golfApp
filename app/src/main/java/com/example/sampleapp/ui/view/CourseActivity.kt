package com.example.sampleapp.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.databinding.SearchActivityBinding
import com.example.sampleapp.ui.adapters.CourseAdapter
import com.example.sampleapp.ui.viewmodel.CourseViewModel
import com.example.sampleapp.utils.Resource
import com.example.sampleapp.utils.addDebouncedTextWatcher
import com.example.sampleapp.utils.hide
import com.example.sampleapp.utils.hideKeyboard
import com.example.sampleapp.utils.onSingleClick
import com.example.sampleapp.utils.show
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.toImmutableList

@AndroidEntryPoint
class CourseActivity : AppCompatActivity() {

    private val viewModel: CourseViewModel by viewModels()
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var binding: SearchActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        courseAdapter = CourseAdapter()

// Set Adapter

        binding.searchRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@CourseActivity)
            adapter = courseAdapter
            itemAnimator = null
        }
        binding.closeIcon.onSingleClick {
            binding.searchInput.setText("")
            binding.closeIcon.hide()
            courseAdapter.submitList(emptyList())
            binding.searchInput.clearFocus()
            hideKeyboard()
        }

// Adding text watcher

        binding.searchInput.addDebouncedTextWatcher(500L, lifecycleScope) { text ->
            val query = text.toString()
            if(query.length>2)
            {
                binding.closeIcon.show()
                viewModel.fetchCourses(query)
            }else
            {
                binding.closeIcon.hide()
                binding.emptyView.hide()
                binding.searchRecyclerview.hide()
            }
        }

// Call API Service

        viewModel.courses.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.shimmerView.show()
                    binding.shimmerView.startShimmer()
                    binding.searchRecyclerview.hide()
                    binding.emptyView.hide()
                }

                is Resource.Success -> {
                    binding.shimmerView.hide()
                    binding.shimmerView.stopShimmer()
                    if(!result.data.isNullOrEmpty())
                    {
                        binding.emptyView.hide()
                        courseAdapter.submitList(null)
                        courseAdapter.submitList(result.data.toMutableList()){
                            binding.searchRecyclerview.show()
                        }
                    }else
                    {
                        showEmptyView()
                    }
                }
                is Resource.Error -> {
                    showEmptyView()
                }
            }
        }
    }

// Function to show Empty View

    private fun showEmptyView() {
        binding.shimmerView.hide()
        binding.shimmerView.stopShimmer()
        binding.emptyView.show()
        binding.searchRecyclerview.hide()
    }
}
