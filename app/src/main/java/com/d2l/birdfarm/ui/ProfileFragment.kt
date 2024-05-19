package com.d2l.birdfarm.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.d2l.birdfarm.R
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.data.api.response.user.UserUpdateResponse
import com.d2l.birdfarm.databinding.FragmentProfileBinding
import com.d2l.birdfarm.utils.SessionManager
import com.d2l.birdfarm.utils.ToastMSG
import com.d2l.birdfarm.viewmodel.UserViewModel


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener{
            SessionManager.clearData(this.requireContext())
            val intent = Intent(this.activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }


        binding.btnSave.setOnClickListener {
            updateData()
        }

        viewModel.updateResult.observe(viewLifecycleOwner){
            when(it) {
                is BaseResponse.Loading -> {
                    binding.updateLoading.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.updateLoading.visibility = View.GONE
                    proccesUpdate(it.data)
                    viewModel.resetData()
                }

                is BaseResponse.Error -> {
                    ToastMSG()?.ErrorMSG(it.message, this.requireContext())
                    binding.updateLoading.visibility = View.GONE
                }
                else -> {
                    binding.updateLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun updateData(){
        val id = SessionManager.getUserid(this.requireContext()).toString()
        val name = binding.userName.text.toString()
        val email = binding.userEmail.text.toString()
        viewModel.updateUser(id = id, name = name, email = email)
    }

    private fun proccesUpdate(data: UserUpdateResponse?) {
        ToastMSG()?.SuccessMSG("Success: ${data?.message}", this.requireContext())
        if (!data?.data?.apikey.isNullOrBlank()){
            data?.data?.let { SessionManager.saveUserdata(this.requireContext(),
                id = it.id.toString(),
                name = it.name.toString(),
                email = it.email.toString(),
                apikey = it.apikey.toString()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.userName.setText(SessionManager.getName(this.requireContext()))
        binding.userEmail.setText(SessionManager.getEmail(this.requireContext()))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}