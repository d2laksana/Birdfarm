package com.d2l.birdfarm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.d2l.birdfarm.R
import com.d2l.birdfarm.databinding.FragmentHomeBinding
import com.d2l.birdfarm.utils.SessionManager


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        binding.lightLayout.setOnClickListener(){
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

        binding.name.setText(SessionManager.getName(this.requireContext()))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}