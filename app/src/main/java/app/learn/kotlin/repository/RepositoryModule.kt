package app.learn.kotlin.repository

import android.app.Application
import app.learn.kotlin.di.scope.ApplicationContext
import app.learn.kotlin.di.scope.ApplicationScope
import app.learn.kotlin.helper.database
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @ApplicationScope
    @Provides
    internal fun provideDatabaseHelper(@ApplicationContext application: Application): DatabaseUtils {
        return application.database
    }

    @ApplicationScope
    @Provides
    internal fun provideFavoriteEventRepository(databaseUtils: DatabaseUtils): FavoriteMatchRepository {
        return FavoriteMatchRepositoryImpl(databaseUtils)
    }

    @ApplicationScope
    @Provides
    internal fun provideFavoriteTeamRepository(databaseUtils: DatabaseUtils): FavoriteTeamRepository {
        return FavoriteTeamRepositoryImpl(databaseUtils)
    }
}