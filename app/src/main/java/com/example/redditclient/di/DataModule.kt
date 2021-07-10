package com.example.redditclient.di

import androidx.room.Room
import com.example.redditclient.data.local.model.LocalDataDao
import com.example.redditclient.data.local.model.LocalDataStore
import com.example.redditclient.data.local.model.RoomDB
import com.example.redditclient.data.remote.RemoteDataStore
import com.example.redditclient.data.remote.RetrofitService
import com.example.redditclient.domain.Repository
import com.example.redditclient.domain.UseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

object DataModule {

    val dataModule = Kodein.Module("data module", false) {

        //Remote
        bind<RetrofitService>() with singleton {
            instance<Retrofit>().create(RetrofitService::class.java)
        }

        bind() from singleton { RemoteDataStore() }

        //Local
        bind<RoomDB>() with singleton {
            Room.databaseBuilder(
                instance(),
                RoomDB::class.java, "items-name"
            ).build()
        }

        bind<LocalDataDao>() with provider { instance<RoomDB>().dataDao() }

        bind<LocalDataStore>() with singleton {
            LocalDataStore(
                localDataDao = instance()
            )
        }

        //Domain
        bind<Repository>() with singleton {
            Repository(
                remoteDataStore = instance(),
                localDataStore = instance()
            )
        }

        //UseCase
        bind<UseCase>() with singleton {
            UseCase(
                repository = instance()
            )
        }
    }
}