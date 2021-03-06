package com.example.redditclient.ui

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redditclient.NetworkManager
import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data
import com.example.redditclient.domain.usecase.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val deleteFavoritePostUseCase: DeleteFavoritePostUseCase,
    private val getAllFavoritePostsUseCase: GetAllFavoritePostsUseCase,
    private val getNextPostsPageUseCase: GetNextPostsPageUseCase,
    private val getPrevPostsPageUseCase: GetPrevPostsPageUseCase,
    private val saveFavoritePostUseCase: SaveFavoritePostUseCase,
    application: Application
) :
    ViewModel() {

    private val liveDataRemote = MutableLiveData<ArrayList<Data>>()
    val liveDataRemoteProvider: LiveData<ArrayList<Data>> = liveDataRemote

    private val liveDataLocal = MutableLiveData<List<FavoritePost>>()
    val liveDataLocalProvider: LiveData<List<FavoritePost>> = liveDataLocal

    private val compositeDisposable = CompositeDisposable()
    private val context = application.applicationContext
    private val networkManager = NetworkManager(context)

    private var firstPostName: String = ""
    private var lastPostName: String = ""

    fun loadAllPosts() {
        if (networkManager.isConnectedToInternet) {
            compositeDisposable.add(
                getPostsUseCase.getPosts()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<List<Data>>() {
                        override fun onError(e: Throwable) {
                            Toast.makeText(
                                context,
                                "Check your internet connection and try again",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                        override fun onNext(listPosts: List<Data>) {
                            liveDataRemote.value = getSinglePost(listPosts)
                        }

                        override fun onComplete() {
                        }
                    })
            )
        }
    }

    fun loadNextPostsPage() {
        if (networkManager.isConnectedToInternet) {
            compositeDisposable.add(
                getNextPostsPageUseCase.getNextPostsPage(lastPostName)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<List<Data>>() {
                        override fun onError(e: Throwable) {
                            Toast.makeText(
                                context,
                                "Check your internet connection and try again",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                        override fun onNext(listPosts: List<Data>) {
                            liveDataRemote.value = getSinglePost(listPosts)
                        }

                        override fun onComplete() {
                        }
                    })
            )
        }
    }

    fun loadPrevPostsPage() {
        if (networkManager.isConnectedToInternet) {
            compositeDisposable.add(
                getPrevPostsPageUseCase.getPrevPostsPage(firstPostName)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<List<Data>>() {
                        override fun onError(e: Throwable) {
                            Toast.makeText(
                                context,
                                "Check your internet connection and try again",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                        override fun onNext(listPosts: List<Data>) {
                            liveDataRemote.value = getSinglePost(listPosts)
                        }

                        override fun onComplete() {
                        }
                    })
            )
        }
    }

    private fun getSinglePost(data: List<Data>): ArrayList<Data> {
        val entries = ArrayList<Data>(data.size)
        for (i in 0..49) {
            entries.add(
                Data(
                    numOfEntry = i + 1,
                    title = data[i].title,
                    author = data[i].author,
                    numLikes = data[i].numLikes,
                    subreddit = data[i].subreddit,
                    numComments = data[i].numComments,
                    timeCreation = data[i].timeCreation,
                    url = data[i].url,
                    thumbnail = data[i].thumbnail,
                    name = data[i].name,
                    id = data[i].id
                )
            )
        }

        firstPostName = entries[0].name
        lastPostName = entries[49].name

        return entries
    }

    fun openEntryInChromeTab(index: Int, context: Context) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        builder.setToolbarColor(Color.parseColor("#9e9e9e"))
        builder.setSecondaryToolbarColor(Color.parseColor("#9e9e9e"))

        customTabsIntent.launchUrl(context, Uri.parse(liveDataRemote.value?.get(index)?.url))
    }

    fun openEntryInChromeTabFromFavorite(index: Int, context: Context) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        builder.setToolbarColor(Color.parseColor("#9e9e9e"))
        builder.setSecondaryToolbarColor(Color.parseColor("#9e9e9e"))

        customTabsIntent.launchUrl(context, Uri.parse(liveDataLocal.value?.get(index)?.url))
    }

    fun loadFavoritePosts() {
        viewModelScope.launch {
            liveDataLocal.postValue(getAllFavoritePostsUseCase.getAllFavoritePosts())
        }
    }

    fun savePost(post: Data) {
        viewModelScope.launch {
            saveFavoritePostUseCase.saveFavoritePost(post)
        }
    }

    fun deletePost(id: String) {
        viewModelScope.launch {
            deleteFavoritePostUseCase.deleteFavoritePost(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}