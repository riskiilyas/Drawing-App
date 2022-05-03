package com.keecoding.drawingapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.Toast
import com.keecoding.drawingapp.databinding.ActivityDrawingBinding

class DrawingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_DrawingApp)
        window.statusBarColor = Color.BLACK
        binding.drawingView.setBrushSize(10f)
        binding.btnBrush.setOnClickListener { showBrushSizeDialog() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder(this).apply {
            setTitle("Confirm")
            setMessage("Are you sure back?")
            setCancelable(false)
            setPositiveButton("Back") { _, _ -> finish() }
            setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    private fun showBrushSizeDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Choose the Brush Size")
        brushDialog.show()
        brushDialog.findViewById<ImageButton>(R.id.btnBrushLarge).setOnClickListener {
            binding.drawingView.setBrushSize(20f)
            brushDialog.dismiss()
        }
        brushDialog.findViewById<ImageButton>(R.id.btnBrushMedium).setOnClickListener {
            binding.drawingView.setBrushSize(10f)
            brushDialog.dismiss()
        }
        brushDialog.findViewById<ImageButton>(R.id.btnBrushSmall).setOnClickListener {
            binding.drawingView.setBrushSize(5f)
            brushDialog.dismiss()
        }
    }
}