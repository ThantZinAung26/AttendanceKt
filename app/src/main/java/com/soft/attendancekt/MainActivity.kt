package com.soft.attendancekt

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soft.attendancekt.ui.chat.ChatMessageViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatMessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        NavigationUI.setupWithNavController(
            bottomNavigation,
            Navigation.findNavController(this, R.id.nav_graph)
        )

        viewModel = ViewModelProviders.of(this)[ChatMessageViewModel::class.java]
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)

        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
