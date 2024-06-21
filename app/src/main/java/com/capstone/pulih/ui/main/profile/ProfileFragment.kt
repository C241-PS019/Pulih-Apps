package com.capstone.pulih.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.capstone.pulih.R
import com.capstone.pulih.databinding.FragmentProfileBinding
import com.capstone.pulih.ui.inputdata.InputDataActivity
import com.capstone.pulih.ui.onboarding.welcome.WelcomeActivity
import com.capstone.pulih.ui.viewmodel.HomeViewModel
import com.capstone.pulih.ui.viewmodel.ProfileViewModel
import com.capstone.pulih.utils.Preferences
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ProfileViewModel by activityViewModels()
    private lateinit var sharedPref: Preferences
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        setNama()
        setUniv()
        binding.tvEdit.setOnClickListener {
            Preferences().setFromEdit(true, requireContext())
            val intent = Intent(activity, InputDataActivity::class.java)
            startActivity(intent)
        }
        binding.tvChangePw.setOnClickListener {
            comingSoon()
        }
        binding.tvDeleteAccount.setOnClickListener {
            comingSoon()
        }
        binding.tvLogout.setOnClickListener {
            firebaseAuth.signOut()
            Preferences().clear(requireContext())
            Toast.makeText(requireContext(), "Anda telah log out!", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, WelcomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun setNama(){
        viewModel.getName(Preferences().getNama(requireContext()).toString())
        viewModel.name.observe(viewLifecycleOwner){
            binding.tvName.text = it.toString()
        }
    }
    private fun setUniv(){
        viewModel.getUniv(Preferences().getUniv(requireContext()).toString())
        viewModel.univ.observe(viewLifecycleOwner){
            binding.tvUniv.text = it.toString()
        }
    }
    private fun comingSoon(){
        Toast.makeText(requireContext(), getString(R.string.coming_soon), Toast.LENGTH_LONG).show()
    }
}