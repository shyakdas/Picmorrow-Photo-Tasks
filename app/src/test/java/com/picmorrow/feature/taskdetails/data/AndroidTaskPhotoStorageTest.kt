package com.picmorrow.feature.taskdetails.data

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import app.cash.paparazzi.Paparazzi
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

class AndroidTaskPhotoStorageTest {
    @get:Rule
    val paparazzi = Paparazzi()

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun saveToGalleryCopiesPhotoAndPublishesMediaEntry() = runTest {
        val photoBytes = byteArrayOf(1, 2, 3, 4)
        val photo = temporaryFolder.newFile("photo.jpg").apply { writeBytes(photoBytes) }
        val resolver = Mockito.mock(ContentResolver::class.java)
        val imageUri = Mockito.mock(Uri::class.java)
        val output = ByteArrayOutputStream()
        val storage = storage(resolver)
        Mockito.`when`(
            resolver.insert(Mockito.any(Uri::class.java), Mockito.any(ContentValues::class.java)),
        ).thenReturn(imageUri)
        Mockito.`when`(resolver.openOutputStream(imageUri)).thenReturn(output)

        storage.saveToGallery(photo.path)

        assertArrayEquals(photoBytes, output.toByteArray())
        val insertedValues = ArgumentCaptor.forClass(ContentValues::class.java)
        Mockito.verify(resolver).insert(Mockito.any(Uri::class.java), insertedValues.capture())
        assertEquals("photo.jpg", insertedValues.value.getAsString(MediaStore.Images.Media.DISPLAY_NAME))
        assertEquals("image/jpeg", insertedValues.value.getAsString(MediaStore.Images.Media.MIME_TYPE))
        assertEquals(
            "${Environment.DIRECTORY_PICTURES}/Picmorrow",
            insertedValues.value.getAsString(MediaStore.Images.Media.RELATIVE_PATH),
        )
        assertEquals(1, insertedValues.value.getAsInteger(MediaStore.Images.Media.IS_PENDING))

        val publishedValues = ArgumentCaptor.forClass(ContentValues::class.java)
        Mockito.verify(resolver).update(
            Mockito.eq(imageUri),
            publishedValues.capture(),
            Mockito.isNull(),
            Mockito.isNull(),
        )
        assertEquals(0, publishedValues.value.getAsInteger(MediaStore.Images.Media.IS_PENDING))
        Mockito.verify(resolver, Mockito.never()).delete(imageUri, null, null)
    }

    @Test
    fun saveToGalleryReusesExistingPublishedCopy() = runTest {
        val photo = temporaryFolder.newFile("photo.jpg")
        val resolver = Mockito.mock(ContentResolver::class.java)
        val cursor = Mockito.mock(Cursor::class.java)
        val storage = storage(resolver)
        Mockito.`when`(cursor.moveToFirst()).thenReturn(true)
        Mockito.`when`(
            resolver.query(
                Mockito.any(Uri::class.java),
                Mockito.any(Array<String>::class.java),
                Mockito.anyString(),
                Mockito.any(Array<String>::class.java),
                Mockito.isNull(),
            ),
        ).thenReturn(cursor)

        storage.saveToGallery(photo.path)

        Mockito.verify(resolver, Mockito.never()).insert(
            Mockito.any(Uri::class.java),
            Mockito.any(ContentValues::class.java),
        )
        Mockito.verify(cursor).close()
    }

    @Test
    fun pendingCopyDoesNotPreventGalleryRetry() = runTest {
        val photo = temporaryFolder.newFile("photo.jpg")
        val resolver = Mockito.mock(ContentResolver::class.java)
        val cursor = Mockito.mock(Cursor::class.java)
        val imageUri = Mockito.mock(Uri::class.java)
        val storage = storage(resolver)
        Mockito.`when`(cursor.moveToFirst()).thenReturn(false)
        Mockito.`when`(
            resolver.query(
                Mockito.any(Uri::class.java),
                Mockito.any(Array<String>::class.java),
                Mockito.anyString(),
                Mockito.any(Array<String>::class.java),
                Mockito.isNull(),
            ),
        ).thenReturn(cursor)
        Mockito.`when`(
            resolver.insert(Mockito.any(Uri::class.java), Mockito.any(ContentValues::class.java)),
        ).thenReturn(imageUri)
        Mockito.`when`(resolver.openOutputStream(imageUri)).thenReturn(ByteArrayOutputStream())

        storage.saveToGallery(photo.path)

        Mockito.verify(resolver).insert(
            Mockito.any(Uri::class.java),
            Mockito.any(ContentValues::class.java),
        )
        Mockito.verify(cursor).close()
    }

    @Test
    fun saveToGalleryRejectsMissingPhoto() {
        val storage = storage(Mockito.mock(ContentResolver::class.java))

        assertThrows(IllegalArgumentException::class.java) {
            runTest { storage.saveToGallery(temporaryFolder.root.resolve("missing.jpg").path) }
        }
    }

    @Test
    fun saveToGalleryFailsWhenMediaEntryCannotBeCreated() {
        val photo = temporaryFolder.newFile("photo.jpg")
        val resolver = Mockito.mock(ContentResolver::class.java)
        val storage = storage(resolver)

        assertThrows(IllegalStateException::class.java) {
            runTest { storage.saveToGallery(photo.path) }
        }
    }

    @Test
    fun saveToGalleryDeletesPendingEntryWhenOutputCannotBeOpened() {
        val photo = temporaryFolder.newFile("photo.jpg")
        val resolver = Mockito.mock(ContentResolver::class.java)
        val imageUri = Mockito.mock(Uri::class.java)
        val storage = storage(resolver)
        Mockito.`when`(
            resolver.insert(Mockito.any(Uri::class.java), Mockito.any(ContentValues::class.java)),
        ).thenReturn(imageUri)
        Mockito.`when`(resolver.openOutputStream(imageUri)).thenReturn(null)

        assertThrows(IllegalStateException::class.java) {
            runTest { storage.saveToGallery(photo.path) }
        }
        Mockito.verify(resolver).delete(imageUri, null, null)
        Mockito.verify(resolver, Mockito.never()).update(
            Mockito.eq(imageUri),
            Mockito.any(ContentValues::class.java),
            Mockito.isNull(),
            Mockito.isNull(),
        )
    }

    @Test
    fun deleteLocalPhotoDeletesExistingFileAndAcceptsMissingFile() = runTest {
        val storage = storage(Mockito.mock(ContentResolver::class.java))
        val photo = temporaryFolder.newFile("photo.jpg")

        storage.deleteLocalPhoto(photo.path)
        storage.deleteLocalPhoto(photo.path)

        assertFalse(photo.exists())
    }

    @Test
    fun deleteLocalPhotoFailsWhenPathCannotBeDeleted() {
        val storage = storage(Mockito.mock(ContentResolver::class.java))
        val directory = temporaryFolder.newFolder("photo").apply {
            resolve("content").writeText("not empty")
        }

        assertThrows(IllegalStateException::class.java) {
            runTest { storage.deleteLocalPhoto(directory.path) }
        }
        assertTrue(directory.exists())
    }

    private fun storage(resolver: ContentResolver): AndroidTaskPhotoStorage {
        val context = Mockito.mock(Context::class.java)
        val applicationContext = Mockito.mock(Context::class.java)
        Mockito.`when`(context.applicationContext).thenReturn(applicationContext)
        Mockito.`when`(applicationContext.contentResolver).thenReturn(resolver)
        return AndroidTaskPhotoStorage(context)
    }
}
