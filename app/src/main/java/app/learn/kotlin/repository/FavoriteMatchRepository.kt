package app.learn.kotlin.repository

import app.learn.kotlin.model.entity.FavoriteEventEntity
import io.reactivex.Observable
import io.reactivex.Single

interface FavoriteMatchRepository {

    fun insertEvent(favoriteEventEntity: FavoriteEventEntity): Single<Boolean>

    fun getEvent(eventId: String): Single<FavoriteEventEntity>

    fun getEventAll(): Observable<FavoriteEventEntity>

    fun deleteEvent(eventId: String): Single<Boolean>

    fun isExistEvent(eventId: String): Single<Boolean>
}