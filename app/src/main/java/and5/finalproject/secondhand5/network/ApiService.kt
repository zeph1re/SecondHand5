package and5.finalproject.secondhand5.network

import and5.finalproject.secondhand5.model.LoginResponse
import and5.finalproject.secondhand5.model.RegisterResponse
import and5.finalproject.secondhand5.model.buyerproduct.Category
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST ("auth/login")
    @FormUrlEncoded
    fun loginUser(
        @Field ("email") email : String,
        @Field ("password") password : String
    ) : Call<LoginResponse>

    @POST ("auth/register")
    @FormUrlEncoded
    fun registerUser(
        @Field ("full_name") full_name : String,
        @Field ("email") email : String,
        @Field ("password") password : String,
        @Field ("phone_number") phone_number : Int,
        @Field ("address") address : String,
        @Field ("city") city : String,
    ) : Call<RegisterResponse>

    @GET("buyer/product")
    suspend fun getAllProduct() : List<GetProductItem>

    @GET("buyer/product/{id}/")
    suspend fun getDetailProduct(
        @Path("id")id : Int
    ) : GetProductItem

    //SELLER
    @Multipart
    @POST ("seller/product")
    suspend fun postProduct(
        @Part("name") name : String,
        @Part("base_price") price : Int,
        @Part("categories") categories : List<Category>,
        @Part("desc") desc: RequestBody,
        @Part image: MultipartBody.Part,
    ) : Call<GetProductItem>


}