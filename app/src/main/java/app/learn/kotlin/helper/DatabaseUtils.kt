package app.learn.kotlin.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.model.entity.FavoriteTeamEntity
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
                FavoriteEventEntity.TABLE_NAME, true,
                FavoriteEventEntity.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteEventEntity.EVENT_ID to TEXT + UNIQUE,
                FavoriteEventEntity.TEAM_HOME_NAME to TEXT,
                FavoriteEventEntity.TEAM_HOME_SCORE to INTEGER,
                FavoriteEventEntity.TEAM_AWAY_NAME to TEXT,
                FavoriteEventEntity.TEAM_AWAY_SCORE to INTEGER,
                FavoriteEventEntity.START_DATE to TEXT)

        db.createTable(FavoriteTeamEntity.TABLE_NAME, true,
                FavoriteTeamEntity.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeamEntity.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeamEntity.TEAM_NAME to TEXT,
                FavoriteTeamEntity.TEAM_LOGO_URL to TEXT,
                FavoriteTeamEntity.TEAM_BANNER to TEXT,
                FavoriteTeamEntity.TEAM_DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteEventEntity.TABLE_NAME, true)
        db.dropTable(FavoriteTeamEntity.TABLE_NAME, true)
    }
}