package and5.finalproject.secondhand5.network

import and5.finalproject.secondhand5.model.auth.GetAllUser
import and5.finalproject.secondhand5.model.auth.LoginResponse
import and5.finalproject.secondhand5.model.auth.RegisterResponse
import and5.finalproject.secondhand5.model.auth.UpdateUserBody
import and5.finalproject.secondhand5.model.buyerproduct.AddBuyerOrderResponse
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.seller.*
import android.app.Application
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    //USER
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

    @GET("auth/user")
    suspend fun getUserItem(
        @Header("access_token")token:String,
    ) : GetAllUser

    @PUT("auth/user")
    suspend fun updateUser(
        @Header("access_token")token:String,
        @Body user : UpdateUserBody
    ) : GetAllUser


    // BUYER
    @GET("buyer/product")
    suspend fun getAllProduct() : List<GetProductItem>

    @GET("buyer/product/{id}/")
    suspend fun getDetailProduct(
        @Path("id")id : Int
    ) : GetProductItem

    @POST ("buyer/order")
    @FormUrlEncoded
    fun postBuyerOrder(
        @Header("access_token") token: String,
        @Field("product_id") product_id: Int,
        @Field("bid_price") bid_price: Int
    ) : Call<AddBuyerOrderResponse>


    // SELLER
    @Multipart
    @POST ("seller/product")
    fun postProduct(
        @Header("access_token") token: String,
        @Part  ("name") name : RequestBody,
        @Part  ("description") description : RequestBody,
        @Part  ("base_price") base_price : RequestBody,
        @Part  ("category_ids") category_ids: RequestBody,
        @Part  ("location") location: RequestBody,
        @Part image : MultipartBody.Part
    ) : Call<PostResponse>

    @GET ("seller/product")
    suspend fun getSellerProduct(
        @Header("access_token") token: String,
    ) : List<GetSellerProductItem>
    @GET("seller/category")
    suspend fun getAllCategory() : List<Category>
    @GET ("seller/category")
    suspend fun getSellerCategory(
    ) : List<GetSellerCategoryItem>


}