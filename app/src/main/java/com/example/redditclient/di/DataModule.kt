package com.example.redditclient.di

import androidx.room.Room
import com.example.redditclient.data.local.LocalDataDao
import com.example.redditclient.data.local.LocalDataStore
import com.example.redditclient.data.local.RoomDB
import com.example.redditclient.data.remote.RemoteDataStore
import com.example.redditclient.data.remote.RetrofitService
import com.example.redditclient.domain.Repository
import com.example.redditclient.domain.usecase.*
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

        //GetPostsUseCase
        bind<GetPostsUseCase>() with singleton {
            GetPostsUseCase(
                repository = instance()
            )
        }

        bind<DeleteFavoritePostUseCase>() with singleton {
            DeleteFavoritePostUseCase(
                repository = instance()
            )
        }

        bind<GetAllFavoritePostsUseCase>() with singleton {
            GetAllFavoritePostsUseCase(
                repository = instance()
            )
        }

        bind<GetNextPostsPageUseCase>() with singleton {
            GetNextPostsPageUseCase(
                repository = instance()
            )
        }

        bind<GetPrevPostsPageUseCase>() with singleton {
            GetPrevPostsPageUseCase(
                repository = instance()
            )
        }

        bind<SaveFavoritePostUseCase>() with singleton {
            SaveFavoritePostUseCase(
                repository = instance()
            )
        }
    }
}