package com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyShipmentEntity(
    val date: String,
    val drivername: String,
    val destinations: String,
    val driverSuitabilityScore: Double,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
    ) {
}