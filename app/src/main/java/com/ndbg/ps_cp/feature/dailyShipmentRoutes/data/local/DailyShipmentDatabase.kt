package com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.dao.DailyShipmentDao
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity

@Database(
    entities = [DailyShipmentEntity::class],
    version =1
)
//@TypeConverters(DateTypeConvertor::class)

abstract class DailyShipmentDatabase :RoomDatabase() {

    abstract fun dailyShipmentDao(): DailyShipmentDao

    companion object{
        val SHIPMENT_DB_NAME = "dailyshipment"
    }

}