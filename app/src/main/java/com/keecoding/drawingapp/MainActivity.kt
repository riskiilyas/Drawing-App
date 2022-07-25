package com.keecoding.drawingapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.keecoding.drawingapp.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_DrawingApp)
        setContentView(binding.root)

        if(!SharedPref.isMusicOn) {
            binding.btnSound.apply {
                setImageResource(R.drawable.ic_baseline_volume_off_24)
                drawable.setTint(Color.parseColor("#018786"))
            }
        }

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.btnDrawing.setOnClickListener {
            SharedPref.setActivity(2)
            startActivity(Intent(this, DrawingActivity::class.java))
        }

        binding.btnGallery.setOnClickListener {
//            startActivity(Intent(this, VirtualGalleryActivity::class.java))
            val path = Environment.getExternalStorageDirectory().toString() + "/" + "Pictures" + "/"
            val file = File(filesDir, "myFile")

            val uri = Uri.parse(path)
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(uri, "*/*")
            startActivity(intent)
        }

        binding.btnAbout.setOnClickListener {
            Dialog(this)
                .apply {
                    setContentView(R.layout.dialog_dev_contact)
                    window?.setBackgroundDrawableResource(R.color.transparent)
                    findViewById<ImageView>(R.id.btnCloseAbout).setOnClickListener {
                        dismiss()
                    }
                    show()
                }
        }

        binding.btnSound.setOnClickListener {
            SharedPref.isMusicOn = !SharedPref.isMusicOn
            if(!SharedPref.isMusicOn) {
                binding.btnSound.apply {
                    setImageResource(R.drawable.ic_baseline_volume_off_24)
                    drawable.setTint(Color.parseColor("#018786"))
                }
            } else {
                binding.btnSound.apply {
                    setImageResource(R.drawable.ic_baseline_volume_up_24)
                    drawable.setTint(Color.parseColor("#018786"))
                }
            }
        }

        binding.btnExit2.setOnClickListener { confitmExit() }
    }

    override fun onResume() {
        super.onResume()
        val anim = AnimationUtils.loadAnimation(this, R.anim.button_setup_anim)
        val animFade = AnimationUtils.loadAnimation(this, R.anim.ffade_in)
        binding.btnDrawing.startAnimation(anim)
        binding.btnAbout.startAnimation(anim)
        binding.btnGallery.startAnimation(anim)
        binding.btnExit2.startAnimation(anim)
        binding.textView.startAnimation(animFade)
        binding.textView2.startAnimation(animFade)
        binding.btnSound.startAnimation(animFade)

        SharedPref.setActivity(1)
        SharedPref.resume(1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPause() {
        SharedPref.pause(1)
        super.onPause()
    }

    private fun confitmExit() {
        Dialog(this).apply {
            setContentView(R.layout.exit_dialog)
            window?.setBackgroundDrawableResource(R.color.transparent)
            findViewById<AppCompatButton>(R.id.btnExit).setOnClickListener { finish() }
            findViewById<AppCompatButton>(R.id.btnCancelExit).setOnClickListener { dismiss() }
            show()
        }
    }


    override fun onBackPressed() {
        confitmExit()
    }
}