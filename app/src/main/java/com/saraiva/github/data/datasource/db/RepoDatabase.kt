package com.saraiva.github.data.datasource.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saraiva.github.data.datasource.db.model.GithubRepo

@Database(entities = [GithubRepo::class], version = 1, exportSchema = false)
abstract class RepoDatabase: RoomDatabase() {

    abstract fun repoDao(): GithubRepoDao

    companion object {
        private var INSTANCE: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, RepoDatabase::class.java, "repo_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}