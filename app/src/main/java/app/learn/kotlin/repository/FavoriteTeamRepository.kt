package app.learn.kotlin.repository

import app.learn.kotlin.model.entity.FavoriteTeamEntity
import io.reactivex.Observable
import io.reactivex.Single

interface FavoriteTeamRepository {

    fun insert(favoriteTeamEntity: FavoriteTeamEntity): Single<Boolean>

    fun find(teamId: String): Single<FavoriteTeamEntity>

    fun findAll(): Observable<FavoriteTeamEntity>

    fun delete(teamId: String): Single<Boolean>

    fun isExist(teamId: String): Single<Boolean>
}