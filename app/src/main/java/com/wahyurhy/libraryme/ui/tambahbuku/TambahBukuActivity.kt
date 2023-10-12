package com.wahyurhy.libraryme.ui.tambahbuku

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.wahyurhy.libraryme.databinding.ActivityTambahBukuBinding
import com.wahyurhy.libraryme.model.Book
import com.wahyurhy.libraryme.utils.Utils.isEmpty

class TambahBukuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahBukuBinding

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var imageUri: Uri? = null

    private var completeListener: DatabaseReference.CompletionListener? = null

    private var isAllSet: Boolean = false
    private var isSubmitted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("LibraryMe")

        initCLick()
    }

    private fun initCLick() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        val startForProfileImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //Image Uri will not be null for RESULT_OK
                        imageUri = data?.data!!

                        binding.imageBook.setImageURI(imageUri)
                        binding.imageBook.scaleType = ImageView.ScaleType.CENTER_CROP
                    }

                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        binding.btnPilihGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop(
                    908f,
                    1207f
                )                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.btnSimpan.setOnClickListener {
            simpanData()
        }
    }

    private fun simpanData() {
        if (!isSubmitted) {
            val judul = binding.tvJudul.text.toString()
            val pengarang = binding.tvPengarang.text.toString()
            val tahunTerbit = binding.tvTerbit.text.toString()
            val penerbit = binding.tvPenerbit.text.toString()
            val isbn = binding.tvIsbn.text.toString()

            isAllSet = judul.isEmpty(binding.tvJudul, "Kolom ini wajib diisi!")
            isAllSet = pengarang.isEmpty(binding.tvPengarang, "Kolom ini wajib diisi!")
            isAllSet = tahunTerbit.isEmpty(binding.tvTerbit, "Kolom ini wajib diisi!")
            isAllSet = penerbit.isEmpty(binding.tvPenerbit, "Kolom ini wajib diisi!")
            isAllSet = isbn.isEmpty(binding.tvIsbn, "Kolom ini wajib diisi!")

            if (isAllSet) {
                addDataToFirebase(judul, pengarang, tahunTerbit, penerbit, isbn, imageUri)
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "You have saved the transaction data", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun addDataToFirebase(judul: String, pengarang: String, tahunTerbit: String, penerbit: String, isbn: String, image: Uri?) {
        showLoading(true)
        val book = Book(isbn, judul, tahunTerbit, penerbit, pengarang, "")
        completeListener = DatabaseReference.CompletionListener { databaseError, _ ->
            if (databaseError == null) {
                // code yang akan dijalankan jika tidak terjadi error
                Log.d("AddTransactionActivity", "saveTransactionData: masuk addOnCompleteListener")
                Toast.makeText(this@TambahBukuActivity, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Log.e("AddTransactionActivity", "saveTransactionData: Error: ${databaseError.message}")
                showLoading(false)
                Toast.makeText(this@TambahBukuActivity, "Error ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        }
        databaseReference.setValue(book, completeListener)
        isSubmitted = true
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }
}