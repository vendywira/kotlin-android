package app.learn.kotlin.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import app.learn.kotlin.helper.convertMapToContentValues
import app.learn.kotlin.helper.objectMapper
import app.learn.kotlin.model.entity.TeamEntity
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select


class FavoriteTeamRepositoryImpl(private val db: DatabaseUtils)
    : FavoriteTeamRepository {

    override fun insert(teamEntity: TeamEntity): Single<Boolean> {
        return Single.create {
            try {
                db.use {
                    insert(TeamEntity.TABLE_NAME, null,
                            convertMapToContentValues(objectMapper
                                    .convertValue(teamEntity, Map::class.java)))
                }
                it.onSuccess(true)
            } catch (e: Exception) {
                Log.e("ERROR", e.printStackTrace().toString())
                it.onError(e)
            }
        }
    }

    override fun find(teamId: String): Single<TeamEntity> {
        return Single.create {
            try {
                it.onSuccess(db.use {
                    select(TeamEntity.TABLE_NAME)
                            .whereArgs("${TeamEntity.TEAM_ID} = {teamId}", "teamId" to teamId)
                            .exec { parseSingle(classParser<TeamEntity>()) }
                })
            } catch (e: Exception) {
                Log.e("Error", e.stackTrace.toString())
                it.onError(e)
            }
        }
    }

    override fun findAll(): Observable<TeamEntity> {
        return Observable.create<TeamEntity> {
            try {
                val result = db.use {
                    select(TeamEntity.TABLE_NAME)
                            .exec { parseList(classParser<TeamEntity>()) }
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
                    delete(TeamEntity.TABLE_NAME,
                            "${TeamEntity.TEAM_ID} = {teamId}",
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
                    select(TeamEntity.TABLE_NAME)
                            .whereArgs("${TeamEntity.TEAM_ID} = {teamId}", "teamId" to teamId)
                            .exec { parseList(classParser<TeamEntity>()) }
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