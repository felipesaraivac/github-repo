package com.saraiva.github.presentation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.asSnapshot
import com.saraiva.github.CoroutinesRule
import com.saraiva.github.core.Constants
import com.saraiva.github.data.FakePagingSource
import com.saraiva.github.data.RepositoryImpl
import com.saraiva.github.domain.entity.GithubRepoEntity
import com.saraiva.github.ui.screens.rankings.RankingViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.util.Date
import kotlin.test.assertNotNull

class RankingViewModelTest {

    @get:Rule
    val coroutineRule = CoroutinesRule()

    private val repository = mockk<RepositoryImpl>(relaxed = true)

    lateinit var viewModel: RankingViewModel

    val fakelist = listOf(
        GithubRepoEntity(
            id = 1,
            name = "repo1",
            fullName = "repo1",
            description = "repo1",
            htmlUrl = "repo1",
            language = "repo1",
            stargazersCount = 1,
            forksCount = 1,
            ownerLogin = "repo1",
            ownerAvatarUrl = "repo1",
            lastUpdate = Date()
        )
    )

    val pager = Pager(
        config = PagingConfig(
            pageSize = Constants.ITEMS_PER_PAGE,
            prefetchDistance = 30
        ),
        pagingSourceFactory = { FakePagingSource(fakelist) }
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN an valid result RETURN list snapshot WHEN page is requested`() =
        runTest {
            // GIVEN\

            coEvery {
                repository.getRepos(true)
            } returns pager.flow
            // WHEN
            viewModel = RankingViewModel(repository)
            viewModel.getTopRepos(true)
            advanceUntilIdle()

            // THEN
            viewModel.repos.asSnapshot() shouldBe fakelist
        }

    val fakeSource = mockk<FakePagingSource>(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN an exception result RETURN an exception WHEN page is requested`() =
        runTest {

            val trowable = Throwable("Expected Message")
//             GIVEN
            coEvery {
                fakeSource.load(any())
            } returns PagingSource.LoadResult.Error(trowable)

            coEvery {
                fakeSource.getRefreshKey(any())
            } returns 1

            coEvery {
                repository.getRepos(true)
            } returns Pager(config = PagingConfig(1), pagingSourceFactory = { fakeSource }).flow
            // WHEN
            var error: kotlin.Throwable? = null
            runCatching {
                viewModel = RankingViewModel(repository)

                viewModel.getTopRepos(true)

                viewModel.repos.asSnapshot()

            }.recoverCatching { e ->
                error = e
            }
            assertNotNull(error)
            assertTrue(error.message == trowable.message)

        }


}