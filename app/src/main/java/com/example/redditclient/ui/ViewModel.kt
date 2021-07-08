package com.example.redditclient.ui

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redditclient.NetworkManager
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import com.example.redditclient.domain.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModel(private val useCase: UseCase, application: Application) : ViewModel() {

    private val liveDataRemote = MutableLiveData<List<Children>>()
    val liveDataRemoteProvider: LiveData<List<Children>> = liveDataRemote

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
                    .subscribe({
                        liveDataRemote.postValue(it)
                    }, {
                        Log.getStackTraceString(it)
                        Toast.makeText(
                            context,
                            "Check your internet connection and try again",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    })
            )
        }
    }

    fun openEntryInChromeTab(index: Int, context: Context) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        builder.setToolbarColor(Color.parseColor("#004d40"))
        builder.setSecondaryToolbarColor(Color.parseColor("#ff5252"))

        //customTabsIntent.launchUrl(context, Uri.parse()
    }

//    private fun getSingleEntry(data: List<Children>): ArrayList<Children.Data> {
//        val entries = List<Children.Data>(data.size)
//        for (i in 0..49) {
//            entries.add(
//                EntriesResponse.Data(
//                    numOfEntry = i + 1,
//                    title = data[i].data.title,
//                    author = data[i].data.author,
//                    score = data[i].data.score,
//                    subreddit = data[i].data.subreddit,
//                    num_comments = data[i].data.num_comments,
//                    created_utc = data[i].data.created_utc,
//                    url = data[i].data.url,
//                    thumbnail = data[i].data.thumbnail,
//                    name = data[i].data.name
//                )
//            )
//        }
//
//        firstEntryName = entries[0].name
//        lastEntryName = entries[49].name
//
//        isLoading.set(false)
//        return entries
//    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}