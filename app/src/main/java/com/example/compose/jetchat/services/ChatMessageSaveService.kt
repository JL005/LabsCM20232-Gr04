package com.example.compose.jetchat.services

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ChatMessageSaveService : IntentService("ChatSaveService") {
    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val fileName = intent.getStringExtra("file_name")
            val fileContent = intent.getStringExtra("file_content")

            if (fileName != null && fileContent != null) {
                saveFile(fileName, fileContent)
            }
        }
    }

    private fun saveFile(fileName: String, fileContent: String) {
        try {
            val storageDir: File? =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            if (storageDir != null && !storageDir.exists()) {
                storageDir.mkdirs()
            }
            val file = File(storageDir, "$fileName.txt")
            Log.d("ChatMessageSaveService", "saveFile: ${file.absolutePath}")
            val fos = FileOutputStream(file)
            fos.write(fileContent.toByteArray())
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}