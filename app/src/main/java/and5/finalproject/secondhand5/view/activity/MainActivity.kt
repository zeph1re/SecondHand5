package and5.finalproject.secondhand5.view.activity

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var userManager: UserManager
    private lateinit var navController: NavController

    lateinit var executor : Executor
    lateinit var biometricPromt : BiometricPrompt
    lateinit var promtInfo : BiometricPrompt.PromptInfo

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biometricAuth()


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

    private fun biometricAuth() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPromt = BiometricPrompt(this@MainActivity, executor, object:BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@MainActivity, "Error ${errString}" , Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@MainActivity, "Successfully", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@MainActivity, "Auth Failed", Toast.LENGTH_SHORT).show()
            }
        })

        //Setup Alert Dialog
        promtInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Use Fingerprint to Open this App")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPromt.authenticate(promtInfo)
    }


}

