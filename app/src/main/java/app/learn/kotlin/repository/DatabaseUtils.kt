package app.learn.kotlin.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.entity.EventEntity
import app.learn.kotlin.model.entity.TeamEntity
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
        db.createTable(EventEntity.TABLE_NAME, true,
                EventEntity.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EventEntity.EVENT_ID to TEXT + UNIQUE,
                EventEntity.TEAM_HOME_NAME to TEXT,
                EventEntity.TEAM_HOME_SCORE to INTEGER,
                EventEntity.TEAM_AWAY_NAME to TEXT,
                EventEntity.TEAM_AWAY_SCORE to INTEGER,
                EventEntity.DATE_EVENT to TEXT,
                EventEntity.TIME to TEXT)

        db.createTable(TeamEntity.TABLE_NAME, true,
                TeamEntity.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamEntity.TEAM_ID to TEXT + UNIQUE,
                TeamEntity.TEAM_NAME to TEXT,
                TeamEntity.TEAM_LOGO_URL to TEXT,
                TeamEntity.TEAM_BANNER to TEXT,
                TeamEntity.TEAM_STADIUM_NAME to TEXT,
                TeamEntity.TEAM_FORMED_YEAR to TEXT,
                TeamEntity.TEAM_DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(EventEntity.TABLE_NAME, true)
        db.dropTable(TeamEntity.TABLE_NAME, true)
    }
}