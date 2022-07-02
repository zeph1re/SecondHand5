package and5.finalproject.secondhand5.network

import and5.finalproject.secondhand5.model.auth.*
import and5.finalproject.secondhand5.model.buyerproduct.AddBuyerOrderResponse
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.notification.GetNotificationItem
import and5.finalproject.secondhand5.model.seller.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Headers("Accept: application/json")
    @Multipart
    @PUT("auth/user")
    fun updateUser(
        @Header("access_token")token:String,
        @Part("full_name")full_name:  RequestBody,
        @Part("email")email:  RequestBody,
        @Part("password")password:  RequestBody,
        @Part("phone_number")phone_number: RequestBody,
        @Part("address")address:  RequestBody,
        @Part image :  MultipartBody.Part,
        @Part ("city") city: RequestBody
    ) : Call<GetAllUser>

    @GET ("/notification")
    suspend fun getNotification(
        @Header("access_token") token: String
    ) : List<GetNotificationItem>


    // BUYER
    @GET("buyer/product")
    suspend fun getAllProduct(
        @Query("status") status : String
    ) : List<GetProductItem>

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
    @Headers("Accept: application/json")
    @Multipart
    @POST ("/seller/product")
    fun postProduct(
        @Header("access_token") token: String,
        @Part  ("name") name : RequestBody,
        @Part  ("description") description : RequestBody,
        @Part  ("base_price") base_price : RequestBody,
        @Part  ("category_ids") category_ids: RequestBody,
        @Part  ("location") location: RequestBody,
        @Part image : MultipartBody.Part,
    ) : Call<PostResponse>

    @Headers("Accept: application/json")
    @Multipart
    @PUT ("/seller/product")
    fun updateProduct(
        @Header("access_token") token: String,
        @Part  ("name") name : RequestBody,
        @Part  ("description") description : RequestBody,
        @Part  ("base_price") base_price : RequestBody,
        @Part  ("category_ids") category_ids: RequestBody,
        @Part  ("location") location: RequestBody,
        @Part image : MultipartBody.Part,
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

    @GET("seller/order")
    suspend fun getSellerOrder(
        @Header("access_token") token : String,
        @Query("status") status : String
    ) : List<GetSellerOrderItem>

    @PATCH("seller/order/{id}")
    @FormUrlEncoded
    fun responseSellerOrder(
        @Header("access_token") token : String,
        @Path("id")id : Int,
        @Field("status") status: String
    ) : Call<PatchOrderResponse>


}