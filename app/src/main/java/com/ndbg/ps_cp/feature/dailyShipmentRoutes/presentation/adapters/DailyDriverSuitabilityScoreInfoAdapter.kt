package com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ndbg.ps_cp.R
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity

class DailyDriverSuitabilityScoreInfoAdapter:
    RecyclerView.Adapter<DailyDriverSuitabilityScoreInfoAdapter.DailyDriverSSViewHolder>() {

    inner class DailyDriverSSViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvDriverName: TextView
        init {
            tvDriverName = itemView.findViewById(R.id.tvDriverName)
        }
    }

    private val diffCallback = object: DiffUtil.ItemCallback<DailyShipmentEntity>(){
        override fun areItemsTheSame(oldItem: DailyShipmentEntity,
                                     newItem: DailyShipmentEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DailyShipmentEntity,
                                        newItem: DailyShipmentEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyDriverSSViewHolder {
        return DailyDriverSSViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_driver_suitability,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DailyDriverSSViewHolder, position: Int) {
        val driverInfo = differ.currentList[position]
        holder.apply { tvDriverName.text= driverInfo.drivername }
    }
}