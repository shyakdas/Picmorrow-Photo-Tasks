package com.picmorrow.feature.taskdetails.data

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import com.picmorrow.feature.taskdetails.domain.TaskPhotoStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

internal class AndroidTaskPhotoStorage(context: Context) : TaskPhotoStorage {
    private val contentResolver = context.applicationContext.contentResolver

    @Suppress("TooGenericExceptionCaught")
    override suspend fun saveToGallery(photoPath: String) {
        withContext(Dispatchers.IO) {
            val source = File(photoPath)
            require(source.isFile) { "The task photo is unavailable" }

            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, source.name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/Picmorrow")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
            val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val imageUri = checkNotNull(contentResolver.insert(collection, values)) {
                "Unable to create the gallery image"
            }

            try {
                val output = checkNotNull(contentResolver.openOutputStream(imageUri)) {
                    "Unable to open the gallery image"
                }
                output.use { target -> source.inputStream().use { it.copyTo(target) } }
                contentResolver.update(
                    imageUri,
                    ContentValues().apply { put(MediaStore.Images.Media.IS_PENDING, 0) },
                    null,
                    null,
                )
            } catch (exception: Exception) {
                contentResolver.delete(imageUri, null, null)
                throw exception
            }
        }
    }

    override suspend fun deleteLocalPhoto(photoPath: String) = withContext(Dispatchers.IO) {
        val photo = File(photoPath)
        check(!photo.exists() || photo.delete()) { "Unable to delete the task photo" }
    }
}
