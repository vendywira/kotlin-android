package app.learn.kotlin.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEvent
import app.learn.kotlin.model.entity.FavoriteTeam
import org.jetbrains.anko.db.*

class DatabaseUtils(ctx: Context) : ManagedSQLiteOpenHelper(ctx, Constant.DB_NAME, null, 1) {

    companion object {
        private var instance: DatabaseUtils? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseUtils {
            if (instance == null) {
                instance = DatabaseUtils(ctx.applicationContext)
            }
            return instance as DatabaseUtils
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
                FavoriteEvent.TABLE_NAME, true,
                FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteEvent.EVENT_ID to TEXT + UNIQUE,
                FavoriteEvent.TEAM_HOME_NAME to TEXT,
                FavoriteEvent.TEAM_HOME_SCORE to INTEGER,
                FavoriteEvent.TEAM_AWAY_NAME to TEXT,
                FavoriteEvent.TEAM_AWAY_SCORE to INTEGER,
                FavoriteEvent.START_DATE to TEXT)

        db.createTable(FavoriteTeam.TABLE_NAME, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.TEAM_LOGO_URL to TEXT,
                FavoriteTeam.TEAM_BANNER to TEXT,
                FavoriteTeam.TEAM_DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteEvent.TABLE_NAME, true)
        db.dropTable(FavoriteTeam.TABLE_NAME, true)
    }
}