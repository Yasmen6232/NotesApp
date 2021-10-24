package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.muddz.styleabletoast.StyleableToast

class MainActivity : AppCompatActivity() {

    private lateinit var mainRV: RecyclerView
    private lateinit var noteEntry: EditText
    private lateinit var saveButton: Button
    private lateinit var dbHelper: DBHelper
    private lateinit var adapter: RVAdapter
    private lateinit var notes: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRV= findViewById(R.id.rvMain)
        noteEntry= findViewById(R.id.noteEntry)
        saveButton= findViewById(R.id.saveButton)

        notes= arrayListOf()
        adapter= RVAdapter(notes)
        mainRV.adapter= adapter
        mainRV.layoutManager= LinearLayoutManager(this)

        dbHelper= DBHelper(this)

        saveButton.setOnClickListener{
            if (noteEntry.text.isNotBlank()){
                val check= dbHelper.saveNotes(noteEntry.text.toString())
                val wrongCode: Long= -1
                if (check != wrongCode) {
                    StyleableToast.makeText(this, "Saved Successfully!!\n$check", R.style.mytoast)
                        .show()
                    notes.add(noteEntry.text.toString())
                    adapter.notifyDataSetChanged()
                }
                else
                    StyleableToast.makeText(this,"Something Went Wrong!!\n$check",R.style.mytoast).show()
                noteEntry.text.clear()
            }
            else
                StyleableToast.makeText(this,"Please Enter Valid Values!!",R.style.mytoast).show()
        }
    }
}