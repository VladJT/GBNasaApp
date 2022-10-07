package jt.projects.gbnasaapp.ui.pod

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import jt.projects.gbnasaapp.databinding.PictureOfTheDayMainBinding
import jt.projects.gbnasaapp.utils.DBYESTERDAY_FRAGMENT
import jt.projects.gbnasaapp.utils.TODAY_FRAGMENT
import jt.projects.gbnasaapp.utils.YESTERDAY_FRAGMENT
import jt.projects.gbnasaapp.viewmodel.pod.PodViewPagerAdapter


class PodViewPagerFragment : Fragment() {
    private var _binding: PictureOfTheDayMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PodViewPagerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PictureOfTheDayMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = PodViewPagerAdapter(requireActivity())
        binding.indicator.setViewPager(binding.viewPager)
        setTabs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                TODAY_FRAGMENT -> "Сегодня"
                YESTERDAY_FRAGMENT -> "Вчера"
                DBYESTERDAY_FRAGMENT -> "Позавчера"
                else -> "Сегодня"
            }
        }.attach()
    }
}