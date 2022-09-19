package jt.projects.gbnasaapp.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import jt.projects.gbnasaapp.R
import jt.projects.gbnasaapp.databinding.MarsFragmentBinding
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.model.mars.MarsPhoto
import jt.projects.gbnasaapp.utils.DURATION_ITEM_ANIMATOR
import jt.projects.gbnasaapp.utils.OnItemViewClickListener
import jt.projects.gbnasaapp.utils.showPictureInFullMode
import jt.projects.gbnasaapp.utils.snackBar
import jt.projects.gbnasaapp.viewmodel.mars.MarsData
import jt.projects.gbnasaapp.viewmodel.mars.MarsViewModel
import java.time.LocalDate


class MarsFragment : Fragment() {

    private var _binding: MarsFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MarsAdapter(object : OnItemViewClickListener {
        override fun onImageClick(data: MarsPhoto) {
            //    snackBar("${data.id}")
            showPictureInFullMode(data.imgSrc)
        }
    })

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
        _binding = MarsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        val localDate: LocalDate =
            LocalDate.now().minusDays(SharedPref.settings.marsPhotoDaysBefore.toLong())
        viewModel.loadMarsByDate(localDate)
    }

    private fun initRecyclerView() {
        binding.marsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.marsRecyclerView.adapter = adapter
        //  разделитель карточек
        DividerItemDecoration(requireContext(), GridLayoutManager.HORIZONTAL).also {
            it.setDrawable(resources.getDrawable(R.drawable.separator, null))
            binding.marsRecyclerView.addItemDecoration(it)
        }
        // Установим анимацию. А чтобы было хорошо заметно, сделаем анимацию долгой
        val animator = DefaultItemAnimator().apply {
            addDuration = DURATION_ITEM_ANIMATOR
            changeDuration = DURATION_ITEM_ANIMATOR
            removeDuration = DURATION_ITEM_ANIMATOR
            moveDuration = DURATION_ITEM_ANIMATOR
        }
        binding.marsRecyclerView.itemAnimator = animator
    }

    private fun renderData(data: MarsData) {
        when (data) {
            is MarsData.Success -> {
                binding.marsProgressBar.visibility = View.GONE
                adapter.setMarsData(data.serverResponseData.photos)
            }
            is MarsData.Loading -> {
                binding.marsProgressBar.visibility = View.VISIBLE
            }
            is MarsData.Error -> {
                binding.marsProgressBar.visibility = View.GONE
                adapter.setMarsData(listOf())
                snackBar(data.error.message ?: "")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}