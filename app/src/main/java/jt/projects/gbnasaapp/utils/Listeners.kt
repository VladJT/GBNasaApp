package jt.projects.gbnasaapp.utils

import jt.projects.gbnasaapp.model.mars.MarsPhoto

interface OnItemViewClickListener {
    fun onImageClick(data: MarsPhoto)
//    fun onButtonFavoritesClick(weather: Weather, operation: OperationType)
}

