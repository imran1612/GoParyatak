package app.com.goparyatak.Retrofit

import DashBoardData
import UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
     fun addUser(userData: UserInfo, onResult: (DashBoardData?) -> Unit){
        val retrofit = ServiceBuilder.buildService(APIService::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<DashBoardData> {
                override fun onFailure(call: Call<DashBoardData>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<DashBoardData>, response: Response<DashBoardData>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
}