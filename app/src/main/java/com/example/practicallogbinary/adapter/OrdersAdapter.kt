package com.example.practicallogbinary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicallogbinary.databinding.ItemOrdersLayoutBinding
import com.example.practicallogbinary.interfaces.OrderAdapterInterface

class OrdersAdapter(
    private val someList: List<String>,
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
        //val userResponse: UserResponse = someList[position]
        holder.apply {
            binding.apply {

                /*if (position == lastSelectedPosition){
                    //change color
                }
                else{
                    //change color
                }*/

                mtxtOrderId.text = ""
                mtxtOrderType.text = ""
                mtxtExpectedDate.text = ""
                mtxtSequenceNumber.text = ""

                itemView.setOnClickListener {
                    mOnItemClickListener.onItemClicked("")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return someList.size
    }

    inner class OrdersItemViewHolder(val binding: ItemOrdersLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}
}