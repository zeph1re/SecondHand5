package and5.finalproject.secondhand5.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import android.os.Handler
import android.os.Looper
import androidx.navigation.NavOptions
import androidx.navigation.findNavController


class SplashScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        Handler(Looper.getMainLooper()).postDelayed({
            view.findNavController().navigate(
                R.id.action_splashScreen_to_login, null,
                NavOptions.Builder()
                    .setPopUpTo(
                        R.id.splashScreen,
                        true
                    ).build())
        },2000)
        return view

    }

}