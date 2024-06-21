package com.capstone.pulih.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class UserResponse(
	@field:SerializedName("id")
	val id:Int?,

	@field:SerializedName("nim")
	val nim: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("telepon")
	val telepon: String? = null,

	@field:SerializedName("universitas")
	val universitas: String? = null,

	@field:SerializedName("nama_panggilan")
	val nama_panggilan: String? = null,

	@field:SerializedName("foto")
	val foto: String ,
	)

data class InputResponse(
	@field:SerializedName("akun_id")
	val akun_id:String?=null,

	@field:SerializedName("nim")
	val nim: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("telepon")
	val telepon: String? = null,

	@field:SerializedName("universitas")
	val universitas: String? = null,

	@field:SerializedName("nama_panggilan")
	val nama_panggilan: String? = null,

	@field:SerializedName("foto")
	val foto: String
	)

data class DetectResult(
	@field:SerializedName("predicted_level")
	val predictLevel: String? = null
)

data class ResponseJournalingMorning(
	@field:SerializedName("pengguna_id")
	val penggunaId: Int,
	@field:SerializedName("judul")
	val judul: String,
	@field:SerializedName("tanggal")
	val tanggal: String,
	@field:SerializedName("p1")
	val p1: String,
	@field:SerializedName("p2")
	val p2: String,
	@field:SerializedName("p3")
	val p3: String,
	@field:SerializedName("p4")
	val p4: String
)

data class ResponseJournalingEvening(
	@field:SerializedName("pengguna_id")
	val userId: Int,
	@field:SerializedName("judul")
	val judul: String,
	@field:SerializedName("tanggal")
	val tanggal: String,
	@field:SerializedName("p1")
	val p1: String,
	@field:SerializedName("p2")
	val p2: String,
	@field:SerializedName("p3")
	val p3: String,
	@field:SerializedName("p4")
	val p4: String,
	@field:SerializedName("p5")
	val p5: String,

)

@Parcelize
data class JournalResponseMorning(
	val id: Int,
	val judul: String,
	val tanggal: String,
	val q1: String,
	val q2: String,
	val q3: String,
	val q4: String
) : Parcelable

@Parcelize
data class JournalResponseEvening(
	val id: Int,
	val judul: String,
	val tanggal: String,
	val q1: String,
	val q2: String,
	val q3: String,
	val q4: String,
	val q5: String
) : Parcelable


data class KonselorResponse(
	@field:SerializedName("id")
	val id:Int?,

	@field:SerializedName("nama")
	val nama: String ,

	@field:SerializedName("nama_panggilan")
	val nama_panggilan: String,

	@field:SerializedName("telepon")
	val telepon: String? = null,

	@field:SerializedName("mitra")
	val mitra: String? = null,

	@field:SerializedName("foto")
	val foto:String?=null,
)

@Parcelize
data class PengajuanResponse(
	@field:SerializedName("konselor_id")
	val konselorId: Int,
	@field:SerializedName("pengguna_id")
	val penggunaId: Int,
	@field:SerializedName("jenis")
	val jenis: String,
	@field:SerializedName("tempat")
	val tempat: String,
	@field:SerializedName("tanggal")
	val tanggal: String,
	@field:SerializedName("waktu")
	val waktu: String,
	@field:SerializedName("pesan")
	val pesan: String,
	@field:SerializedName("status")
	val status: String,
	@field:SerializedName("nama_konselor")
	val namaKonselor: String,
	@field:SerializedName("nama_pengguna")
	val namaPengguna: String,

) : Parcelable

@Parcelize
data class AjuanResponse(
	val id: Int,
	val namaUser: String, // Make nullable if required
	val namaKonselor: String,
	val jenis: String,
	val tempat: String,
	val tanggal: String,
	val waktu: String,
	val pesan: String,
	val status: String
) : Parcelable


data class NewsResponse(
	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)






