package com.example.firstapp.testflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TestFlowViewModel : ViewModel() {
    private val repository = TestFlowRepository()
    private val _userState = MutableStateFlow("Empty")
    val userState: StateFlow<String> = _userState
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()
    private val _toastData = MutableSharedFlow<String>()
    val toastData = _toastData.asSharedFlow()

    fun loadUser() {
        viewModelScope.launch {
            repository.fetchUser()
                .collect { value ->
                    _userState.value = value
                }
        }
    }
    fun onButtonClick() {
        viewModelScope.launch {
            _toastEvent.emit("Đây là thông báo")
        }
    }
    fun fetchDataAsync() {
        viewModelScope.launch {
            _toastData.emit(repository.fetchDataAsync())
        }
    }

}