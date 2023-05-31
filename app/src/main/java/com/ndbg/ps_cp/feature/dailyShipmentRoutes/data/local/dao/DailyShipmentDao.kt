package com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Dao
@Singleton
interface DailyShipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDriverSuitabilityScore(listOfDriverSScore: List<DailyShipmentEntity>)

    @Transaction
    @Query(value ="SELECT * FROM dailyshipmententity ORDER BY date DESC")
    fun getAllDriverSuitabilityScoreData(): List<DailyShipmentEntity>

    @Transaction
    @Query(value ="SELECT * FROM dailyshipmententity WHERE date in (:shipmentDate) ORDER BY drivername DESC")
    fun getDriverSuitabilityScoreDataForDate(shipmentDate: String): Flow<MutableList<DailyShipmentEntity>>
}