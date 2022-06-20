package and5.finalproject.secondhand5.network

import and5.finalproject.secondhand5.model.LoginResponse
import and5.finalproject.secondhand5.model.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @POST ("auth/login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field ("email") email : String,
        @Field ("password") password : String
    ) : LoginResponse

    @POST ("auth/register")
    @Multipart
    suspend fun registerUser(
        @Part ("full_name") full_name : String,
        @Part ("email") email : String,
        @Part  image_url : MultipartBody.Part,
    ) : RegisterResponse




}