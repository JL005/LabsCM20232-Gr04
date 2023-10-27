package com.example.compose.jetchat.intents

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private const val REQUEST_IMAGE_CAPTURE = 1

@SuppressLint("QueryPermissionsNeeded")
fun openCamera(context: Context) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (takePictureIntent.resolveActivity(context.packageManager) != null) {
        val photoFile: File? = createImageFile()
        if (photoFile != null) {
            val photoURI: Uri = FileProvider.getUriForFile(
                context,
                "com.example.compose.jetchat.fileprovider",
                photoFile
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            (context as Activity).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }
}


private fun createImageFile(): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    if (storageDir != null && !storageDir.exists()) {
        storageDir.mkdirs()
    }
    return File(storageDir,"JPEG_${timeStamp}_.jpg")
}

fun openGalleryToViewRecentPhotos(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.type = "image/*"
    intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
    context.startActivity(intent)
}