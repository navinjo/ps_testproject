package com.ndbg.ps_cp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ndbg.ps_cp.DailyShipmentApp
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.DailyShipmentRepositoryImpl
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.DailyShipmentDatabase
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.dao.DailyShipmentDao
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.repository.DailyShipmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApplication(
        @ApplicationContext app: Context):DailyShipmentApp {
        return app as DailyShipmentApp
    }

    @Provides
    @Singleton
    fun provideDailyShipmentDatabase(
        @ApplicationContext context: Context): DailyShipmentDatabase{
        return Room.databaseBuilder(
            context,
            DailyShipmentDatabase::class.java,
            DailyShipmentDatabase.SHIPMENT_DB_NAME
        ).build()
    }

    @Provides
    fun providesDailyShipmentDao(
        database: DailyShipmentDatabase,
    ): DailyShipmentDao = database.dailyShipmentDao()

    @Provides
    @Singleton
    fun provideDailyShipmentRepository(
        @ApplicationContext app: Context,
        db: DailyShipmentDatabase): DailyShipmentRepository {
        return DailyShipmentRepositoryImpl(app, db.dailyShipmentDao())
    }
}