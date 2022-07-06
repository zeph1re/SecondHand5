package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.view.adapter.CategoriesAdapter
import and5.finalproject.secondhand5.view.adapter.ProductAdapter
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*


class Home : Fragment() {

    lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productAdapter = ProductAdapter {
//            val data = bundleOf("data" to it)
            val data = Bundle()
            data.putInt("product_id", it.id)

            Log.d("testes id", it.id.toString())
//            Log.d("testes imageName", it.imageName.toString())
//            Log.d("testes basePrice", it.basePrice.toString())
//            Log.d("testes imageUrl", it.imageUrl.toString())
//            Log.d("testes name", it.name.toString())
//            Log.d("testes location", it.location.toString())
//            Log.d("testes userId", it.userId.toString())
//            Log.d("testes createdAt", it.createdAt.toString())
//            Log.d("testes updatedAt", it.updatedAt.toString())
//            Log.d("testes categories", it.categories.toString())
            view.findNavController().navigate(R.id.action_home_to_productDetail, data)
        }
        initCategory()
        initProduct()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initProduct(){
        val productAdapter = productAdapter

        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewmodelproduct.product.observe(viewLifecycleOwner) {
            if (it != null) {

                rv_list_item.layoutManager =
                    LinearLayoutManager(requireActivity(),   RecyclerView.HORIZONTAL, false)

//                rv_list_item.layoutManager =
//                    GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
                rv_list_item.adapter = productAdapter

                productAdapter.setProductList(it)
                productAdapter.notifyDataSetChanged()

            }
        }
        viewmodelproduct.getAllProduct()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initCategory(){
        val categoriesAdapter = CategoriesAdapter()

        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewmodelproduct.sellerCategory.observe(viewLifecycleOwner) {
            if (it != null) {
                rv_list_category.layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                rv_list_category.adapter = categoriesAdapter

                categoriesAdapter.setProductList(it)
                categoriesAdapter.notifyDataSetChanged()

            }
        }
        viewmodelproduct.getSellerCategory()
    }
}