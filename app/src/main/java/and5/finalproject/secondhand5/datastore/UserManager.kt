package and5.finalproject.secondhand5.datastore

import android.content.Context
import android.net.Uri
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager (context: Context) {

    private val userDataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs")
    private val postImageDataStore : DataStore<Preferences> = context.createDataStore(name = "post_image_prefs")

    companion object{
        val ID = preferencesKey<String>("USER_ID")
        val TOKEN = preferencesKey<String>("USER_TOKEN")
        val POSTIMAGE = preferencesKey<String>("USER_TOKEN")
    }

    suspend fun saveDataUser(token : String) {
        userDataStore.edit {
            it[TOKEN] = token
        }
    }

    suspend fun deleteDataUser() {
        userDataStore.edit{
            it.clear()
        }
    }

    suspend fun savePostImageCache(image : String) {
        postImageDataStore.edit {
            it[POSTIMAGE] = image
        }
    }

    suspend fun deletePostImageCache() {
        postImageDataStore.edit {
            it.clear()
        }
    }


    val userToken : Flow<String> = userDataStore.data.map {
        it [TOKEN] ?: ""
    }

    val postImage : Flow<String> = postImageDataStore.data.map {
        it [POSTIMAGE] ?: ""
    }

}
