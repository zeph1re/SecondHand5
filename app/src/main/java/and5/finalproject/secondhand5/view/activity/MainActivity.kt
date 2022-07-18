package and5.finalproject.secondhand5.view.activity

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var userManager: UserManager
    private lateinit var navController: NavController

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userManager = UserManager(this)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        navController = navHostFragment.navController
        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottomNavigationView.setupWithNavController(navController)





        navController.addOnDestinationChangedListener { _, destination, _ ->
            //check fragment name is "home" or not

            if (destination.id == R.id.home) {
                GlobalScope.launch {
                    userManager.deletePostImageCache()
                    userManager.savePostImageCache("")
                }
                bottomnav.visibility = View.VISIBLE
            } else if (destination.id == R.id.notification) {
                GlobalScope.launch {
                    userManager.deletePostImageCache()
                    userManager.savePostImageCache("")
                }
                bottomnav.visibility = View.VISIBLE
            } else if (destination.id == R.id.addProduct) {

                bottomnav.visibility = View.GONE
            } else if (destination.id == R.id.myListProduct) {
                GlobalScope.launch {
                    userManager.deletePostImageCache()
                    userManager.savePostImageCache("")
                }
                bottomnav.visibility = View.VISIBLE
            } else if (destination.id == R.id.account) {
                GlobalScope.launch {
                    userManager.deletePostImageCache()
                    userManager.savePostImageCache("")
                }
                bottomnav.visibility = View.VISIBLE
            } else {
                bottomnav.visibility = View.GONE
            }
        }


    }


}

