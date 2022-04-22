package io.github.tuguzt.preferencesscreenexample

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import io.github.tuguzt.preferencesscreenexample.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: SettingsActivityBinding

    private val defaultSharedPreferences get() = PreferenceManager.getDefaultSharedPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.settings.id, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        defaultSharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onDestroy() {
        defaultSharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        defaultSharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        defaultSharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "example_switch" -> {
                val test = sharedPreferences?.getBoolean("example_switch", false)
                Toast.makeText(this, test.toString(), Toast.LENGTH_SHORT).show()
            }
            "list" -> when (sharedPreferences?.getString("list", null)) {
                "1" -> binding.settings.setBackgroundColor(Color.RED)
                "2" -> binding.settings.setBackgroundColor(Color.GREEN)
                "3" -> binding.settings.setBackgroundColor(Color.BLUE)
            }
        }
    }
}
