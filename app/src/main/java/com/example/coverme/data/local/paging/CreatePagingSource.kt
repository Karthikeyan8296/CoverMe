package com.example.coverme.data.local.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coverme.data.remote.DTO.PhotoDTO.PhotoDTOItem
import com.example.coverme.data.remote.api.UnSplashAPI

class UnSplashPagingSource(
    private val api: UnSplashAPI
) : PagingSource<Int, PhotoDTOItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDTOItem> {
        return try {
            val page = params.key ?: 1

            val photos = api.getPhotos(
                page = page, per_page = params.loadSize
            )

            Log.d("Paging3", "Loading page $page with size ${params.loadSize}")

            LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )


        } catch (e: Exception) {
            Log.d("HELLOOOO", "error occur on random api ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoDTOItem>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}