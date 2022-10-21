package jt.projects.gbnasaapp.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbnasaapp.databinding.RecyclerNotesBinding
import jt.projects.gbnasaapp.model.notes.NotesData
import jt.projects.gbnasaapp.ui.common.OnStartDragListener
import jt.projects.gbnasaapp.utils.snackBar
import jt.projects.gbnasaapp.viewmodel.notes.NotesDataStatus
import jt.projects.gbnasaapp.viewmodel.notes.NotesViewModel

class NotesFragment : Fragment() {
    private var _binding: RecyclerNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(NotesViewModel::class.java)
    }

    lateinit var itemTouchHelper: ItemTouchHelper
    private val notesAdapter = NotesRecyclerAdapter(object : OnStartDragListener {
        override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }
    })

    private var isNewList = false


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
        binding.recyclerNotesFAB.setOnClickListener { notesAdapter.appendItem() }

        ItemTouchHelper(ItemTouchHelperCallback(notesAdapter))
            .attachToRecyclerView(binding.recyclerViewNotes)


        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(notesAdapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)

        binding.recyclerNotesDiff.setOnClickListener { changeAdapterData() }
    }

    private fun changeAdapterData() {
        notesAdapter.setItems(createItemList(isNewList).map { it })
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<NotesData, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(NotesData(type = NotesData.TYPE_HEADER), false),
                Pair(NotesData(topic = "11", fullText = "Купить цветы жене"), false),
                Pair(NotesData(topic = "232", fullText = "Купить цветы жене"), false),
                Pair(NotesData(topic = "22", fullText = "Поменять резину на зимнюю"), false)
            )
            true -> listOf(
                Pair(NotesData(type = NotesData.TYPE_HEADER), false),
                Pair(NotesData(topic = "333", fullText = "Купить цветы жене"), false),
                Pair(NotesData(topic = "444", fullText = "Поменять резину на зимнюю"), false),
                Pair(NotesData(topic = "232", fullText = "Купить цветы жене"), false)
            )
        }
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