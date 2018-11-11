package app.learn.kotlin.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import app.learn.kotlin.helper.convertMapToContentValues
import app.learn.kotlin.helper.objectMapper
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.model.entity.FavoriteTeamEntity
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import javax.inject.Inject


class FavoriteTeamRepositoryImpl @Inject constructor(private val db: DatabaseUtils)
    : FavoriteTeamRepository {

    override fun insert(favoriteTeamEntity: FavoriteTeamEntity): Single<Boolean> {
        return Single.create {
            try {
                db.use {
                    insert(FavoriteTeamEntity.TABLE_NAME, null,
                            convertMapToContentValues(objectMapper
                                    .convertValue(favoriteTeamEntity, Map::class.java)))
                }
                it.onSuccess(true)
            } catch (e: Exception) {
                Log.e("ERROR", e.printStackTrace().toString())
                it.onError(e)
            }
        }
    }

    override fun find(teamId: String): Single<FavoriteTeamEntity> {
        return Single.create {
            try {
                it.onSuccess(db.use {
                    select(FavoriteTeamEntity.TABLE_NAME)
                            .whereArgs("${FavoriteTeamEntity.TEAM_ID} = {teamId}", "teamId" to teamId)
                            .exec { parseSingle(classParser<FavoriteTeamEntity>()) }
                })
            } catch (e: Exception) {
                Log.e("Error", e.stackTrace.toString())
                it.onError(e)
            }
        }
    }

    override fun findAll(): Observable<FavoriteTeamEntity> {
        return Observable.create<FavoriteTeamEntity> {
            try {
                val result = db.use {
                    select(FavoriteTeamEntity.TABLE_NAME)
                            .exec { parseList(classParser<FavoriteTeamEntity>()) }
                }
                for (r in result) it.onNext(r)
                it.onComplete()
            } catch (e: SQLiteException) {
                Log.e("Error", e.stackTrace.toString())
                it.onError(e)
            }
        }
    }

    override fun delete(teamId: String): Single<Boolean> {
        return Single.create { emitter ->
            try {
                db.use {
                    delete(FavoriteTeamEntity.TABLE_NAME,
                            "${FavoriteTeamEntity.TEAM_ID} = {teamId}",
                            "teamId" to teamId)
                }

                emitter.onSuccess(true)
            } catch (e: SQLiteException) {
                Log.e("Error", e.stackTrace.toString())
                emitter.onSuccess(false)
                emitter.onError(e)
            }
        }

    }

    override fun isExist(teamId: String): Single<Boolean> {
        return Single.create {
            try {
                val result = db.use {
                    select(FavoriteTeamEntity.TABLE_NAME)
                            .whereArgs("${FavoriteTeamEntity.TEAM_ID} = {teamId}", "teamId" to teamId)
                            .exec { parseList(classParser<FavoriteTeamEntity>()) }
                }

                if(result.isNotEmpty()) {
                    it.onSuccess(true)
                } else {
                    it.onSuccess(false)
                }
            } catch (e: Exception) {
                Log.e("Error", e.stackTrace.toString())
                it.onSuccess(false)
                it.onError(e)
            }
        }
    }
}