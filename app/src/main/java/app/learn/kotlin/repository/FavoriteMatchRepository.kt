package app.learn.kotlin.repository

import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.model.entity.FavoriteTeamEntity
import io.reactivex.Observable
import io.reactivex.Single

interface FavoriteMatchRepository {

    fun insert(favoriteEventEntity: FavoriteEventEntity): Single<Boolean>

    fun find(eventId: String): Single<FavoriteEventEntity>

    fun findAll(): Observable<FavoriteEventEntity>

    fun delete(eventId: String): Single<Boolean>

    fun isExist(eventId: String): Single<Boolean>
}