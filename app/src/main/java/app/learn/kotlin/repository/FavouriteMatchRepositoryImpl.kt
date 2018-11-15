package app.learn.kotlin.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import app.learn.kotlin.helper.convertMapToContentValues
import app.learn.kotlin.helper.objectMapper
import app.learn.kotlin.model.entity.EventEntity
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.anko.db.*
import javax.inject.Inject

class FavouriteMatchRepositoryImpl @Inject constructor(private val db: DatabaseUtils)
    : FavouriteMatchRepository {

    override fun insert(eventEntity: EventEntity): Single<Boolean> {
        return Single.create {
            try {
                db.use {
                    insert(EventEntity.TABLE_NAME, null,
                            convertMapToContentValues(objectMapper
                                    .convertValue(eventEntity, Map::class.java)))
                }
                it.onSuccess(true)
            } catch (e: Exception) {
                Log.e("ERROR", e.printStackTrace().toString())
                it.onError(e)
            }
        }
    }

    override fun find(eventId: String): Single<EventEntity> {
        return Single.create {
            try {
                it.onSuccess(db.use {
                    select(EventEntity.TABLE_NAME)
                            .whereArgs("${EventEntity.EVENT_ID} = {eventId}", "eventId" to eventId)
                            .exec { parseSingle(classParser<EventEntity>()) }
                })
            } catch (e: Exception) {
                Log.e("Error", e.stackTrace.toString())
                it.onError(e)
            }
        }
    }

    override fun findAll(): Observable<EventEntity> {
        return Observable.create<EventEntity> {
            try {
                val result = db.use {
                    select(EventEntity.TABLE_NAME)
                            .exec { parseList(classParser<EventEntity>()) }
                }
                for (r in result) it.onNext(r)
                it.onComplete()
            } catch (e: SQLiteException) {
                Log.e("Error", e.stackTrace.toString())
                it.onError(e)
            }
        }
    }

    override fun delete(eventId: String): Single<Boolean> {
        return Single.create { emitter ->
            try {
                db.use {
                    delete(EventEntity.TABLE_NAME,
                            "${EventEntity.EVENT_ID} = {eventId}",
                            "eventId" to eventId)
                }

                emitter.onSuccess(true)
            } catch (e: SQLiteException) {
                Log.e("Error", e.stackTrace.toString())
                emitter.onError(e)
            }
        }

    }

    override fun isExist(eventId: String): Single<Boolean> {
        return Single.create {
            try {
                val result = db.use {
                    select(EventEntity.TABLE_NAME)
                            .whereArgs("${EventEntity.EVENT_ID} = {eventId}", "eventId" to eventId)
                            .exec { parseList(classParser<EventEntity>()) }
                }

                if(result.isNotEmpty()) {
                    it.onSuccess(true)
                } else {
                    it.onSuccess(false)
                }
            } catch (e: Exception) {
                Log.e("Error", e.stackTrace.toString())
                it.onError(e)
            }
        }
    }
}