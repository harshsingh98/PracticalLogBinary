package com.example.practicallogbinary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicallogbinary.adapter.OrdersAdapter
import com.example.practicallogbinary.databinding.ActivityMainBinding
import com.example.practicallogbinary.interfaces.OrderAdapterInterface
import com.example.practicallogbinary.utils.AppUtils
import com.example.practicallogbinary.utils.ExtFuncs.notifyUser
import com.example.practicallogbinary.viewmodel.OrdersViewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OrderAdapterInterface {

    //for binding views
    private lateinit var binding: ActivityMainBinding

    //for view model
    private lateinit var ordersViewModel: OrdersViewModel

    //for adapter
    private lateinit var ordersAdapter: OrdersAdapter
    private var listOfOrders: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initializeDataObjects()
        initializeClickListeners()
    }

    private fun initializeDataObjects(){
        ordersViewModel =
            ViewModelProvider(this).get(OrdersViewModel::class.java)

        inflateListOfOrders()
    }

    private fun initializeClickListeners(){
        binding.apply {
            fabDelete.setOnClickListener{

            }
        }
    }

    private fun inflateListOfOrders(){
        if (AppUtils.hasInternetConnection(this)) {
            ordersViewModel.getOrdersList(1, 1, 1)?.observe(this) {
                if (it.success) {
                    /*ordersAdapter = OrdersAdapter(listOfOrders, this)
                    val layoutManager = LinearLayoutManager(
                        this, RecyclerView.VERTICAL, false)
                    binding.rcycListOfOrders.adapter = ordersAdapter
                    binding.rcycListOfOrders.layoutManager = layoutManager*/
                } else {
                    notifyUser(it.message)
                }
            }
        } else {
            notifyUser("Please check your network connection")
        }
    }

    override fun onItemClicked(value: String) {

    }
}