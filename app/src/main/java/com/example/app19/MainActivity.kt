package com.example.app19

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.app19.databinding.ActivityMainBinding

// App19: Base de Dados SQLite

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<Utilizador>
    private var pos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = DBHelper(this)
        val listaUtilizadores = db.utilizadorListSelectAll()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaUtilizadores)
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            binding.textId.text = "ID: ${listaUtilizadores[position].id}"
            binding.editUsername.setText(listaUtilizadores[position].username)
            binding.editPassword.setText(listaUtilizadores[position].password)
            pos = position
        }

        binding.buttonInsert.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val res = db.utilizadorInsert(username, password)

            if (res > 0) {
                Toast.makeText(applicationContext, "Insert OK: $res", Toast.LENGTH_SHORT).show()
                listaUtilizadores.add(Utilizador(res.toInt(), username, password))
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(applicationContext, "Insert ERRO: $res", Toast.LENGTH_SHORT).show()
            }
            binding.editUsername.setText("")
            binding.editPassword.setText("")
            binding.textId.text = "ID"
            pos = -1
        }

        binding.buttonUpdate.setOnClickListener {
            if (pos >= 0) {
                val id = listaUtilizadores[pos].id
                val username = binding.editUsername.text.toString()
                val password = binding.editPassword.text.toString()
                val res = db.utilizadorUpdate(id, username, password)

                if (res > 0) {
                    Toast.makeText(applicationContext, "Update OK: $res", Toast.LENGTH_SHORT).show()
                    listaUtilizadores[pos].username = username
                    listaUtilizadores[pos].password = password
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Update ERRO: $res", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.editUsername.setText("")
                binding.editPassword.setText("")
                binding.textId.text = "ID"
                pos = -1
            }
        }

        binding.buttonDelete.setOnClickListener {
            if (pos >= 0) {
                val id = listaUtilizadores[pos].id
                val res = db.utilizadorDelete(id)

                if (res > 0) {
                    Toast.makeText(applicationContext, "Delete OK: $res", Toast.LENGTH_SHORT).show()
                    listaUtilizadores.removeAt(pos)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Delete ERRO: $res", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            binding.editUsername.setText("")
            binding.editPassword.setText("")
            binding.textId.text = "ID"
            pos = -1
        }
    }
}