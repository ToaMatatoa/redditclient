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
import com.example.redditclient.data.local.model.LocalData
import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data
import com.example.redditclient.domain.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ViewModel(private val useCase: UseCase, application: Application) : ViewModel() {

    private val liveDataRemote = MutableLiveData<ArrayList<Data>>()
    val liveDataRemoteProvider: LiveData<ArrayList<Data>> = liveDataRemote

    private val liveDataLocal = MutableLiveData<List<LocalData>>()
    val liveDataLocalProvider: LiveData<List<LocalData>> = liveDataLocal

    private val compositeDisposable = CompositeDisposable()
    private val context = application.applicationContext
    private val networkManager = NetworkManager(context)

    private var firstEntryName: String = ""
    private var lastEntryName: String = ""

    fun loadTopEntries() {

        if (networkManager.isConnectedToInternet) {
            compositeDisposable.add(
                useCase.getTopEntries()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<ResponseData>() {
                        override fun onError(e: Throwable) {
                            Toast.makeText(
                                context,
                                "Check your internet connection and try again",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                        override fun onNext(t: ResponseData) {
                            liveDataRemote.value = getSingleEntry(t.data.children)
                        }

                        override fun onComplete() {
                        }
                    })
            )
        }
    }

    private fun getSingleEntry(data: List<Children>): ArrayList<Data> {
        val entries = ArrayList<Data>(data.size)
        for (i in 0..49) {
            entries.add(
                Data(
                    numOfEntry = i + 1,
                    title = data[i].data.title,
                    author = data[i].data.author,
                    numLikes = data[i].data.numLikes,
                    subreddit = data[i].data.subreddit,
                    numComments = data[i].data.numComments,
                    timeCreation = data[i].data.timeCreation,
                    url = data[i].data.url,
                    thumbnail = data[i].data.thumbnail,
                    name = data[i].data.name,

                    )
            )
        }

        firstEntryName = entries[0].name
        lastEntryName = entries[49].name

        return entries
    }

    fun openEntryInChromeTab(index: Int, context: Context) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        builder.setToolbarColor(Color.parseColor("#004d40"))
        builder.setSecondaryToolbarColor(Color.parseColor("#ff5252"))

        customTabsIntent.launchUrl(context, Uri.parse(liveDataRemote.value?.get(index)?.url))
    }

    fun getLocalData() {
        viewModelScope.launch {
            liveDataLocal.postValue(useCase.getAllLocalData())
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}