package jt.projects.gbnasaapp.ui.pod

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import jt.projects.dil.inject
import jt.projects.gbnasaapp.R
import jt.projects.gbnasaapp.databinding.PictureOfTheDayFragmentBinding
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.utils.snackBar
import jt.projects.gbnasaapp.utils.toDecoratedDescription
import jt.projects.gbnasaapp.utils.toDecoratedSign
import jt.projects.gbnasaapp.viewmodel.pod.PictureOfTheDayData
import jt.projects.gbnasaapp.viewmodel.pod.PictureOfTheDayViewModel
import java.time.LocalDate


class PodFragment(val localDate: LocalDate = LocalDate.now()) : Fragment() {

    private var _binding: PictureOfTheDayFragmentBinding? = null
    private val binding get() = _binding!!
    private var isExpanded = true

    private val sharedPref: SharedPref by inject()


    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    companion object {
        fun newInstance() = PodFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.loadPictureOfTheDayByDate(localDate)
        _binding = PictureOfTheDayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                binding.podProgressBar.visibility = View.GONE
                val serverResponseData = data.serverResponseData
                var url: String? = serverResponseData.url
                if (sharedPref.getData().podHD) {
                    url = serverResponseData.hdurl
                }
                if (url.isNullOrEmpty()) {
                    snackBar("Link is empty")
                } else {
                    if (serverResponseData.mediaType == "image") {
                        showImage(url)
                        binding.imageViewPod.setOnClickListener {
                            // snackBar("Идет загрузка изображения в HD...")
                            // serverResponseData.hdurl?.let { showPictureInFullMode(it) }
                            isExpanded = !isExpanded
                            if (isExpanded)
                                expandImagePod()
                            else
                                minimizeImagePod()
                        }
                    }
                    if (serverResponseData.mediaType == "video") {
                        showVideo(serverResponseData.url)
                    }
                }
                with(data.serverResponseData) {
                    val author = copyright ?: "no Author"

                    val face = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_place_24
                    )!!.toBitmap()

                    val sTitle = SpannableString("_ ${title}").apply {
                        setSpan(
                            ImageSpan(requireContext(), face),
                            0,
                            1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }

                    binding.podHeader.text = sTitle
                    // binding.podDescription.text = "${explanation}\n\n©️${authorSpan} - ${date}"
                    binding.podDescription.text = explanation!!.toDecoratedDescription()
                    binding.podAuthor.text = "©${author} - ${date}".toDecoratedSign()
                }
            }
            is PictureOfTheDayData.Loading -> {
                binding.podProgressBar.visibility = View.VISIBLE
            }
            is PictureOfTheDayData.Error -> {
                binding.podProgressBar.visibility = View.GONE
                data.error.message?.let { snackBar(it) }
            }
        }
    }


    private fun prepareAnimationPod() {
        val imageTransition = ChangeBounds()
        imageTransition.interpolator = AnticipateOvershootInterpolator(1.0f)
        imageTransition.duration = 1000L

        val textTransition = TransitionSet().apply {
            val fade = Fade()
            fade.duration = 1000L
            addTransition(fade)
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000L
            addTransition(changeBounds)
        }

        TransitionManager.beginDelayedTransition(binding.podDescriptionContainer, textTransition)
        TransitionManager.beginDelayedTransition(binding.podAuthorContainer, textTransition)
        TransitionManager.beginDelayedTransition(binding.imageViewPodContainer, imageTransition)
    }

    private fun expandImagePod() {
        prepareAnimationPod()

        binding.imageViewPod.layoutParams.let { params ->
            params.height = resources.getDimensionPixelSize(R.dimen.image_height_normal)
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            binding.imageViewPod.layoutParams = params
        }
        binding.podDescription.visibility = View.GONE
        binding.podAuthor.visibility = View.GONE
    }

    private fun minimizeImagePod() {
        prepareAnimationPod()
        binding.imageViewPod.layoutParams.let { params ->
            params.height = resources.getDimensionPixelSize(R.dimen.image_height_small)
            params.width = resources.getDimensionPixelSize(R.dimen.image_width_small)
            binding.imageViewPod.layoutParams = params
        }
        binding.podDescription.visibility = View.VISIBLE
        binding.podAuthor.visibility = View.VISIBLE
    }

    private fun showVideo(url: String?) {
        binding.imageViewPod.visibility = View.INVISIBLE
        binding.webview.visibility = View.VISIBLE
        url?.let {
            binding.webview.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.pluginState = WebSettings.PluginState.ON
                settings.mediaPlaybackRequiresUserGesture = false
                webChromeClient = WebChromeClient()
            }.loadUrl(it)
        }
    }


    private fun showImage(url: String) {
        binding.imageViewPod.visibility = View.VISIBLE
        //Coil в работе: достаточно вызвать у нашего ImageView нужную extension - функцию и передать ссылку на изображение
        //а в лямбде указать дополнительные параметры (не обязательно) для отображения ошибки, процесса загрузки, анимации смены изображений
        binding.imageViewPod.load(url) {
            lifecycle(this@PodFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
            crossfade(true)
        }
    }
}