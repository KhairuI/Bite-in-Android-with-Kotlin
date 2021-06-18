package com.example.bite_mvvm_sqlite.Utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.bite_mvvm_sqlite.databinding.InsertDialogueBinding
import com.example.bite_mvvm_sqlite.interfaces.AddInterface
import com.example.bite_mvvm_sqlite.model.Item

class InsertDialogue(context: Context,private var addListener:AddInterface):AppCompatDialog(context) {

    private lateinit var binding: InsertDialogueBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= InsertDialogueBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            val name= binding.itemEditText.text.toString()
            val amount= binding.amountEditText.text.toString()
            if(name.isNotEmpty() && amount.isNotEmpty()){

                val item= Item(name, amount.toInt())
                addListener.addOnClick(item)
                dismiss()

            }
            else{
                Toast.makeText(context,"Enter all field", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cancleBtn.setOnClickListener {
            cancel()
        }
    }

}