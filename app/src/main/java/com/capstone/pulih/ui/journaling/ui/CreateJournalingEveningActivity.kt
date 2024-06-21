package com.capstone.pulih.ui.journaling.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.data.response.JournalResponseEvening
import com.capstone.pulih.data.response.ResponseJournalingEvening
import com.capstone.pulih.data.response.UserResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityCreateEveningJournalingBinding
import com.capstone.pulih.ui.main.MainActivity
import com.capstone.pulih.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateJournalingEveningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEveningJournalingBinding
    private var journalEntry: JournalResponseEvening? = null

    companion object {
        const val EXTRA_JOURNAL_ENTRY = "extra_journal_entry"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateEveningJournalingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        journalEntry = intent.getParcelableExtra(EXTRA_JOURNAL_ENTRY)

        journalEntry?.let {
            binding.etTitleJournalEvening.setText(it.judul)
            binding.etTodo1.setText(it.q1)
            binding.etTodo2.setText(it.q2)
            binding.etTodo3.setText(it.q3)
            binding.etTodo4.setText(it.q4)
            binding.etTodo5.setText(it.q5)
        }



        getIdJournal(Preferences().getUid(applicationContext).toString())

        binding.modenight.setOnClickListener {
            val intent = Intent(this, CreateJournalingMorningActivity::class.java)
            startActivity(intent)
        }

        binding.btnEveningJournaling.setOnClickListener {
            val id = Preferences().getId(applicationContext).toString()
            if (id.isNotEmpty()) {
                if (journalEntry == null) {
                    // Creating a new journal entry
                    postJournalEvening(id.toInt())
                } else {
                    // Editing an existing journal entry
                    updateJournalEvening(journalEntry!!.id, id.toInt())
                }
                val i = Intent(this@CreateJournalingEveningActivity, MainActivity::class.java)
                startActivity(i)
                finish()
                Toast.makeText(this, "Journal successfully created/updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "User ID not available. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun postJournalEvening(userId: Int) {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val data = retrofit.postJurnalEvening(
            userId = userId,
            judul = binding.etTitleJournalEvening.text.toString(),
            q1 = binding.etTodo1.text.toString(),
            q2 = binding.etTodo2.text.toString(),
            q3 = binding.etTodo3.text.toString(),
            q4 = binding.etTodo4.text.toString(),
            q5 = binding.etTodo5.text.toString()
        )
        data.enqueue(object : Callback<ResponseJournalingEvening> {
            override fun onResponse(
                p0: Call<ResponseJournalingEvening>,
                p1: Response<ResponseJournalingEvening>
            ) {
                if (p1.isSuccessful) {
                    Log.d("postJournalPagi", "Journal posted successfully")
                } else {
                    Log.e("postJournalPagi", "Error: ${p1.errorBody()?.string()}")
                }
            }

            override fun onFailure(p0: Call<ResponseJournalingEvening>, p1: Throwable) {
                Log.e("postJournalPagi", "Error: ${p1.message}")
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
                        fetchJournalEveningId(userId)
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

    private fun fetchJournalEveningId(userId: Int) {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retrofitData = retrofit.getJournalEvening()

        retrofitData.enqueue(object : Callback<List<JournalResponseEvening>?> {
            override fun onResponse(
                p0: Call<List<JournalResponseEvening>?>,
                p1: Response<List<JournalResponseEvening>?>
            ) {
                val responseBody = p1.body()
                if (!responseBody.isNullOrEmpty()) {
                    val journal = responseBody.find { it.id == userId }
                    if (journal != null) {
                        Log.d("fetchJournalEveningId", "Journal ID: ${journal.id}")
                    } else {
                        Log.e("fetchJournalEveningId", "No journal found for user ID: $userId")
                    }
                } else {
                    Log.e("fetchJournalEveningId", "Response body is null or empty")
                }
            }

            override fun onFailure(p0: Call<List<JournalResponseEvening>?>, p1: Throwable) {
                Log.e("fetchJournalEveningId", "Error: ${p1.message}")
            }
        })
    }

    private fun updateJournalEvening(journalId: Int, userId: Int) {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val data = retrofit.updateJurnalEvening(
            journalId = journalId,
            userId = userId,
            judul = binding.etTitleJournalEvening.text.toString(),
            q1 = binding.etTodo1.text.toString(),
            q2 = binding.etTodo2.text.toString(),
            q3 = binding.etTodo3.text.toString(),
            q4 = binding.etTodo4.text.toString(),
            q5 = binding.etTodo5.text.toString(),
        )
        data.enqueue(object : Callback<ResponseJournalingEvening> {
            override fun onResponse(
                p0: Call<ResponseJournalingEvening>,
                p1: Response<ResponseJournalingEvening>
            ) {
                if (p1.isSuccessful) {
                    Log.d("updateJournalEvening", "Journal updated successfully")
                } else {
                    Log.e("updateJournalEvening", "Error: ${p1.errorBody()?.string()}")
                }
            }

            override fun onFailure(p0: Call<ResponseJournalingEvening>, p1: Throwable) {
                Log.e("updateJournalEvening", "Error: ${p1.message}")
            }
        })
    }
}
