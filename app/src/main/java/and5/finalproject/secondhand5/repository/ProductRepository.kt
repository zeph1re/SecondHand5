package and5.finalproject.secondhand5.repository

import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.network.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi : ApiService) {

    suspend fun getAllProduct(): List<GetProductItem>{
        return productApi.getAllProduct()
    }

    suspend fun getDetailProduct(id:Int): GetProductItem {
        return productApi.getDetailProduct(id)

    }
}