package jt.projects.gbnasaapp.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbnasaapp.R
import jt.projects.gbnasaapp.model.notes.NotesData

class NotesRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    lateinit var data: MutableList<NotesData>
    private var counter = 1

    fun appendItem() {
        data.add(generateNote())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateNote(): NotesData {
        val n = NotesData(topic = "Тема № $counter", fullText = "...")
        counter++
        return n
    }


    fun setAdapterData(responce: ArrayList<NotesData>) {
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
        return data[position].type
    }


    inner class NoteViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: NotesData) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.notes_topic).text =
                    data.topic
                itemView.findViewById<TextView>(R.id.notes_fulltext).text =
                    data.fullText
                itemView.findViewById<ImageView>(R.id.addNote).setOnClickListener {
                    addItem()
                }
                itemView.findViewById<ImageView>(R.id.removeNote).setOnClickListener {
                    removeItem()
                }
                itemView.findViewById<ImageView>(R.id.moveItemDown).setOnClickListener {
                    moveDown() }
                itemView.findViewById<ImageView>(R.id.moveItemUp).setOnClickListener {
                    moveUp() }
            }
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


    }


    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: NotesData) {

        }
    }
}