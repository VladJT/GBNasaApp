package jt.projects.gbnasaapp.model.mars


import com.google.gson.annotations.SerializedName

data class MarsServerResponseData(
    @SerializedName("photos")
    val photos: List<MarsPhoto>
)