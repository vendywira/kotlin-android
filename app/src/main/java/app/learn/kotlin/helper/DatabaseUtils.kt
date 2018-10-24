package app.learn.kotlin.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import app.learn.kotlin.model.entity.FavoriteEventNames
import app.learn.kotlin.model.entity.FavoriteTeamNames
import org.jetbrains.anko.db.*

class DatabaseUtils(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DatabaseUtils.DB_NAME, null, 1) {

    companion object {
        private const val DB_NAME = "FootballClub.db"
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
                FavoriteEventNames.TABLE_NAME, true,
                FavoriteEventNames.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteEventNames.EVENT_ID to TEXT + UNIQUE,
                FavoriteEventNames.TEAM_HOME_NAME to TEXT,
                FavoriteEventNames.TEAM_HOME_SCORE to INTEGER,
                FavoriteEventNames.TEAM_AWAY_NAME to TEXT,
                FavoriteEventNames.TEAM_AWAY_SCORE to INTEGER,
                FavoriteEventNames.START_DATE to TEXT)

        db.createTable(FavoriteTeamNames.TABLE_NAME, true,
                FavoriteTeamNames.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeamNames.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeamNames.TEAM_NAME to TEXT,
                FavoriteTeamNames.TEAM_LOGO_URL to TEXT,
                FavoriteTeamNames.TEAM_BANNER to TEXT,
                FavoriteTeamNames.TEAM_DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteEventNames.TABLE_NAME, true)
        db.dropTable(FavoriteTeamNames.TABLE_NAME, true)
    }
}