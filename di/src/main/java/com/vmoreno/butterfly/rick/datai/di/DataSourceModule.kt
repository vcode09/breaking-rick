package com.vmoreno.butterfly.rick.datai.di

import android.app.Application
import androidx.room.Room
import com.vmoreno.butterfly.rick.data.database.Dao
import com.vmoreno.butterfly.rick.data.database.Database
import com.vmoreno.butterfly.rick.data.database.RoomDataSource
import com.vmoreno.butterfly.rick.data.datasource.source.RemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single {
        RemoteDataSource(get())
    }
    single { providesDatabase(get()) }
    single { providesDao(get()) }
    single {
        RoomDataSource(get())
    }
}

fun providesDatabase(application: Application): Database =
    Room.databaseBuilder(application, Database::class.java, "database")
        .build()

fun providesDao(db: Database): Dao = db.dbDao()