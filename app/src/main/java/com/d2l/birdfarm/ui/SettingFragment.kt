package com.d2l.birdfarm.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.d2l.birdfarm.R
import com.d2l.birdfarm.databinding.FragmentSettingBinding


class SettingFragment : Fragment(R.layout.fragment_setting) {
    private  var _binding: FragmentSettingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = this.activity?.getSharedPreferences(this.requireContext().getString(R.string.app_name), Context.MODE_PRIVATE) ?: return
        val darkModeEnabled = sharedPreferences.getBoolean("darkMode", false)
        if(darkModeEnabled) {
            binding.darkmode.isChecked = true
        } else {
            binding.darkmode.isChecked = false
        }

        binding.darkModeLayout.setOnClickListener {
            if (binding.darkmode.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.darkmode.isChecked = false
                val sharedPreferences = this.activity?.getSharedPreferences(this.requireContext().getString(R.string.app_name), Context.MODE_PRIVATE) ?: return@setOnClickListener
                with(sharedPreferences.edit()){
                    putBoolean("darkMode", false)
                    apply()
                }
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.darkmode.isChecked = true
                val sharedPreferences = this.activity?.getSharedPreferences(this.requireContext().getString(R.string.app_name), Context.MODE_PRIVATE) ?: return@setOnClickListener
                with(sharedPreferences.edit()){
                    putBoolean("darkMode", true)
                    apply()
                }
            }
        }

        binding.darkmode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                val sharedPreferences = this.activity?.getSharedPreferences(this.requireContext().getString(R.string.app_name), Context.MODE_PRIVATE) ?: return@setOnCheckedChangeListener
                with(sharedPreferences.edit()){
                    putBoolean("darkMode", true)
                    apply()
                }
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                val sharedPreferences = this.activity?.getSharedPreferences(this.requireContext().getString(R.string.app_name), Context.MODE_PRIVATE) ?: return@setOnCheckedChangeListener
                with(sharedPreferences.edit()){
                    putBoolean("darkMode", false)
                    apply()
                }
            }
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}