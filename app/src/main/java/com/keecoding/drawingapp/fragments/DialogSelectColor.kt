package com.keecoding.drawingapp.fragments

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.keecoding.drawingapp.CustomColors
import com.keecoding.drawingapp.DrawingActivity
import com.keecoding.drawingapp.MainActivity
import com.keecoding.drawingapp.R
import com.keecoding.drawingapp.databinding.DialogFillBinding

class DialogSelectColor(private var onColorSelected: (Int) -> Unit): DialogFragment() {

    private lateinit var binding: DialogFillBinding
    private var selecterColor = -1
    private var listColor = ArrayList<ImageView>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFillBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        listColor.add(binding.fillBlack)
        listColor.add(binding.fillBlue)
        listColor.add(binding.fillCyan)
        listColor.add(binding.fillGreen)
        listColor.add(binding.fillLime)
        listColor.add(binding.fillOrange)
        listColor.add(binding.fillPink)
        listColor.add(binding.fillPurple)
        listColor.add(binding.fillRed)
        listColor.add(binding.fillYellow)

        listColor.forEachIndexed { id, iv ->
            iv.setOnClickListener { v ->
                unselectAll()
                iv.setBackgroundColor(Color.parseColor("#C1C1C1"))
                selecterColor = CustomColors.ColorList[id]
            }
        }

        binding.btnCancelFill.setOnClickListener { dismiss() }

        binding.btnSaveFill.setOnClickListener {
            if (selecterColor != -1) {
                onColorSelected.invoke(selecterColor)
                dismiss()
            } else {
                Toast.makeText(activity, "Nothing Changed!", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }

    }

    private fun unselectAll() {
        listColor.forEach {
            it.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}