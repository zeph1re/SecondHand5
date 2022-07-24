package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.banner.GetBannerItem
import and5.finalproject.secondhand5.model.buyerproduct.GetBuyerOrderItem
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.seller.Category
import and5.finalproject.secondhand5.model.seller.GetSellerCategoryItem
import and5.finalproject.secondhand5.model.seller.GetSellerOrderItem
import and5.finalproject.secondhand5.model.seller.GetSellerProductItem
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
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private var productRepository : ProductRepository) : ViewModel(){

    //buyer
    private var productLivedata = MutableLiveData<List<GetProductItem>>()
    val product : LiveData<List<GetProductItem>> = productLivedata

    private var detailProductLivedata = SingeLiveEvent<GetProductItem>()
    val detailProduct : LiveData<GetProductItem> = detailProductLivedata

    private var detailProductLivedata2 = MutableLiveData<GetProductItem>()
    val detailProduct2 : LiveData<GetProductItem> = detailProductLivedata2

//    private var detailProductLivedata3Wish = SingeLiveEvent<GetProductItem>()
    val detailProduct3 : SingeLiveEvent<GetProductItem> =  SingeLiveEvent()

    var responseCodeAddBuyerOrder : SingeLiveEvent<String> = SingeLiveEvent ()

    private var buyerlOrderLivedata = MutableLiveData<List<GetBuyerOrderItem>>()
    val buyerOrder : LiveData<List<GetBuyerOrderItem>> = buyerlOrderLivedata

    var responseCodeUpdateBuyerOrder : SingeLiveEvent<String> = SingeLiveEvent ()


    //seller
    var responseCodeAddProduct : SingeLiveEvent<String>  = SingeLiveEvent ()
    var sellerProductLiveData = MutableLiveData<List<GetSellerProductItem>>()
    var sellerCategory = MutableLiveData<List<GetSellerCategoryItem>>()
    private var categoryLivedata = MutableLiveData<List<Category>>()
    val category : LiveData<List<Category>> = categoryLivedata


    var sellerOrderLiveData = MutableLiveData<List<GetSellerOrderItem>>()
    private var detailOrderLiveData = MutableLiveData<GetSellerOrderItem>()
    var detailOrder : LiveData<GetSellerOrderItem> = detailOrderLiveData
    var sellerSoldOrderLiveData = MutableLiveData<List<GetSellerOrderItem>>()


    private var responseCodePatchSellerOrder : SingeLiveEvent<String> = SingeLiveEvent ()
    var responseCodeDeleteProduct : SingeLiveEvent<String> = SingeLiveEvent ()

    private var responseCodeUpdateProduct : SingeLiveEvent<String>  = SingeLiveEvent ()

    private var detailSellerProductLivedata = MutableLiveData<GetSellerProductItem>()
    var detailSellerProduct : LiveData<GetSellerProductItem> = detailSellerProductLivedata

    private var sellerBannerLivedata = MutableLiveData<List<GetBannerItem>>()
    var sellerBanner : LiveData<List<GetBannerItem>> = sellerBannerLivedata

    var userToken : MutableLiveData<String> = MutableLiveData()


    //  Buyer

    fun updateBuyerOrder(access_token:String, id: Int, bid_price: Int){
        viewModelScope.launch {
            productRepository.updateBuyerOrder(access_token, id, bid_price, responseCodeUpdateBuyerOrder)
        }
    }

    fun getAllProduct(searchQuery : String){
        viewModelScope.launch {
            val dataproduct = productRepository.getAllProduct(searchQuery)
            productLivedata.value = dataproduct

        }
    }

    fun getProductbyCategories(category_id: Int, searchQuery : String){
        viewModelScope.launch {
            val dataproduct = productRepository.getProductbyCategories(category_id, searchQuery)
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
            detailProductLivedata2.value = detailproduct
            detailProduct3.value = detailproduct
        }
    }

//    fun clearBuyerDetailProductLiveData(){
//        viewModelScope.launch {
//            detailProductLivedata.postValue(null)
//        }
//    }


    fun postBuyerOrder(access_token:String, id: Int, bid_price:Int){
        viewModelScope.launch {
            val postProduct = productRepository.addBuyerOrder(access_token, id, bid_price, responseCodeAddBuyerOrder)
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

            val partName = name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partDesc= desc.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partHarga= price.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partCategory = category.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partLocation = location.toRequestBody("multipart/form-data".toMediaTypeOrNull())

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
            val partName = name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partDesc= desc.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partHarga= price.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partCategory = category.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val partLocation = location.toRequestBody("multipart/form-data".toMediaTypeOrNull())

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