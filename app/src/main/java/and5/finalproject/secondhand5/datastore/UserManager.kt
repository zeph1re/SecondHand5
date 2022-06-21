package and5.finalproject.secondhand5.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.map

class UserManager (context: Context) {

    private val userDataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs")
    private val loginDataStore : DataStore<Preferences> = context.createDataStore(name = "login_prefs")


    companion object{
        val ID = preferencesKey<String>("USER_ID")
        val TOKEN = preferencesKey<String>("USER_TOKEN")
        val LOGIN_STATE= preferencesKey<String>("USER_TOKEN")
    }

    suspend fun saveDataUser(token : String) {
        userDataStore.edit {

        }
    }

    suspend fun deleteDataUser() {
        userDataStore.edit{
            it.clear()
        }
    }

    suspend fun saveDataLogin(login : String) {
        loginDataStore.edit {
            it[LOGIN_STATE] = login
        }
    }

    suspend fun deleteDataLogin() {
        loginDataStore.edit{
            it.clear()
        }
    }

    val userToken : kotlinx.coroutines.flow.Flow<String> = userDataStore.data.map {
        it [ID] ?: ""
    }

    val loginState : kotlinx.coroutines.flow.Flow<String> = loginDataStore.data.map {
        it [LOGIN_STATE] ?: ""
    }
}