package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.seller.*
import and5.finalproject.secondhand5.repository.ProductRepository
import and5.finalproject.secondhand5.singleliveevent.SingeLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private var productRepository : ProductRepository) : ViewModel(){

    private var productLivedata = MutableLiveData<List<GetProductItem>>()
    private var detailProductLivedata = MutableLiveData<GetProductItem>()
    var responseCodeAddProduct : SingeLiveEvent<String>  = SingeLiveEvent ()
    var responseCodeAddBuyerOrder : SingeLiveEvent<String> = SingeLiveEvent ()
    var sellerProductLiveData = MutableLiveData<List<GetSellerProductItem>>()
    var sellerCategory = MutableLiveData<List<GetSellerCategoryItem>>()
    private var categoryLivedata = MutableLiveData<List<Category>>()
    val category : LiveData<List<Category>> = categoryLivedata
    val product : LiveData<List<GetProductItem>> = productLivedata
    val detailProduct : LiveData<GetProductItem> = detailProductLivedata
    val sellerProduct : LiveData<List<GetSellerProductItem>> = sellerProductLiveData


    //seller
    var sellerOrderLiveData = MutableLiveData<List<GetSellerOrderItem>>()
    var sellerOrder : LiveData<List<GetSellerOrderItem>> = sellerOrderLiveData

    var responseCodePatchSellerOrder : SingeLiveEvent<String> = SingeLiveEvent ()
    var responseCodeDeleteProduct : SingeLiveEvent<String> = SingeLiveEvent ()

    var updateProductLiveData = MutableLiveData<List<GetProductItem>>()
    var sellerUpdateProduct : LiveData<List<GetProductItem>> = updateProductLiveData
    var responseCodeUpdateProduct : SingeLiveEvent<String>  = SingeLiveEvent ()

    var detailSellerProductLivedata = MutableLiveData<GetSellerProductItem>()
    var detailSellerProduct : LiveData<GetSellerProductItem> = detailSellerProductLivedata

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
            val postProduct = productRepository.addBuyerOrder(access_token, id, bid_price, responseCodeAddBuyerOrder)
        }
    }

    fun getAllCategory(){
        viewModelScope.launch {
            val datacategory = productRepository.getAllCategory()
            categoryLivedata.value = datacategory
        }
    }

    //  Seller
    fun getSellerDetailProduct(access_token:String, id: Int){
        viewModelScope.launch {
            val detailproduct = productRepository.getSellerDetailProduct(access_token, id)
            detailSellerProductLivedata.value = detailproduct
        }
    }

    fun updateSellerProduct(access_token:String, id: Int,  name: String, desc: String, price: Int, category: String, location: String){
        viewModelScope.launch {

            val partName = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name)
            val partDesc= RequestBody.create("multipart/form-data".toMediaTypeOrNull(), desc)
            val partHarga= RequestBody.create("multipart/form-data".toMediaTypeOrNull(), price.toString())
            val partCategory = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), category)
            val partLocation = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), location)

            productRepository.updateSellerProduct(access_token, id, partName, partDesc, partHarga, partCategory, partLocation, responseCodeUpdateProduct)
        }
    }

    fun deleteProduct(access_token:String, id: Int){
        viewModelScope.launch {
            productRepository.sellerDeleteProduct(access_token, id, responseCodeDeleteProduct)
        }
    }

    fun patchSellerOrder(access_token:String, id: Int, status:String){
        viewModelScope.launch {
            productRepository.patchSellerOrder(access_token, id, status, responseCodePatchSellerOrder)
        }
    }

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

    fun addSellerProduct(token: String, name: String, desc: String, price: Int, category: String, location: String, image:  MultipartBody.Part){
        viewModelScope.launch  {
            val partName = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name)
            val partDesc= RequestBody.create("multipart/form-data".toMediaTypeOrNull(), desc)
            val partHarga= RequestBody.create("multipart/form-data".toMediaTypeOrNull(), price.toString())
            val partCategory = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), category)
            val partLocation = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), location)

            productRepository.addProduct(token, partName, partDesc, partHarga, partCategory, partLocation, image, responseCodeAddProduct)

        }
    }

    fun getSellerOrder(token:String){
        viewModelScope.launch{
            val dataSellerOrder = productRepository.getSellerOrder(token)
            sellerOrderLiveData.value = dataSellerOrder
        }
    }




}