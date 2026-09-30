package com.picmorrow.feature.taskdetails.domain

interface TaskPhotoStorage {
    /** Ensures the photo has one published gallery copy. Repeated calls for the same path are idempotent. */
    suspend fun saveToGallery(photoPath: String)

    suspend fun deleteLocalPhoto(photoPath: String)
}
