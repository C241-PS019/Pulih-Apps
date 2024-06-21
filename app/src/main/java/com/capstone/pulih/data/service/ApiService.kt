package com.capstone.pulih.data.service

import com.capstone.pulih.data.response.AjuanResponse
import com.capstone.pulih.data.response.DetectResult
import com.capstone.pulih.data.response.InputResponse
import com.capstone.pulih.data.response.JournalResponseEvening
import com.capstone.pulih.data.response.JournalResponseMorning
import com.capstone.pulih.data.response.KonselorResponse
import com.capstone.pulih.data.response.NewsResponse
import com.capstone.pulih.data.response.PengajuanResponse
import com.capstone.pulih.data.response.ResponseJournalingEvening
import com.capstone.pulih.data.response.ResponseJournalingMorning
import com.capstone.pulih.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("penggunas/")
    fun getPengguna(
        @Query("akun_id") user_id:String
    ) : Call<List<UserResponse>>

    @FormUrlEncoded
    @PUT("penggunas/{id}/")
    fun putPengguna(
        @Path("id") id:Int,
        @Field("akun_id") user_id:String?,
        @Field("nama") nama : String?,
        @Field("nama_panggilan") nama_panggilan:String?,
        @Field("universitas") universitas:String?,
        @Field("nim") nim:String?,
        @Field("telepon") telepon:String?,
    ) : Call<List<InputResponse>>

    @FormUrlEncoded
    @POST("predict/")
    fun postDeteksi(
        @Field("p1") p1:Int,
        @Field("p2") p2:Int,
        @Field("p3") p3:Int,
        @Field("p4") p4:Int,
        @Field("p5") p5:Int,
        @Field("p6") p6:Int,
        @Field("p7") p7:Int,
        @Field("p8") p8:Int,
        @Field("p9") p9:Int,
        @Field("p10") p10:Int
    ) :Call<DetectResult>

    @FormUrlEncoded
    @POST("jurnal-pagis/")
    fun postJurnalMorning(
        @Field("pengguna_id") userId: Int? = null,
        @Field("judul") judul: String,
        @Field("q1") q1: String,
        @Field("q2") q2: String,
        @Field("q3") q3: String,
        @Field("q4") q4: String,
    ) :Call<ResponseJournalingMorning>

    @FormUrlEncoded
    @POST("jurnal-sores/")
    fun postJurnalEvening(
        @Field("pengguna_id") userId: Int? = null,
        @Field("judul") judul: String,
        @Field("q1") q1: String,
        @Field("q2") q2: String,
        @Field("q3") q3: String,
        @Field("q4") q4: String,
        @Field("q5") q5: String
    ) :Call<ResponseJournalingEvening>

    @FormUrlEncoded
    @PUT("jurnal-pagis/{id}")
    fun updateJurnalMorning(
        @Path("id") journalId: Int,
        @Field("pengguna_id") userId: Int,
        @Field("judul") judul: String,
        @Field("q1") q1: String,
        @Field("q2") q2: String,
        @Field("q3") q3: String,
        @Field("q4") q4: String
    ): Call<ResponseJournalingMorning>

    @FormUrlEncoded
    @PUT("jurnal-sores/{id}")
    fun updateJurnalEvening(
        @Path("id") journalId: Int,
        @Field("pengguna_id") userId: Int,
        @Field("judul") judul: String,
        @Field("q1") q1: String,
        @Field("q2") q2: String,
        @Field("q3") q3: String,
        @Field("q4") q4: String,
        @Field("q5") q5: String,
    ): Call<ResponseJournalingEvening>

    @FormUrlEncoded
    @POST("konseling/")
    fun postPengajuan(
        @Field("pengguna_id") userId: Int,
        @Field("konselor_id") konselorId: Int,
        @Field("tanggal") tanggal: String,
        @Field("waktu") waktu: String,
        @Field("jenis") jenis: String,
        @Field("tempat") tempat: String,
        @Field("pesan") pesan: String,
        @Field("status") status: String,
        @Field("nama_konselor") namaKonselor: String,
        @Field("nama_user") namaUser: String,
    ) :Call<List<PengajuanResponse>>

    @GET("konselors/")
    fun getKonselor() : Call<List<KonselorResponse>>

    @GET("konseling/")
    fun getKonselorname() : Call<List<PengajuanResponse>>

    @GET("konseling/")
    fun getPengajuan(
    ): Call<List<PengajuanResponse>>

    @GET("konseling/")
    fun getAjuan(
    ): Call<List<PengajuanResponse>>

    @GET("jurnal-pagis/")
    fun getJournalMorning(): Call<List<JournalResponseMorning>>

    @GET("jurnal-sores/")
    fun getJournalEvening(): Call<List<JournalResponseEvening>>

    @FormUrlEncoded
    @PUT("konselors/{id}/")
    fun putKonselor(
        @Path("id") id:Int,
        @Field("akun_id") konselor_id:String?,
        @Field("nama") nama : String?,
        @Field("nama_panggilan") nama_panggilan:String?,
        @Field("mitra") universitas:String?,
        @Field("telepon") telepon:String?
    ) : Call<List<InputResponse>>

    @FormUrlEncoded
    @PATCH("konseling/{id}/")
    fun updateStatus(
        @Path("id") id: Int,
        @Field("status") status: String,
        @Field("pesan") pesan: String,
    ): Call<PengajuanResponse>

    @GET("everything?q=tesla&from=2024-05-19&sortBy=publishedAt&apiKey=7445f90f3d30470ea6d808377d270c18")
    fun getNews() : Call<List<NewsResponse>>

    @GET("everything?q=tesla&from=2024-05-19&sortBy=publishedAt&apiKey=7445f90f3d30470ea6d808377d270c18")
    fun getKonseling() : Call<List<NewsResponse>>



}