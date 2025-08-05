package com.example.mylsp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylsp.api.APIClient
import com.example.mylsp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {
    private val _users =  MutableStateFlow<List<User>>(emptyList())
    val users:StateFlow<List<User>> = _users

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val result = APIClient.api.getUsers()
                _users.value = result
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}