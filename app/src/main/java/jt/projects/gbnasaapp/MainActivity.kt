package jt.projects.gbnasaapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import jt.projects.gbnasaapp.databinding.ActivityMainBinding
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.ui.common.BottomNavigationDrawerFragment
import jt.projects.gbnasaapp.ui.common.SettingsFragment
import jt.projects.gbnasaapp.ui.mars.MarsFragment
import jt.projects.gbnasaapp.ui.pod.PodViewPagerFragment
import jt.projects.gbnasaapp.utils.BOTTOM_NAV_FRAGMENT_TAG
import jt.projects.gbnasaapp.utils.SETTINGS_FRAGMENT_TAG
import jt.projects.gbnasaapp.utils.toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPref.initSharedPreferencesContext(applicationContext)
        setTheme(SharedPref.getData().theme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        initToolbar()

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PodViewPagerFragment.newInstance())
                .commitNow()
        }

        initLogoListener()
        binding.fabTop.setOnClickListener {
            showFragmentWithBS(SettingsFragment.newInstance(), SETTINGS_FRAGMENT_TAG)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            onOptionsItemSelected(item)
            true
        }

    }

    private fun initLogoListener() {
        binding.logoIconMars.setOnClickListener {
            toast("mars")
            showFragment(MarsFragment.newInstance())
        }
        binding.logoIconSolar.setOnClickListener {
            toast("solar")
        }
        binding.logoIconEarth.setOnClickListener {
            toast("earth")
        }
    }

    private fun initToolbar() {
        binding.toolbar.navigationIcon =
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_hamburger_menu
            )
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
            R.id.menu_action_solar -> {
                toast("В разработке...")
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
                    resources.getString(R.string.theme_default) -> newTheme =
                        R.style.Theme_GBNasaApp
                    resources.getString(R.string.theme_1) -> newTheme = R.style.AppTheme_Mars
                    resources.getString(R.string.theme_2) -> newTheme = R.style.AppTheme_Neptune
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            showExitDialog()
        } else super.onBackPressed()
    }

    @Suppress("DEPRECATION")
    fun showExitDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Выход")
            .setMessage("Вы уверены, что хотите выйти?")
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ -> finish() }//иначе Activity переходит в "спящий режим" и остается в стеке
            .setNegativeButton(android.R.string.no, null)
            .setIcon(R.drawable.mars)
            .show()
    }
}