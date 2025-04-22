package com.saraiva.github.data.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saraiva.github.data.datasource.db.model.GithubRepo

@Dao
interface GithubRepoDao {

    @Query("SELECT * FROM github_repo where page = :page ORDER BY stargazersCount DESC LIMIT 30")
    suspend fun getPagedList(page: Int): List<GithubRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: GithubRepo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<GithubRepo>)

    @Query("DELETE FROM github_repo")
    suspend fun deleteAll()


}