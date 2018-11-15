package app.learn.kotlin.feature.favorite

import app.learn.kotlin.feature.favorite.event.FavoriteEventContract
import app.learn.kotlin.feature.favorite.event.FavoriteEventPresenterImpl
import app.learn.kotlin.model.entity.FavoriteEventEntity
import app.learn.kotlin.repository.FavoriteMatchRepository
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteEventPresenterImplTest {

    @InjectMocks
    private lateinit var impl: FavoriteEventPresenterImpl

    @Spy
    private lateinit var view: FavoriteEventContract.View

    @Spy
    private lateinit var favoriteRepository: FavoriteMatchRepository

    private val EVENT_ID = "eventId"
    private val HOME_TEAM_NAME = "homeTeamName"
    private val HOME_TEAM_SCORE = 3
    private val AWAY_TEAM_NAME = "awayTeamName"
    private val AWAY_TEAM_SCORE = 2
    private val STR_DATE = "strDate"
    private val TIME = "time"

    private lateinit var favoriteEventEntity: FavoriteEventEntity
    private lateinit var responseAllEvent: Observable<FavoriteEventEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        favoriteEventEntity = FavoriteEventEntity(id = null,
                eventId = EVENT_ID, homeTeamName = HOME_TEAM_NAME,
                awayTeamName = AWAY_TEAM_NAME, homeTeamScore = HOME_TEAM_SCORE,
                awayTeamScore = AWAY_TEAM_SCORE, strDate = STR_DATE, time = TIME)

        responseAllEvent = Observable.just(favoriteEventEntity)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(view, favoriteRepository)
    }

    @Test
    fun getListEventFavoriteTest_valid_success() {
        `when`(favoriteRepository.findAll()).thenReturn(responseAllEvent)

        impl.getListFavorite()

        verify(favoriteRepository).findAll()
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).notifyDataChange()
        verify(view).setViewModel(favoriteEventEntity)
    }
}