package and5.finalproject.secondhand5.view.activity

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.view.fragment.user.Home
import and5.finalproject.secondhand5.view.fragment.user.Login
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.view.*
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

        userManager= UserManager(this)
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
            } else if (destination.id == R.id.notification){
                GlobalScope.launch {
                    userManager.deletePostImageCache()
                    userManager.savePostImageCache("")
                }
                bottomnav.visibility = View.VISIBLE
            } else if (destination.id == R.id.addProduct){

                bottomnav.visibility = View.GONE
            } else if (destination.id == R.id.myListProduct){
                GlobalScope.launch {
                    userManager.deletePostImageCache()
                    userManager.savePostImageCache("")
                }
                bottomnav.visibility = View.VISIBLE
            } else if (destination.id == R.id.account){
                GlobalScope.launch {
                    userManager.deletePostImageCache()
                    userManager.savePostImageCache("")
                }
                bottomnav.visibility = View.VISIBLE
            }
            else {
                bottomnav.visibility = View.GONE
            }
        }


    }



}

