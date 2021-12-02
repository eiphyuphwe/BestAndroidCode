package com.example.bestandroidcode.ui.main

import androidx.lifecycle.*
import com.example.bestandroidcode.datasource.MainRepository
import com.example.bestandroidcode.model.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository): ViewModel() {
    // Hmm, why is this empty?
    private val _randomCatDataList = MutableLiveData<Response<List<Cat>>>()
    val randomCatDataList : LiveData<Response<List<Cat>>> = _randomCatDataList

    fun getCatRandom() {
        viewModelScope.launch {
           mainRepository.getCatRandom().collect {
                _randomCatDataList.postValue(it)
            }
        }
    }

    fun getCatBasedOnCategory(categoryIds:String) {
        viewModelScope.launch {
            mainRepository.getCatBasedOnCategory(categoryIds).collect {
                _randomCatDataList.postValue(it)
            }
        }
    }
}