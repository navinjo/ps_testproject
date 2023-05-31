package com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.usecases.getDriverSuitabilityScoreUsecase

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager

object SuitabilityScoreMapper {

    private const val SuitabilityScoreWrkr = "SuitabilityScoreWorker"
    fun generateDailySuitableRoutes(context: Context){
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SuitabilityScoreWrkr,
                ExistingWorkPolicy.KEEP,
                SuitabilityScoreWorker.assignSuitableRoutes(),
            )
        }

    }
}