package com.saraiva.github.presentation

import com.saraiva.github.CoroutinesRule
import com.saraiva.github.data.RepositoryImpl
import com.saraiva.github.ui.screens.rankings.RankingViewModel
import io.mockk.mockk
import org.junit.Rule

class RankingViewModelTest {

    @get:Rule
    val coroutineRule = CoroutinesRule()

    private val repository = mockk<RepositoryImpl>(relaxed = true)

    lateinit var viewModel: RankingViewModel

//    @Test
//    fun `GIVEN an valid result RETURN State success WHEN viewModel is created`() =
//        runTest {
//            // GIVEN
//            val success = State.success(listOf(GithubRepoEntity(
//                alias = "alias",
//                capacity = 1,
//                channels = 1,
//                firstSeen = 1,
//                updatedAt = 1,
//                city = emptyMap(),
//                country = emptyMap(),
//                publicKey = "publicKey"
//            )))
//
//            coEvery {
//                repository.getRepos()
//            } returns success.data
//            // WHEN
//            // THEN
//            viewModel = RankingViewModel(repository)
//            viewModel.nodes.value shouldBe State.Loading
//            viewModel.getTopRankings().join()
//            viewModel.nodes.value shouldBe success
//        }
//
//    @Test
//    fun `GIVEN an exception result RETURN State fail WHEN viewModel is created`() =
//        runTest {
//            // GIVEN
//
//            coEvery {
//                repository.getRepos()
//            } throws Exception("ERROR")
//            // WHEN
//            // THEN
//            viewModel = RankingViewModel(repository)
//            viewModel.nodes.value shouldBe State.Loading
//            viewModel.getTopRankings().join()
//            viewModel.nodes.value shouldBe State.failed("ERROR")
//        }
}