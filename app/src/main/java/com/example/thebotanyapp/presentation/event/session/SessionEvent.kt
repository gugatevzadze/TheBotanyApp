package com.example.thebotanyapp.presentation.event.session

sealed class SessionEvent{
    data object CheckCurrentSession : SessionEvent()
}