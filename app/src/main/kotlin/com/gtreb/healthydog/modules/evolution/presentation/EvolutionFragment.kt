package com.gtreb.healthydog.modules.evolution.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.common.presentation.CustomFragment
import com.gtreb.healthydog.databinding.EvolutionFragmentBinding
import com.gtreb.healthydog.modules.evolution.EvolutionModule
import com.gtreb.healthydog.modules.evolution.EvolutionModule.Params.DOG_ID
import com.gtreb.healthydog.utils.Constants
import com.gtreb.healthydog.utils.DateValueFormatter
import kotlinx.android.synthetic.main.evolution_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.sharedViewModel

class EvolutionFragment : CustomFragment<EvolutionFragmentBinding>() {

    private val viewModelEvolution: EvolutionFragmentViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.evolution_fragment
    private lateinit var dogId: String

    override val navigationItem: NavigationItem
        get() = NavigationItem(EvolutionModule::class, EvolutionFragment::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dogId = arguments?.getString(DOG_ID) ?: 1.toString()
        viewModelEvolution.init(dogId)
        viewModelEvolution.load()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelEvolution.dogsEvolutionData.observe(lifecycleOwner){
            displayDataOnChart()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun displayDataOnChart() {
        chart.setDrawGridBackground(true)
        chart.xAxis.valueFormatter = DateValueFormatter()
        chart.data = buildData()
        chart.invalidate()
    }

    private fun buildData(): LineData {
        val entryWeight : MutableList<Entry> = arrayListOf()
        val entryHeight : MutableList<Entry> = arrayListOf()
        viewModelEvolution.dogsEvolutionData.value?.forEach {
            entryWeight.add(Entry(it.date.time.toFloat(), it.weight))
            entryHeight.add(Entry(it.date.time.toFloat(), it.height))
        }
        val dataSetWeight = LineDataSet(entryWeight, requireContext().getString(R.string.weight))
        val dataSetHeight = LineDataSet(entryHeight, requireContext().getString(R.string.height))

        dataSetWeight.circleRadius = Constants.CHART_CIRCLE_RADIUS
        dataSetHeight.circleRadius = Constants.CHART_CIRCLE_RADIUS

        dataSetWeight.lineWidth = Constants.CHART_LINE_WIDTH
        dataSetHeight.lineWidth = Constants.CHART_LINE_WIDTH

        dataSetHeight.color = ColorTemplate.PASTEL_COLORS[0]
        dataSetWeight.color = ColorTemplate.PASTEL_COLORS[1]

        dataSetWeight.setCircleColor(dataSetWeight.color)
        dataSetHeight.setCircleColor(dataSetHeight.color)

        return LineData(dataSetHeight, dataSetWeight)
    }

    @ExperimentalCoroutinesApi
    override fun bindViewModels(binding: EvolutionFragmentBinding) {
        binding.viewModelEvolution = viewModelEvolution
    }
}