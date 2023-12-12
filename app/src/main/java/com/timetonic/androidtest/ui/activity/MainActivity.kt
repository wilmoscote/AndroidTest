package com.timetonic.androidtest.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.timetonic.androidtest.R
import com.timetonic.androidtest.data.model.datamodels.AllBooks
import com.timetonic.androidtest.data.model.datamodels.Book
import com.timetonic.androidtest.databinding.ActivityMainBinding
import com.timetonic.androidtest.ui.adapter.BookAdapter
import com.timetonic.androidtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Lateinit var for view binding to access the layout views of ActivityMain
    private lateinit var binding: ActivityMainBinding
    // Lazy initialization of the ViewModel specific to this activity, using the 'by viewModels()' Kotlin property delegate
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding for the main activity layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set Welcome text info
        lifecycleScope.launch {
            val username = viewModel.getOU()
            runOnUiThread {
                binding.txtWelcome.text = getString(R.string.welcome_text, username)
            }
        }

        // Request all books from the ViewModel
        viewModel.getAllBooks()
        // Set up UI event listeners
        setupListeners()
        // Set up observers for LiveData from the ViewModel
        setupObservers()
    }

    private fun setupListeners() {
        // Handle the logout button click: log out the user and navigate to the login screen
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupObservers() {
        // Observe loading state changes and toggle the visibility of the loading indicator
        viewModel.loading.observe(this) { isLoading ->
            binding.loadingIndicator.isVisible = isLoading
        }

        // Observe and display errors using a Snackbar
        viewModel.error.observe(this) { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
        }

        // Observe the list of all books and set up the RecyclerView when data is available
        viewModel.allbooks.observe(this) { allBooks ->
            setupRecyclerView(allBooks)
        }
    }

    private fun setupRecyclerView(allBooks: AllBooks) {
        // Set the number of columns for the grid layout
        val columns = 3
        // Set up the RecyclerView with a GridLayoutManager
        val layoutManager = GridLayoutManager(this, columns)
        binding.rvBooks.layoutManager = layoutManager
        // Set the adapter for the RecyclerView with the list of books and contacts
        binding.rvBooks.adapter = BookAdapter(allBooks.books ?: listOf(), allBooks.contacts ?: listOf())
    }

}