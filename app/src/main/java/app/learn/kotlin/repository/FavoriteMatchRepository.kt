package app.learn.kotlin.repository

import app.learn.kotlin.model.entity.FavoriteEventEntity

interface FavoriteMatchRepository {

    fun insertEvent(favoriteEventEntity: FavoriteEventEntity): Boolean

    fun getEvent(eventId: String): FavoriteEventEntity

    fun getEventAll(): List<FavoriteEventEntity>

    fun deleteEvent(eventId: String): Boolean

    fun isExistEvent(eventId: String): Boolean
}