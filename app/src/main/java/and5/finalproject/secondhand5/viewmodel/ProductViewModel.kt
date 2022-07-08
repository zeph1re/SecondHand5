package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.banner.GetBannerItem
import and5.finalproject.secondhand5.model.buyerproduct.GetBuyerOrderItem
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private var productRepository : ProductRepository) : ViewModel(){

    //buyer
    private var productLivedata = MutableLiveData<List<GetProductItem>>()
    private var detailProductLivedata = MutableLiveData<GetProductItem>()
    val detailProduct : LiveData<GetProductItem> = detailProductLivedata
    val product : LiveData<List<GetProductItem>> = productLivedata
    var responseCodeAddBuyerOrder : SingeLiveEvent<String> = SingeLiveEvent ()

    private var buyerDetailOrderLivedata = MutableLiveData<GetBuyerOrderItem>()
    val buyerDetailOrder : LiveData<GetBuyerOrderItem> = buyerDetailOrderLivedata

    private var buyerlOrderLivedata = MutableLiveData<List<GetBuyerOrderItem>>()
    val buyerOrder : LiveData<List<GetBuyerOrderItem>> = buyerlOrderLivedata

    var responseCodeUpdateBuyerOrder : SingeLiveEvent<String> = SingeLiveEvent ()


    //seller
    var responseCodeAddProduct : SingeLiveEvent<String>  = SingeLiveEvent ()
    var sellerProductLiveData = MutableLiveData<List<GetSellerProductItem>>()
    var sellerCategory = MutableLiveData<List<GetSellerCategoryItem>>()
    private var categoryLivedata = MutableLiveData<List<Category>>()
    val category : LiveData<List<Category>> = categoryLivedata


    val sellerProduct : LiveData<List<GetSellerProductItem>> = sellerProductLiveData

    var sellerOrderLiveData = MutableLiveData<List<GetSellerOrderItem>>()
    var detailOrderLiveData = MutableLiveData<GetSellerOrderItem>()
    var sellerOrder : LiveData<List<GetSellerOrderItem>> = sellerOrderLiveData
    var detailOrder : LiveData<GetSellerOrderItem> = detailOrderLiveData
    var sellerSoldOrderLiveData = MutableLiveData<List<GetSellerOrderItem>>()
    var sellerSoldOrder : LiveData<List<GetSellerOrderItem>> = sellerSoldOrderLiveData


    var responseCodePatchSellerOrder : SingeLiveEvent<String> = SingeLiveEvent ()
    var responseCodeDeleteProduct : SingeLiveEvent<String> = SingeLiveEvent ()

    var updateProductLiveData = MutableLiveData<List<GetProductItem>>()
    var sellerUpdateProduct : LiveData<List<GetProductItem>> = updateProductLiveData
    var responseCodeUpdateProduct : SingeLiveEvent<String>  = SingeLiveEvent ()

    var detailSellerProductLivedata = MutableLiveData<GetSellerProductItem>()
    var detailSellerProduct : LiveData<GetSellerProductItem> = detailSellerProductLivedata

    var sellerBannerLivedata = MutableLiveData<List<GetBannerItem>>()
    var sellerBanner : LiveData<List<GetBannerItem>> = sellerBannerLivedata

    var userToken : MutableLiveData<String> = MutableLiveData()


    //  Buyer

    fun updateBuyerOrder(access_token:String, id: Int, bid_price: Int){
        viewModelScope.launch {
            productRepository.updateBuyerOrder(access_token, id, bid_price, responseCodeUpdateBuyerOrder)
        }
    }

    fun getAllProduct(){
        viewModelScope.launch {
            val dataproduct = productRepository.getAllProduct()
            productLivedata.value = dataproduct
        }
    }

    fun getAllBuyerOrder(access_token : String){
        viewModelScope.launch {
            val databuyerorder = productRepository.getBuyerOrder(access_token)
            buyerlOrderLivedata.value = databuyerorder
        }
    }

    fun getBuyerDetailProduct(id:Int){
        viewModelScope.launch {
            val detailproduct = productRepository.getDetailProduct(id)
            detailProductLivedata.value = detailproduct
        }
    }

    fun getBuyerDetailOrder(access_token : String, id:Int){
        viewModelScope.launch {
            val buyerdetailOrder = productRepository.getBuyerDetailOrder(access_token, id)
            buyerDetailOrderLivedata.value = buyerdetailOrder
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

    fun getDetailOrder(access_token: String,id:Int){
        viewModelScope.launch {
            val detailorder = productRepository.getSellerDetailOrder(access_token, id)
            detailOrderLiveData.value = detailorder
        }
    }

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

    fun addSellerProduct(token: String, name: String, desc: String, price: String, category: String, location: String, image:  MultipartBody.Part){
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

    fun getSellerSuccesfulOrder(token:String){
        viewModelScope.launch{
            val dataSellerSoldOrder = productRepository.getSellerSuccesfulOrder(token)
            sellerSoldOrderLiveData.value = dataSellerSoldOrder
        }
    }

    fun getSellerBanner() {
        viewModelScope.launch {
            val dataSellerBanner = productRepository.getSellerBanner()
            sellerBannerLivedata.value = dataSellerBanner
        }
    }


}