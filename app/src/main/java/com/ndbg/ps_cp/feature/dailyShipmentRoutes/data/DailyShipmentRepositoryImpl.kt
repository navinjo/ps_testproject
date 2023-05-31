package com.ndbg.ps_cp.feature.dailyShipmentRoutes.data

import android.content.Context
import android.util.Log
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.dao.DailyShipmentDao
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.repository.DailyShipmentRepository
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.usecases.getDriverSuitabilityScoreUsecase.SuitabilityScoreMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class DailyShipmentRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val driverSSDao: DailyShipmentDao
): DailyShipmentRepository {

    override fun getDailyDriverSuitabilityScoreDataForDate(date: String): Flow<List<DailyShipmentEntity>> {
        //get from local db first
        val data = driverSSDao.getDriverSuitabilityScoreDataForDate(date)
        SuitabilityScoreMapper.generateDailySuitableRoutes(context)
        return data
    }

    override suspend fun updateDailyDriverSuitabilityScoreDataForDate(dailyShipmentData: List<DailyShipmentEntity>) {
        driverSSDao.updateDriverSuitabilityScore(dailyShipmentData)
    }


}