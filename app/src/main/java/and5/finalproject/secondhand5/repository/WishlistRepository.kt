package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.model.auth.RegisterResponse
import and5.finalproject.secondhand5.model.wishlist.DeleteWishlistResponse
import and5.finalproject.secondhand5.model.wishlist.GetWishlistProduct
import and5.finalproject.secondhand5.model.wishlist.GetWishlistProductItem
import and5.finalproject.secondhand5.model.wishlist.PostProductToWishlist
import and5.finalproject.secondhand5.network.ApiService
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WishlistRepository @Inject constructor( private val service : ApiService ) {


    fun postProductToWishlist (token: String, product_id : Int, liveCode : MutableLiveData<String>) {
        val apiClient: Call<PostProductToWishlist> = service.postProductToWishlist(token,product_id)

        apiClient.enqueue(object : Callback<PostProductToWishlist> {
            override fun onResponse(
                call: Call<PostProductToWishlist>,
                response: Response<PostProductToWishlist>
            ) {
                liveCode.postValue(response.code().toString())
            }

            override fun onFailure(call: Call<PostProductToWishlist>, t: Throwable) {
                liveCode.postValue(null)
            }

        }
        )
    }


    suspend fun getAllProductWishlist(token:String) : List<GetWishlistProductItem> {
        return service.getWishlistProduct(token)
    }

    suspend fun getDetailProductWishlist(token: String, id: Int) : GetWishlistProductItem {
        return service.getWishlistDetail(token,id)
    }

    fun deleteProductWishlist(
        token: String,
        id: Int,
        liveCode: MutableLiveData<String>
    ) : DeleteWishlistResponse {
        return service.deleteWishlistItem(token, id)
    }

}