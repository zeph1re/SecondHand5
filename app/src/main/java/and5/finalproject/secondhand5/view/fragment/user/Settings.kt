package and5.finalproject.secondhand5.view.fragment.user

import and5.finalproject.secondhand5.R
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class Settings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}