package jt.projects.gbnasaapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import jt.projects.dil.inject
import jt.projects.gbnasaapp.databinding.ActivityMainBinding
import jt.projects.gbnasaapp.model.SharedPref
import jt.projects.gbnasaapp.ui.animation.AnimationFragment
import jt.projects.gbnasaapp.ui.common.BottomNavigationDrawerFragment
import jt.projects.gbnasaapp.ui.common.SettingsFragment
import jt.projects.gbnasaapp.ui.mars.MarsFragment
import jt.projects.gbnasaapp.ui.notes.NotesFragment
import jt.projects.gbnasaapp.ui.pod.PodViewPagerFragment
import jt.projects.gbnasaapp.utils.BOTTOM_NAV_FRAGMENT_TAG
import jt.projects.gbnasaapp.utils.SETTINGS_FRAGMENT_TAG


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isMainMenuOnBottomBar = true
    private val sharedPref: SharedPref by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(sharedPref.getData().theme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        initToolbar()
        //initFabListener()

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PodViewPagerFragment.newInstance())
                .commitNow()
        }

        initLogoListener()

        binding.fabTop.setOnClickListener {
            ObjectAnimator.ofFloat(binding.fabTopImageview, View.ROTATION, 0f, 360f)
                .setDuration(500L).start()
            binding.fabTopImageview.animate().setDuration(1000L).alpha(0.1f)
            showFragmentWithBS(SettingsFragment.newInstance(), SETTINGS_FRAGMENT_TAG)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            onOptionsItemSelected(item)
            true
        }

    }


    // TODO почему-то не работает - надо переделать!
    private fun initLogoListener() {
        binding.logoIconMars.setOnClickListener {
            Toast.makeText(this, "mars", Toast.LENGTH_SHORT).show()
            //   showFragment(MarsFragment.newInstance())
        }
        binding.logoIconSolar.setOnClickListener {
            Toast.makeText(this, "solar", Toast.LENGTH_SHORT).show()
        }
        binding.logoIconEarth.setOnClickListener {
            Toast.makeText(this, "earth", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            navigationIcon =
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.ic_hamburger_menu
                )
        }
//        (binding.toolbar as AppBarLayout).addOnOffsetChangedListener { appBarLayout, verticalOffset ->
//            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
//                //  on Collapse
//                binding.fabTopImageview.alpha = 0f
//            } else {
//                binding.fabTopImageview.alpha = 1f
//            }
//        }
    }

//    private fun initFabListener() {
//        binding.fab.setOnClickListener {
//            if (isMainMenuOnBottomBar) {
//                isMainMenuOnBottomBar = false
//                binding.bottomAppBar.navigationIcon = null
//                binding.bottomAppBar.fabAlignmentMode =
//                    BottomAppBar.FAB_ALIGNMENT_MODE_END
//                binding.fab.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        this,
//                        R.drawable.ic_back_fab
//                    )
//                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
//            } else {
//                isMainMenuOnBottomBar = true
//                binding.bottomAppBar.navigationIcon =
//                    ContextCompat.getDrawable(
//                        this,
//                        R.drawable.ic_hamburger_menu
//                    )
//                binding.bottomAppBar.fabAlignmentMode =
//                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                binding.fab.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        this,
//                        R.drawable.ic_plus_fab
//                    )
//                )
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
//            }
//        }
//    }

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
            R.id.menu_action_solar -> {
                showFragment(AnimationFragment.newInstance())
            }
            R.id.menu_action_notes -> {
                showFragment(NotesFragment.newInstance())
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
            .setCustomAnimations(R.animator.std_left, R.animator.std_right)
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    fun showFragmentWithBS(fragment: Fragment, fragmentTag: String) {
        val f = supportFragmentManager.findFragmentByTag(fragmentTag)
        if (f == null) {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
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
                if (sharedPref.getData().theme != newTheme) {
                    sharedPref.settings.theme = newTheme
                    sharedPref.save()
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