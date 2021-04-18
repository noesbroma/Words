package com.example.words.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.words.R
import com.example.words.ui.WordsApplication
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import www.sanju.motiontoast.MotionToast
import java.io.*

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()

            return fragment
        }
    }

    private val viewModel: HomeViewModel by viewModel()
    private val CREATE_REQUEST_CODE = 40
    private val OPEN_REQUEST_CODE = 41
    //val FILE = "file.ser"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setTxt("Esta es la home")

        setUI()
        setListeners()
    }


    fun setUI() {
        //text_home.text = viewModel.txt.value
    }


    fun setListeners() {
        uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/plain"
            startActivityForResult(intent, OPEN_REQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        var currentUri: Uri? = null

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_REQUEST_CODE) {
                if (resultData != null) {
                    //updateViewModel.text
                    //fileText.text = ""
                }
            } else if (requestCode == OPEN_REQUEST_CODE) {
                resultData?.let {
                    currentUri = it.data

                    try {
                        val content = readFileContent(currentUri)
                        saveFile(content)
                        //fileText.text = content
                    } catch (e: IOException) {
                        // Handle error here
                    }

                }
            }
        }
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
            fos = context?.openFileOutput(WordsApplication.FILE, Context.MODE_PRIVATE)!!
            val out = ObjectOutputStream(fos)
            out.writeObject(content)
            fos.close()

            fileText.text = content
            WordsApplication.fileText = content

            this.activity?.let {
                MotionToast.darkToast(
                    it,
                    "Upload success 😍",
                    "Congrats!!",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    this.context?.let { ResourcesCompat.getFont(it,R.font.helvetica_regular) })
            }
        } catch (e: FileNotFoundException) {
            this.activity?.let {
                MotionToast.darkToast(
                    it,
                    "Upload error 😍",
                    "Sorry!!",
                    MotionToast.TOAST_ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    this.context?.let { ResourcesCompat.getFont(it,R.font.helvetica_regular) })
            }

            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}