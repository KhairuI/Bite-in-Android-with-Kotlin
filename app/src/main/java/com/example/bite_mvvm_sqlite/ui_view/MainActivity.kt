package com.example.bite_mvvm_sqlite.ui_view

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bite_mvvm_sqlite.R
import com.example.bite_mvvm_sqlite.Utils.InsertDialogue
import com.example.bite_mvvm_sqlite.Utils.MyDB
import com.example.bite_mvvm_sqlite.adapter.MyAdapter
import com.example.bite_mvvm_sqlite.databinding.ActivityMainBinding
import com.example.bite_mvvm_sqlite.interfaces.AddInterface
import com.example.bite_mvvm_sqlite.model.Item
import com.example.bite_mvvm_sqlite.repository.GroceryRepo
import java.util.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    val AUDIO_PERMISSION= 1
    private lateinit var viewModel: MainViewModel
    private lateinit var allList:MutableList<Item>
    private lateinit var adapter: MyAdapter

    // listener
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myDB= MyDB(this)
        val repo= GroceryRepo(myDB)
        val factory= MainViewModelFactory(repo)

        binding.search.voiceImage.setOnClickListener(this)
        binding.insertBtn.setOnClickListener(this)

        viewModel= ViewModelProvider(this,factory).get(MainViewModel::class.java)

        // adapter
        adapter= MyAdapter(mutableListOf(),mutableListOf(),viewModel)
        binding.recycleView.layoutManager= LinearLayoutManager(this)
        binding.recycleView.adapter= adapter

        viewModel.getAllItem().observe(this, androidx.lifecycle.Observer {
            allList= it
            adapter.itemList= allList
            adapter.searchList= allList
            adapter.notifyDataSetChanged()
        })

        setRecord()
        binding.refresh.setOnRefreshListener {
            viewModel.getAllItem().observe(this, androidx.lifecycle.Observer {
                allList= it
                adapter.itemList= allList
                adapter.searchList= allList
                adapter.notifyDataSetChanged()
            })

            binding.refresh.isRefreshing= false
        }

        //  text search
        binding.search.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                adapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun setRecord() {
        speechRecognizer= SpeechRecognizer.createSpeechRecognizer(this)
        speechIntent= Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())

        // set listener...
        speechRecognizer.setRecognitionListener(object : RecognitionListener{
            override fun onReadyForSpeech(p0: Bundle?) {
               //binding.search.searchEditText.setText("Say something..")
                binding.voiceStatus.text= "Say something.."
            }

            override fun onBeginningOfSpeech() {
                //binding.search.searchEditText.setText("Listening....")
                binding.voiceStatus.text= "Listening...."
            }

            override fun onRmsChanged(p0: Float) {

            }

            override fun onBufferReceived(p0: ByteArray?) {

            }

            override fun onEndOfSpeech() {
               // binding.search.searchEditText.setText("Enter name")
                binding.voiceStatus.text= "Listening end ..."

            }

            override fun onError(p0: Int) {
                //binding.search.searchEditText.setText("Can't listening...")
                binding.voiceStatus.text= "Can't listening..."
            }

            override fun onResults(p0: Bundle?) {
                val data= p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                binding.search.searchEditText.setText(data!![0])
            }

            override fun onPartialResults(p0: Bundle?) {

            }

            override fun onEvent(p0: Int, p1: Bundle?) {

            }

        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.voiceImage -> {
                checkPermission(android.Manifest.permission.RECORD_AUDIO,"Record",AUDIO_PERMISSION)
            }
            R.id.insertBtn -> {
                InsertDialogue(this,object:AddInterface{
                    override fun addOnClick(item: Item) {
                        viewModel.insert(item)
                    }

                }).show()
            }
        }
    }

    private fun checkPermission(permission:String,name:String,code:Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext,permission) ==
                        PackageManager.PERMISSION_GRANTED -> {
                           /* Toast.makeText(this,"$name permission granted",
                                Toast.LENGTH_SHORT).show()*/
                            speechRecognizer.startListening(speechIntent)
                        }
                shouldShowRequestPermissionRationale(permission) ->{
                    showDialogue(permission,name,code)
                }
                else ->{
                    ActivityCompat.requestPermissions(this, arrayOf(permission),code)
                }
            }
        }
        else{
            Toast.makeText(this,"No need to permission",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialogue(permission: String, name: String, code: Int) {
        val builder= AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to access $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),code)
            }
        }
        builder.create().show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Permission deny",
                Toast.LENGTH_SHORT).show()
        }
        else{
            /*Toast.makeText(this,"Permission Success",
                Toast.LENGTH_SHORT).show()*/
            speechRecognizer.startListening(speechIntent)
           // speechRecognizer.stopListening()
        }
    }

}