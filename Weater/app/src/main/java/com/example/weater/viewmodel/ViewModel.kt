package com.example.weater.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weater.API_KEY
import com.example.weater.network.NetworkInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ViewModel @Inject constructor(private val networkApi: NetworkInterface) {

    val disposable: CompositeDisposable = CompositeDisposable()

    val emitter: MutableLiveData<EventClass> = MutableLiveData()

    fun retrieveInfo(lat: Double, long: Double) {
        disposable.add(
            networkApi.getInformationAboutLocation(API_KEY, lat, long)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    emitter.postValue(SuccessEvent(it))
                }, {
                    emitter.postValue(ErrorEvent(it))
                })
        )
    }

    //Not needed right now
    fun stop() {
        disposable.dispose()
    }

}

