package and5.finalproject.secondhand5.view.fragment.seller

import and5.finalproject.secondhand5.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_product2.*

class AddProduct : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
            Navigation.findNavController(requireView()).navigate(R.id.action_addProduct_to_home)
        }
    }

    private fun openImageChooser() {
        add_product_image.setOnClickListener{

        }
    }



    private fun goToPreview() {
        preview_btn.setOnClickListener(){
            val productName = add_product_name.text.toString()
            val productPrice = add_product_price.text.toString()
            val productCategory = add_product_category.text.toString()
            val productDesc = add_product_desc.text.toString()

            Navigation.findNavController(requireView()).navigate(R.id.action_addProduct_to_productPreview)
        }
    }

    private fun addProduct() {
        add_btn.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_home_to_myListProduct)
        }
    }



}