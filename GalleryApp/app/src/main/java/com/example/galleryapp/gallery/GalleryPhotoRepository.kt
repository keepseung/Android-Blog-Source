package com.example.galleryapp.gallery

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GalleryPhotoRepository(
    private val context: Context
) {

    suspend fun getAllPhotos(): MutableList<GalleryPhoto> = withContext(Dispatchers.IO) {
        val galleryPhotoList = mutableListOf<GalleryPhoto>()
        // 외장 메모리에 있는 URI를 받도록 함
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        // 커서에 가져올 정보에 대해서 지정한다.
        val query: Cursor?
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DISPLAY_NAME, // 이름
            MediaStore.Images.ImageColumns.SIZE, // 크기
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATE_ADDED, // 추가된 날짜
            MediaStore.Images.ImageColumns._ID
        )
        // contentResolver를 사용해 가져오도록 함
        val resolver = context.contentResolver
        // 추가된 날짜의 내림차순으로 가져오도록 함
        query = resolver?.query(uriExternal, projection, null, null, "${MediaStore.Images.ImageColumns.DATE_ADDED} DESC")
        query?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val date = cursor.getString(dateColumn)

                val contentUri = ContentUris.withAppendedId(uriExternal, id)

                galleryPhotoList.add(
                    GalleryPhoto(
                        id,
                        uri = contentUri,
                        name = name,
                        date = date ?: "",
                        size = size
                    )
                )
            }
        }

        return@withContext galleryPhotoList
    }
}
