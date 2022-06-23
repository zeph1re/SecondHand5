package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.model.seller.GetSellerProductItem
import and5.finalproject.secondhand5.repository.ProductRepository
import and5.finalproject.secondhand5.singleliveevent.SingeLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private var productRepository : ProductRepository) : ViewModel(){

    private var productLivedata = MutableLiveData<List<GetProductItem>>()
    private var detailProductLivedata = MutableLiveData<GetProductItem>()
    private var sellerProductLiveData = MutableLiveData<List<GetSellerProductItem>>()
    var addProductLiveData : SingeLiveEvent<String> = SingeLiveEvent ()
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

    fun addSellerProduct(
        access_token:String,
        product_name: String,
        product_price: Int,
        product_desc: String
    ){
      viewModelScope.launch {
          productRepository.addProductSeller(access_token,product_name,product_price,product_desc,addProductLiveData)
      }
    }

}