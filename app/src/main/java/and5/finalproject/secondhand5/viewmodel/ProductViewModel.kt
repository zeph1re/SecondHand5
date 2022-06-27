package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.seller.GetSellerCategoryItem
import and5.finalproject.secondhand5.model.seller.GetSellerProductItem
import and5.finalproject.secondhand5.model.seller.PostResponse
import and5.finalproject.secondhand5.repository.ProductRepository
import and5.finalproject.secondhand5.singleliveevent.SingeLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private var productRepository : ProductRepository) : ViewModel(){

    private var productLivedata = MutableLiveData<List<GetProductItem>>()
    private var detailProductLivedata = MutableLiveData<GetProductItem>()
    var sellerProductLiveData = MutableLiveData<List<GetSellerProductItem>>()
    var sellerCategory = MutableLiveData<List<GetSellerCategoryItem>>()
    var addProductLiveData : MutableLiveData<PostResponse> = MutableLiveData()
    var addBuyerOrderLiveData : SingeLiveEvent<String> = SingeLiveEvent ()

    val product : LiveData<List<GetProductItem>> = productLivedata
    val detailProduct : LiveData<GetProductItem> = detailProductLivedata
    val sellerProduct : LiveData<List<GetSellerProductItem>> = sellerProductLiveData

    var userToken : MutableLiveData<String> = MutableLiveData()

    //  Buyer
    fun getAllProduct(){
        viewModelScope.launch {
            val dataproduct = productRepository.getAllProduct()
            productLivedata.value = dataproduct
        }
    }

    fun getDetailProduct(id:Int){
        viewModelScope.launch {
            val detailproduct = productRepository.getDetailProduct(id)
            detailProductLivedata.value = detailproduct
        }
    }

    fun postBuyerOrder(access_token:String, id: Int, bid_price:Int){
        viewModelScope.launch {
            productRepository.addBuyerOrder(access_token, id, bid_price, addBuyerOrderLiveData)
        }
    }

    //  Seller
    fun getSellerProduct(token:String){
        viewModelScope.launch{
            val dataSellerProduct = productRepository.getSellerProduct(token)
            sellerProductLiveData.value = dataSellerProduct
        }
    }

    fun getSellerCategory(){
        viewModelScope.launch{
            val category = productRepository.getSellerCategory()
            sellerCategory.value = category
        }
    }

    fun addSellerProduct(token: String, name: String, desc: String, price: Int, category: String, location: String, image: MultipartBody.Part){
        viewModelScope.launch  {
            val partName = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name)
            val partDesc= RequestBody.create("multipart/form-data".toMediaTypeOrNull(), desc)
            val partHarga= RequestBody.create("multipart/form-data".toMediaTypeOrNull(), price.toString())
            val partCategory = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "31")
            val partLocation = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "Bandung")

            productRepository.addProduct(token, partName, partDesc, partHarga, partCategory, partLocation, image)
        }
    }


}