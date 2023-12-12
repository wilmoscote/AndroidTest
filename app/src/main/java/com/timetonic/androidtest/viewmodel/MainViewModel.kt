package com.timetonic.androidtest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timetonic.androidtest.data.api.Constants.DEBUG_TAG
import com.timetonic.androidtest.data.model.datamodels.AllBooks
import com.timetonic.androidtest.data.model.datamodels.Book
import com.timetonic.androidtest.data.repository.ApiRepository
import com.timetonic.androidtest.data.repository.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    // LiveData to track the loading state
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // LiveData to track any errors that occur
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // LiveData to indicate if a session has been successfully created
    private val _sessCreated = MutableLiveData<Boolean>()
    val sessCreated: LiveData<Boolean> get() = _sessCreated

    // LiveData to hold the list of all books
    private val _allbooks = MutableLiveData<AllBooks>()
    val allbooks: LiveData<AllBooks> get() = _allbooks

    // Function to create an app key using the API
    fun createAppkey() {
        viewModelScope.launch {
            // Indicate that loading has started
            _loading.postValue(true)

            try {
                // Make an API call to create an app key
                val response = apiRepository.createAppkey()

                // Check if the response is successful
                if (response.isSuccessful) {
                    // Log the response and save the app key in preferences
                    Log.d(DEBUG_TAG, response.body().toString())
                    response.body()?.appkey.let { key ->
                        userPreferences.setAppKey(key!!)
                    }
                }
            } catch (e: Exception) {
                // Log the error and post the error message to LiveData
                Log.e(DEBUG_TAG, e.message.toString())
                _error.postValue("An error has occurred: ${e.message.toString()}" ?: "HTTP error")
            } finally {
                // Indicate that loading has finished
                _loading.postValue(false)
            }
        }
    }

    // Function to create an OAuth key
    fun createOauthkey(login: String, pwd: String) {
        viewModelScope.launch {
            // Signal the start of a loading process
            _loading.postValue(true)

            try {
                // Make an API call to authenticate the user
                val response =
                    apiRepository.login(login, pwd, userPreferences.getAppKey().firstOrNull() ?: "")

                // Check if the response from the API is successful
                if (response.isSuccessful) {
                    // Log the successful response
                    Log.d(DEBUG_TAG, response.body().toString())

                    // Process the response based on the status
                    when (response.body()?.status) {
                        "ok" -> {
                            // On success, save the OAuth key and user information, then create a session key
                            response.body()?.oauthkey?.let { userPreferences.setOauthKey(it) }
                            response.body()?.ou?.let { userPreferences.setOU(it) }
                            createSesskey(response.body()?.ou.toString())
                        }

                        "nok" -> {
                            // Post an error message if the status is not 'ok'
                            _error.postValue(
                                "An error has occurred: ${response.body()?.error}" ?: "HTTP error"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle any exceptions that occur during the API call
                Log.e(DEBUG_TAG, e.message.toString())
                _error.postValue("An error has occurred: ${e.message.toString()}" ?: "HTTP error")
            } finally {
                // Signal the end of the loading process
                _loading.postValue(false)
            }
        }
    }


    // Function to fetch all books from the API
    fun getAllBooks() {
        viewModelScope.launch {
            // Signal the start of a loading process
            _loading.postValue(true)

            try {
                // Retrieve user information from preferences
                val ouuc = userPreferences.getOU().firstOrNull() ?: ""
                val sesskey = userPreferences.getSessKey().firstOrNull() ?: ""
                Log.d(DEBUG_TAG,"OU: $ouuc sessKey: $sesskey")

                // Make an API call to fetch all books
                val response = apiRepository.getAllBooks(ouuc, ouuc, sesskey)

                // Check if the API response is successful
                if (response.isSuccessful) {
                    // Log the successful response
                    //Log.d(DEBUG_TAG, response.body().toString())

                    // Process the response based on the status
                    when (response.body()?.status) {
                        "ok" -> {
                            // On success, post the list of all books to LiveData
                            _allbooks.postValue(response.body()?.allBooks)
                        }
                        "nok" -> {
                            // Post an error message if the status is not 'ok'
                            _error.postValue("An error has occurred" ?: "HTTP error")
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle any exceptions during the API call
                Log.e(DEBUG_TAG, e.message.toString())
                _error.postValue("An error has occurred: ${e.message.toString()}" ?: "HTTP error")
            } finally {
                // Signal the end of the loading process
                _loading.postValue(false)
            }
        }
    }

    // Function to create a session key
    private fun createSesskey(ou: String) {
        viewModelScope.launch {
            // Indicate that a loading operation has started
            _loading.postValue(true)

            try {
                // Make an API call to create a session key
                val response = apiRepository.createSesskey(
                    ou,
                    ou,
                    userPreferences.getOauthKey().firstOrNull() ?: ""
                )

                // Check if the API response is successful
                if (response.isSuccessful) {
                    // Log the successful response
                    Log.d(DEBUG_TAG, response.body().toString())

                    // Process the response based on the returned status
                    when (response.body()?.status) {
                        "ok" -> {
                            // On success, save the session key and post a successful creation to LiveData
                            response.body()?.sesskey?.let { userPreferences.setSessKey(it) }
                            _sessCreated.postValue(true)
                        }

                        "nok" -> {
                            // Post an error message if the status is not 'ok'
                            _error.postValue(
                                "An error has occurred: ${response.body()?.error}" ?: "HTTP error"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle any exceptions that occur during the API call
                Log.e(DEBUG_TAG, e.message.toString())
                _error.postValue("An error has occurred: ${e.message.toString()}" ?: "HTTP error")
            } finally {
                // Indicate that the loading operation has finished
                _loading.postValue(false)
            }
        }
    }

    //Check if there is a session saved to restore
    suspend fun sessSaved(): String? {
        return userPreferences.getSessKey().firstOrNull()
    }

    //Logout and finish the session, clear the dataStore
    fun logout() {
        viewModelScope.launch {
            try {
                userPreferences.clearDataStore()
            } catch (e: Exception){
                Log.e(DEBUG_TAG, e.message.toString())
                _error.postValue("An error has occurred: ${e.message.toString()}" ?: "HTTP error")
            }
        }
    }

    suspend fun getOU(): String {
        return userPreferences.getOU().firstOrNull() ?: "User"
    }
}