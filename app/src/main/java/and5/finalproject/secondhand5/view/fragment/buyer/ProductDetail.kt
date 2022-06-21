package and5.finalproject.secondhand5.view.fragment.buyer

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Logger.global


class ProductDetail : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getInt("id")

        var id = data
        Toast.makeText( requireContext(), "$id" , Toast.LENGTH_SHORT).show()
        Log.d("testes 2 id ", id.toString())


            init(id!!.toInt())



    }

    fun init(id:Int){
        Log.d("testes 2 id ", id.toString())

        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewmodelproduct.getDetailProduct(id)

        viewmodelproduct.detailProduct.observe(viewLifecycleOwner) {
            product_name.text = it.name
            product_price.text = it.basePrice.toString()
            Glide.with(requireContext()).load(it.imageUrl).into(product_image)
            Log.d("testes 2 id ", id.toString())
        }



    }


}