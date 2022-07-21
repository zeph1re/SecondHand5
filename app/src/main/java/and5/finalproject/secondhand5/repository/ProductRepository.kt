package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.Room.Model.GetProductHome
import and5.finalproject.secondhand5.Room.OfflineDB
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.banner.GetBannerItem
import and5.finalproject.secondhand5.model.buyerproduct.*
import and5.finalproject.secondhand5.model.seller.*
import and5.finalproject.secondhand5.model.seller.Category
import and5.finalproject.secondhand5.network.ApiService
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi : ApiService) {

    lateinit var userManager: UserManager


    //Buyer
    suspend fun getAllProduct(searchQuery : String): List<GetProductItem>{

        return productApi.getAllProduct("available", searchQuery)
    }

    suspend fun getProductbyCategories(category_id: Int, searchQuery : String): List<GetProductItem>{
        return productApi.getProductbyCategories("available", category_id, searchQuery)
    }

    suspend fun getDetailProduct(id:Int): GetProductItem {
        return productApi.getDetailProduct(id)
    }

    suspend fun getBuyerOrder(access_token : String): List<GetBuyerOrderItem>{
        return productApi.getBuyerOrder(access_token)
    }

    suspend fun getBuyerDetailOrder(access_token : String, id:Int): GetBuyerOrderItem {
        return productApi.getBuyerDetailOrder(access_token, id)
    }

    fun updateBuyerOrder(
        access_token : String,
        id : Int,
        bid_price: Int,
        liveCode: MutableLiveData<String>
    ){
        val apiClient : Call<UpdateBuyerOrderResponse> = productApi.updateBuyerOrder(access_token, id, bid_price)
        apiClient.enqueue(object :Callback<UpdateBuyerOrderResponse>{
            override fun onResponse(
                call: Call<UpdateBuyerOrderResponse>,
                response: Response<UpdateBuyerOrderResponse>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<UpdateBuyerOrderResponse>, t: Throwable) {
                liveCode.postValue(null)
            }

        })
    }




    fun addBuyerOrder(
        access_token : String,
        id: Int,
        bid_price: Int,
        liveCode: MutableLiveData<String>
    ) {
        val apiClient :Call<AddBuyerOrderResponse> = productApi.postBuyerOrder(access_token,id,bid_price)
        apiClient.enqueue(object : Callback<AddBuyerOrderResponse> {
            override fun onResponse(
                call: Call<AddBuyerOrderResponse>,
                response: Response<AddBuyerOrderResponse>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<AddBuyerOrderResponse>, t: Throwable) {
                liveCode.postValue(null)
            }
        })
    }


//Seller

    suspend fun getSellerDetailOrder(
        access_token : String,
        id: Int
    ): GetSellerOrderItem {
        return productApi.getSellserDetailOrder(access_token, id)
    }

    fun sellerDeleteProduct(
        access_token : String,
        id: Int,
        liveCode: MutableLiveData<String>
    ) {
        val apiClient : Call<GetSellerProductItem> = productApi.deleteProduct(access_token, id)
        apiClient.enqueue(object : Callback<GetSellerProductItem>{
            override fun onResponse(
                call: Call<GetSellerProductItem>,
                response: Response<GetSellerProductItem>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<GetSellerProductItem>, t: Throwable) {
                liveCode.postValue(null)
            }

        })
    }

    suspend fun getSellerDetailProduct(
        access_token : String,
        id: Int,
    ) : GetSellerProductItem{
        return productApi.getSellerDetailProduct(access_token, id)
    }

    fun updateSellerProduct(
        access_token : String,
        id : Int,
        name : RequestBody,
        desc: RequestBody,
        price: RequestBody,
        category: RequestBody,
        location : RequestBody,
        liveCode: MutableLiveData<String>
    ){
        val apiClient : Call<UpdateProductResponse> = productApi.updateProduct(access_token,id, name, desc, price, category, location)
        apiClient.enqueue(object :Callback<UpdateProductResponse>{
            override fun onResponse(
                call: Call<UpdateProductResponse>,
                response: Response<UpdateProductResponse>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<UpdateProductResponse>, t: Throwable) {
                liveCode.postValue(null)
            }

        })
    }

    fun patchSellerOrder(
        access_token : String,
        id: Int,
        status: String,
        liveCode: MutableLiveData<String>
    ) {
        val apiClient :Call<PatchOrderResponse> = productApi.responseSellerOrder(access_token,id, status)
        apiClient.enqueue(object : Callback<PatchOrderResponse> {
            override fun onResponse(
                call: Call<PatchOrderResponse>,
                response: Response<PatchOrderResponse>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<PatchOrderResponse>, t: Throwable) {
                liveCode.postValue(null)
            }
        })
    }

    suspend fun getSellerProduct(token:String) : List<GetSellerProductItem> {
        return productApi.getSellerProduct(token)
    }
    suspend fun getSellerOrder(token:String) : List<GetSellerOrderItem> {
        return productApi.getSellerOrder(token, "pending")
    }

    suspend fun getSellerSuccesfulOrder(token:String) : List<GetSellerOrderItem> {
        return productApi.getSellerSuccesfulOrder(token, "accepted")
    }

    suspend fun getAllCategory(): List<Category>{
        return productApi.getAllCategory()
    }


    suspend fun getSellerCategory(): List<GetSellerCategoryItem> {
        return productApi.getSellerCategory()
    }

    fun addProduct(token:String, name : RequestBody, desc: RequestBody, price: RequestBody, category: RequestBody, location : RequestBody, image :  MultipartBody.Part, liveCode: MutableLiveData<String>){

        val apiClient : Call<PostResponse> = productApi.postProduct(token, name, desc, price, category, location, image)
        apiClient.enqueue(object : Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                liveCode.postValue(null)
            }
        })

    }

    suspend fun getSellerBanner() : List<GetBannerItem>{
        return productApi.getAllBanner()
    }


}