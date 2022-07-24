

import and5.finalproject.secondhand5.Room.OfflineDB
import and5.finalproject.secondhand5.Room.OfflineModeDao
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OfflineDBTest : TestCase() {

    private lateinit var db :OfflineDB
    private lateinit var dao:OfflineModeDao
    @Before
    public override fun setUp() {
        val context  = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, OfflineDB::class.java).build()
        dao = db.offlineDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun getOfflineDao() {
        dao.getDataOfflineProductHome()
    }
}