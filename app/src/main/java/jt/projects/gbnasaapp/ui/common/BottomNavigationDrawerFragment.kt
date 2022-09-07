package jt.projects.gbnasaapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jt.projects.gbnasaapp.MainActivity
import jt.projects.gbnasaapp.R
import jt.projects.gbnasaapp.databinding.BottomNavigationLayoutBinding
import jt.projects.gbnasaapp.utils.SETTINGS_FRAGMENT_TAG


class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_navigation_theme -> (requireActivity() as MainActivity).showThemeDialog()
                R.id.bottom_navigation_settings -> (requireActivity() as MainActivity).showFragmentWithBS(
                    SettingsFragment.newInstance(),
                    SETTINGS_FRAGMENT_TAG
                )
            }
            true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}