package jt.projects.gbnasaapp.ui.notes


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import jt.projects.gbnasaapp.R
import jt.projects.gbnasaapp.model.notes.NotesData
import jt.projects.gbnasaapp.ui.common.ItemTouchHelperAdapter
import jt.projects.gbnasaapp.ui.common.ItemTouchHelperViewHolder
import jt.projects.gbnasaapp.ui.common.OnStartDragListener


class NotesRecyclerAdapter(private val dragListener: OnStartDragListener) :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    lateinit var data: MutableList<Pair<NotesData, Boolean>>

    private var counter = 1

    fun appendItem() {
        data.add(generateNote())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateNote(): Pair<NotesData, Boolean> {
        val n = Pair(NotesData(topic = "Тема № $counter", fullText = "..."), false)
        counter++
        return n
    }


    fun setAdapterData(responce: MutableList<Pair<NotesData, Boolean>>) {
        data = responce
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            NotesData.TYPE_TEXT ->
                NoteViewHolder(
                    inflater.inflate(
                        R.layout.recycler_item_notes,
                        parent,
                        false
                    ) as View
                )
            else ->
                HeaderViewHolder(
                    inflater.inflate(
                        R.layout.recycler_item_header,
                        parent,
                        false
                    ) as View
                )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].first.type
    }

    //onItemMove будет вызываться, когда элемент списка будет
    //перетянут на достаточное расстояние, чтобы запустить анимацию перемещения
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(
                if (toPosition > fromPosition) toPosition - 1 else toPosition,
                this
            )
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    //onItemDismiss будет вызываться во время свайпа по элементу
    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class NoteViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<NotesData, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.notes_topic).text =
                    data.first.topic
                itemView.findViewById<TextView>(R.id.notes_fulltext).text =
                    data.first.fullText

                itemView.findViewById<ImageView>(R.id.addNote).setOnClickListener {
                    addItem()
                }
                itemView.findViewById<ImageView>(R.id.removeNote).setOnClickListener {
                    removeItem()
                }

                itemView.findViewById<ImageView>(R.id.moveItemDown).setOnClickListener {
                    moveDown()
                }
                itemView.findViewById<ImageView>(R.id.moveItemUp).setOnClickListener {
                    moveUp()
                }

                itemView.findViewById<TextInputLayout>(R.id.notes_fulltext_layout).visibility =
                    if (data.second) View.VISIBLE else View.GONE
                itemView.findViewById<TextInputLayout>(R.id.notes_topic_layout)
                    .setEndIconOnClickListener {
                        toggleText()
                    }
                itemView.findViewById<ImageView>(R.id.dragHandleNote)
                    .setOnTouchListener { _, event ->
                        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                            dragListener.onStartDrag(this)
                        }
                        false
                    }

            }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun addItem() {
            data.add(layoutPosition, generateNote())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.CYAN)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }


    }


    // ***** HEADER *****
    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<NotesData, Boolean>) {

        }
    }

}