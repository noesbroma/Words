package com.example.words.ui.update

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.words.R
import java.io.*


class UpdateFragment : Fragment() {

    private lateinit var updateViewModel: UpdateViewModel
    private var FILE_SELECT_CODE: Int = 1
    private val CREATE_REQUEST_CODE = 40
    private val OPEN_REQUEST_CODE = 41
    private val SAVE_REQUEST_CODE = 42
    val FILE = "file.ser"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_update, container, false)
        //val textView: TextView = root.findViewById(R.id.text_dashboard)
        val uploadButton: Button = root.findViewById(R.id.uploadButton)


        updateViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })


        uploadButton.setOnClickListener {
            /*val fileintent = Intent(Intent.ACTION_GET_CONTENT)
            fileintent.setType("text/plain")
            fileintent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(fileintent, FILE_SELECT_CODE)*/

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/plain"
            startActivityForResult(intent, OPEN_REQUEST_CODE)

        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        //val fileText: TextView = root.findViewById(R.id.fileText)
        var currentUri: Uri? = null

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_REQUEST_CODE) {
                if (resultData != null) {
                    //fileText.text = ""
                }
           /* } else if (requestCode == SAVE_REQUEST_CODE) {
                resultData?.let {
                    currentUri = it.data
                    writeFileContent(currentUri)
                }*/
            } else if (requestCode == OPEN_REQUEST_CODE) {

                resultData?.let {
                    currentUri = it.data

                    try {
                        val content = readFileContent(currentUri)
                        saveFile(content)
                        val a = ""
                        //fileText.text = content
                    } catch (e: IOException) {
                        // Handle error here
                    }

                }
            }
        }
    }


    fun newFile(view: View) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, "newfile.txt")

        startActivityForResult(intent, CREATE_REQUEST_CODE)
    }


    private fun readFileContent(uri: Uri?): String {
        val inputStream = requireActivity().contentResolver.openInputStream(uri!!)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()


        var currentline = reader.readLine()

        while (currentline != null) {
            stringBuilder.append(currentline + "\n")
            currentline = reader.readLine()
        }

        inputStream?.close()

        return stringBuilder.toString()
    }

    private fun saveFile(content: String) {
        val fos: FileOutputStream

        try {
            fos = context?.openFileOutput(FILE, Context.MODE_PRIVATE)!!
            val out = ObjectOutputStream(fos)
            out.writeObject(content)
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

   /* fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            val content_describer = data.data
            val src = content_describer!!.path
            source = File(src)
            Log.d("src is ", source.toString())
            val filename = content_describer.lastPathSegment
            text.setText(filename)
            Log.d("FileName is ", filename)
            destination = File(Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/Test/TestTest/" + filename)
            Log.d("Destination is ", destination.toString())
            SetToFolder.setEnabled(true)
        }
    }*/
}