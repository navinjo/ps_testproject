package com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ndbg.ps_cp.R
import com.ndbg.ps_cp.databinding.FragmentDailyDriverSuitabilityBinding
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.data.local.model.DailyShipmentEntity
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.adapters.DailyDriverAdapter
import com.ndbg.ps_cp.feature.dailyShipmentRoutes.presentation.adapters.DailyDriverSuitabilityScoreInfoAdapter
import com.ndbg.ps_cp.util.DataStatus
import com.ndbg.ps_cp.util.TodayDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyDriverSuitabilityFragment : Fragment(R.layout.fragment_daily_driver_suitability), RecyclerViewClickListener {

    private lateinit var fragmentBinding: FragmentDailyDriverSuitabilityBinding
    private lateinit var recyclerView: RecyclerView

    lateinit var driverInfoAdapter: DailyDriverSuitabilityScoreInfoAdapter
    lateinit var viewModel: DailyDriverSuitabilityViewModel

    companion object {
        fun newInstance() = DailyDriverSuitabilityFragment()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DailyDriverSuitabilityViewModel::class.java]

        driverInfoAdapter =  DailyDriverSuitabilityScoreInfoAdapter()
        fragmentBinding = FragmentDailyDriverSuitabilityBinding.inflate(layoutInflater)

        //fragmentBinding.apply {

            viewModel.getDailyShipmentListForDate(TodayDate.getTodayDate())

            viewModel.dailyShipmentList.observe(viewLifecycleOwner, Observer { it ->
                when (it.status) {
                    DataStatus.Status.LOADING -> {
                        fragmentBinding.progressBar.visibility = View.VISIBLE
                        fragmentBinding.message.visibility = View.VISIBLE
                        fragmentBinding.rvDriverInfo.visibility = View.GONE
                    }
                    DataStatus.Status.SUCCESS -> {
                        fragmentBinding.progressBar.visibility = View.GONE
                        fragmentBinding.message.visibility = View.GONE
                        it.data?.forEach(){ shipmentData ->
                            Log.v("ndbg","shipmentData is ${shipmentData.driverSuitabilityScore} for ${shipmentData.drivername}")

                        }
                        driverInfoAdapter.differ.submitList(it.data)

                        it.data?.let { it1 ->
                            val ddAdapter = DailyDriverAdapter(it1, this)

                        fragmentBinding.rvDriverInfo.visibility = View.VISIBLE
                        fragmentBinding.rvDriverInfo.apply {
                            layoutManager = LinearLayoutManager(activity)
                            setHasFixedSize(true)
                            //adapter  = driverInfoAdapter
                            adapter = ddAdapter
                        }
                        }

                    }
                    DataStatus.Status.ERROR -> {
                        fragmentBinding.message.visibility = View.VISIBLE
                        fragmentBinding.message.text = "Error Encountered"
                        fragmentBinding.progressBar.visibility = View.GONE
                        fragmentBinding.rvDriverInfo.visibility = View.GONE
                    }
                }
            })
        //}
    }

    override fun onRecyclerViewItemClick(view: View, shipment: DailyShipmentEntity) {
        when(view.id){
            R.id.cvCardParent -> {
                Toast.makeText(requireContext(), "Suitability score is ${shipment.driverSuitabilityScore}", Toast.LENGTH_LONG).show()
            }
        }
    }

}