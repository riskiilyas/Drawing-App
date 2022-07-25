package com.keecoding.drawingapp

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.keecoding.drawingapp.databinding.ActivityDrawingBinding
import com.keecoding.drawingapp.fragments.DialogSelectColor
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DrawingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawingBinding
    private var isErase = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_DrawingApp)

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adViewDrawing.loadAd(adRequest)

        window.statusBarColor = Color.BLACK
        binding.drawingView.setBrushSize(10f)
        binding.btnBrush.setOnClickListener { showBrushSizeDialog() }

        binding.btnErase.setOnClickListener {
            if (isErase) {
                binding.btnErase.background.setTint(Color.parseColor("#E3E3E3"))
            } else {
                binding.btnErase.background.setTint(Color.CYAN)
            }
            isErase = !isErase
            binding.drawingView.eraseMode()
        }

        binding.btnUndo.setOnClickListener {
            binding.drawingView.undo()
        }

        binding.btnRedo.setOnClickListener {
            binding.drawingView.redo()
        }

        binding.btnFill.setOnClickListener {
            DialogSelectColor { color ->
                if(color==-1) return@DialogSelectColor
                binding.colorSel.background.setTint(color)
                binding.drawingView.setColor(color)
                isErase = false
                binding.btnErase.background.setTint(Color.parseColor("#E3E3E3"))
            }.apply {
                window.decorView.setBackgroundColor(Color.TRANSPARENT)
                show(supportFragmentManager, "Fill Dialog")
            }
        }

        binding.btnSaveD.setOnClickListener {
            saveMediaToStorage(binding.drawingView.saveToBitmap())
        }

        binding.btnHomeD.setOnClickListener {
            SharedPref.setActivity(1)
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        SharedPref.setActivity(2)
        SharedPref.resume(2)
    }

    override fun onPause() {
        super.onPause()
        SharedPref.pause(2)
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
            setPositiveButton("Back") { _, _ ->
                SharedPref.setActivity(1)
                finish()
            }
            setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    private fun showBrushSizeDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.window?.setBackgroundDrawableResource(R.color.transparent)
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

    fun saveMediaToStorage(bitmap: Bitmap) {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"
        var imageS = ""
        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Drawing App")
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                imageS = imageUri?.path ?: "Gallery"
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Drawing App")
            val image = File(imagesDir, filename)
            imageS = image.path
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Saved To $imageS.jpg", Toast.LENGTH_SHORT).show()
        }
    }
}