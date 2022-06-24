package and5.finalproject.secondhand5.view.fragment.seller

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_product2.*

class AddProduct : Fragment() {

    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userManager = UserManager(requireActivity())
        return inflater.inflate(R.layout.fragment_add_product2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToPreview()
        addProduct()
        openImageChooser()
        backToHome()
    }

    private fun backToHome() {
        btn_backtohome.setOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun openImageChooser() {
        add_product_image.setOnClickListener{
        }
    }



    private fun goToPreview() {
        preview_btn.setOnClickListener(){
            val productName = add_product_name.text.toString()
            val productPrice = add_product_price.text.toString().toInt()
            val productDesc = add_product_desc.text.toString()

            val bundle = Bundle()
            bundle.putString("PRODUCT_DATA", productName)
            bundle.putInt("PRODUCT_DATA", productPrice)
            bundle.putString("PRODUCT_DATA", productDesc)
            Navigation.findNavController(requireView()).navigate(R.id.action_addProduct_to_productPreview, bundle)
        }
    }

    private fun addProduct() {
        add_btn.setOnClickListener {
            val productName = add_product_name.text.toString()
            val productPrice = add_product_price.text.toString().toInt()
            val productDesc = add_product_desc.text.toString()

            val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
            userManager.userToken.asLiveData().observe(viewLifecycleOwner){
                viewModelProduct.addSellerProduct(it,productName,productPrice,productDesc)
//                Navigation.findNavController(requireView()).navigate(R.id.action_home_to_myListProduct)
            }


        }
    }



}