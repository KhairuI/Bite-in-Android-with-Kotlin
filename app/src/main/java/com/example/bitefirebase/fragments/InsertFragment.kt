package com.example.bitefirebase.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitefirebase.R
import com.example.bitefirebase.adapter.GalleryUploadAdapter
import com.example.bitefirebase.databinding.FragmentInsertBinding
import com.example.bitefirebase.model.ModelImage
import com.example.bitefirebase.model.ModelSaveImage
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class InsertFragment : Fragment(),GalleryUploadAdapter.OnCardClickListener {
    private lateinit var binding: FragmentInsertBinding
    private var imageList= mutableListOf<ModelImage>()
    private var imageSaveList= mutableListOf<ModelSaveImage>()
    private lateinit var adapter: GalleryUploadAdapter
    private var counter=0

    //firebase
    private val firebaseAuth= FirebaseAuth.getInstance()
    private val currentUser= firebaseAuth.currentUser
    private val database= FirebaseDatabase.getInstance()
    private val mRef= database.getReference("User_Data")
    private val storage= FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding= FragmentInsertBinding.inflate(layoutInflater)
        binding.txtGallery.setOnClickListener {
            openGallery()
        }
        binding.btnUpload.setOnClickListener {
           if(isField()) {
               binding.progressBar.visibility= View.VISIBLE
               uploadData()
           }
        }
        setAdapter()
        return binding.root
    }

    private fun uploadData() {

        val name= binding.edtName.text.toString()
        val status= binding.edtStatus.text.toString()
        val currentUid= currentUser?.uid
        val key= mRef.push().key // MF4545nvnddfn
        val dataPath= mRef.child(currentUid!!).child(key!!)
        val imagePath= storage.child("Image_Path").child(currentUid).child(key)

        val dataMap:MutableMap<String,String> = HashMap()
        dataMap["name"]= name
        dataMap["status"]= status

        dataPath.setValue(dataMap).addOnCompleteListener {
            if(it.isSuccessful){
                for(i in 0 until imageList.size){
                    imagePath.child(imageList[i].name).putFile(imageList[i].uri).addOnSuccessListener {
                        imagePath.child(imageList[i].name).downloadUrl.addOnCompleteListener {
                            if(it.isSuccessful){
                                counter++
                                val url= it.result.toString()
                                val name= imageList[i].name
                                imageSaveList.add(ModelSaveImage(name, url))
                            }
                            else{
                                Log.d("xx", "uploadData: ${it.exception.toString()}")
                            }
                            if(counter==imageList.size){
                                storeImage(key)
                                counter=0
                            }
                        }
                    }
                }
            }
        }

    }

    private fun storeImage(key: String) {

        val imagePath= mRef.child(currentUser?.uid!!).child(key).child("image")
        for(i in 0 until imageSaveList.size){
            val dataMap:MutableMap<String,String> = HashMap()
            dataMap["name"]= imageSaveList[i].name
            dataMap["link"]= imageSaveList[i].url
            imagePath.push().setValue(dataMap)
        }
        binding.progressBar.visibility= View.GONE
    }

    private fun isField():Boolean{
        if(binding.edtName.text.toString().isEmpty()){
            Snackbar.make(requireView(),"Enter your name",Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(binding.edtStatus.text.toString().isEmpty()){
            Snackbar.make(requireView(),"Enter your status",Snackbar.LENGTH_SHORT).show()
            return false
        }
        if(imageList.size==0){
            Snackbar.make(requireView(),"Upload images",Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun setAdapter() {
        binding.rvUploadGallery.setHasFixedSize(true)
        adapter= GalleryUploadAdapter(this)
        binding.rvUploadGallery.adapter= adapter
    }

    private fun openGallery() {
        val intent= Intent(Intent.ACTION_PICK)
        intent.apply {
            type="image/*"
            action= Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        }
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode== RESULT_OK){

            if(data?.clipData != null){ // multiple image
                imageList.clear()
                imageSaveList.clear()
                val totalImage= data.clipData!!.itemCount
                for(i in 0 until totalImage){
                    val uri= data.clipData!!.getItemAt(i).uri
                    val imageName= getImageName(uri)
                    imageList.add(ModelImage(imageName,uri))
                }
                adapter.addAll(imageList,true)

            }
            else if(data?.data != null){ // single image
                imageList.clear()
                imageSaveList.clear()
                val uri= data.data
                val imageName= getImageName(uri!!)
                imageList.add(ModelImage(imageName,uri))
                adapter.addAll(imageList,true)
            }
        }
    }

    private fun getImageName(uri: Uri): String {

        var result = ""
        if (uri.scheme.equals("content")) {
            val cursor: Cursor? = requireActivity().contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path.toString()
            val cut = result.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    override fun onCardClick(position: Int) {
        imageList.removeAt(position)
    }

}