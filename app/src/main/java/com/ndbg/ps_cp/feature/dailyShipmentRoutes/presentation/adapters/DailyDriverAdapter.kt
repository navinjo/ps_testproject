package com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ndbg.ps_cp.R
import com.ndbg.ps_cp.databinding.ItemDriverSuitabilityBinding
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.ui.RecyclerViewClickListener

class DailyDriverAdapter(
    private val dailyshipment: List<DailyShipmentEntity>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<DailyDriverAdapter.DailyDriverViewHolder>(){

    inner class DailyDriverViewHolder(
        val recyclerviewDailyShipmentBinding:ItemDriverSuitabilityBinding
    ) : RecyclerView.ViewHolder(recyclerviewDailyShipmentBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDriverViewHolder =
        DailyDriverViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_driver_suitability,
                parent,
                false
            )
        )


    override fun getItemCount(): Int =
        dailyshipment.size


    override fun onBindViewHolder(holder: DailyDriverViewHolder, position: Int) {
        holder.recyclerviewDailyShipmentBinding.tvDriverName.text = dailyshipment.get(position).drivername
        holder.recyclerviewDailyShipmentBinding.cvCardParent.setOnClickListener {
            listener.onRecyclerViewItemClick(
                holder.recyclerviewDailyShipmentBinding.cvCardParent,  dailyshipment.get(position)
            )}

    }
}