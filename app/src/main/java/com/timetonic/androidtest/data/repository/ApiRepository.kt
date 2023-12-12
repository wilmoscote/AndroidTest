package com.timetonic.androidtest.data.repository

import com.timetonic.androidtest.data.api.ApiService
import com.timetonic.androidtest.data.model.responses.AppKeyResponse
import com.timetonic.androidtest.data.model.responses.GetAllBooksResponse
import com.timetonic.androidtest.data.model.responses.OauthResponse
import com.timetonic.androidtest.data.model.responses.SessKeyResponse
import retrofit2.Response
import javax.inject.Inject

class ApiRepository@Inject constructor(private val api: ApiService) {

    suspend fun createAppkey(): Response<AppKeyResponse> {
        return api.createAppkey()
    }

    suspend fun login(login: String, pwd: String, appkey: String): Response<OauthResponse> {
        return api.login(login = login, pwd = pwd, appkey = appkey)
    }

    suspend fun createSesskey(ou: String, uc: String, oauthkey: String): Response<SessKeyResponse> {
        return api.createSesskey(ou = ou, uc = uc, oauthkey = oauthkey)
    }

    suspend fun getAllBooks(ou: String, uc: String, sesskey: String): Response<GetAllBooksResponse> {
        return api.getAllBooks(ou = ou, uc = uc, sesskey = sesskey)
    }
}