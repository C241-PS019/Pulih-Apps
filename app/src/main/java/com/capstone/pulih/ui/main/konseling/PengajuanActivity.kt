package com.capstone.pulih.ui.main.konseling

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.data.response.JournalResponseMorning
import com.capstone.pulih.data.response.KonselorResponse
import com.capstone.pulih.data.response.PengajuanResponse
import com.capstone.pulih.data.response.UserResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityPengajuanBinding
import com.capstone.pulih.ui.journaling.adapter.JournalMorningAdapter
import com.capstone.pulih.ui.main.MainActivity
import com.capstone.pulih.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PengajuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengajuanBinding
    private var konselorId: Int = 0
    private var nama :String? = null
    private var userId: Int? = null
    private var selectedDate: String? = null
    private var selectedTime: String? = null
    private lateinit var konselorMap: Map<String, KonselorResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengajuanBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        getKonselortempat()

        val spinnerTempat = binding.spinnerTempat
        val spinnerKonselor = binding.spinnerKonselor

        binding.Tanggal.setOnClickListener {
            showDatePickerDialog()
        }

        // Add time picker for waktu
        binding.Waktu.setOnClickListener {
            showTimePickerDialog()
        }

        spinnerTempat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                konselorId.let { Preferences().saveKonselorId(it, applicationContext) }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        spinnerKonselor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedKonselorName = parent.getItemAtPosition(position) as String
                konselorId = konselorMap[selectedKonselorName]?.id!!
                nama = konselorMap[selectedKonselorName]?.nama
                Preferences().saveKonselorId(konselorId, applicationContext)
                nama?.let { Preferences().saveKonselorName(it, applicationContext) }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        getIdJournal(Preferences().getUid(applicationContext).toString())
        getPengajuanData()

        binding.btnPengajuan.setOnClickListener {
            userId = Preferences().getId(applicationContext)
            konselorId = Preferences().getKonselorId(applicationContext)
            if (userId != null && konselorId != null) {
                postPengajuan(userId!!, konselorId!!)
                Preferences().saveKonselorId(konselorId!!, applicationContext)
                val action = Intent(this, MainActivity::class.java)
                startActivity(action)
                finish()
                Log.d("PengajuanActivity", "User ID is $userId or Konselor ID is $konselorId ")
            } else {
                Log.e("PengajuanActivity", "User ID is $userId or Konselor ID is $konselorId ")
            }
        }
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate = "$year-${month + 1}-$dayOfMonth"
                binding.Tanggal.text = selectedDate

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            { _,hoursOfDay, minute,  ->
                selectedTime = "$hoursOfDay:$minute:00"
                binding.Waktu.text = selectedTime
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),

            true
        )
        timePickerDialog.show()
    }

    private fun getKonselortempat() {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retrofitData = retrofit.getKonselor()
        retrofitData.enqueue(object : Callback<List<KonselorResponse>> {
            override fun onResponse(
                p0: Call<List<KonselorResponse>>,
                p1: Response<List<KonselorResponse>>
            ) {
                if (p1.isSuccessful) {
                    val item = p1.body()
                    if (item != null) {
                        konselorMap = item.associateBy { it.nama }

                        val spinnerTempat = binding.spinnerTempat
                        val spinnerKonselor = binding.spinnerKonselor

                        val adapter = ArrayAdapter(this@PengajuanActivity, android.R.layout.simple_spinner_item, item.map { it.nama })
                        val adapter2 = ArrayAdapter(this@PengajuanActivity, android.R.layout.simple_spinner_item, item.map { it.mitra ?: "" })
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerKonselor.adapter = adapter
                        spinnerTempat.adapter = adapter2
                    }
                }
            }

            override fun onFailure(p0: Call<List<KonselorResponse>>, p1: Throwable) {
                Log.e("API Error", "Error: ${p1.message}")
            }
        })
    }

    private fun postPengajuan(userId: Int, konselorId: Int) {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val selectedTempat = binding.spinnerTempat.selectedItem.toString()
        val nameKonselor = binding.spinnerKonselor.selectedItem.toString()


        val retrofitData = retrofit.postPengajuan(
            userId = userId,
            konselorId = konselorId,
            tanggal = selectedDate ?: "",
            waktu = selectedTime ?: "",
            pesan = binding.Pesan.text.toString(),
            jenis = binding.jenisMasalah.text.toString(),
            status = "diproses",
            tempat = selectedTempat,
            namaKonselor = nameKonselor,
            namaUser = Preferences().getNama(applicationContext).toString()

        )

        retrofitData.enqueue(object : Callback<List<PengajuanResponse>> {
            override fun onResponse(
                call: Call<List<PengajuanResponse>>,
                response: Response<List<PengajuanResponse>>
            ) {
                if (response.isSuccessful) {
                    Log.d("Post Pengajuan", "Success: ${response.body()}")
                } else {
                    Log.e("Post Pengajuan", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<PengajuanResponse>>, t: Throwable) {
                Log.e("Post Pengajuan", "Failure: ${t.message}")
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

    private fun getPengajuanData() {
        val apiService = ApiConfig.getApiService(applicationContext)
        val call = apiService.getPengajuan()

        call.enqueue(object : Callback<List<PengajuanResponse>> {
            override fun onResponse(
                call: Call<List<PengajuanResponse>>,
                response: Response<List<PengajuanResponse>>
            ) {
                if (response.isSuccessful) {
                    val pengajuanList = response.body()
                    if (pengajuanList != null) {
                        Log.d("Pengajuan Info", "Data fetched successfully")
                    }
                } else {
                    Log.e("API Error", "Failed to fetch data")
                }
            }

            override fun onFailure(call: Call<List<PengajuanResponse>>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
            }
        })
    }

}
