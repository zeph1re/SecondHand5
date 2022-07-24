package and5.finalproject.secondhand5.Room

import and5.finalproject.secondhand5.Room.Model.GetProductHome
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OfflineModeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHomeOffline (home: GetProductHome) : Long

    @Query("SELECT *  FROM GetProductHome")
    fun getDataOfflineProductHome(): List<GetProductHome>

    @Query("DELETE FROM GetProductHome")
    fun clearData():Int
}