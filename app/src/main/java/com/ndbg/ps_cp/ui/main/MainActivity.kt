package com.ndbg.ps_cp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ndbg.ps_cp.R
import com.ndbg.ps_cp.databinding.ActivityMainBinding
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.ui.DailyDriverSuitabilityFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DailyDriverSuitabilityFragment.newInstance())
                .commitNow()
        }
    }
}