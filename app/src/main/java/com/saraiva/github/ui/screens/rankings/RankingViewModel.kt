package com.saraiva.github.ui.screens.rankings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saraiva.github.domain.entity.GithubRepoEntity
import com.saraiva.github.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _repos: MutableStateFlow<PagingData<GithubRepoEntity>> =
        MutableStateFlow(PagingData.empty())

    val repos: StateFlow<PagingData<GithubRepoEntity>> = _repos.asStateFlow()


    init {
        getTopRepos()
    }

    /**
     * Repositories fetching
     */

    fun getTopRepos() = viewModelScope.launch {
        repository.getRepos().cachedIn(viewModelScope)
            .collect { pagingData -> _repos.update { pagingData } }
    }

}
