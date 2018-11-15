package app.learn.kotlin.feature.favorite

import app.learn.kotlin.feature.favourite.event.FavouriteEventContract
import app.learn.kotlin.feature.favourite.event.FavouriteEventPresenterImpl
import app.learn.kotlin.model.entity.EventEntity
import app.learn.kotlin.repository.FavouriteMatchRepository
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
class FavouriteEventPresenterImplTest {

    @InjectMocks
    private lateinit var impl: FavouriteEventPresenterImpl

    @Spy
    private lateinit var view: FavouriteEventContract.View

    @Spy
    private lateinit var favouriteRepository: FavouriteMatchRepository

    private val EVENT_ID = "eventId"
    private val HOME_TEAM_NAME = "homeTeamName"
    private val HOME_TEAM_SCORE = 3
    private val AWAY_TEAM_NAME = "awayTeamName"
    private val AWAY_TEAM_SCORE = 2
    private val STR_DATE = "dateEvent"
    private val TIME = "time"

    private lateinit var eventEntity: EventEntity
    private lateinit var responseAllEvent: Observable<EventEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        eventEntity = EventEntity(id = null,
                eventId = EVENT_ID, homeTeamName = HOME_TEAM_NAME,
                awayTeamName = AWAY_TEAM_NAME, homeTeamScore = HOME_TEAM_SCORE,
                awayTeamScore = AWAY_TEAM_SCORE, dateEvent = STR_DATE, time = TIME)

        responseAllEvent = Observable.just(eventEntity)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(view, favouriteRepository)
    }

    @Test
    fun getListEventFavoriteTest_valid_success() {
        `when`(favouriteRepository.findAll()).thenReturn(responseAllEvent)

        impl.getListFavorite()

        verify(favouriteRepository).findAll()
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).notifyDataChange()
        verify(view).setViewModel(eventEntity)
    }
}