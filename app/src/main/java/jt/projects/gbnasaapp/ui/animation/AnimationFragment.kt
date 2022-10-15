package jt.projects.gbnasaapp.ui.animation

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import jt.projects.gbnasaapp.R
import jt.projects.gbnasaapp.databinding.FragmentAnimationsBinding

class AnimationFragment : Fragment() {
    private var _binding: FragmentAnimationsBinding? = null
    private val binding get() = _binding!!
    private var textIsVisible = false

    companion object {
        fun newInstance() = AnimationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonTransitions.setOnClickListener {
            // Виды Transition
            //можно менять: они определяются вторым аргументом для функции beginDelayedTransition:
            //1. ChangeBounds меняет расположение элемента и его размеры, двигает нашу кнопку по
            //умолчанию.
            //2. Fade — медленное проявление и затухание элемента. Применяется для отображения нашего
            //текста по умолчанию.
            //3. TransitionSet — это набор других Transitions, таких как ChangeBounds или Fade.
            //4. AutoTransition — TransitionSet, содержащий Fade out, ChangeBounds и Fade в таком
            //порядке. То есть сначала исчезают view, которые должны исчезнуть, затем изменяется
            //расположение остальных view, затем появляются новые. AutoTransition применяется по
            //умолчанию, если вы не передаёте никаких параметров в качестве второго аргумента в
            //функцию beginDelayedTransition.

            textIsVisible = !textIsVisible

            //TransitionManager.beginDelayedTransition(binding.transitionsContainer, Slide(Gravity.END))//Аналог Fade. Элемент появляется, не меняя видимость, а выезжая из-за границы экрана
            TransitionManager.beginDelayedTransition(binding.transitionsContainer)
            binding.textSlide.visibility = if (textIsVisible) View.VISIBLE else View.GONE
            binding.textTransitionDefault.visibility =
                if (textIsVisible) View.VISIBLE else View.GONE
        }
        binding.recyclerView.adapter = Adapter()
    }

    /**
     * Пустой адаптер, пустой холдер. Вся магия у нас происходит по клику на любой элемент списка в
    методе Explode. Разберёмся, что в нём происходит. Для начала нам понадобится прямоугольник с
    координатами по углам экрана. Внутри этого прямоугольника мы запустим нашу анимацию, в
    процессе которой элементы улетят за границы прямоугольника, то есть экрана. Для этого мы
    создадим класс Rect и передадим ему координаты при помощи метода View.getGlobalVisbleRect.
    Затем мы создаём Transition типа Explode и вешаем на неё EpicenterCallback, который мы
    упоминали выше. Он возвращает прямоугольник с границами нашего экрана. Далее устанавливаем
    длительность анимации и вызываем уже знакомый нам метод beginDelayedTransition. Запустите
    и посмотрите, что получилось. Эпицентром “взрыва” является тот элемент, на который вы нажимаете.
     */
    private fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.duration = 1000
        val set = TransitionSet()
            .addTransition(explode)
            .addTransition(Fade().addTarget(clickedView))
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    transition.removeListener(this)
                }
            })
        TransitionManager.beginDelayedTransition(binding.recyclerView, set)

        binding.recyclerView.adapter = null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_animations_explode_recycle_view_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }

        override fun getItemCount(): Int {
            return 16
        }
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

