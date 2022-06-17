package and5.finalproject.secondhand5.network

import and5.finalproject.secondhand5.model.LoginResponse
import and5.finalproject.secondhand5.model.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST ("auth/login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field ("email") email : String,
        @Field ("password") password : String
    ) : LoginResponse

    @POST ("auth/register")
    @FormUrlEncoded
    suspend fun registerUser(
        @Field ("full_name") full_name : String,
        @Field ("email") email : String,
        @Field ("password") password : String,
        @Field ("phone_number") phone_number : String,
        @Field ("address") address : String,
        @Field ("image_url") image : String,
    ) : RegisterResponse


}