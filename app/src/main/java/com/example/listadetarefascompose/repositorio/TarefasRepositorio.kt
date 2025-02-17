package com.example.listadetarefascompose.repositorio

import com.example.listadetarefascompose.datasource.DataSource
import com.example.listadetarefascompose.model.Tarefa
import kotlinx.coroutines.flow.Flow

class TarefasRepositorio {

    private val dataSource = DataSource()

    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int) {
        dataSource.salvarTarefa(tarefa, descricao, prioridade)

    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>> {
        return dataSource.recuperarTarefas()
    }

    fun deletarTarefa(tarefa: String) {
        dataSource.deletarTarefa(tarefa)
    }
}