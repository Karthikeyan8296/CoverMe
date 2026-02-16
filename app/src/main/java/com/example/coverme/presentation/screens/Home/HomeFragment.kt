package com.example.coverme.presentation.screens.Home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.coverme.R
import com.example.coverme.presentation.adaptor.PhotoPagingAdaptor
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    fun openPhotoDetails(id: String) {

        val dialog = PhotoDetails().apply {
            arguments = Bundle().apply {
                putString("photo_id", id)
            }
        }
        dialog.show(parentFragmentManager, "PhotoDetails")
    }

    private val viewModel: HomeViewModel by viewModels()
    private val adaptor = PhotoPagingAdaptor(
        onItemClick = {
            openPhotoDetails(it)
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycle = view.findViewById<RecyclerView>(R.id.recycleView)

        val layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)

        recycle.layoutManager = layoutManager
        recycle.adapter = adaptor
        lifecycleScope.launch {
            viewModel.photos.collect {
                adaptor.submitData(it)
            }
        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, FavFragment())
                .addToBackStack(null).commit()
        }
    }
}