package app.learn.kotlin.feature.event.match

import app.learn.kotlin.model.Constant
import app.learn.kotlin.model.response.Event
import app.learn.kotlin.model.response.League
import app.learn.kotlin.model.response.ListResponse
import app.learn.kotlin.network.TheSportDBApiService
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MatchPresenterImplTest {

    @InjectMocks
    private lateinit var impl: MatchPresenterImpl

    @Spy
    private lateinit var view: MatchContract.View

    @Spy
    private lateinit var apiService: TheSportDBApiService

    @Captor
    private lateinit var responseEventCaptor: ArgumentCaptor<ListResponse<Event>>

    @Captor
    private lateinit var responseLeagueCaptor: ArgumentCaptor<ListResponse<League>>

    private val LEAGUE_ID = "LEAGUE_ID"
    private val TEAM_HOME_NAME = "TEAM_HOME_NAME"
    private val TEAM_AWAY_NAME = "TEAM_AWAY_NAME"
    private val TEAM_HOME_SCORE = 5
    private val TEAM_AWAY_SCORE = 2

    private lateinit var event: Event
    private lateinit var listResponseEvent: ListResponse<Event>
    private lateinit var responseEvent: Observable<ListResponse<Event>>

    private lateinit var league: League
    lateinit var listResponseLeague: ListResponse<League>
    lateinit var responseLeague: Observable<ListResponse<League>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        event = Event(
                teamHomeScore = TEAM_HOME_SCORE,
                teamHomeName = TEAM_HOME_NAME,
                teamAwayScore = TEAM_AWAY_SCORE,
                teamAwayName = TEAM_AWAY_NAME)
        val events = listOf(event)
        listResponseEvent = ListResponse(events)
        responseEvent = Observable.just(listResponseEvent)

        league = League(id = LEAGUE_ID)
        val leagues = mutableListOf(league)
        listResponseLeague = ListResponse(leagues)
        responseLeague = Observable.just(listResponseLeague)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, apiService)
    }

    @Test
    fun getLastMatchTest_valid_success() {
        `when`(view.getSelectedLeagueId()).thenReturn(LEAGUE_ID)
        `when`(apiService.getLastMatchByLeagueId(LEAGUE_ID)).thenReturn(responseEvent)

        impl.getLastMatch()

        verify(view).getSelectedLeagueId()
        verify(apiService).getLastMatchByLeagueId(LEAGUE_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).setViewModel(responseEventCaptor.capture())

        val response = responseEventCaptor.value
        assertNotNull(response)
        assertEquals(response.contents?.get(0), event)
    }

    @Test
    fun getLastMatchTest_valid_exception() {
        `when`(view.getSelectedLeagueId()).thenReturn(LEAGUE_ID)
        `when`(apiService.getLastMatchByLeagueId(LEAGUE_ID))
                .thenReturn(Observable.error(Exception()))

        impl.getLastMatch()

        verify(view).getSelectedLeagueId()
        verify(apiService).getLastMatchByLeagueId(LEAGUE_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage(Constant.FAILED_GET_DATA);
        verify(view).setViewModel(responseEventCaptor.capture())

        val response = responseEventCaptor.value
        assertNotNull(response)
    }


    @Test
    fun getNextMatchTest_valid_success() {
        `when`(view.getSelectedLeagueId()).thenReturn(LEAGUE_ID)
        `when`(apiService.getNextMatchByLeagueId(LEAGUE_ID)).thenReturn(responseEvent)

        impl.getNextMatch()

        verify(view).getSelectedLeagueId()
        verify(apiService).getNextMatchByLeagueId(LEAGUE_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).setViewModel(responseEventCaptor.capture())

        val response = responseEventCaptor.value
        assertNotNull(response)
        assertEquals(response.contents?.get(0), event)
    }

    @Test
    fun getNextMatchTest_valid_exception() {
        `when`(view.getSelectedLeagueId()).thenReturn(LEAGUE_ID)
        `when`(apiService.getNextMatchByLeagueId(LEAGUE_ID))
                .thenReturn(Observable.error(Exception()))

        impl.getNextMatch()

        verify(view).getSelectedLeagueId()
        verify(apiService).getNextMatchByLeagueId(LEAGUE_ID)
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage(Constant.FAILED_GET_DATA)
        verify(view).setViewModel(responseEventCaptor.capture())

        val response = responseEventCaptor.value
        assertNotNull(response)
    }

    @Test
    fun getAllLeagueTest_valid_success() {
        `when`(apiService.getAllLeagues()).thenReturn(responseLeague)

        impl.getAllLeague()

        verify(apiService).getAllLeagues()
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).setLeagues(responseLeagueCaptor.capture())

        val response = responseLeagueCaptor.value
        assertNotNull(response)
        assertEquals(response.contents?.get(0), league)
    }

    @Test
    fun getAllLeagueTest_valid_exception() {
        `when`(apiService.getAllLeagues()).thenReturn(Observable.error(Exception()))

        impl.getAllLeague()

        verify(apiService).getAllLeagues()
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showMessage(Constant.FAILED_GET_DATA)
        verify(view).setLeagues(responseLeagueCaptor.capture())

        val response = responseLeagueCaptor.value
        assertNotNull(response)
    }
}