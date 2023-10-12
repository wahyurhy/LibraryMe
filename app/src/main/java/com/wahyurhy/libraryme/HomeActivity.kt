package com.wahyurhy.libraryme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wahyurhy.libraryme.databinding.ActivityHomeBinding
import com.wahyurhy.libraryme.ui.tambahbuku.TambahBukuActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_library -> {
                    binding.fbTambahData.show()
                }

                R.id.navigation_about -> {
                    binding.fbTambahData.hide()
                }
                else -> {}
            }
        }


        navView.setupWithNavController(navController)

        binding.fbTambahData.setOnClickListener {
            startActivity(Intent(this, TambahBukuActivity::class.java))
        }

    }
}