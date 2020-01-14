package com.soft.attendancekt.ui.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.soft.attendancekt.AttendanceApplication
import com.soft.attendancekt.R
import com.soft.attendancekt.model.entity.ChatMessage
import com.soft.attendancekt.model.entity.MessageType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

class ChatMessageViewModel(application: Application) : AndroidViewModel(application) {

    private var stompClient: StompClient
    private var objectMapper: ObjectMapper
    private var disposable: CompositeDisposable? = null
    private val list = mutableListOf<ChatMessage>()
    val messages = MutableLiveData<List<ChatMessage>>()
    val error = MutableLiveData<String>()
    val connectResult = MutableLiveData<Boolean>()

    init {
        val baseUrl = application.getString(R.string.server_url)
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, baseUrl)
        stompClient.withClientHeartbeat(1000).withServerHeartbeat(1000)
        objectMapper = jacksonObjectMapper()
    }

    fun connect(user: String) {
        AttendanceApplication.currentUser = user

        disposable = CompositeDisposable()

        val d = stompClient.lifecycle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
            when (event.type) {
                LifecycleEvent.Type.OPENED -> {
                    /*val dis = stompClient.topic("/msg/public").subscribe {
                        val message = objectMapper.readValue(it.payload, ChatMessage::class.java)
                        list.add(message)

                        val data = mutableListOf<ChatMessage>()
                        data.addAll(list)
                        messages.postValue(data)
                    }*/

                    //disposable.add(dis)

                    val joinMsg = ChatMessage(MessageType.JOIN, sender = user)
                    stompClient.send("/app/chat.addUser", objectMapper.writeValueAsString(joinMsg)).subscribe()

                    connectResult.value = true

                }

                LifecycleEvent.Type.CLOSED -> disconnect()

                LifecycleEvent.Type.ERROR -> {
                    connectResult.value = false
                    error.postValue(event.exception.message)
                }
                else -> {

                }
            }
        }

        val dis = stompClient.topic("/msg/public").subscribe {
            val message = objectMapper.readValue(it.payload, ChatMessage::class.java)
            list.add(message)

            val data = mutableListOf<ChatMessage>()
            data.addAll(list)
            messages.postValue(data)
        }
        disposable?.addAll(d, dis)

        stompClient.connect()

    }

    fun send(text: String) {
        val msg = ChatMessage(MessageType.CHAT, text, AttendanceApplication.currentUser)
        stompClient.send("/app/chat.sendMessage", objectMapper.writeValueAsString(msg))
            .subscribe()
    }

    fun disconnect() {
        list.clear()
        messages.value = null
        disposable?.dispose()
        stompClient.disconnect()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}

