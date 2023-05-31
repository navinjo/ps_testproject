package com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.ui

import android.view.View
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, dailyShipment: DailyShipmentEntity)
}