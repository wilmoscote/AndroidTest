package com.timetonic.androidtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timetonic.androidtest.R
import com.timetonic.androidtest.data.api.Constants.WEB_URL
import com.timetonic.androidtest.data.model.datamodels.Book
import com.timetonic.androidtest.data.model.datamodels.Contact
import com.timetonic.androidtest.databinding.ItemBookBinding

// Adapter class for RecyclerView to display a list of books
class BookAdapter(private val books: List<Book>, private val contacts: List<Contact>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // Filter out books that are in the user's contacts
    private val filteredBooks = books.filterNot { book ->
        contacts.any { contact -> book.bo == contact.uc }
    }

    // Create new ViewHolder for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    // Bind data to each ViewHolder, setting up the book information in each item
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = filteredBooks[position]
        holder.bind(book)
    }

    // Return the total count of items in the filtered book list
    override fun getItemCount(): Int {
        return filteredBooks.size
    }

    // ViewHolder class to hold and bind views for each book item
    class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        // Bind the book data to the view
        fun bind(book: Book) {
            // Set the book title from the owner preferences
            binding.tvBookTitle.text = book.ownerPrefs.title.toString()

            // Load the book cover image using Glide, or a default image if not available
            val imageUrl = book.ownerPrefs.oCoverImg
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(binding.ivBookCover.context)
                    .load("$WEB_URL$imageUrl")
                    .placeholder(R.drawable.ic_default_book)
                    .error(R.drawable.ic_default_book)
                    .into(binding.ivBookCover)
            } else {
                Glide.with(binding.ivBookCover.context)
                    .load(R.drawable.ic_default_book)
                    .into(binding.ivBookCover)
            }
        }
    }
}