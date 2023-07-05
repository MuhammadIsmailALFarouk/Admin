package com.example.manajemenklubolahraga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList: ArrayList<User>) :RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val Jadwal: TextView = itemView.findViewById(R.id.jadwal)
        val NamaPemain: TextView = itemView.findViewById(R.id.namaPemain)
        val NamaTim: TextView = itemView.findViewById(R.id.namaTim)
        val StatistikPemain: TextView = itemView.findViewById(R.id.statistikPemain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.Jadwal.text = userList[position].jadwal
        holder.NamaPemain.text = userList[position].namePemain
        holder.NamaTim.text = userList[position].nameTim
        holder.StatistikPemain.text = userList[position].statistikPemain
    }
}