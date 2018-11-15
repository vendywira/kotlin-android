package app.learn.kotlin.repository

import app.learn.kotlin.model.entity.EventEntity
import io.reactivex.Observable
import io.reactivex.Single

interface FavouriteMatchRepository {

    fun insert(eventEntity: EventEntity): Single<Boolean>

    fun find(eventId: String): Single<EventEntity>

    fun findAll(): Observable<EventEntity>

    fun delete(eventId: String): Single<Boolean>

    fun isExist(eventId: String): Single<Boolean>
}