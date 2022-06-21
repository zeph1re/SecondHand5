package and5.finalproject.secondhand5.view.activity

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.fragment.seller.AddProduct
import and5.finalproject.secondhand5.view.fragment.seller.MyListProduct
import and5.finalproject.secondhand5.view.fragment.seller.Notification
import and5.finalproject.secondhand5.view.fragment.user.Account
import and5.finalproject.secondhand5.view.fragment.user.Home
import and5.finalproject.secondhand5.view.fragment.user.Login
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            //check fragment name is "home" or not
            if (destination.id == R.id.home) {
                bottomnav.visibility = View.VISIBLE

            }else if (destination.id == R.id.login){
            bottomnav.visibility = View.GONE
            }
            else {
            bottomnav.visibility = View.GONE
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
}