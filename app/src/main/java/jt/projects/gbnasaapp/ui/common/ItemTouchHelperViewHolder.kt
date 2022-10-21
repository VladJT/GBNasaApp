package jt.projects.gbnasaapp.ui.common

interface ItemTouchHelperViewHolder {
    //будет вызываться в процессе смахивания или перетаскивания элемента
    fun onItemSelected()

    //будет вызываться когда этот процесс закончится
    fun onItemClear()
}