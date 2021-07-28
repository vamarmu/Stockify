package ar.team.stockify.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockDatabaseTest : TestCase() {

    private lateinit var db: StockDatabase

    private lateinit var dao: StockDao

    @Before
    public override fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, StockDatabase::class.java).build()

        dao = db.stockDao()
    }

    @After
    fun closeDb() {

        db.close()
    }

    //Test Functions

    @Test
    fun insertAndDeleteStock()= runBlocking{

        val mockStock1 = LocalStock(symbol = "TSLA", name = "Tesla")
        val mockStock2 = LocalStock(symbol = "SPCE", name = "Virgin Galactic")

        dao.insert(mockStock1)
        dao.insert(mockStock2)
        dao.delete(mockStock1)

        val favourites = dao.getAllFav()

        assertThat((!favourites.contains(mockStock1))).isTrue()

    }


    @Test
    fun insertAndGetAllFavourites()= runBlocking{

        val mockStock1 = LocalStock(symbol = "TSLA", name = "Tesla")
        val mockStock2 = LocalStock(symbol = "SPCE", name = "Virgin Galactic")

        dao.insert(mockStock1)
        dao.insert(mockStock2)

        val favourites = dao.getAllFav()

        assertThat((favourites.contains(mockStock1))).isTrue()

    }

}