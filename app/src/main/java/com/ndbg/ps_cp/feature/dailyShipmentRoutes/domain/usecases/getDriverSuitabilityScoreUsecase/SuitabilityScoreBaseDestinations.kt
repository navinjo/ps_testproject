package com.ndbg.ps_cp.feature.dailyShipmentRoutes.domain.usecases.getDriverSuitabilityScoreUsecase

import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.model.BaseDestinations

data class SuitabilityScoreBaseDestinations(
    override val name: String,
    val length:Int,
    val commonFactors:IntArray
) : BaseDestinations(name) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SuitabilityScoreBaseDestinations

        if (name != other.name) return false
        if (length != other.length) return false
        if (!commonFactors.contentEquals(other.commonFactors)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + length
        result = 31 * result + commonFactors.contentHashCode()
        return result
    }
}