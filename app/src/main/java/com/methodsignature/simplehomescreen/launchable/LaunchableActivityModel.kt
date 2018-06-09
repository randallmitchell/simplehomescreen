package com.methodsignature.simplehomescreen.launchable

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Database
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.RoomDatabase
import com.methodsignature.simplehomescreen.launchable.LaunchableActivity.Companion.COL_ACTIVITY_NAME
import com.methodsignature.simplehomescreen.launchable.LaunchableActivity.Companion.COL_PACKAGE_NAME
import com.methodsignature.simplehomescreen.launchable.LaunchableActivity.Companion.COL_READABLE_NAME
import com.methodsignature.simplehomescreen.launchable.LaunchableActivity.Companion.TABLE_NAME
import io.reactivex.Completable
import io.reactivex.Flowable

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [COL_PACKAGE_NAME, COL_ACTIVITY_NAME]
)
data class LaunchableActivity(
    @ColumnInfo(name = COL_PACKAGE_NAME)
    val packageName: String,

    @ColumnInfo(name = COL_ACTIVITY_NAME)
    val activityName: String,

    @ColumnInfo(name = COL_READABLE_NAME)
    val readableName: String
) {
    companion object {
        const val TABLE_NAME = "launchable_activities"
        const val COL_PACKAGE_NAME = "package_name"
        const val COL_ACTIVITY_NAME = "activity_name"
        const val COL_READABLE_NAME = "readable_name"
    }
}

@Dao
interface LaunchableActivityStore {
    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COL_READABLE_NAME")
    fun getAll(): Flowable<List<LaunchableActivity>>

    @Insert
    fun synchronousAddAll(vararg launchableActivities: LaunchableActivity)

    @Query("DELETE FROM $TABLE_NAME WHERE $COL_PACKAGE_NAME = :packageName")
    fun deleteByPackage(packageName: String)
}

fun LaunchableActivityStore.addAll(launchableActivities: List<LaunchableActivity>): Completable {
    return Completable.fromCallable { synchronousAddAll(*launchableActivities.toTypedArray()) }
}

@Database(entities = [LaunchableActivity::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun launchableActivityStore(): LaunchableActivityStore
}
