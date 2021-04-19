package com.example.words.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.words.ui.WordsApplication
import java.io.*

class HomeViewModel : ViewModel() {
    val onGetContentFileEvent = MutableLiveData<Boolean>()
    val onGetContentFileError = MutableLiveData<Boolean>()


    fun saveFile(content: String, context: Context) {
        val fos: FileOutputStream

        try {
            fos = context?.openFileOutput(WordsApplication.FILE, Context.MODE_PRIVATE)!!
            val out = ObjectOutputStream(fos)
            out.writeObject(content)
            fos.close()

            WordsApplication.fileText = content

            onGetContentFileEvent.value = true

        } catch (e: FileNotFoundException) {
            onGetContentFileError.value = true

        } catch (e: IOException) {
            onGetContentFileError.value = true
        }
    }
}