package com.example.app19

class Utilizador(val id: Int = 0, var username: String = "", var password: String = "") {

    override fun toString(): String {
        return "Utilizador(id=$id, username='$username', password='$password')"
    }
}