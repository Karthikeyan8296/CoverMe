package com.example.coverme.presentation.screens.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.coverme.R
import com.example.coverme.presentation.adaptor.PhotoPagingAdaptor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //root - viewGroup, that is FrameLayout, LinearLayout
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    fun openPhotoDetails(id: String) {
        val dialog = PhotoDetails().apply {
            arguments = Bundle().apply {
                putString("photo_id", id)
            }
        }
        dialog.show(parentFragmentManager, "PhotoDetails")
    }

    private val viewModel: FavViewModel by viewModels()
    private val adaptor = PhotoPagingAdaptor(
        onItemClick = {
            openPhotoDetails(it)
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycle = view.findViewById<RecyclerView>(R.id.favRecycle)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        recycle.layoutManager = layoutManager
        recycle.adapter = adaptor

        viewModel.loadFavPhotos()

        lifecycleScope.launch {
            viewModel.photo.collect { list ->
                adaptor.submitData(PagingData.from(list))
            }
        }
    }
}