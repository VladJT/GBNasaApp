package jt.projects.gbnasaapp.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jt.projects.gbnasaapp.databinding.MarsFragmentBinding
import jt.projects.gbnasaapp.viewmodel.mars.MarsData
import jt.projects.gbnasaapp.viewmodel.mars.MarsViewModel
import java.time.LocalDate

class MarsFragment : Fragment() {

    private var _binding: MarsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarsViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MarsViewModel::class.java)
    }

    companion object {
        fun newInstance() = MarsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getDate().observe(viewLifecycleOwner) { renderData(it) }
        val localDate: LocalDate = LocalDate.now().minusDays(7)
        viewModel.loadMarsByDate(localDate)
        _binding = MarsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun renderData(data: MarsData) {
        val i = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}