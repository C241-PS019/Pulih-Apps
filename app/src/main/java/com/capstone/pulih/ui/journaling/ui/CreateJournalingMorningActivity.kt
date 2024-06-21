package com.capstone.pulih.ui.journaling.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.data.response.JournalResponseMorning
import com.capstone.pulih.data.response.ResponseJournalingMorning
import com.capstone.pulih.data.response.UserResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityCreateMorningJournalingBinding
import com.capstone.pulih.ui.main.MainActivity
import com.capstone.pulih.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateJournalingMorningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateMorningJournalingBinding
    private var journalEntry: JournalResponseMorning? = null

    companion object {
        const val EXTRA_JOURNAL_ENTRY = "extra_journal_entry"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateMorningJournalingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        journalEntry = intent.getParcelableExtra(EXTRA_JOURNAL_ENTRY)

        journalEntry?.let {
            binding.etTitleJournalMorning.setText(it.judul)
            binding.etTodo1.setText(it.q1)
            binding.etTodo2.setText(it.q2)
            binding.etTodo3.setText(it.q3)
            binding.etTodo4.setText(it.q4)
        }



        getIdJournal(Preferences().getUid(applicationContext).toString())

        binding.modelight.setOnClickListener {
            val intent = Intent(this, CreateJournalingEveningActivity::class.java)
            startActivity(intent)
        }

        binding.btnMorningJournaling.setOnClickListener {
            val id = Preferences().getId(applicationContext).toString()
            if (id.isNotEmpty()) {
                if (journalEntry == null) {
                    // Creating a new journal entry
                    postJournalMorning(id.toInt())
                } else {
                    // Editing an existing journal entry
                    updateJournalPagi(journalEntry!!.id, id.toInt())
                }
                val i = Intent(this@CreateJournalingMorningActivity, MainActivity::class.java)
                startActivity(i)
                finish()
                Toast.makeText(this, "Journal successfully created/updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "User ID not available. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun postJournalMorning(userId: Int) {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val data = retrofit.postJurnalMorning(
            userId = userId,
            judul = binding.etTitleJournalMorning.text.toString(),
            q1 = binding.etTodo1.text.toString(),
            q2 = binding.etTodo2.text.toString(),
            q3 = binding.etTodo3.text.toString(),
            q4 = binding.etTodo4.text.toString()
        )
        data.enqueue(object : Callback<ResponseJournalingMorning> {
            override fun onResponse(
                p0: Call<ResponseJournalingMorning>,
                p1: Response<ResponseJournalingMorning>
            ) {
                if (p1.isSuccessful) {
                    Log.d("postJournalMorning", "Journal posted successfully")
                } else {
                    Log.e("postJournalMorning", "Error: ${p1.errorBody()?.string()}")
                }
            }

            override fun onFailure(p0: Call<ResponseJournalingMorning>, p1: Throwable) {
                Log.e("postJournalMorning", "Error: ${p1.message}")
            }
        })
    }

    private fun getIdJournal(id: String) {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retrofitData = retrofit.getPengguna(id)

        retrofitData.enqueue(object : Callback<List<UserResponse>?> {
            override fun onResponse(
                p0: Call<List<UserResponse>?>,
                p1: Response<List<UserResponse>?>
            ) {
                val responseBody = p1.body()
                if (!responseBody.isNullOrEmpty()) {
                    val userId = responseBody[0].id
                    if (userId != null) {
                        Preferences().saveId(userId, applicationContext)
                        Log.d("getIdJournal", "User ID saved: $userId")
                        fetchJournalMorningId(userId)
                    } else {
                        Log.e("getIdJournal", "User ID is null")
                    }
                } else {
                    Log.e("getIdJournal", "Response body is null or empty")
                }
            }

            override fun onFailure(p0: Call<List<UserResponse>?>, p1: Throwable) {
                Log.e("getIdJournal", "Error: ${p1.message}")
            }
        })
    }

    private fun fetchJournalMorningId(userId: Int) {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retrofitData = retrofit.getJournalMorning()

        retrofitData.enqueue(object : Callback<List<JournalResponseMorning>?> {
            override fun onResponse(
                p0: Call<List<JournalResponseMorning>?>,
                p1: Response<List<JournalResponseMorning>?>
            ) {
                val responseBody = p1.body()
                if (!responseBody.isNullOrEmpty()) {
                    val journal = responseBody.find { it.id == userId }
                    if (journal != null) {
                        Log.d("fetchJournalMorningId", "Journal ID: ${journal.id}")
                    } else {
                        Log.e("fetchJournalMorningId", "No journal found for user ID: $userId")
                    }
                } else {
                    Log.e("fetchJournalMorningId", "Response body is null or empty")
                }
            }

            override fun onFailure(p0: Call<List<JournalResponseMorning>?>, p1: Throwable) {
                Log.e("fetchJournalMorningId", "Error: ${p1.message}")
            }
        })
    }

    private fun updateJournalPagi(journalId: Int, userId: Int) {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val data = retrofit.updateJurnalMorning(
            journalId = journalId,
            userId = userId,
            judul = binding.etTitleJournalMorning.text.toString(),
            q1 = binding.etTodo1.text.toString(),
            q2 = binding.etTodo2.text.toString(),
            q3 = binding.etTodo3.text.toString(),
            q4 = binding.etTodo4.text.toString()
        )
        data.enqueue(object : Callback<ResponseJournalingMorning> {
            override fun onResponse(
                p0: Call<ResponseJournalingMorning>,
                p1: Response<ResponseJournalingMorning>
            ) {
                if (p1.isSuccessful) {
                    Log.d("updateJournalPagi", "Journal updated successfully")
                } else {
                    Log.e("updateJournalPagi", "Error: ${p1.errorBody()?.string()}")
                }
            }

            override fun onFailure(p0: Call<ResponseJournalingMorning>, p1: Throwable) {
                Log.e("updateJournalPagi", "Error: ${p1.message}")
            }
        })
    }
}
