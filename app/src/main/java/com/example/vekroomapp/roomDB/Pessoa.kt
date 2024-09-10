//Vek Histories

package com.example.vekroomaapp.roomDB;

import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity
data class Pessoa(
        val nome: String,
        val telefone: String,
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0
)