package app.learn.kotlin.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import app.learn.kotlin.helper.DatabaseUtils
import app.learn.kotlin.helper.convertMapToContentValues
import app.learn.kotlin.helper.objectMapper
import app.learn.kotlin.model.entity.FavoriteEventEntity
import org.jetbrains.anko.db.*
import javax.inject.Inject

class FavoriteMatchRepositoryImpl @Inject constructor(private val db: DatabaseUtils)
    : FavoriteMatchRepository {

    override fun insertEvent(favoriteEventEntity: FavoriteEventEntity): Boolean {
        try {
            db.use {
                insert(FavoriteEventEntity.TABLE_NAME, null,
                        convertMapToContentValues(objectMapper
                                .convertValue(favoriteEventEntity, Map::class.java)))
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.printStackTrace().toString())
            return false
        }

        return true
    }

    override fun getEvent(eventId: String): FavoriteEventEntity {
        var result = FavoriteEventEntity()
        try {
            db.use {
                 result = select(FavoriteEventEntity.TABLE_NAME)
                        .whereArgs("${FavoriteEventEntity.EVENT_ID} = {id}", "id" to eventId)
                        .exec { parseSingle(classParser()) }
            }
        } catch (e: Exception) {
            Log.e("Error", e.stackTrace.toString())
        }
        return result;
    }

    override fun getEventAll(): List<FavoriteEventEntity> {
        var result = listOf<FavoriteEventEntity>()
        try {
            db.use {
                result = select(FavoriteEventEntity.TABLE_NAME)
                        .exec { parseList(classParser()) }
            }
        } catch (e: SQLiteException) {
            Log.e("Error", e.stackTrace.toString())
        }
        return result;
    }

    override fun deleteEvent(eventId: String): Boolean {
        try {
            db.use {
                delete(FavoriteEventEntity.TABLE_NAME,
                        "${FavoriteEventEntity.EVENT_ID} = {id}",
                        "id" to eventId)
            }
        } catch (e: SQLiteException) {
            Log.e("Error", e.stackTrace.toString())
            return false
        }

        return true
    }

    override fun isExistEvent(eventId: String): Boolean {
        var result: FavoriteEventEntity? = null
        try {
            db.use {
                result = select(FavoriteEventEntity.TABLE_NAME)
                        .whereArgs("${FavoriteEventEntity.EVENT_ID} = {id}", "id" to eventId)
                        .exec { parseSingle(classParser()) }
            }
        } catch (e: Exception) {
            Log.e("Error", e.stackTrace.toString())
        }
       return result != null
    }
}