package com.example.words.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.words.R
import java.io.FileNotFoundException
import java.io.ObjectInputStream

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    val FILE = "file.ser"
    var text = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        getFile()

        return root
    }


    fun getFile() {
        try {
            val fileIn = context?.openFileInput(FILE)
            val `in` = ObjectInputStream(fileIn)
            text = `in`.readObject() as String
            `in`.close()
            fileIn?.close()
        } catch (e: FileNotFoundException) {
            text = ""
        } catch (e: Exception) {
        } finally {
            if (text == null) {
                text = ""
            }
        }
    }
}