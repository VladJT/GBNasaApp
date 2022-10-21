package jt.projects.gbnasaapp.ui.notes

import androidx.recyclerview.widget.DiffUtil
import jt.projects.gbnasaapp.model.notes.Change
import jt.projects.gbnasaapp.model.notes.NotesData


class DiffUtilCallback(
    private var oldItems: List<Pair<NotesData, Boolean>>,
    private var newItems: List<Pair<NotesData, Boolean>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size//возвращает размер старого списка

    override fun getNewListSize(): Int = newItems.size//возвращает размер нового списка

    //должен возвращать true, если элементы списка одинаковые. Обычно тут как раз
    //сравнивают id элементов, так получается удобнее всего.
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id

    //вызывается, только если areItemsTheSame вернул true. Это дополнительная
    //проверка, которая сравнивает переменные класса (элемента списка) между собой по
    //аналогии с equals, чтобы выяснить, изменились ли данные внутри.
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        (oldItems[oldItemPosition].first.fullText == newItems[newItemPosition].first.fullText && oldItems[oldItemPosition].first.topic == newItems[newItemPosition].first.topic)

    // вызывается, только если areContentsTheSame возвращает false. То есть старый и новый
    //элементы списка имеют одинаковый id, но содержат разные данные. Тут как раз мы
    //используем payload для отображения информации в дальнейшем. Этот метод можно не
    //переопределять, но тогда элементы списка будут заменяться полностью, то есть мерцать. А
    //это нам совсем не нужно
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return Change(
            oldItem, newItem
        )
    }
}
