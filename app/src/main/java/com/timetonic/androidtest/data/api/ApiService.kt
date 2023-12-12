package com.timetonic.androidtest.data.api

import com.timetonic.androidtest.data.api.Constants.API_VERSION
import com.timetonic.androidtest.data.api.Constants.APP_NAME
import com.timetonic.androidtest.data.model.responses.AppKeyResponse
import com.timetonic.androidtest.data.model.responses.GetAllBooksResponse
import com.timetonic.androidtest.data.model.responses.OauthResponse
import com.timetonic.androidtest.data.model.responses.SessKeyResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    /**  ATHENTICATION **/
    @POST("api.php")
    suspend fun createAppkey(
        @Query("req") req: String? = "createAppkey",
        @Query("version") version: String? = API_VERSION,
        @Query("appname") appname: String? = APP_NAME,
    ): Response<AppKeyResponse>

    @POST("api.php")
    suspend fun login(
        @Query("req") req: String? = "createOauthkey",
        @Query("version") version: String? = API_VERSION,
        @Query("login") login: String,
        @Query("pwd") pwd: String,
        @Query("appkey") appkey: String,
    ): Response<OauthResponse>

    @POST("api.php")
    suspend fun createSesskey(
        @Query("req") req: String? = "createSesskey",
        @Query("version") version: String? = API_VERSION,
        @Query("o_u") ou: String,
        @Query("u_c") uc: String,
        @Query("oauthkey") oauthkey: String,
        @Query("restrictions") restrictions: String? = null,
    ): Response<SessKeyResponse>

    @POST("api.php")
    suspend fun getAllBooks(
        @Query("req") req: String? = "getAllBooks",
        @Query("version") version: String? = API_VERSION,
        @Query("o_u") ou: String,
        @Query("u_c") uc: String,
        @Query("sesskey") sesskey: String,
        @Query("sstamp ") sstamp: Int? = null,
        @Query("b_c") bc: String? = null,
        @Query("b_o") bo: String? = null,
    ): Response<GetAllBooksResponse>
}