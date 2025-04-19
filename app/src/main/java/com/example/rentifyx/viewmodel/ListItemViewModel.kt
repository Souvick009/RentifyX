package com.example.rentifyx.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ListItemViewModel(applicationContext: Application) : AndroidViewModel(applicationContext) {

    suspend fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                null
            }
        }
    }

    suspend fun compressBitmap(context: Context, bitmap: Bitmap?): File? {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.webp")
                val fileOutputStream = FileOutputStream(file)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    bitmap?.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 80, fileOutputStream)
                }
                fileOutputStream.flush()
                fileOutputStream.close()
                file
            } catch (exception: Exception) {
                exception.printStackTrace()
                null
            }
        }
    }

    fun compressImages(context: Context, imageList: List<Uri>, onResult: (List<File>) -> Unit) {
        viewModelScope.launch {
            val compressedImages = imageList.mapNotNull { uri ->
                val bitmap = uriToBitmap(context, uri)
                bitmap?.let {
                    compressBitmap(context, bitmap)
                }
            }
            onResult(compressedImages)
        }
    }
}