package com.picmorrow.feature.taskdetails.domain

interface TaskPhotoStorage {
    suspend fun saveToGallery(photoPath: String)

    suspend fun deleteLocalPhoto(photoPath: String)
}
