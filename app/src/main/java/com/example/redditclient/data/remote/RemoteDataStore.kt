package com.example.redditclient.data.remote

import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data
import io.reactivex.Observable

class RemoteDataStore {

    private val apiService by lazy { RetrofitClient.redditPostsService() }

    fun getPosts(): Observable<List<Data>> {
        return apiService.getPosts()
            .map {
                it.data.children.map { post ->
                    post.data
                }
            }
    }

    fun getNextPostsPage(name: String): Observable<List<Data>> {
        return apiService.getNextPostsPage(name)
            .map {
                it.data.children.map { post ->
                    post.data
                }
            }
    }

    fun getPrevPostsPage(name: String): Observable<List<Data>> {
        return apiService.getPrevPostsPage(name)
            .map {
                it.data.children.map { post ->
                    post.data
                }
            }
    }
}