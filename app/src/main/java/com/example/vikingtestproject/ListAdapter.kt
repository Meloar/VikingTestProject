package com.example.vikingtestproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.master.glideimageview.GlideImageView


class ListAdapter (val context: Context, val list:ArrayList<MyData>):BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view:View = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false)
        val name = view.findViewById<TextView>(R.id.textView_name)
        val subtitle = view.findViewById<TextView>(R.id.textView_subtitle)
        val glideImage = view.findViewById<GlideImageView>(R.id.glideImage)

        name.text = list[position].name
        subtitle.text = list[position].title
        val url_adress = list[position].thumbnail
        Glide.with(context).load(url_adress).centerCrop().into(glideImage)
        
        return view

    }



    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
    
    
}