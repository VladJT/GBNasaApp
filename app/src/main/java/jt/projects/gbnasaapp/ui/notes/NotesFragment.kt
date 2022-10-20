package jt.projects.gbnasaapp.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jt.projects.gbnasaapp.databinding.RecyclerNotesBinding
import jt.projects.gbnasaapp.utils.snackBar
import jt.projects.gbnasaapp.viewmodel.notes.NotesDataStatus
import jt.projects.gbnasaapp.viewmodel.notes.NotesViewModel

class NotesFragment : Fragment() {

    private var _binding: RecyclerNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(NotesViewModel::class.java)
    }
    private val notesAdapter = NotesRecyclerAdapter()


    companion object {
        fun newInstance() = NotesFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun initRecyclerView() {
        binding.recyclerViewNotes.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = notesAdapter
        }
    }

    private fun renderData(data: NotesDataStatus) {
        when (data) {
            is NotesDataStatus.Success -> data.responce?.let { notesAdapter.setAdapterData(it) }
            is NotesDataStatus.Loading -> {
                //    binding.marsProgressBar.visibility = View.VISIBLE
            }
            is NotesDataStatus.Error -> snackBar(data.error.message ?: "")
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}