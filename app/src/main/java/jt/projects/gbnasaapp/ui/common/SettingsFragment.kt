package jt.projects.gbnasaapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import jt.projects.gbnasaapp.App
import jt.projects.gbnasaapp.R
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
        initExitButton()
        initMarsDaysBeforeSlider()
    }

    private fun initSaveButton() {
        binding.buttonSaveSettings.setOnClickListener {
            App.instance.sharedPref.settings.podHD = binding.switchPodHd.isChecked
            App.instance.sharedPref.settings.marsPhotoDaysBefore =
                binding.sliderMarsPhotoDaysBefore.value.toInt()
            App.instance.sharedPref.save()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initExitButton() {
        binding.buttonExitSettings.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initPodHdSwitch() {
        binding.switchPodHd.isChecked = App.instance.sharedPref.settings.podHD
    }

    private fun initMarsDaysBeforeSlider() {
        binding.sliderMarsPhotoDaysBefore.value =
            App.instance.sharedPref.settings.marsPhotoDaysBefore.toFloat()
    }


    override fun onDestroyView() {
        requireActivity().findViewById<ImageView>(R.id.fab_top_imageview).animate()
            .setDuration(1000L).alpha(1f)
        _binding = null
        super.onDestroyView()
    }
}