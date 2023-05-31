package com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.repository

import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity
import kotlinx.coroutines.flow.Flow

interface DailyShipmentRepository {

     fun getDailyDriverSuitabilityScoreDataForDate(date: String): Flow<List<DailyShipmentEntity>>

     suspend fun updateDailyDriverSuitabilityScoreDataForDate(dailyShipmentData:List<DailyShipmentEntity>)
}