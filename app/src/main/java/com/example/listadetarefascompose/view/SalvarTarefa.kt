package com.example.listadetarefascompose.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.listadetarefascompose.components.Botao
import com.example.listadetarefascompose.components.CaixaDeTexto
import com.example.listadetarefascompose.constantes.Constantes
import com.example.listadetarefascompose.repositorio.TarefasRepositorio
import com.example.listadetarefascompose.ui.theme.Purple700
import com.example.listadetarefascompose.ui.theme.RadioButtonGreenDisabled
import com.example.listadetarefascompose.ui.theme.RadioButtonGreenSelected
import com.example.listadetarefascompose.ui.theme.RadioButtonRedDisabled
import com.example.listadetarefascompose.ui.theme.RadioButtonRedSelected
import com.example.listadetarefascompose.ui.theme.RadioButtonYellowDisabled
import com.example.listadetarefascompose.ui.theme.RadioButtonYellowSelected
import com.example.listadetarefascompose.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ### CLASSE RESPONSÁVEL POR CAPTURAR OS DADOS A SEREM SALVOS ### //

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalvarTarefa( navController: NavController ) {

    // A função composable SalvarTarefa controla a criação e o salvamento de tarefas no bd
    // observe que o atributo

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val tarefasRepositorio = TarefasRepositorio()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Salvar tarefa",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple700 // Define a cor de fundo da AppBar
                )
            )
        }
    ) {
        var tituloTarefa by remember {
            mutableStateOf("")
        }
        var descricaoTarefa by remember {
            mutableStateOf("")
        }
        var semPrioridadeTarefa by remember {
            mutableStateOf(false)
        }
        var prioridadeBaixaTarefa by remember {
            mutableStateOf(false)
        }
        var prioridadeMediaTarefa by remember {
            mutableStateOf(false)
        }
        var prioridadeAltaTarefa by remember {
            mutableStateOf(false)
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CaixaDeTexto(
                value = tituloTarefa,
                onValueChange = {
                    tituloTarefa = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 100.dp, 20.dp, 0.dp),
                label = "Titulo Tarefa",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )

            CaixaDeTexto(
                value = descricaoTarefa,
                onValueChange = {
                    descricaoTarefa = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 10.dp, 20.dp, 0.dp),
                label = "Descrição",
                maxLines = 5,
                keyboardType = KeyboardType.Text
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Nível de prioridade")

                RadioButton(
                    selected = prioridadeBaixaTarefa,
                    onClick = {
                        prioridadeBaixaTarefa = !prioridadeBaixaTarefa
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RadioButtonGreenDisabled,
                        selectedColor = RadioButtonGreenSelected
                    )
                )
                RadioButton(
                    selected = prioridadeMediaTarefa,
                    onClick = {
                        prioridadeMediaTarefa = !prioridadeMediaTarefa
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RadioButtonYellowDisabled,
                        selectedColor = RadioButtonYellowSelected
                    )
                )
                RadioButton(
                    selected = prioridadeAltaTarefa,
                    onClick = {
                        prioridadeAltaTarefa = !prioridadeAltaTarefa
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RadioButtonRedDisabled,
                        selectedColor = RadioButtonRedSelected
                    )
                )

            }
            Botao(
                onClick = {

                    var mensagem = true

                    scope.launch(Dispatchers.IO) {
                        if (tituloTarefa.isEmpty()){
                            mensagem = false
                        }else if (tituloTarefa.isNotEmpty() && descricaoTarefa.isNotEmpty() && prioridadeBaixaTarefa) {
                            tarefasRepositorio.salvarTarefa(tituloTarefa, descricaoTarefa, Constantes.PRIORIDADE_BAIXA)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && descricaoTarefa.isNotEmpty() && prioridadeMediaTarefa) {
                            tarefasRepositorio.salvarTarefa(tituloTarefa, descricaoTarefa, Constantes.PRIORIDADE_MEDIA)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && descricaoTarefa.isNotEmpty() && prioridadeAltaTarefa) {
                            tarefasRepositorio.salvarTarefa(tituloTarefa, descricaoTarefa, Constantes.PRIORIDADE_ALTA)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && descricaoTarefa.isNotEmpty() && semPrioridadeTarefa) {
                            tarefasRepositorio.salvarTarefa(tituloTarefa, descricaoTarefa, Constantes.SEM_PRIORIDADE)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && prioridadeBaixaTarefa) {
                            tarefasRepositorio.salvarTarefa(tituloTarefa, descricaoTarefa, Constantes.PRIORIDADE_BAIXA)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && prioridadeMediaTarefa) {
                            tarefasRepositorio.salvarTarefa(tituloTarefa, descricaoTarefa, Constantes.PRIORIDADE_MEDIA)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && prioridadeAltaTarefa) {
                            tarefasRepositorio.salvarTarefa(tituloTarefa, descricaoTarefa, Constantes.PRIORIDADE_ALTA)
                            mensagem = true
                        }else {
                            tarefasRepositorio.salvarTarefa(tituloTarefa, descricaoTarefa, Constantes.SEM_PRIORIDADE)
                            mensagem = true
                        }
                    }

                    scope.launch(Dispatchers.Main) {
                        if (mensagem) {
                            Toast.makeText(context, "Sucesso ao salvar tarefa!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }else {
                            Toast.makeText(context, "Título da tarefa é obrigatório!", Toast.LENGTH_SHORT).show()
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(20.dp),
                texto = "Salvar"
            )
        }
    }
}