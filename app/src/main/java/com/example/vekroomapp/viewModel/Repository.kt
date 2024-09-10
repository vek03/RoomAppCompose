//Vek Histories

package com.example.vekroomaapp.viewModel;

import com.example.vekroomaapp.roomDB.Pessoa
import com.example.vekroomaapp.roomDB.PessoaDatabase


class Repository(private val db : PessoaDatabase){
        suspend fun upsertPessoa(pessoa: Pessoa){
                db.pessoaDao().upsertPessoa(pessoa)
        }

        suspend fun deletePessoa(pessoa: Pessoa){
                db.pessoaDao().deletePessoa(pessoa)
        }

        fun getAllPessoa() = db.pessoaDao().getAllPessoa()
}