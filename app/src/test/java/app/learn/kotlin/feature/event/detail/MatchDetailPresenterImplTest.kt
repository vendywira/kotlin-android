package app.learn.kotlin.feature.event.detail

import app.learn.kotlin.feature.event.detail.MatchDetailPresenterImpl.Companion.FAILED_GET_DATA_FROM_DB
import app.learn.kotlin.feature.event.detail.MatchDetailPresenterImpl.Companion.FAILED_TO_REMOVE_FROM_FAVORITE
import app.learn.kotlin.model.entity.EventEntity
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.model.response.Team
import app.learn.kotlin.network.TheSportDBApiService
import app.learn.kotlin.repository.FavouriteMatchRepository
import io.reactivex.Observable
import io.reactivex.Single
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
class MatchDetailPresenterImplTest {

    @InjectMocks
    private lateinit var impl: MatchDetailPresenterImpl

    @Spy
    private lateinit var view: MatchDetailContract.View

    @Spy
    private lateinit var apiService: TheSportDBApiService

    @Spy
    private lateinit var favouriteRepository: FavouriteMatchRepository

    private lateinit var eventEntity: EventEntity
    private lateinit var event: Event
    private lateinit var listEventResponse: ListResponse<Event>
    private lateinit var responseEvents: Observable<ListResponse<Event>>

    private lateinit var teamHome: Team
    private lateinit var teamAway: Team

    private val EVENT_ID = "EVENT_ID"
    private val ID_HOME_TEAM = "ID_HOME_TEAM"
    private val ID_AWAY_TEAM = "ID_AWAY_TEAM"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        eventEntity = EventEntity()
        event = Event(eventId = EVENT_ID, idHomeTeam = ID_HOME_TEAM, idAwayTeam = ID_AWAY_TEAM)
        listEventResponse = ListResponse(mutableListOf(event))
        responseEvents = Observable.just(listEventResponse)
        teamHome = Team(teamId = ID_HOME_TEAM)
        teamAway = Team(teamId = ID_AWAY_TEAM)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(view, apiService, favouriteRepository)
    }

    @Test
    fun insertMatchToFavoriteTest_valid_true() {
        Mockito.`when`(favouriteRepository.insert(eventEntity)).thenReturn(Single.just(true))

        impl.insertMatchToFavorite(eventEntity)

        Mockito.verify(favouriteRepository).insert(eventEntity)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showMessage("Added to favorite")
    }

    @Test
    fun insertMatchToFavoriteTest_valid_exception() {
        Mockito.`when`(favouriteRepository.insert(eventEntity))
                .thenReturn(Single.error(Exception()))

        impl.insertMatchToFavorite(eventEntity)

        Mockito.verify(favouriteRepository).insert(eventEntity)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).hideLoading()
        Mockito.verify(view).showMessage("Failed add to favorite")
    }

    @Test
    fun deleteMatchFromFavoriteTest_valid_success() {
        `when`(favouriteRepository.delete(EVENT_ID)).thenReturn(Single.just(true))

        impl.deleteMatchFromFavorite(EVENT_ID)

        verify(favouriteRepository).delete(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage("Removed from favorite")
    }

    @Test
    fun deleteMatchFromFavoriteTest_valid_failed() {
        `when`(favouriteRepository.delete(EVENT_ID))
                .thenReturn(Single.error(Exception()))

        impl.deleteMatchFromFavorite(EVENT_ID)

        verify(favouriteRepository).delete(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage(FAILED_TO_REMOVE_FROM_FAVORITE)
    }

    @Test
    fun isExistFavoriteEventTest_valid_true() {
        `when`(favouriteRepository.isExist(EVENT_ID)).thenReturn(Single.just(true))

        impl.isExistFavoriteEvent(EVENT_ID)

        verify(favouriteRepository).isExist(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).isExistFavoriteEvent(true)
    }

    @Test
    fun isExistFavoriteEventTest_valid_false() {
        `when`(favouriteRepository.isExist(EVENT_ID)).thenReturn(Single.just(false))

        impl.isExistFavoriteEvent(EVENT_ID)

        verify(favouriteRepository).isExist(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).isExistFavoriteEvent(false)
    }

    @Test
    fun isExistFavoriteEventTest_valid_exception() {
        `when`(favouriteRepository.isExist(EVENT_ID)).thenReturn(Single.error(Exception()))

        impl.isExistFavoriteEvent(EVENT_ID)

        verify(favouriteRepository).isExist(EVENT_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage(FAILED_GET_DATA_FROM_DB)
    }

    @Test
    fun getDetailEventTest_valid_success() {
        `when`(view.getEventId()).thenReturn(EVENT_ID)
        `when`(apiService.getEventByEventId(EVENT_ID)).thenReturn(responseEvents)
        `when`(apiService.getTeamByTeamId(ID_HOME_TEAM))
                .thenReturn(Observable.just(ListResponse(mutableListOf(teamHome))))
        `when`(apiService.getTeamByTeamId(ID_AWAY_TEAM))
                .thenReturn(Observable.just(ListResponse(mutableListOf(teamAway))))

        impl.getDetailEvent()

        verify(view).getEventId()
        verify(apiService).getEventByEventId(EVENT_ID)
        verify(apiService).getTeamByTeamId(ID_HOME_TEAM)
        verify(apiService).getTeamByTeamId(ID_AWAY_TEAM)
        verify(view).setTeamDetailModel(teamHome)
        verify(view).setTeamDetailModel(teamAway)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).setEventDetailModel(event)
    }
}