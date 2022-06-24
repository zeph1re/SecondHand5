package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.buyerproduct.AddBuyerOrderResponse
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.seller.AddProductResponse
import and5.finalproject.secondhand5.model.seller.GetSellerCategoryItem
import and5.finalproject.secondhand5.model.seller.GetSellerProductItem
import and5.finalproject.secondhand5.network.ApiService
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi : ApiService) {

    lateinit var userManager: UserManager

    suspend fun getAllProduct(): List<GetProductItem>{
        return productApi.getAllProduct()
    }

    suspend fun getDetailProduct(id:Int): GetProductItem {
        return productApi.getDetailProduct(id)
    }

    suspend fun getSellerProduct(token:String) : List<GetSellerProductItem> {
        return productApi.getSellerProduct(token)
    }

    suspend fun getSellerCategory() : List<GetSellerCategoryItem> {
        return productApi.getSellerCategory()
    }

    suspend fun addBuyerOrder(
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

    suspend fun addProductSeller(
        access_token : String,
        name: String,
        base_price: Int,
        desc: String,
        liveCode: MutableLiveData<String>
    ) {
        val apiClient :Call<AddProductResponse> = productApi.postProduct(access_token,name,base_price,desc)
        apiClient.enqueue(object : Callback<AddProductResponse> {
            override fun onResponse(
                call: Call<AddProductResponse>,
                response: Response<AddProductResponse>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<AddProductResponse>, t: Throwable) {
                liveCode.postValue(null)
            }
        })


    }
}