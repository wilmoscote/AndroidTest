package com.timetonic.androidtest.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.timetonic.androidtest.R
import com.timetonic.androidtest.databinding.ActivityLoginBinding
import com.timetonic.androidtest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    // Lateinit var for view binding to access the layout views
    private lateinit var binding: ActivityLoginBinding
    // Lazy initialization of the ViewModel to this activity
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding for the activity layout
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Launch a coroutine to check for a valid session asynchronously
        lifecycleScope.launch {
            val validSession = viewModel.sessSaved()

            // If a valid session is found, navigate to the MainActivity
            if (validSession != null) {
                runOnUiThread {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }

            // Execute UI-related actions on the main thread
            runOnUiThread {
                viewModel.createAppkey()
                setupListener()
                setupObservers()
            }
        }
    }

    private fun setupListener() {
        // Set an onClickListener for the login button
        binding.btnLogin.setOnClickListener {
            // Check if the email or password fields are empty and show an error if they are
            if (binding.txtEmail.text.isNullOrEmpty() || binding.txtPwd.text.isNullOrEmpty()) {
                Snackbar.make(binding.root, getString(R.string.empty_fields_error), Snackbar.LENGTH_SHORT).show()
            } else {
                // If email and password are provided, attempt to create an OAuth key
                viewModel.createOauthkey(binding.txtEmail.text.toString(), binding.txtPwd.text.toString())
            }
        }
    }

    private fun setupObservers() {
        // Observe loading state changes and update UI accordingly
        viewModel.loading.observe(this) { isLoading ->
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            binding.txtEmail.isEnabled = !isLoading
            binding.txtPwd.isEnabled = !isLoading
            binding.btnLogin.isEnabled = !isLoading
        }

        // Observe and display errors using a Snackbar
        viewModel.error.observe(this) { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
        }

        // Observe session creation and navigate to the MainActivity if successful
        viewModel.sessCreated.observe(this) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }
}