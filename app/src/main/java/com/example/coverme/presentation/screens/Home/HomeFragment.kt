package com.example.coverme.presentation.screens.Home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.coverme.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    val viewModel: HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.findViewById<ImageView>(R.id.imageView)
        val errorText = view.findViewById<TextView>(R.id.txtError)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (state.isLoading) {
                    view.findViewById<TextView>(R.id.txtError).text = "Loading..."
                }
                errorText.text = state.error

                state.image?.let { url ->
                    Glide.with(requireContext()).load(url).into(image)

                }
            }
        }
    }
}