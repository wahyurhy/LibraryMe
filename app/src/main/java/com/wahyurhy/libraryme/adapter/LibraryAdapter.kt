package com.wahyurhy.libraryme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.libraryme.R
import com.wahyurhy.libraryme.model.Book

class LibraryAdapter : RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    var mBook = ArrayList<Book>()
        set(mBook) {
            if (mBook.size > 0) {
                this.mBook.clear()
            }
            this.mBook.addAll(mBook)
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val transaksiView = inflater.inflate(R.layout.item_book, parent, false)
        return ViewHolder(transaksiView)
    }

    override fun getItemCount(): Int = mBook.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = mBook[position]
        val pengarang = holder.pengarang
        pengarang.text = book.pengarang
        val judul = holder.judul
        judul.text = book.judul
        val terbit = holder.terbit
        terbit.text = book.terbit
        val isbn = holder.isbn
        isbn.text = book.isbn
        val image = holder.image
        image.setBackgroundResource(R.drawable.img_melangkah_preview)

    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pengarang = itemView.findViewById<TextView>(R.id.tv_pengarang)
        val judul = itemView.findViewById<TextView>(R.id.tv_judul)
        val terbit = itemView.findViewById<TextView>(R.id.tv_terbit)
        val isbn = itemView.findViewById<TextView>(R.id.tv_isbn)
        val image = itemView.findViewById<ImageView>(R.id.image_book)

            init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, position)
                }
            }
        }
    }
}