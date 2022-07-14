package and5.finalproject.secondhand5.Room

import and5.finalproject.secondhand5.Room.Model.GetProductHome
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ GetProductHome::class], version = 1)
abstract class OfflineDB : RoomDatabase() {
    abstract fun offlineDao():OfflineModeDao

    companion object{
        private var INSTANCE :OfflineDB? = null

        fun getInstance(context: Context):OfflineDB?{
            if (INSTANCE == null){
                synchronized(OfflineDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,OfflineDB::class.java,"Offline.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}