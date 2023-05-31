package com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.usecases.getDriverSuitabilityScoreUsecase

import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.model.BaseDrivers

data class SuitabilityScoreDriver(
    override val name: String,
    val length:Int,
    val commonFactors:IntArray,
    val vowelCount:Int,
    val consonantCount:Int
    ) : BaseDrivers(name) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SuitabilityScoreDriver

        if (name != other.name) return false
        if (length != other.length) return false
        if (!commonFactors.contentEquals(other.commonFactors)) return false
        if (vowelCount != other.vowelCount) return false
        if (consonantCount != other.consonantCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + length
        result = 31 * result + commonFactors.contentHashCode()
        result = 31 * result + vowelCount
        result = 31 * result + consonantCount
        return result
    }
}