package jt.projects.gbnasaapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import jt.projects.gbnasaapp.databinding.ActivityMainBinding
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.ui.common.BottomNavigationDrawerFragment
import jt.projects.gbnasaapp.ui.common.SettingsFragment
import jt.projects.gbnasaapp.ui.mars.MarsFragment
import jt.projects.gbnasaapp.ui.pod.PodViewPagerFragment
import jt.projects.gbnasaapp.utils.BOTTOM_NAV_FRAGMENT_TAG
import jt.projects.gbnasaapp.utils.SETTINGS_FRAGMENT_TAG

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isMainMenuOnBottomBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPref.initSharedPreferencesContext(applicationContext)
        setTheme(SharedPref.getData().theme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.bottomAppBar)
        initFabListener()

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PodViewPagerFragment.newInstance())
                .commitNow()
        }

        // TODO костыль для решения вопроса отрисовки иконок меню при старте приложения
        // пока не понятна ошибка почему при старте - они не отрисовываются, но надо переделать
        Thread {
            Thread.sleep(2000)
            runOnUiThread() { binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar) }
        }.start()
    }


    private fun initFabListener() {
        binding.fab.setOnClickListener {
            if (isMainMenuOnBottomBar) {
                isMainMenuOnBottomBar = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode =
                    BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMainMenuOnBottomBar = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_hamburger_menu
                    )
                binding.bottomAppBar.fabAlignmentMode =
                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(
                    supportFragmentManager,
                    BOTTOM_NAV_FRAGMENT_TAG
                )
            }
            R.id.menu_action_pod -> {
                showFragment(PodViewPagerFragment.newInstance())
            }
            R.id.menu_action_mars -> {
                showFragment(MarsFragment.newInstance())
            }
            R.id.menu_action_settings -> {
                showFragmentWithBS(SettingsFragment.newInstance(), SETTINGS_FRAGMENT_TAG)
            }
            R.id.menu_action_themes -> {
                showThemeDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    fun showFragmentWithBS(fragment: Fragment, fragmentTag: String) {
        val f = supportFragmentManager.findFragmentByTag(fragmentTag)
        if (f == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, fragment, fragmentTag)
                .addToBackStack("")
                .commit()
        }
    }

    fun showThemeDialog() {
        val items = resources.getStringArray(R.array.choose_notes_theme)
        // Создаём билдер и передаём контекст приложения
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Выбор темы")
            .setItems(items) { _, which ->
                var newTheme = 0
                when (items[which]) {
                    "Astro (default_theme)" -> newTheme = R.style.Theme_GBNasaApp
                    "Mars (Cut_style)" -> newTheme = R.style.AppTheme_Mars
                    "Mercury (Rounded_style)" -> newTheme = R.style.AppTheme_Neptune
                }
                if (SharedPref.getData().theme != newTheme) {
                    SharedPref.settings.theme = newTheme
                    SharedPref.save()
                    recreate()
                }
            }
            .setNegativeButton("Отмена", null)
            .create().show()
    }

}