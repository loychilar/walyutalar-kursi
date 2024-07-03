package com.example.walyutalarkursi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.walyutalarkursi.R
import com.example.walyutalarkursi.models.Kurs

class RvAdapter (val context: Context,val list:ArrayList<Kurs>,val rvClick:RvClick):RecyclerView.Adapter<RvAdapter. VH> (){
    inner class VH(var itemRv:View):RecyclerView.ViewHolder(itemRv){
        fun onBind(kurs: Kurs){
            itemRv.findViewById<TextView>(R.id.itemCountryText).text=kurs.title
            itemRv.findViewById<TextView>(R.id.itemTextMoney).text=kurs.cb_price

            itemRv.setOnClickListener {
               rvClick.onClick(kurs)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RvClick{
        fun onClick(kurs: Kurs)
    }
}