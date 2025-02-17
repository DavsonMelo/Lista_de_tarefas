package com.example.listadetarefascompose.itemlista

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.listadetarefascompose.R
import com.example.listadetarefascompose.model.Tarefa
import com.example.listadetarefascompose.repositorio.TarefasRepositorio
import com.example.listadetarefascompose.ui.theme.RadioButtonGreenSelected
import com.example.listadetarefascompose.ui.theme.RadioButtonRedSelected
import com.example.listadetarefascompose.ui.theme.RadioButtonYellowSelected
import com.example.listadetarefascompose.ui.theme.ShapeCardPrioridade
import androidx.compose.ui.graphics.Color.Companion.White as ColorWhite
import com.example.listadetarefascompose.ui.theme.cardPrioridadeBaixa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TarefaItem(
    position: Int,
    listaTarefas: MutableList<Tarefa>,
    context: Context,
    navController: NavController
) {
    val tituloTarefa = listaTarefas[position].tarefa
    val descricaoTarefa = listaTarefas[position].descricao
    val prioridade = listaTarefas[position].prioridade

    val scope = rememberCoroutineScope()
    val tarefasRepositorio = TarefasRepositorio()

    fun dialogDeletar() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Deletar Tarefa")
            .setMessage("Deseja excluir a tarefa?")
            .setPositiveButton("Sim") {_, _ ->
                tarefasRepositorio.deletarTarefa(tituloTarefa.toString())

                scope.launch(Dispatchers.Main) {
                    listaTarefas.removeAt(position)
                    navController.navigate("listaTarefas")
                    Toast.makeText(context, "Sucesso ao deletar tarefa!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Não") {_, _ ->

            }
            .show()
    }

    var nivelDePrioridade: String = when(prioridade) {
        0 -> {
            "Sem prioridade"
        }
        1 -> {
            "Prioridade baixa"
        }
        2 -> {
            "Prioridade média"
        }
        else -> {
            "Prioridade alta"
        }
    }
    val color = when(prioridade) {
        0 -> {
            Color.Black
        }
        1 -> {
            RadioButtonGreenSelected
        }
        2 -> {
            RadioButtonYellowSelected
        }
        else -> {
            RadioButtonRedSelected
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            val (txtTitulo, txtDescricao, cardPrioridade, txtPrioridade, btDeletar) = createRefs()
            Text(
                text = tituloTarefa.toString(),
                modifier = Modifier
                    .constrainAs(txtTitulo) {
                        top.linkTo(parent.top, margin = 10.dp)
                        start.linkTo(parent.start, margin = 10.dp)
                    }
            )
            Text(
                text = descricaoTarefa.toString(),
                modifier = Modifier
                    .constrainAs(txtDescricao) {
                        top.linkTo(txtTitulo.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 10.dp)
                        end.linkTo(parent.end, margin = 10.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = nivelDePrioridade,
                modifier = Modifier
                    .constrainAs(txtPrioridade) {
                        top.linkTo(txtDescricao.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    }
            )
            Card(
              colors = CardDefaults.cardColors(containerColor = color),
              modifier = Modifier
                  .size(30.dp)
                  .constrainAs(cardPrioridade) {
                      top.linkTo(txtDescricao.bottom, margin = 10.dp)
                      start.linkTo(txtPrioridade.end, margin = 10.dp)
                      bottom.linkTo(parent.bottom, margin = 10.dp)
                  },
                shape = ShapeCardPrioridade.large
            ) {

            }
            IconButton(
                onClick = {
                    dialogDeletar()
                },
                modifier = Modifier.constrainAs(btDeletar){
                    top.linkTo(txtDescricao.bottom, margin = 10.dp)
                    start.linkTo(cardPrioridade.end, margin = 30.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            ) {
               Image(
                   imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                   contentDescription = null
               )
            }
        }
    }

}