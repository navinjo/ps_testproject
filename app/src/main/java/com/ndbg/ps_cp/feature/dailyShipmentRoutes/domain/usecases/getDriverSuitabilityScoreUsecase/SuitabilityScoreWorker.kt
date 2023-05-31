package com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.usecases.getDriverSuitabilityScoreUsecase

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.model.DailyShipments
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.repository.DailyShipmentRepository
import com.ndbg.ps_cp.util.TodayDate
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.IOException
import java.io.InputStream

@HiltWorker
class SuitabilityScoreWorker @AssistedInject constructor(
    private val dailyShipmentRepository: DailyShipmentRepository,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val ctxt = appContext
    private val repo = dailyShipmentRepository

    override suspend fun doWork(): Result {
        //Get from local and remote "json" data sources
        val dailyShipmentInfo = readJSONFromAssets()
        dailyShipmentInfo?.let {
            transformDataPerSuitability(dailyShipmentInfo)
        }
        return Result.success()
    }

    private suspend fun transformDataPerSuitability(dailyShipmentInfo: DailyShipments) {

        val ssDrivers:MutableList<SuitabilityScoreDriver> = mutableListOf()
        val ssDestinations:MutableList<SuitabilityScoreBaseDestinations> = mutableListOf()

        dailyShipmentInfo.drivers.forEach() { driverName ->
            var vowelCount=0
            var consonantCount =0
            val commonFactors:MutableList<Int> = mutableListOf()
            driverName.lowercase().forEach { ch->
                if (ch == 'a' || ch == 'e' || ch == 'i'
                    || ch == 'o' || ch == 'u') {
                    ++vowelCount
                } else if (ch in 'a'..'z') {
                    ++consonantCount
                }
            }
            var i =2 //ignoring 1
            //spaces and numbers are counted towards length
            while (i <= driverName.lowercase().length) {
                if (driverName.lowercase().length % i == 0) {
                    commonFactors.add(i)
                }
                i++
            }
            ssDrivers.add(SuitabilityScoreDriver(driverName, driverName.length, commonFactors.toIntArray(), vowelCount, consonantCount))
            ssDrivers.sortByDescending {it.vowelCount}
        }

        dailyShipmentInfo.shipments.forEach { destination ->
            val commonFactors:MutableList<Int> = mutableListOf()
            var i =2 //ignoring 1
            //spaces and numbers are counted towards length
            while (i <= destination.lowercase().length) {
                if (destination.lowercase().length % i == 0) {
                    commonFactors.add(i)
                }
                i++
            }
            ssDestinations.add(SuitabilityScoreBaseDestinations(destination,destination.lowercase().length, commonFactors.toIntArray()))
            ssDestinations.sortByDescending {it.length}
        }

        updateInfoToDB(evaluateSS(ssDrivers, ssDestinations))
    }

    private fun evaluateSS(
        ssDriversList:MutableList<SuitabilityScoreDriver>,
        ssDestinationList: MutableList<SuitabilityScoreBaseDestinations>): MutableList<DailyShipmentEntity> {

        val dailyShipmentEntityList: MutableList<DailyShipmentEntity> = mutableListOf()

        ssDestinationList.forEach(){destination->
            if(destination.length %2 == 0) { //even count
                val ssDriver = ssDriversList.removeFirst()
                val baseSSScore = ssDriver.vowelCount *1.5
                var finalSSScore:Double = baseSSScore
                destination.commonFactors.forEach {it->
                    if(ssDriver.commonFactors.contains(it)) {
                        finalSSScore = baseSSScore + (baseSSScore * 0.5)
                    }
                }
                val dailyShipmentEntity = DailyShipmentEntity(TodayDate.getTodayDate(),ssDriver.name,destination.name, finalSSScore)
                dailyShipmentEntityList.add(dailyShipmentEntity)
            } else{ //odd count
                val ssDriver = ssDriversList.removeFirst()
                val baseSSScore = ssDriver.consonantCount
                var finalSSScore:Double = baseSSScore.toDouble()
                destination.commonFactors.forEach {it->
                    if(ssDriver.commonFactors.contains(it)) {
                        finalSSScore = baseSSScore + (baseSSScore * 0.5)
                    }
                }
                val dailyShipmentEntity = DailyShipmentEntity(TodayDate.getTodayDate(),ssDriver.name,destination.name, finalSSScore)
                dailyShipmentEntityList.add(dailyShipmentEntity)
            }
        }
        return dailyShipmentEntityList
    }

    private suspend fun updateInfoToDB(dailyShipmentList: MutableList<DailyShipmentEntity>) {
        repo.updateDailyDriverSuitabilityScoreDataForDate(dailyShipmentList)
    }

    private fun readJSONFromAssets(): DailyShipments? {
        var json: String? = null
        json = try {
            val `is`: InputStream = ctxt.assets.open("data.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<DailyShipments> = moshi.adapter(DailyShipments::class.java)

        return json?.let { jsonAdapter.fromJson(it) }
    }

    companion object {
        fun assignSuitableRoutes() = OneTimeWorkRequestBuilder<SuitabilityScoreWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
    }
}