package jt.projects.gbnasaapp.ui.common

interface ItemTouchHelperAdapter {
    // будет вызываться, когда элемент списка будет
    //перетянут на достаточное расстояние, чтобы запустить анимацию перемещения
    fun onItemMove(fromPosition: Int, toPosition: Int)

    //будет вызываться во время свайпа по элементу.
    fun onItemDismiss(position: Int)
}
