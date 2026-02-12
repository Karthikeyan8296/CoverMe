package com.example.coverme.data.mapper

import com.example.coverme.data.remote.DTO.PhotoDTO.PhotoDTOItem
import com.example.coverme.domain.models.PhotoModel

fun PhotoDTOItem.toDomain(): PhotoModel {
    return PhotoModel(
        blur_hash = blur_hash,
        color = color,
        created_at = created_at,
        description = description,
        height = height,
        id = id,
        updated_at = updated_at,
        urls = urls,
        user = user,
        width = width
    )
}