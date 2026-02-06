package com.example.coverme

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.coverme.data.local.dataStore.OnBoardingDataStore
import com.example.coverme.presentation.screens.Home.HomeFragment
import com.example.coverme.presentation.screens.onBoarding.Onboarding1
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val onBoardingDataStore = OnBoardingDataStore(this)

        Log.d("HOHOHO", "hhee")

        if (savedInstanceState == null) {
            lifecycleScope.launch {
                val hasOnboarded = onBoardingDataStore.hasOnboarded()
                val fragment = if (hasOnboarded) {
                    HomeFragment()
                } else {
                    Onboarding1()
                }

                supportFragmentManager.beginTransaction().replace(R.id.main, fragment).commit()
            }
        }
    }
}
