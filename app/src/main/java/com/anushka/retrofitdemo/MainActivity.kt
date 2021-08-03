package com.anushka.retrofitdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import kotlinx.coroutines.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        getRequestWithQueryParameters()
    }


    private fun getRequestWithQueryParameters() {
        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {

            val response = async {
                try {
                    retService.getSortedAlbums(3)
                }catch (e : Exception) { }
            }
            val response1 = async {
                try {

                    retService.getSortedAlbums(3)
                }catch (e :Exception){}
            }
            successView(response as Deferred<Response<Albums>>, response1 as Deferred<Response<Albums>>)

        }
    }

    private suspend fun successView(first : Deferred<Response<Albums>>, second : Deferred<Response<Albums>>) {
        first.await().body()?.let {
            val albumsList = it[0].title
            Log.e("ljy", "res1 " + albumsList)
        }
        second.await().body()?.let {
            val albumsList = it[0].title
            Log.e("ljy", "res1 " + albumsList)
        }
    }

}
