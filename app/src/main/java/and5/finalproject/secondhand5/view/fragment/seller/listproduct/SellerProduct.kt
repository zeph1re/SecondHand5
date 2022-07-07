package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.seller.UpdateProductBody
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.SellerProductAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_seller_detail_product.*
import kotlinx.android.synthetic.main.custom_seller_detail_product.view.*
import kotlinx.android.synthetic.main.custom_seller_detail_product.view.dropdown_category
import kotlinx.android.synthetic.main.fragment_add_product2.view.*
import kotlinx.android.synthetic.main.fragment_seller_product.*
import kotlinx.android.synthetic.main.home_product_adapter.view.*
import okhttp3.MultipartBody
import kotlin.properties.Delegates


class SellerProduct : Fragment() {
    lateinit var myListProductAdapter: SellerProductAdapter
    lateinit var userManager: UserManager
    lateinit var updateProductBody: UpdateProductBody
    lateinit var productname:String
    lateinit var productdescription:String
    var productprice by Delegates.notNull<Int>()
    lateinit var productlocation:String
    lateinit var arrayAdapter: ArrayAdapter<String>
    var categoryID = mutableListOf<Int>()
    var categoryName = mutableListOf<String>()
    var getName = mutableListOf<String>()
    private val selectedName: MutableList<String?> = mutableListOf()
    private var selectedID: MutableList<Int> = mutableListOf()
    lateinit var postCategory : String
    lateinit var productCategory :String
    lateinit var preventDoubleCall : String






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_seller_product, container, false)

        return view
    }
/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireActivity())
        preventDoubleCall = "true"
        myListProductAdapter= SellerProductAdapter{
            preventDoubleCall = "true"

            initDetailProductData(it.id)

        }
        var style = LinearLayoutManager(requireContext(),  LinearLayoutManager.VERTICAL, false)
        myListProduct_recyclerview.adapter = myListProductAdapter
        myListProduct_recyclerview.layoutManager = style

        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){
            getProduct(it)
        }

    }

    fun getProduct(token:String){
        val viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModel.getSellerProduct(token)
        viewModel.sellerProductLiveData.observe(viewLifecycleOwner){
            if(it != null){
                myListProductAdapter.setListProduct(it)
                myListProductAdapter.notifyDataSetChanged()

            }
        }
    }

    fun initDetailProductData(id:Int){
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){token->
            if(token!=null){
                viewModelProduct.getSellerDetailProduct(token, id)
                viewModelProduct.detailSellerProduct.observe(viewLifecycleOwner) {
                    productname = it.name
                    productdescription = it.desc
                    productprice = it.basePrice
                    productlocation = it.location



                    it.categories.forEach {
                        getName.remove(it.name)
                        getName.add(it.name)
                        selectedID.remove(it.id)
                        selectedID.add(it.id)
                        categoryName.remove(it.name)
                    }

                    val getID = selectedID.toString()
                    postCategory = getID.replace("[","").replace("]", "")
                    val listToString = getName.toString()
                    val getCategory = listToString.replace("[","").replace("]", "")
                    productCategory = getCategory


//                    Log.d("testes a", it.name)
                    if (preventDoubleCall == "true"){
                        detailProduct(
                            id,
                            productname,
                            productdescription,
                            productprice,
                            productlocation
                        )
                        preventDoubleCall = "false"
                    }


                }
            }
        }

    }

    fun detailProduct(id:Int, name:String, description:String, price:Int, location:String){

        val customDetailProductDialog = LayoutInflater.from(requireContext()).inflate(R.layout.custom_seller_detail_product, null, false)
        var ADBuilder = AlertDialog.Builder(requireContext())
            .setView(customDetailProductDialog)
            .create()
        ADBuilder.show()
        customDetailProductDialog.input_product_name.setText(productname.toString())
        customDetailProductDialog.input_product_description.setText(productdescription.toString())
//        customDetailProductDialog.input_product_location.hint = productlocation.toString()
        customDetailProductDialog.input_product_base_price.setText(productprice.toString())

        val checkCategory = customDetailProductDialog.dropdown_category.text.toString()
        if (checkCategory == ""){
            customDetailProductDialog.dropdown_category.setText(productCategory)
        }


        customDetailProductDialog.dropdown_category?.hint = "Select Category"
        getCategory()
        arrayAdapter = ArrayAdapter(requireActivity(), R.layout.adapter_pilih_kategory, categoryName)
        customDetailProductDialog.dropdown_category?.setAdapter(arrayAdapter)
        customDetailProductDialog.dropdown_category?.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        arrayAdapter.notifyDataSetChanged()
        customDetailProductDialog.dropdown_category?.setOnItemClickListener { adapterView, view, position, l ->
            val selectedValue: String? = arrayAdapter.getItem(position)
            selectedName.add(arrayAdapter.getItem(position))
            selectedID.add(categoryID[position])
            categoryName.remove(selectedValue)

            categoryID.remove(categoryID[position])
            val getID = selectedID.toString()
            postCategory = getID.replace("[","").replace("]", "")
        }


        customDetailProductDialog.reset_category_detail.setOnClickListener {
            customDetailProductDialog.dropdown_category.setText("")
            selectedID.clear()
            categoryName.clear()
            getCategory()
            arrayAdapter = ArrayAdapter(requireActivity(), R.layout.adapter_pilih_kategory, categoryName)
            customDetailProductDialog.dropdown_category?.setAdapter(arrayAdapter)
            customDetailProductDialog.dropdown_category?.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
            customDetailProductDialog.dropdown_category?.setOnItemClickListener { adapterView, view, position, l ->
                val selectedValue: String? = arrayAdapter.getItem(position)
                selectedName.add(arrayAdapter.getItem(position))
                selectedID.add(categoryID[position])
                categoryName.remove(selectedValue)
                categoryID.remove(categoryID[position])
                val getID = selectedID.toString()
                postCategory = getID.replace("[","").replace("]", "")
                Log.d("cateeee", postCategory)
                Log.d("asdd", selectedID.toString())
            }

        }
        customDetailProductDialog.btn_delete_product.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

            dialogBuilder.setMessage("Hapus Produk?")
                .setCancelable(false)
                .setPositiveButton("Ya") { dialogInterface: DialogInterface, i: Int ->
                    userManager.userToken.asLiveData().observe(viewLifecycleOwner) {
                        if (it != null) {
                            viewModelProduct.responseCodeDeleteProduct.observe(viewLifecycleOwner) {
                                if (it == "201") {
                                    Toast.makeText(
                                        requireContext(),
                                        "berhasil delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        }
                        viewModelProduct.deleteProduct(it, id)
                        ADBuilder.dismiss()

                        findNavController().navigate(R.id.myListProduct)


                    }

                }

                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    ADBuilder.dismiss()
                })
            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Hapus")
            // show alert dialog
            alert.show()

        }

        customDetailProductDialog.btn_update_product.setOnClickListener {
            val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
            val viewModelLogin = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)




            viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner){token->
                getProduct(token)
                if(it!=null) {

                    var newName: String
                    var newDesc: String
                    var newLocation: String
                    var newPrice: Int

                    if (customDetailProductDialog.input_product_name.text.isNotEmpty()) {
                        newName = customDetailProductDialog.input_product_name.text.toString()
                    } else {
                        newName = name
                    }

                    if (customDetailProductDialog.input_product_description.text.isNotEmpty()) {
                        newDesc =
                            customDetailProductDialog.input_product_description.text.toString()
                    } else {
                        newDesc = description
                    }
//
//                    if (customDetailProductDialog.input_product_location.text.isNotEmpty()){
//                        newLocation = customDetailProductDialog.input_product_location.text.toString()
//                    }else{
//                        newLocation = location
//                    }

                    if (customDetailProductDialog.input_product_base_price.text.isNotEmpty()) {
                        newPrice =
                            customDetailProductDialog.input_product_base_price.text.toString()
                                .toInt()
                    } else {
                        newPrice = price
                    }


                    val userViewModel =
                        ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
                    userViewModel.getUserData.observe(viewLifecycleOwner) {user->


                        viewModelProduct.responseCodeDeleteProduct.observe(viewLifecycleOwner) {
                            if (it == "200") {
                                Toast.makeText(
                                    requireContext(),
                                    "berhasil update",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }

                        updateProductBody = UpdateProductBody(newPrice, newDesc, user.city, newName)
                        viewModelProduct.updateSellerProduct(
                            token,
                            id,
                            newName,
                            newDesc,
                            newPrice,
                            postCategory,
                            user.city
                        )
                    }

                    findNavController().navigate(R.id.myListProduct)
                }

            }
            ADBuilder.dismiss()


        }




    }
    fun getCategory(){
        val viewModelProduct = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        viewModelProduct.sellerCategory.observe(viewLifecycleOwner) {
            it.forEach {
                categoryName.add(it.name)
                categoryID.add(it.id)
            }
        }
        viewModelProduct.getSellerCategory()
    }

}