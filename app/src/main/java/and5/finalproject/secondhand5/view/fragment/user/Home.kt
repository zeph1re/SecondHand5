package and5.finalproject.secondhand5.view.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.Room.Adapter.AdapterHome
import and5.finalproject.secondhand5.Room.Model.GetProductHome
import and5.finalproject.secondhand5.Room.OfflineDB
import and5.finalproject.secondhand5.Room.OfflineModeDao
import and5.finalproject.secondhand5.connectivity.CheckConnectivity
import and5.finalproject.secondhand5.model.buyerproduct.GetProductItem
import and5.finalproject.secondhand5.view.adapter.BannerAdapter
import and5.finalproject.secondhand5.view.adapter.CategoriesAdapter
import and5.finalproject.secondhand5.view.adapter.ProductAdapter
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Home : Fragment() {

    var connectivity: CheckConnectivity = CheckConnectivity()

    lateinit var productAdapter: ProductAdapter
    lateinit var offlineHomeAdapter: AdapterHome
    lateinit var bannerAdapter: BannerAdapter
    var searchQuery = ""
    var idQuery = 0
    var db: OfflineDB? = null
    lateinit var offlineCategory: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db =OfflineDB.getInstance(requireContext())

        if (connectivity.isOnline(requireContext())){
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
            initBanner()
            initCategory()

            if (idQuery == 0){
                initProduct()
                searchFilter()
            }else {
                categoryFilter()
            }

        }else{
            GlobalScope.launch {
                val listdata = db?.offlineDao()?.getDataOfflineProductHome()

                requireActivity().runOnUiThread {
                    listdata.let {
                        if (listdata?.size == 0) {

                        }
                        offlineHomeAdapter= AdapterHome(it!!)
                        Log.d("xccc123", it.toString())
                        rv_list_item.adapter =  offlineHomeAdapter
                        rv_list_item.layoutManager =
                            GridLayoutManager(requireActivity(), 3, GridLayoutManager.HORIZONTAL, false)
                    }
                }
            }

        }

    }

    fun categoryFilter(){
        initProductbyCategories()
    }

    fun searchFilter(){
        et_primary_search.setOnClickListener {
            searchQuery = et_primary_search.text.toString()
            initProduct()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initProductbyCategories(){
        val productAdapter = productAdapter

        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewmodelproduct.product.observe(viewLifecycleOwner) {
            if (it != null) {


//                rv_list_item.layoutManager =
//                    LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)

                rv_list_item.layoutManager =
                    GridLayoutManager(requireActivity(), 3, GridLayoutManager.HORIZONTAL, false)
                rv_list_item.adapter = productAdapter

                productAdapter.setProductList(it)
                productAdapter.notifyDataSetChanged()
//                productAdapter.setHasStableIds(true)

            }
        }
        viewmodelproduct.getProductbyCategories(idQuery, searchQuery)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initProduct(){
        val productAdapter = productAdapter

        val viewmodelproduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewmodelproduct.product.observe(viewLifecycleOwner) {
            if (it != null) {

                it.forEach {
                    it.categories.forEach {
                        offlineCategory = it.name
                    }
                    GlobalScope.launch {
                        db?.offlineDao()?.clearData()
                        Handler(Looper.getMainLooper()).postDelayed({
                            GlobalScope.launch {
                            db?.offlineDao()?.addHomeOffline(GetProductHome(it.basePrice, offlineCategory, it.createdAt, it.description, it.id, it.imageName, it.imageUrl, it.location, it.name, it.status, it.updatedAt))
                        } },5000)

                    }

                    val a = it.categories
                    Log.d("qwe123", a.toString())
                    it.categories.forEach {
                        it.name
                    }


                }

//                rv_list_item.layoutManager =
//                    LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)





                rv_list_item.layoutManager =
                    GridLayoutManager(requireActivity(), 3, GridLayoutManager.HORIZONTAL, false)
                rv_list_item.adapter = productAdapter

                productAdapter.setProductList(it)
                productAdapter.notifyDataSetChanged()
//                productAdapter.setHasStableIds(true)

            }
        }
        viewmodelproduct.getAllProduct(searchQuery)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initCategory(){
        val categoriesAdapter = CategoriesAdapter(){
            idQuery = it.id
            categoryFilter()
        }

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

    fun initBanner() {
        bannerAdapter = BannerAdapter()

        val viewmodelbanner = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewmodelbanner.sellerBanner.observe(viewLifecycleOwner) {
            if (it != null) {
                banner_rv.layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                banner_rv.adapter = bannerAdapter
                bannerAdapter.setBannerList(it)
                bannerAdapter.notifyDataSetChanged()
            }
        }
        viewmodelbanner.getSellerBanner()
    }
}