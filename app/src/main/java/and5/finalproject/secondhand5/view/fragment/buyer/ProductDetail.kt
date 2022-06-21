package and5.finalproject.secondhand5.view.fragment.buyer

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_product_detail.*


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

        product_name.setText("tes")
        product_price.setText("tes")

        val dataId = arguments?.getInt("id")
//        val data = arguments?.getParcelable<GetProductItem>("data") as GetProductItem
        var id = dataId

        Toast.makeText( requireContext(), "$id" , Toast.LENGTH_SHORT).show()
//        Log.d("testes 1 id ", id.toString())

        init(id!!.toInt())

        buyProduct(id)

    }

    fun init(id:Int){
//        Log.d("testes 2 id ", id.toString())

        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewmodelproduct.getDetailProduct(id)

        viewmodelproduct.detailProduct.observe(viewLifecycleOwner,{
//            Log.d("testes 3 id ", id.toString())

            product_name.setText(it.name)
            product_price.setText(it.basePrice.toString())
            Glide.with(requireContext()).load(it.imageUrl).into(product_image)
//            Log.d("testes 4 id ", id.toString())

        })

    }

    fun buyProduct(id: Int) {
        buy_btn.setOnClickListener{

        }
    }

}