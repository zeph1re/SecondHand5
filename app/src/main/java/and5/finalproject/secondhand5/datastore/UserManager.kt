package and5.finalproject.secondhand5.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.map

class UserManager (context: Context) {

    private val userDataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs")

    companion object{
        val ID = preferencesKey<String>("USER_ID")
        val TOKEN = preferencesKey<String>("USER_TOKEN")
        val LOGIN_STATE= preferencesKey<String>("USER_TOKEN")
    }

    suspend fun saveDataUser(token : String) {
        userDataStore.edit {
            it[TOKEN] = token
        }
    }

    suspend fun saveDataLogin(login : String) {
        userDataStore.edit {
            it[LOGIN_STATE] = login
        }
    }

    suspend fun deleteDataLogin() {
        userDataStore.edit{
            it.clear()
        }
    }

    val userID : kotlinx.coroutines.flow.Flow<String> = userDataStore.data.map {
        it [ID] ?: ""
    }

    val userToken : kotlinx.coroutines.flow.Flow<String> = userDataStore.data.map {
        it [TOKEN] ?: ""
    }

    val loginState : kotlinx.coroutines.flow.Flow<String> = userDataStore.data.map {
        it [LOGIN_STATE] ?: ""
    }
}