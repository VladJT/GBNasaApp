package jt.projects.gbnasaapp.ui.notes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbnasaapp.model.notes.NotesData

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Pair<NotesData, Boolean>)
}