package and5.finalproject.secondhand5.view.fragment

import and5.finalproject.secondhand5.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
                        R.id.action_splashScreen_to_home, null,
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.splashScreen,
                                true
                            ).build())
                },3000)


        return view

    }

}