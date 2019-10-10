package com.example.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.*
import kotlinx.android.synthetic.main.activity_main.*

// Entity - represents a table within the database
@Entity(tableName = "hsData")
data class Highscore (
    @PrimaryKey var id: Int,
    var name: String?,
    var score: Double?
)

// Dao - contains the methods used for accessing database
@Dao
interface HSDao {
   @Query("SELECT * FROM hsData")
   fun loadAll(): Array<Highscore>

    @Insert
    fun insert(hs: Highscore)

    @Query("DELETE FROM hsData")
    fun deleteAll()
}

// Database
// main access point to database
@Database(entities = [Highscore::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hsDao(): HSDao
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create database and get instance of it
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "hs_db").allowMainThreadQueries().build()

        // clear
        db.hsDao().deleteAll()

        // add a few highscores
        db.hsDao().insert(Highscore(0, "Kirsi Kernel", 1000.00))
        db.hsDao().insert(Highscore(1, "Jagoda Stuart", 800.00))
        db.hsDao().insert(Highscore(2, "Kaylie Wilkins", 600.00))
        db.hsDao().insert(Highscore(3, "Leonardo Bender", 100.00))

        // get all highscores from database and show in textView
        val allHS = db.hsDao().loadAll()
        // empty textView
        textView.text = ""
        // loop all highscores
        allHS.forEach {
            textView.append(it.name + ": " + it.score + "\n")
        }
    }
}
