//Vek Histories

@file:Suppress("UNCHECKED_CAST")

package com.example.vekroomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.vekroomaapp.roomDB.Pessoa
import com.example.vekroomaapp.roomDB.PessoaDatabase
import com.example.vekroomaapp.viewModel.PessoaViewModel
import com.example.vekroomaapp.viewModel.Repository
import com.example.vekroomapp.ui.theme.VekRoomAppTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PessoaDatabase::class.java,
            "pessoa.db"
        ).build()
    }

    private val viewModel by viewModels<PessoaViewModel>(
        factoryProducer = {
            object :ViewModelProvider.Factory{
                override fun <T : ViewModel> create (modelClass: Class<T>): T{
                    return PessoaViewModel(Repository(db)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VekRoomAppTheme {
                App(viewModel, this)
            }
        }
    }
}

@Composable
fun App(viewModel: PessoaViewModel, mainActivity: MainActivity) {
    var nome by remember{
        mutableStateOf("")
    }

    var telefone by remember {
        mutableStateOf("")
    }

    val pessoa = Pessoa(
        nome,
        telefone
    )

    var pessoaList by remember {
        mutableStateOf(listOf<Pessoa>())
    }

    viewModel.getPessoa().observe(mainActivity){
        pessoaList = it
    }

    Column(
        Modifier
            .background(Color.Black)
            .height(400.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ){
            Text(
                text = "App Database",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ){
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text(text = "Nome: ") }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ) {
            TextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text(text = "Telefone: ") }
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            Arrangement.Center
        ){
            Button(onClick = {
                viewModel.upsertPessoa(pessoa)
                nome = ""
                telefone = ""
            }) {
                Text(text = "Cadastrar")
            }
        }
    }

    HorizontalDivider()

    LazyColumn{
        items(pessoaList){ pessoa ->
            Row(
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ){
                Column(
                    Modifier
                        .fillMaxWidth(),
                    Arrangement.Center
                ){
                    Text(text = pessoa.nome)
                }

                Column(
                    Modifier
                        .fillMaxWidth(),
                    Arrangement.Center
                ){
                    Text(text = pessoa.telefone)
                }
            }
            HorizontalDivider()
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AdicionarDialog(
//    estado: Contato,
//    evento: (ContatoAcoes) -> Unit,
//    modifier: Modifier = Modifier
//){
//    AlertDialog(
//        modifier = modifier,
//        onDismissRequest = {
//            evento(ContatoAcoes.OcultarDialog)
//        },
//        title = { Text(text = "Adicionar Contato") },
//        text = {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                TextField(
//                    value = estado.nome,
//                    onValueChange = {
//                        evento(ContatoAcoes.SetNome(it))
//                    },
//                    placeholder = {
//                        Text(text = "Nome")
//                    }
//                )
//                TextField(
//                    value = estado.sobrenome,
//                    onValueChange = {
//                        evento(ContatoAcoes.SetSobrenome(it))
//                    },
//                    placeholder = {
//                        Text(text = "Sobrenome")
//                    }
//                )
//                TextField(
//                    value = estado.telefone,
//                    onValueChange = {
//                        evento(ContatoAcoes.SetTelefone(it))
//                    },
//                    placeholder = {
//                        Text(text = "Telefone")
//                    }
//                )
//            }
//        },
//        confirmButton = {
//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.CenterEnd
//            ){
//                Button(
//                    onClick = {
//                        evento(ContatoAcoes.CadastrarContato)
//                    }
//                ) {
//                    Text(text = "Cadastrar")
//                }
//            }
//        }
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    VekRoomAppTheme {
//        App()
//    }
//}