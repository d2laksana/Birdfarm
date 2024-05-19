package com.d2l.birdfarm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.d2l.birdfarm.R
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.databinding.FragmentHomeBinding
import com.d2l.birdfarm.utils.SessionManager
import com.d2l.birdfarm.utils.ToastMSG
import com.d2l.birdfarm.viewmodel.IotViewModel
import kotlin.math.roundToInt


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<IotViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lightLayout.setOnClickListener {
            if (binding.switchLight.isChecked){
                binding.lightIcon.setImageResource(R.drawable.light)
                binding.switchLight.isChecked = false
            } else {
                binding.lightIcon.setImageResource(R.drawable.light_on)
                binding.switchLight.isChecked = true
            }
        }

        binding.switchLight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.lightIcon.setImageResource(R.drawable.light_on)
            } else {
                binding.lightIcon.setImageResource(R.drawable.light)
            }
        }

        SessionManager.getName(this.requireContext()).also { binding.name.text = it }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDHT()
        viewModel.dhtResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    binding.loadingData.visibility = View.VISIBLE
                }
                is BaseResponse.Success -> {
                    binding.loadingData.visibility = View.GONE
                    binding.circleTemp
                }

                is BaseResponse.Error -> {
                    ToastMSG()?.ErrorMSG(it.message, this.requireContext())
                }
                else -> {
                }
            }
        }
    }

    private fun calculateProgress(temperature: Int): Int {
        val minTemperature = 0
        val maxTemperature = 45
        val minProgress = 0
        val maxProgress = 100

        // Calculate the progress using linear interpolation
        val progress = (temperature - minTemperature) / (maxTemperature - minTemperature).toDouble() * (maxProgress - minProgress)

        // Round the progress to an integer value
        return progress.roundToInt()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}