package com.capstone.pulih.ui.inputdata

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.data.response.InputResponse
import com.capstone.pulih.data.response.UserResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityInputDataBinding
import com.capstone.pulih.ui.main.MainActivity
import com.capstone.pulih.ui.mainkonselor.MainKonselorActivity
import com.capstone.pulih.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputDataActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInputDataBinding
    private lateinit var intent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUser(Preferences().getUid(applicationContext).toString())
        checkFromEdit()

        binding.btnSave.setOnClickListener {
            setUser()
            Toast.makeText(this, "Data Berhasil Disimpan!", Toast.LENGTH_LONG).show()
            if (Preferences().getRole(applicationContext)=="KONSELOR"){
                konselorActivity()
            } else {
                nextActivity()
            }
        }
    }
    private fun nextActivity(){
        intent = Intent(this@InputDataActivity, MainActivity::class.java)
        Preferences().setFromEdit(false,applicationContext)
        startActivity(intent)
        finish()
    }

    private fun konselorActivity(){
        intent = Intent(this@InputDataActivity, MainKonselorActivity::class.java)
        Preferences().setFromEdit(false,applicationContext)
        startActivity(intent)
        finish()
    }
    private fun checkFromEdit(){
        if(Preferences().getFromEdit(applicationContext)==false&&binding.edNama.text.toString().isEmpty()&&Preferences().getRole(applicationContext)=="USER") {
            nextActivity()
        } else if (Preferences().getFromEdit(applicationContext)==false&&binding.edNama.text.toString().isNotEmpty()&&Preferences().getRole(applicationContext)=="USER"){
            nextActivity()
        } else if((Preferences().getFromEdit(applicationContext)==false&&binding.edNama.text.toString().isEmpty()&&Preferences().getRole(applicationContext)=="KONSELOR")){
            konselorActivity()
        } else if (Preferences().getFromEdit(applicationContext)==false&&binding.edNama.text.toString().isNotEmpty()&&Preferences().getRole(applicationContext)=="KONSELOR"){
            konselorActivity()
        } else if (Preferences().getFromEdit(applicationContext)&&binding.edNama.text.toString().isNotEmpty()){

        }
    }
    private fun setUser(){
        val idUser = Preferences().getId(applicationContext).toString().toInt()
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retrofitData = retrofit.putPengguna(
            user_id = Preferences().getUid(applicationContext),
            id = idUser,
            nama = binding.edNama.text.toString(),
            nama_panggilan = binding.edNick.text.toString(),
            nim = binding.edNim.text.toString(),
            universitas = binding.edUniv.text.toString(),
            telepon = binding.edTelp.text.toString()
        )
        Preferences().saveNama(binding.edNama.text.toString(),applicationContext)
        Preferences().saveNick(binding.edNick.text.toString(),applicationContext)
        Preferences().saveUniv(binding.edUniv.text.toString(),applicationContext)

        retrofitData.enqueue(object : Callback<List<InputResponse>?>{
            override fun onResponse(
                p0: Call<List<InputResponse>?>,
                p1: Response<List<InputResponse>?>
            ) {
                Log.e("Input Data Activity","Input Success")
            }

            override fun onFailure(p0: Call<List<InputResponse>?>, p1: Throwable) {
                Log.e("Input Data Activity","Input Error")
            }

        })
    }
    private fun getUser(id:String){
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retrofitData = retrofit.getPengguna(id)

        retrofitData.enqueue(object : Callback<List<UserResponse>?>{
            override fun onResponse(
                p0: Call<List<UserResponse>?>,
                p1: Response<List<UserResponse>?>
            ) {
                val responseBody = p1.body()!!
                for(data in responseBody){
                    data.id?.let { Preferences().saveId(it, applicationContext) }
                    binding.edNama.text = data.nama?.toEditable()
                    binding.edNick.text = data.nama_panggilan?.toEditable()
                    binding.edUniv.text = data.universitas?.toEditable()
                    binding.edNim.text = data.nim?.toEditable()
                    binding.edTelp.text = data.telepon?.toEditable()
                }
                Log.d("Input Data", "Id: ${Preferences().getId(applicationContext)}")
            }

            override fun onFailure(p0: Call<List<UserResponse>?>, p1: Throwable) {
                Log.e("Input Data Activity","onFailure ${p1.message}")
            }

        })
    }
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

}