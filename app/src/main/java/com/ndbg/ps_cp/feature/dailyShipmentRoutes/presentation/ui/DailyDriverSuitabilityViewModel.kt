package com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.repository.DailyShipmentRepository
import com.ndbg.ps_cp.util.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyDriverSuitabilityViewModel @Inject constructor(
    dailyShipmentRepository: DailyShipmentRepository, application: Application
) : AndroidViewModel(application) {

    private val _dailyShipmentList = MutableLiveData<DataStatus<List<DailyShipmentEntity>>>()
    val dailyShipmentList:LiveData<DataStatus<List<DailyShipmentEntity>>>
        get() = _dailyShipmentList

    private val repo = dailyShipmentRepository

    fun getDailyShipmentListForDate(date:String) = viewModelScope.launch {
        _dailyShipmentList.postValue(DataStatus.loading())
        repo.getDailyDriverSuitabilityScoreDataForDate(date)
            .catch { _dailyShipmentList.postValue(DataStatus.error(it.message.toString())) }
            .collect { _dailyShipmentList.postValue(DataStatus.success(it, it.isEmpty())) }
    }

    /*
    fun getDailyShipmentInfoFor(date: String):LiveData<List<DailyShipmentData>>?{
        /*
        val _dailyShipmentInfo = repo.getDailyDriverSuitabilityScoreDataForDate(date)
        dailyShipmentInfo.value = _dailyShipmentInfo.value
        return dailyShipmentInfo

         */

        mObservableShipment = repo.getDailyDriverSuitabilityScoreDataForDate(date)
        return mObservableShipment
        //dailyShipmentInfo.postValue(_dailyShipmentInfo.value)
        /*
        job = Coroutines.ioThenMain(
            { repo.getDailyDriverSuitabilityScoreDataForDate(TodayDate.getTodayDate())},
            {
                if (it != null) {
                    _dailyShipmentInfo.value = it.value
                }
            }
        )

         */
    }

     */
}