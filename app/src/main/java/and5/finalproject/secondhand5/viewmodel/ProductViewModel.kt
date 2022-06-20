package and5.finalproject.secondhand5.viewmodel

import and5.finalproject.secondhand5.model.getProductItem
import and5.finalproject.secondhand5.network.ApiService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(api : ApiService) : ViewModel(){

    private var productLivedata = MutableLiveData<List<getProductItem>>()
    val product : LiveData<List<getProductItem>> = productLivedata

    init{
        viewModelScope.launch {
            val dataproduct = api.getAllProduct()
            delay(
                2000
            )
            productLivedata.value = dataproduct
        }
    }
}