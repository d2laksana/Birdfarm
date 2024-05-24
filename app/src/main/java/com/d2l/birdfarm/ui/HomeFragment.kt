package com.d2l.birdfarm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.d2l.birdfarm.R
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.data.api.response.iot.DhtResponse
import com.d2l.birdfarm.data.api.response.iot.FireResponse
import com.d2l.birdfarm.data.api.response.iot.StockResponse
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
        SessionManager.getName(this.requireContext()).also { binding.name.text = it }


        binding.lightLayout.setOnClickListener {
            if (binding.switchLight.isChecked){
                postSwitch(0, true)
                lightOn(false)
            } else {
                postSwitch(1, true)
                lightOn(true)
            }
        }

        binding.switchLight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                postSwitch(1, true)
                binding.lightIcon.setImageResource(R.drawable.light_on)
            } else {
                postSwitch(0, true)
                binding.lightIcon.setImageResource(R.drawable.light)
            }
        }

        binding.feedLayout.setOnClickListener{
            postSwitch(1, false)
            showDialog()
        }


        viewModel.getDHT()
        viewModel.getRelay()
        viewModel.getStock()
        viewModel.dhtResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    binding.humIndicator.text = "loading"
                    binding.tempIndicator.text = "loading"
                    binding.loadingData.visibility = View.VISIBLE
                }
                is BaseResponse.Success -> {
                    binding.loadingData.visibility = View.GONE
                    loadDataDht(it.data)
                    viewModel.resetData()
                }

                is BaseResponse.Error -> {
                    ToastMSG().ErrorMSG(it.message, this.requireContext())
                }
                else -> {
                }
            }
        }
        viewModel.relayResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    binding.loadingData.visibility = View.VISIBLE
                }
                is BaseResponse.Success -> {
                    binding.loadingData.visibility = View.GONE
                    loadRelay(it.data)
                    viewModel.resetData()
                }

                is BaseResponse.Error -> {
                    ToastMSG().ErrorMSG(it.message, this.requireContext())
                }
                else -> {
                }
            }
        }
        viewModel.stockResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    binding.loadingData.visibility = View.VISIBLE
                }
                is BaseResponse.Success -> {
                    binding.loadingData.visibility = View.GONE
                    loadStock(it.data)
                    viewModel.resetData()
                }

                is BaseResponse.Error -> {
                    ToastMSG().ErrorMSG(it.message, this.requireContext())
                }
                else -> {
                }
            }
        }
        viewModel.postRelayResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    binding.loadingData.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.loadingData.visibility = View.GONE
                    viewModel.resetData()
                }

                is BaseResponse.Error -> {
                    ToastMSG().ErrorMSG(it.message, this.requireContext())
                    binding.loadingData.visibility = View.GONE
                }
                else -> {
                    binding.loadingData.visibility = View.GONE
                }
            }
        }
        viewModel.postServoResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    binding.loadingData.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.loadingData.visibility = View.GONE
                    viewModel.resetData()
                }

                is BaseResponse.Error -> {
                    ToastMSG().ErrorMSG(it.message, this.requireContext())
                    binding.loadingData.visibility = View.GONE
                }
                else -> {
                    binding.loadingData.visibility = View.GONE
                }
            }
        }
    }

    private fun postSwitch(value: Int, type: Boolean){
        if (type){
            viewModel.postRelay(value)
        } else {
            viewModel.postServo(value)
        }

    }
    private fun showDialog() {
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Feeding")
        builder.setMessage("Process Feeding....")
        builder.setNegativeButton("Stop") { dialog, _ ->
            postSwitch(0, false)
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun calculateTemp(temperature: Float): Float {
        // Determine temperature and progress ranges
        val minTemperature = -10f
        val maxTemperature = 50f

        // Ensure the temperature is within the specified range
        val adjustedTemperature = temperature.coerceIn(minTemperature, maxTemperature)

        // Calculate the progress using linear interpolation
        return ((adjustedTemperature - minTemperature) * 100 / (maxTemperature - minTemperature))
    }

    private fun calculateStock(stock: Float): Float {
        val vol = stock.coerceIn(0f, 350f)
        return ((vol - 0f) * 100 / (350f - 0f))
    }

    private fun loadDataDht(data: DhtResponse?){
        if (data?.data?.temperature?.get(0)?.value.toString().isNotBlank()){
            val temp = data?.data?.temperature?.get(0)?.value!!
            val hum = data.data.humidity?.get(0)?.value!!
            binding.circleTemp.progress = calculateTemp(temp)
            binding.circleHum.progress = hum
            binding.tempIndicator.text = "$temp°C"
            binding.humIndicator.text = "$hum%"

        }
    }

    private fun loadRelay(data: FireResponse?){
        if (!data?.data?.toString().isNullOrBlank()){
            if (data?.data == 1) lightOn(true) else lightOn(false)
        }
    }

    private fun loadStock(data: StockResponse?){
        if (!data?.data.isNullOrEmpty()){
            val stock = calculateStock(data?.data?.get(0)?.value!!)
            binding.stock.progress = stock
            binding.stock.labelText = stock.roundToInt().toString() + "%"
        }
    }

    private fun lightOn(checked: Boolean){
        if (checked){
            binding.switchLight.isChecked = true
            binding.lightIcon.setImageResource(R.drawable.light_on)
        } else {
            binding.switchLight.isChecked = false
            binding.lightIcon.setImageResource(R.drawable.light)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }
}