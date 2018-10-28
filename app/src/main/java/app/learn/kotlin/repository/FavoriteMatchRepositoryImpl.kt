package app.learn.kotlin.repository

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteException
import android.util.Log
import app.learn.kotlin.helper.DatabaseUtils
import app.learn.kotlin.helper.convertMapToContentValues
import app.learn.kotlin.helper.objectMapper
import app.learn.kotlin.model.entity.FavoriteEventEntity
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.anko.db.*
import javax.inject.Inject

class FavoriteMatchRepositoryImpl @Inject constructor(private val db: DatabaseUtils)
    : FavoriteMatchRepository {

    override fun insertEvent(favoriteEventEntity: FavoriteEventEntity): Single<Boolean> {
        return Single.create {
            emitter ->
            try {
                db.use {
                    insert(FavoriteEventEntity.TABLE_NAME, null,
                            convertMapToContentValues(objectMapper
                                    .convertValue(favoriteEventEntity, Map::class.java)))
                }
                emitter.onSuccess(true)
            } catch (e: Exception) {
                Log.e("ERROR", e.printStackTrace().toString())
                emitter.onError(e)
            }
        }
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

    @SuppressLint("CheckResult")
    override fun getEventAll(): Observable<FavoriteEventEntity> {
        return Observable.create<FavoriteEventEntity> {
            try {
                val result = db.use { select(FavoriteEventEntity.TABLE_NAME)
                        .exec { parseList(classParser<FavoriteEventEntity>()) } }
                for (r in result) it.onNext(r)
                it.onComplete()
            } catch (e: SQLiteException) {
                Log.e("Error", e.stackTrace.toString())
                it.onError(e)
            }
        }
    }

    override fun deleteEvent(eventId: String): Single<Boolean> {
        return Single.create { emitter ->
            try {
                db.use {
                    delete(FavoriteEventEntity.TABLE_NAME,
                            "${FavoriteEventEntity.EVENT_ID} = {id}",
                            "id" to eventId)
                }

                emitter.onSuccess(true)
            } catch (e: SQLiteException) {
                Log.e("Error", e.stackTrace.toString())
                emitter.onError(e)
            }
        }

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