package com.keecoding.drawingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.keecoding.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAdd.setOnClickListener {
            val intentDrawingActivity = Intent(this, DrawingActivity::class.java)
            startActivity(intentDrawingActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menuDevContact -> showDevContact()
            R.id.menuExit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDevContact() {
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Confirm")
            setMessage("Are you sure exit?")
            setCancelable(false)
            setPositiveButton("Exit") { _, _ -> finish() }
            setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }
}