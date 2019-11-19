package com.example.zooguide.animalList


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.zooguide.R
import com.example.zooguide.model.Animal
import com.example.zooguide.model.NavigationPoint

class MySimpleArrayAdapter(private val context: Context,
                           private val dataSource: ArrayList<NavigationPoint>) : BaseAdapter(){

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val recipe = getItem(position) as NavigationPoint
        val view: View
        val holder: ViewHolder

// 1
        if (convertView == null) {

            // 2
            view = inflater.inflate(R.layout.list_item_animal, parent, false)

            // 3
            holder = ViewHolder()
            holder.thumbnailImageView = view.findViewById(R.id.recipe_list_thumbnail) as ImageView
            holder.titleTextView = view.findViewById(R.id.recipe_list_title) as TextView
            holder.subtitleTextView = view.findViewById(R.id.recipe_list_subtitle) as TextView
            holder.detailTextView = view.findViewById(R.id.recipe_list_detail) as TextView

            // 4
            view.tag = holder
        } else {
            // 5
            view = convertView
            holder = convertView.tag as ViewHolder
        }

// 6
        val titleTextView = holder.titleTextView
        val subtitleTextView = holder.subtitleTextView
        val detailTextView = holder.detailTextView
        val thumbnailImageView = holder.thumbnailImageView // TODO: IMPLEMENT

        val animal = getItem(position) as NavigationPoint

        titleTextView.text = animal.id.toString()
        subtitleTextView.text = animal.coords.toString()
        detailTextView.text = animal.connections.toString()



        return view
    }

    //4
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        // Get view for row item
//        val rowView = inflater.inflate(R.layout.list_item_animal, parent, false)
//
//
//
//        // Get title element
//        val titleTextView = rowView.findViewById(R.id.recipe_list_title) as TextView
//
//// Get subtitle element
//        val subtitleTextView = rowView.findViewById(R.id.recipe_list_subtitle) as TextView
//
//// Get detail element
//        val detailTextView = rowView.findViewById(R.id.recipe_list_detail) as TextView
//
//// Get thumbnail element
//        val thumbnailImageView = rowView.findViewById(R.id.recipe_list_thumbnail) as ImageView
//
//// 1
//        val animal = getItem(position) as NavigationPoint
//
//// 2
//        titleTextView.text = animal.id.toString()
//        subtitleTextView.text = animal.coords.toString()
//        detailTextView.text = animal.connections.toString()
//
//// 3
////        Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)
//
//        return rowView
//    }



    private class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var subtitleTextView: TextView
        lateinit var detailTextView: TextView
        lateinit var thumbnailImageView: ImageView
    }

}