package com.example.practicallogbinary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicallogbinary.adapter.OrdersAdapter
import com.example.practicallogbinary.database.AppDatabase
import com.example.practicallogbinary.databinding.ActivityMainBinding
import com.example.practicallogbinary.interfaces.OrderAdapterInterface
import com.example.practicallogbinary.models.FoodOrder
import com.example.practicallogbinary.utils.AppUtils
import com.example.practicallogbinary.utils.ExtFuncs.logD
import com.example.practicallogbinary.utils.ExtFuncs.notifyUser
import com.example.practicallogbinary.viewmodel.OrdersViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OrderAdapterInterface {

    //for binding views
    private lateinit var binding: ActivityMainBinding

    //for view model
    private lateinit var ordersViewModel: OrdersViewModel

    //for adapter
    private lateinit var ordersAdapter: OrdersAdapter
    private var listOfFoodOrders: ArrayList<FoodOrder> = ArrayList()

    //for db
    private lateinit var db: AppDatabase

    //for no internet include layout
    private lateinit var dataNotFoundLayout: View
    private lateinit var mtxtDescription: MaterialTextView

    //to check which datasource is selected
    private var isFromApiDataSource: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dataNotFoundLayout = view.findViewById(R.id.dataNotFoundLayout)
        mtxtDescription = view.findViewById(R.id.mtxtDescription)

        initializeDataObjects()
        initializeClickListeners()
    }

    private fun initializeDataObjects() {
        ordersViewModel =
            ViewModelProvider(this).get(OrdersViewModel::class.java)

        db = AppDatabase.getDatabase(application)

        inflateListOfOrdersFromApi()
    }

    private fun initializeClickListeners() {
        binding.apply {
            fabDelete.setOnClickListener {

            }

            mcrdFromApi.setOnClickListener {
                isFromApiDataSource = true
                hideDataNotFound()
                inflateListOfOrdersFromApi()
            }

            mcrdFromDB.setOnClickListener {
                isFromApiDataSource = false
                hideDataNotFound()
                inflateListOfOrdersFromDB()
            }

            fabDelete.setOnClickListener {
                deleteEntriesFromDB()
            }
        }
    }

    //from api
    private fun inflateListOfOrdersFromApi() {
        showProgressDialog()
        if (AppUtils.hasInternetConnection(this)) {
            ordersViewModel.getOrdersList(1, 1, 1)?.observe(this) {
                if (it.success) {
                    hideProgressDialog()
                    if (it.listOfFoodOrders.isNullOrEmpty()) {
                        showDataNotFound()
                    } else {
                        hideDataNotFound()
                        listOfFoodOrders = it.listOfFoodOrders
                        ordersAdapter = OrdersAdapter(listOfFoodOrders, this)
                        val layoutManager = LinearLayoutManager(
                            this, RecyclerView.VERTICAL, false
                        )
                        binding.rcycListOfOrders.adapter = ordersAdapter
                        binding.rcycListOfOrders.layoutManager = layoutManager
                    }
                } else {
                    hideProgressDialog()
                    notifyUser(it.message)
                }
            }
        } else {
            hideProgressDialog()
            notifyUser("Please check your network connection")
        }
    }

    //from db
    private fun inflateListOfOrdersFromDB() {
        listOfFoodOrders.clear()
        binding.rcycListOfOrders.adapter?.notifyDataSetChanged()
        if (this::db.isInitialized) {
            ordersViewModel.getOrdersListFromDB(db)?.observe(this) {
                if (it.success) {
                    if (it.listOfFoodOrders.isNullOrEmpty()) {
                        showDataNotFound()
                    } else {
                        hideDataNotFound()
                        listOfFoodOrders = it.listOfFoodOrders
                        ordersAdapter = OrdersAdapter(listOfFoodOrders, this)
                        val layoutManager = LinearLayoutManager(
                            this, RecyclerView.VERTICAL, false
                        )
                        binding.rcycListOfOrders.adapter = ordersAdapter
                        binding.rcycListOfOrders.layoutManager = layoutManager
                    }
                } else {
                    notifyUser(it.message)
                }
            }
        } else {
            notifyUser("Error in database")
        }
    }

    override fun onItemClicked(foodOrder: FoodOrder) {
        ordersViewModel.addOrderInDB(db, foodOrder)?.observe(this) {
            notifyUser("$it")
        }
    }

    //to delete entries from db
    private fun deleteEntriesFromDB() {
        ordersViewModel.deleteOrdersListFromDB(db)?.observe(this) {
            notifyUser("$it")
            listOfFoodOrders.clear()
            binding.rcycListOfOrders.adapter?.notifyDataSetChanged()
            if (isFromApiDataSource){
                inflateListOfOrdersFromApi()
            }
            else{
                inflateListOfOrdersFromDB()
            }
        }
    }

    //to show loader
    private fun showProgressDialog() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.cnstMainParent.visibility = View.GONE
            binding.nestedShimmerView.visibility = View.VISIBLE
            binding.shimmerViewContainer.startShimmer()
            binding.fabDelete.visibility = View.GONE
        }
    }

    //to hide loader
    private fun hideProgressDialog() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.cnstMainParent.visibility = View.VISIBLE
            binding.nestedShimmerView.visibility = View.GONE
            binding.shimmerViewContainer.stopShimmer()
            binding.fabDelete.visibility = View.VISIBLE
        }
    }

    //show data not found layout
    private fun showDataNotFound() {
        dataNotFoundLayout.visibility = View.VISIBLE
    }

    //hide data not found layout
    private fun hideDataNotFound() {
        dataNotFoundLayout.visibility = View.GONE
    }
}