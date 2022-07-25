package com.keecoding.drawingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.keecoding.drawingapp.databinding.ActivityVirtualGalleryBinding

class VirtualGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVirtualGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVirtualGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.btnBackVG.setOnClickListener { finish() }
    }
}