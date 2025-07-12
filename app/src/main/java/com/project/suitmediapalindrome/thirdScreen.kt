package com.project.suitmediapalindrome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.suitmediapalindrome.model.User
import com.project.suitmediapalindrome.model.UserResponse
import com.project.suitmediapalindrome.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class thirdScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: userAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var users = mutableListOf<User>()

    private var currentPage = 1
    private var isLoading = false
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        recyclerView = findViewById(R.id.rv_Users)
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
        adapter = userAdapter(users) { user ->
            val intent = Intent()
            intent.putExtra("selectedUserName", "${user.firstName} ${user.lastName}")
            setResult(RESULT_OK, intent)
            finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            users.clear()
            fetchUsers()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (!rv.canScrollVertically(1) && !isLoading && currentPage <= totalPages) {
                    fetchUsers()
                }
            }
        })
        fetchUsers()

        val backBtn = findViewById<ImageView>(R.id.imageViewBack)
        backBtn.setOnClickListener {
            finish()
        }


    }

    private fun fetchUsers() {
        isLoading = true
        swipeRefreshLayout.isRefreshing = true

        ApiClient.getApiService().getUsers(currentPage, 10).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                isLoading = false
                swipeRefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("FetchUsers", "Response: $userResponse")
                    Log.d("API", "onResponse: ${response.code()}")
                    totalPages = userResponse?.totalPages ?: 1
                    userResponse?.data?.let{
                        users.addAll(it)
                        adapter.notifyDataSetChanged()
                        currentPage++
                    }
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                isLoading = false
                Log.e("API", "onFailure: ${t.message}")
                Toast.makeText(this@thirdScreen, "Failed to fetch users", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


















