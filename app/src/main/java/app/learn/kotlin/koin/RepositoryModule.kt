package app.learn.kotlin.koin

import app.learn.kotlin.repository.DatabaseUtils
import app.learn.kotlin.repository.FavoriteTeamRepository
import app.learn.kotlin.repository.FavoriteTeamRepositoryImpl
import app.learn.kotlin.repository.FavouriteMatchRepository
import app.learn.kotlin.repository.FavouriteMatchRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val repositoryModule = module {
    single { DatabaseUtils(androidContext()) }
    factory<FavouriteMatchRepository> { FavouriteMatchRepositoryImpl(get()) }
    factory<FavoriteTeamRepository> { FavoriteTeamRepositoryImpl(get()) }
}