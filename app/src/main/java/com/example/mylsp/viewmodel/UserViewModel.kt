package com.example.mylsp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lsp24.models.User
import com.example.mylsp.api.APIClient
import com.example.mylsp.util.Util
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
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getUserById(id:Int):User?{
        val userById = Util.dummyUsers.find {
            it.idUser == id
        }
        return userById;
    }

    fun login(username: String, password: String): Boolean{
        val userLog = Util.dummyUsers.find {
            it.username == username
                    &&
            it.passwordHash == password
        }
        val checkUser = userLog != null
        if(checkUser){
            Util.logUser = userLog?.idUser?: 0
        }
        return checkUser;
    }

    fun cekApl01():Boolean{
        val asesi = Util.dummyAsesiList.find {
            it.idUser == Util.logUser
        }
        if (asesi != null){
            val assesmen = Util.dummyAsesmenList.find {
                it.idAsesi == asesi.idAsesi
            }
            if (assesmen != null){
                val apl01 = Util.dummyFormApl01List.find {
                    it.idAsesmen == assesmen.idAsesmen
                }
                if (apl01 != null){
                    return true
                }
            }
        }
        return false
    }
}