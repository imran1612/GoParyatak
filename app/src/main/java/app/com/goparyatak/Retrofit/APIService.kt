package app.com.goparyatak.Retrofit

import DashBoardData
import DashBoardData1
import DashBoardModel
import LoginModel
import LoginResponse
import UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface APIService {
    @POST("Bookinglist")
     fun addUser(@Body userData: UserInfo): Call<DashBoardData>

    @POST("Bookinglist")
    fun bookingDetail(@Body dataModal: UserInfo?): Call<DashBoardData1?>?

    @POST("Servicesectors")
    fun dashBoardData(@Body dataModal: DashBoardModel?): Call<DashBoardData?>?

    @POST("Login/authenticate")
    fun Login(@Body dataModal: LoginModel?): Call<LoginResponse?>?
}