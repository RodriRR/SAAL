package com.saal.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saal.data.model.Category
import com.saal.data.repository.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(repo: DatabaseRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val repo = repo

    var categories = repo.getCategories()

    init {
        //getCategories()
    }

    /*fun getCategories() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            try {
                categories.postValue(repo.dbGetCategories().value)
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }*/

}