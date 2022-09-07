package jt.projects.gbnasaapp.viewmodel.pod

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import jt.projects.gbnasaapp.ui.pod.PodFragment
import jt.projects.gbnasaapp.utils.DBYESTERDAY_FRAGMENT
import jt.projects.gbnasaapp.utils.TODAY_FRAGMENT
import jt.projects.gbnasaapp.utils.YESTERDAY_FRAGMENT
import java.time.LocalDate


// конструктор мы теперь передаем корневую Активити (или фрагмент), хотя
//можно передавать FragmentManager вместе с Lifecycle, но первый способ предпочтительнее
class PodViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf(
        PodFragment(LocalDate.now()),
        PodFragment(LocalDate.now().minusDays(1)),
        PodFragment(LocalDate.now().minusDays(2))
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[TODAY_FRAGMENT]
            1 -> fragments[YESTERDAY_FRAGMENT]
            2 -> fragments[DBYESTERDAY_FRAGMENT]
            else -> fragments[TODAY_FRAGMENT]
        }
    }

}