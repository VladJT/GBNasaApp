package jt.projects.gbnasaapp.ui.notes

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbnasaapp.ui.common.ItemTouchHelperViewHolder

//Это интерфейс, который позволяет вам подписаться на события, связанные с перетаскиванием и
//смахиванием элементов списка. Там же вы можете контролировать состояние выбранного элемента
//или переопределить стандартные анимации
class ItemTouchHelperCallback(private val adapter: NotesRecyclerAdapter) :
    ItemTouchHelper.Callback() {

    //метод для включения возможности перетаскивания по
    //длинному нажатию на элемент списка. С вашей стороны не нужно вешать никакие
    //onLongClickListener
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    //подключает возможность свайпа
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    //этот callback позволяет определить
    //направления перетаскивания и свайпа. Для перетаскивания мы используем направления
    //вверх/вниз, а для смахивания — влево/вправо. Если у вас горизонтальный scroll, то
    //направления будут другими. Или вы можете определить смахивание только вправо
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(
            dragFlags,
            swipeFlags
        )
    }

    //нужны для оповещения нашего адаптера, что некий элемент изменил положение или был
    //удалён, чтобы адаптер мог корректно обработать эти события.
    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    //нужны для оповещения нашего адаптера, что некий элемент изменил положение или был
    //удалён, чтобы адаптер мог корректно обработать эти события.
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    // нужны, чтобы наш ViewHolder корректно обрабатывал
    //выделение элемента
    override fun onSelectedChanged(
        viewHolder: RecyclerView.ViewHolder?, actionState:
        Int
    ) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemViewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    // нужны, чтобы наш ViewHolder корректно обрабатывал
    //выделение элемента
    override fun clearView(
        recyclerView: RecyclerView, viewHolder:
        RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
        itemViewHolder.onItemClear()
    }
}
