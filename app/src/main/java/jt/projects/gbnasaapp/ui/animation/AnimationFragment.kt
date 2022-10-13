package jt.projects.gbnasaapp.ui.animation

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
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
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, ChangeBounds())
            binding.textSlide.visibility = if (textIsVisible) View.VISIBLE else View.GONE
            binding.textTransitionDefault.visibility = if (textIsVisible) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}