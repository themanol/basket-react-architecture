package com.themanol.reactbasket.teams.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import com.themanol.reactbasket.testutils.observer.observeOnce
import com.themanol.reactbasket.testutils.rules.CoroutinesTestRule
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@FlowPreview
class TeamsViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private val repo: TeamRepository = mockk()
    lateinit var teamsViewModel: TeamsViewModel

    @Before
    fun setUpMocks() {
        //clear mock interactions
        clearAllMocks()
    }

    @Test
    fun `Init and respond with SUCCESS`() = runBlockingTest {
        val job = launch {
            val publisher = ConflatedBroadcastChannel<Result<List<Team>>>(Result.loading())
            every { repo.teamsObservable } returns publisher.asFlow()
            teamsViewModel = TeamsViewModel(repo)
            verify { repo.teamsObservable }
            teamsViewModel.progressLiveData.observeOnce {
                assert(it)
            }
            publisher.send(Result.success(listOf(mockk())))
            teamsViewModel.progressLiveData.observeOnce {
                assert(!it)
            }
            teamsViewModel.teamListLiveData.observeOnce {
                assert(it.size == 1)
            }
        }
        job.cancel()
    }

    @Test
    fun `Init and respond with ERROR`() = runBlockingTest {
        val job = launch {
            val publisher = ConflatedBroadcastChannel<Result<List<Team>>>(Result.loading())
            every { repo.teamsObservable } returns publisher.asFlow()
            teamsViewModel = TeamsViewModel(repo)
            verify { repo.teamsObservable }
            teamsViewModel.progressLiveData.observeOnce {
                assert(it)
            }
            publisher.send(Result.error("error"))
            teamsViewModel.progressLiveData.observeOnce {
                assert(!it)
            }
            teamsViewModel.errorLiveData.observeOnce {
                assert(it.message == "error")
            }
        }
        job.cancel()
    }


}