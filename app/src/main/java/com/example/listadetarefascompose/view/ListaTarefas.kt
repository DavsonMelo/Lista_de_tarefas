package com.example.listadetarefascompose.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.listadetarefascompose.R
import com.example.listadetarefascompose.itemlista.TarefaItem
import com.example.listadetarefascompose.model.Tarefa
import com.example.listadetarefascompose.repositorio.TarefasRepositorio
import com.example.listadetarefascompose.ui.theme.Purple700
import com.google.firebase.Firebase

// ### CLASSE RESPONSÁVEL POR LISTAR AS TAREFAS NA TELA ### //

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// cria a função composable ListaTarefas que controla tudo que vai acontecer nesta view
fun ListaTarefas( navController: NavController ) {

    val tarefasRepositorio = TarefasRepositorio() // instancia o repositorio
    val context = LocalContext.current // recupera o contexto da aplicação
    /*
    No Jetpack Compose, o Scaffold é um componente que fornece uma estrutura básica para a construção
    de telas, permitindo a criação de layouts consistentes e organizados. Ele facilita a inclusão de
    elementos comuns da interface, como:
    TopBar: barra superior.
    BottomBar: barra inferior.
    FloatingActionButton (FAB): botão flutuante de ação.
    Drawer: menu lateral deslizante.
    Snackbar: notificações breves.
     */
    Scaffold(
        // Cria a barra superior com o titulo "Lista de Tarefas"
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Tarefas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple700 // Define a cor de fundo da AppBar
            )
          )
        },
        // Define a cor de fundo do Scaffold
        containerColor = Color.Black,
        // Cria um botão flutuante para criar nova tarefa que leva a tela SalvarTarefa
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("SalvarTarefa")
                },
                containerColor = Purple700,
                shape = RoundedCornerShape(30.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                    contentDescription = "Icone de salvar tarefas!"
                )
            }
        }
    ) {paddingValues ->

        val listaTarefas = tarefasRepositorio.recuperarTarefas().collectAsState(mutableListOf()).value
        // Cria uma lista vertical com scroll.
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            itemsIndexed(listaTarefas) {position, _, ->
                TarefaItem(position = position, listaTarefas = listaTarefas, context = context, navController = navController)
            }
        }
    }
}