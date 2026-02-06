package com.example.coverme.presentation.screens.Home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.coverme.R
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PhotoDetails : DialogFragment(R.layout.fragment_photo_details) {
    val viewModel: PhotoDetailsViewModel by viewModels()
    private var isLiked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoId = arguments?.getString("photo_id")
        viewModel.getImages(photoId)
        val mainImage = view.findViewById<ImageView>(R.id.detailImg)
        val error = view.findViewById<TextView>(R.id.idTxt)
        val heart = view.findViewById<ImageView>(R.id.heartIcon)

        heart.setOnClickListener {
            isLiked = !isLiked
            heart.setImageResource(
                if(isLiked) R.drawable.heart_filled else R.drawable.heart
            )

            heart.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100)
                .withEndAction {
                    heart.animate().scaleX(1f).scaleY(1f).duration = 100
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                Glide.with(requireContext()).load(it.image)
                    .transform(GranularRoundedCorners(75f, 75f, 0f, 0f))
                    .into(mainImage)
                error.text = it.error
            }
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        }
    }
}