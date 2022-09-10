package com.example.practicallogbinary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicallogbinary.R
import com.example.practicallogbinary.databinding.ItemOrdersLayoutBinding
import com.example.practicallogbinary.interfaces.OrderAdapterInterface
import com.example.practicallogbinary.models.FoodOrder
import com.example.practicallogbinary.utils.AppUtils
import com.example.practicallogbinary.utils.ExtFuncs.checkNonPrimeOrPrime
import com.example.practicallogbinary.utils.ExtFuncs.logD

class OrdersAdapter(
    private val listOfOrders: List<FoodOrder>,
    private val mOnItemClickListener: OrderAdapterInterface
) :
    RecyclerView.Adapter<OrdersAdapter.OrdersItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersItemViewHolder {

        val binding = ItemOrdersLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrdersItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersItemViewHolder, position: Int) {
        val foodOrder: FoodOrder = listOfOrders[position]
        holder.apply {
            binding.apply {

                if (!foodOrder.orderId!!.checkNonPrimeOrPrime()){
                    //change color if prime
                    mcrdParentLayout.setCardBackgroundColor(
                        ContextCompat.getColor(
                        itemView.context,
                        R.color.colorPrimary
                    ))
                }
                else{
                    //change color if non prime
                    mcrdParentLayout.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorSecondary
                        ))
                }

                mtxtOrderId.text = foodOrder.orderId.toString()
                mtxtOrderType.text = foodOrder.orderType
                mtxtExpectedDate.text = AppUtils.convertDateFormat(foodOrder.expectedDate.toString(), "yyyy-MM-dd'T'HH:mm:ssZ", "dd-MM-yyyy")
                mtxtSequenceNumber.text = foodOrder.sequenceNo.toString()

                itemView.setOnClickListener {
                    mOnItemClickListener.onItemClicked(foodOrder)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfOrders.size
    }

    inner class OrdersItemViewHolder(val binding: ItemOrdersLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}