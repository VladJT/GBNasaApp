package jt.projects.gbnasaapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import jt.projects.gbnasaapp.databinding.FragmentSettingsBinding
import jt.projects.gbnasaapp.model.SharedPref


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SettingsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPodHdSwitch()
        initSaveButton()
    }

    private fun initSaveButton(){
        binding.buttonSaveSettings.setOnClickListener {
            SharedPref.settings.podHD = binding.switchPodHd.isChecked
            SharedPref.save()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initPodHdSwitch() {
        binding.switchPodHd.isChecked = SharedPref.settings.podHD
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}