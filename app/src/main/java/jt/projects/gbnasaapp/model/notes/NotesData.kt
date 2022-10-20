package jt.projects.gbnasaapp.model.notes

data class NotesData(
    val type: Int = TYPE_TEXT,
    val topic: String = "Topic",
    val fullText: String = "Text"
) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_TEXT = 1
    }
}