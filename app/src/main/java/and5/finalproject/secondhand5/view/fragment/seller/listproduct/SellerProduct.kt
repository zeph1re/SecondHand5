@file:Suppress("CascadeIf")

package and5.finalproject.secondhand5.view.fragment.seller.listproduct

import and5.finalproject.secondhand5.R
import and5.finalproject.secondhand5.datastore.UserManager
import and5.finalproject.secondhand5.model.seller.UpdateProductBody
import and5.finalproject.secondhand5.view.fragment.seller.listproduct.adapter.SellerProductAdapter
import and5.finalproject.secondhand5.viewmodel.LoginViewModel
import and5.finalproject.secondhand5.viewmodel.ProductViewModel
import and5.finalproject.secondhand5.viewmodel.UserViewModel
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_seller_detail_product.view.*
import kotlinx.android.synthetic.main.fragment_seller_product.*
import kotlin.properties.Delegates


class SellerProduct : Fragment() {
    private lateinit var myListProductAdapter: SellerProductAdapter
    lateinit var userManager: UserManager
    private lateinit var updateProductBody: UpdateProductBody
    lateinit var productname: String
    private lateinit var productdescription: String
    private var productprice by Delegates.notNull<Int>()
    private lateinit var productlocation: String
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var categoryID = mutableListOf<Int>()
    private var categoryName = mutableListOf<String>()
    private var getName = mutableListOf<String>()
    private val selectedName: MutableList<String?> = mutableListOf()
    private var selectedID: MutableList<Int> = mutableListOf()
    private lateinit var postCategory: String
    private lateinit var productCategory: String
    private lateinit var preventDoubleCall: String

    private lateinit var newName: String
    private lateinit var newDesc: String

    private var newPrice: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_seller_product, container, false)
    }

    //
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireActivity())
        preventDoubleCall = "true"

        myListProductAdapter = SellerProductAdapter { product ->
            initDetailProductData(product.id)
        }

        val style = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        myListProduct_recyclerview.adapter = myListProductAdapter
        myListProduct_recyclerview.layoutManager = style

        val viewModelLogin = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner) {
            getProduct(it)
        }

        cardview_plus.setOnClickListener {
            findNavController().navigate(R.id.action_myListProduct_to_addProduct)
        }

    }

    private fun getProduct(token: String) {
        val viewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        viewModel.getSellerProduct(token)
        viewModel.sellerProductLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                myListProductAdapter.setListProduct(it)
                myListProductAdapter.notifyDataSetChanged()

                if(myListProductAdapter.itemCount == 0){
                    cardview_plus.visibility = View.VISIBLE
                }else{
                    cardview_plus.visibility = View.GONE
                }

            }
        }
    }

    private fun initDetailProductData(id: Int) {
        preventDoubleCall = "true"
        val customDetailProductDialog = LayoutInflater.from(requireContext())
            .inflate(R.layout.custom_seller_detail_product, null, false)
        getCategory()
        val aDBuilder = AlertDialog.Builder(requireContext())
            .setView(customDetailProductDialog)
            .create()
        val viewModelProduct = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        val viewModelLogin = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewModelLogin.userToken(requireActivity()).observe(viewLifecycleOwner) { token ->
            if (token != null) {
                viewModelProduct.getSellerDetailProduct(token, id)
                viewModelProduct.detailSellerProduct.observe(viewLifecycleOwner) { it ->
                    productname = it.name
                    productdescription = it.desc
                    productprice = it.basePrice
                    productlocation = it.location


                    getName.clear()
                    selectedID.clear()

                    it.categories.forEach {
                        getName.add(it.name)
                        selectedID.add(it.id)

                        for (i in categoryName.indices) {
                            val nameCategory = it.name
                            val idCategory = it.id
                            categoryName.remove(nameCategory)
                            categoryID.remove(idCategory)


                        }

                    }

                    val getID = selectedID.toString()
                    postCategory = getID.replace("[", "").replace("]", "")
                    val listToString = getName.toString()
                    val getCategory = listToString.replace("[", "").replace("]", "")
                    productCategory = getCategory


                    customDetailProductDialog.input_product_name.setText(it.name)
                    customDetailProductDialog.input_product_description.setText(it.desc)
                    customDetailProductDialog.input_product_base_price.setText(it.basePrice.toString())


                    customDetailProductDialog.dropdown_category.setText(getCategory)


                    customDetailProductDialog.dropdown_category?.hint = "Select Category"

                    arrayAdapter = ArrayAdapter(
                        requireActivity(),
                        R.layout.adapter_pilih_kategory,
                        categoryName
                    )
                    customDetailProductDialog.dropdown_category?.setAdapter(arrayAdapter)
                    customDetailProductDialog.dropdown_category?.setTokenizer(
                        MultiAutoCompleteTextView.CommaTokenizer()
                    )
                    arrayAdapter.notifyDataSetChanged()
                    customDetailProductDialog.dropdown_category?.setOnItemClickListener { adapterView, view, position, l ->
                        val selectedValue: String? = arrayAdapter.getItem(position)
                        selectedName.add(arrayAdapter.getItem(position))
                        selectedID.add(categoryID[position])
                        categoryName.remove(selectedValue)
                        Log.d("selected", selectedID.toString())
                        categoryID.remove(categoryID[position])
                        val getID = selectedID.toString()
                        postCategory = getID.replace("[", "").replace("]", "")
                    }


                    customDetailProductDialog.reset_category_detail.setOnClickListener {
                        customDetailProductDialog.dropdown_category.setText("")
                        selectedID.clear()
                        categoryName.clear()
                        categoryID.clear()
                        getCategory()
                        arrayAdapter = ArrayAdapter(
                            requireActivity(),
                            R.layout.adapter_pilih_kategory,
                            categoryName
                        )
                        customDetailProductDialog.dropdown_category?.setAdapter(arrayAdapter)
                        customDetailProductDialog.dropdown_category?.setTokenizer(
                            MultiAutoCompleteTextView.CommaTokenizer()
                        )
                        customDetailProductDialog.dropdown_category?.setOnItemClickListener { adapterView, view, position, l ->
                            val selectedValue: String? = arrayAdapter.getItem(position)
                            selectedName.add(arrayAdapter.getItem(position))
                            selectedID.add(categoryID[position])
                            categoryName.remove(selectedValue)
                            categoryID.remove(categoryID[position])

                            val getID = selectedID.toString()
                            postCategory = getID.replace("[", "").replace("]", "")
                            Log.d("cateeee", postCategory)
                            Log.d("asdd", selectedID.toString())
                        }

                    }
                    customDetailProductDialog.btn_delete_product.setOnClickListener {
                        val dialogBuilder = AlertDialog.Builder(requireActivity())
                        val viewModelProduct =
                            ViewModelProvider(requireActivity())[ProductViewModel::class.java]

                        dialogBuilder.setMessage("Hapus Produk?")
                            .setCancelable(false)
                            .setPositiveButton("Ya") { dialogInterface: DialogInterface, i: Int ->
                                userManager.userToken.asLiveData()
                                    .observe(viewLifecycleOwner) { it ->
                                        if (it != null) {
                                            viewModelProduct.responseCodeDeleteProduct.observe(
                                                viewLifecycleOwner
                                            ) {
                                                when (it) {
                                                    "201" -> {
                                                        Toast.makeText(
                                                            requireContext(),
                                                            "berhasil delete",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        var flagDelete = true
                                                    }
                                                    "400" -> {
                                                        Toast.makeText(
                                                            requireContext(),
                                                            "Gagal Menghapus, Product Sedang Ada Yang Order",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                    else -> {
                                                        Toast.makeText(
                                                            requireContext(),
                                                            "Internal Service Error",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            }
                                        }
                                viewModelProduct.deleteProduct(it, id)
                                aDBuilder.dismiss()

                                findNavController().navigate(R.id.myListProduct)


                            }

                    }

                        .setNegativeButton("Tidak") { dialogInterface: DialogInterface, i: Int ->
                            aDBuilder.dismiss()
                        }
                    // create dialog box
                    val alert = dialogBuilder.create()
                    // set title for alert dialog box
                    alert.setTitle("Hapus")
                    // show alert dialog
                    alert.show()

                }

                customDetailProductDialog.btn_update_product.setOnClickListener {
                    val viewModelProduct =
                        ViewModelProvider(requireActivity())[ProductViewModel::class.java]
                    val viewModelLogin =
                        ViewModelProvider(requireActivity())[LoginViewModel::class.java]




                    viewModelLogin.userToken(requireActivity())
                        .observe(viewLifecycleOwner) { token ->
                            getProduct(token)
                            if (it != null) {


                                if (customDetailProductDialog.input_product_name.text.isNotEmpty()) {
                                    newName =
                                        customDetailProductDialog.input_product_name.text.toString()
                                }

                                if (customDetailProductDialog.input_product_description.text.isNotEmpty()) {
                                    newDesc =
                                        customDetailProductDialog.input_product_description.text.toString()
                                }


                                if (customDetailProductDialog.input_product_base_price.text.isNotEmpty()) {
                                    newPrice =
                                        customDetailProductDialog.input_product_base_price.text.toString()
                                            .toInt()
                                }


                                val userViewModel =
                                    ViewModelProvider(requireActivity())[UserViewModel::class.java]
                                userViewModel.getUserData.observe(viewLifecycleOwner) { user ->


                                    viewModelProduct.responseCodeDeleteProduct.observe(
                                        viewLifecycleOwner
                                    ) {
                                        if (it == "200") {
                                            Toast.makeText(
                                                requireContext(),
                                                "berhasil update",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }

                                    updateProductBody =
                                        UpdateProductBody(newPrice, newDesc, user.city, newName)
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


                            }

                        }
                    aDBuilder.dismiss()
                    findNavController().navigate(R.id.myListProduct)
                }
            }
        }
    }
    aDBuilder.show()

}


private fun getCategory() {
    val viewModelProduct = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
    viewModelProduct.sellerCategory.observe(viewLifecycleOwner) { it ->
        categoryName.clear()
        categoryID.clear()
        val sorted = it.sortedBy { it.name }
        sorted.forEach {
            categoryName.add(it.name)
            categoryID.add(it.id)

        }
    }
    viewModelProduct.getSellerCategory()
}

}