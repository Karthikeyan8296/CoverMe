package com.example.coverme.presentation.screens.Home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coverme.R
import com.example.coverme.presentation.adaptor.PhotoPagingAdaptor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val adaptor = PhotoPagingAdaptor()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycle = view.findViewById<RecyclerView>(R.id.recycleView)
        view.findViewById<TextView>(R.id.logo)

        recycle.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycle.adapter = adaptor
        lifecycleScope.launch {
            viewModel.photos.collect {
                adaptor.submitData(it)
            }
        }
    }
}