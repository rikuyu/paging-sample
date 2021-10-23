package com.example.pagingsample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingsample.model.data.Character
import com.example.pagingsample.model.data.Res
import com.example.pagingsample.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

//    private val _characters1: MutableLiveData<Resource<Res>> = MutableLiveData()
//    val characters1: MutableLiveData<Resource<Res>> = _characters1
//
//    fun getCharacters() {
//        viewModelScope.launch {
//            _characters1.value = Resource.Loading()
//            val res = repo.getCharacter()
//            if (res.isSuccessful) {
//                res.body()?.let {
//                    _characters1.value = Resource.Success(it)
//                }
//            } else {
//                _characters1.value = Resource.Error("error")
//            }
//        }
//    }

    val characters: Flow<PagingData<Character>> = Pager(
        PagingConfig(pageSize = 10, initialLoadSize = 10)
    ) {
        CharacterPagingSource(repo)
    }.flow.cachedIn(viewModelScope)
}