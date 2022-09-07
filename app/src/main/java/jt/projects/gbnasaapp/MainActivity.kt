package jt.projects.gbnasaapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import jt.projects.gbnasaapp.databinding.ActivityMainBinding
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.ui.common.BottomNavigationDrawerFragment
import jt.projects.gbnasaapp.ui.pod.PodViewPagerFragment

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
                BottomNavigationDrawerFragment().show(supportFragmentManager, "tag")
            }
            R.id.menu_action_pod -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PodViewPagerFragment.newInstance())
                    .commitNow()
            }
            R.id.menu_action_themes -> {
                showThemeDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showThemeDialog() {
        val items = resources.getStringArray(R.array.choose_notes_theme)
        // Создаём билдер и передаём контекст приложения
        AlertDialog.Builder(this@MainActivity)
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