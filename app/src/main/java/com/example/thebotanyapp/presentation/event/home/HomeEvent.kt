package com.example.thebotanyapp.presentation.event.home


sealed class HomeEvent {
    data object Logout : HomeEvent()
}