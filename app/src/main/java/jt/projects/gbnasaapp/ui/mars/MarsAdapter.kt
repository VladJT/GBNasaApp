package jt.projects.gbnasaapp.ui.mars

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import jt.projects.gbnasaapp.R
import jt.projects.gbnasaapp.model.mars.MarsPhoto
import jt.projects.gbnasaapp.utils.OnItemViewClickListener

class MarsAdapter(private var onItemViewClickListener: OnItemViewClickListener?) :
    RecyclerView.Adapter<MarsAdapter.MarsViewHolder>() {

    var marsPhotos: List<MarsPhoto> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setMarsData(data: List<MarsPhoto>) {
        marsPhotos = data
        this.notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.mars_photo_card, parent, false)
        return MarsViewHolder(v)
    }

    override fun onBindViewHolder(holder: MarsViewHolder, position: Int) {
        holder.bind(marsPhotos[position])
    }

    override fun getItemCount(): Int {
        return marsPhotos.size
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photo: MarsPhoto) {
            with(itemView) {
                findViewById<ImageView>(R.id.mars_image_view).also {
                    it.load(photo.imgSrc)
                    it.setOnClickListener {
                        onItemViewClickListener?.onImageClick(photo)
                    }
                }
                findViewById<TextView>(R.id.mars_text_view).text =
                    "${photo.earthDate} camera=${photo.camera.name} rover=${photo.rover.name}"
            }
        }
    }

}