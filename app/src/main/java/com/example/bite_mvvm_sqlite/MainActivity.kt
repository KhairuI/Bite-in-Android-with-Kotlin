package com.example.bite_mvvm_sqlite

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bite_mvvm_sqlite.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    val AUDIO_PERMISSION= 1

    // listener
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.search.voiceImage.setOnClickListener(this)

        setRecord()
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
               binding.search.searchEditText.setText("Say something..")
            }

            override fun onBeginningOfSpeech() {
                binding.search.searchEditText.setText("Listening....")
            }

            override fun onRmsChanged(p0: Float) {

            }

            override fun onBufferReceived(p0: ByteArray?) {

            }

            override fun onEndOfSpeech() {
                binding.search.searchEditText.setText("Enter name")
            }

            override fun onError(p0: Int) {
                binding.search.searchEditText.setText("Can't listening...")
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