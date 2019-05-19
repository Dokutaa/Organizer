package com.example.organizer.database

import android.arch.persistence.room.*
import android.content.Context

@Dao
interface ActivityDao {

    @Insert
    fun addActivity(dailyActivity: DailyActivity)

    @Query("SELECT * FROM activities WHERE user=:user")
    fun selectAll(user: String): MutableList<DailyActivity>

    @Query("SELECT * FROM activities WHERE user=:user AND dayMonthYear=:dayMonthYear ORDER BY time")
    fun selectByDate(user: String, dayMonthYear: String): MutableList<DailyActivity>

    @Query("SELECT * FROM activities WHERE user=:user AND id=:id")
    fun selectById(user: String, id: Long): DailyActivity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivity(dailyActivity: DailyActivity)

    @Delete
    fun deleteActivity(dailyActivity: DailyActivity)

    @Query("DELETE FROM activities WHERE user=:user AND dayMonthYear=:dayMonthYear")
    fun deleteDailyActivites(user: String, dayMonthYear: String)

}

@Database(entities = [DailyActivity::class], version = 1)
abstract class ActivitiesDatabase : RoomDatabase() {
abstract fun activityDao(): ActivityDao

    companion object {
        private var INSTANCE: ActivitiesDatabase? = null

        fun getInstance(context: Context): ActivitiesDatabase? {
            if (INSTANCE == null) {
                synchronized(ActivitiesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ActivitiesDatabase::class.java,
                        "note"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}