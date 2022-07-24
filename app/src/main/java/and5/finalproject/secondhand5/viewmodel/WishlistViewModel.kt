package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.wishlist.GetWishlistProductItem
import and5.finalproject.secondhand5.repository.WishlistRepository
import and5.finalproject.secondhand5.singleliveevent.SingeLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val repo : WishlistRepository) : ViewModel() {

    //Get All Wishlist Product

    private var wishlistProductLiveData = MutableLiveData<List<GetWishlistProductItem>>()
    val wishlistProduct : LiveData<List<GetWishlistProductItem>> = wishlistProductLiveData



    fun getAllWishlistProduct(access_token : String)  {
        viewModelScope.launch {
            val dataWishlistProduct = repo.getAllProductWishlist(access_token)
            wishlistProductLiveData.value = dataWishlistProduct

        }
    }


    //Detail Wishlist Product


    //Post Product to Wishlist Product

    private var responseCodePostProductToWIshlist : SingeLiveEvent<String> = SingeLiveEvent()

    fun postProductToWishlist(access_token: String, product_id: Int){
        viewModelScope.launch {
            repo.postProductToWishlist(access_token, product_id, responseCodePostProductToWIshlist)
        }
    }

    //Delete Wishlist Product

    private var responseCodeDeleteProductWishlist : SingeLiveEvent<String> = SingeLiveEvent()

    fun deleteWishlistProduct(access_token: String, id: Int){
        viewModelScope.launch {
            repo.deleteProductWishlist(access_token,id, responseCodeDeleteProductWishlist)
        }
    }




}