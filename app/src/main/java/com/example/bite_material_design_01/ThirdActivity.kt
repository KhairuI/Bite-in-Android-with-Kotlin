package com.example.bite_material_design_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bite_material_design_01.databinding.ActivityThirdBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.bottomSheet.titleLayout.setOnClickListener {
            if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior.state= BottomSheetBehavior.STATE_COLLAPSED
            }
            else{
                bottomSheetBehavior.state= BottomSheetBehavior.STATE_EXPANDED
            }
        }

        bottomSheetBehavior= BottomSheetBehavior.from(binding.bottomSheet.layoutCustomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_COLLAPSED -> binding.bottomSheet.demoTitle.text="COLLAPSED"
                    BottomSheetBehavior.STATE_EXPANDED -> binding.bottomSheet.demoTitle.text="EXPANDED"
                    BottomSheetBehavior.STATE_DRAGGING -> binding.bottomSheet.demoTitle.text="DRAGGING"
                    BottomSheetBehavior.STATE_SETTLING -> binding.bottomSheet.demoTitle.text="SETTLING"
                    BottomSheetBehavior.STATE_HIDDEN -> binding.bottomSheet.demoTitle.text="HIDDEN"
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> binding.bottomSheet.demoTitle.text="HALF_EXPANDED"
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bottomSheet.titleIcon.rotation= slideOffset * 180
            }

        })

    }
}