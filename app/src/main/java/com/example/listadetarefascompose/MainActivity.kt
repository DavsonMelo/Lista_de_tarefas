package com.example.listadetarefascompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listadetarefascompose.ui.theme.ListaDeTarefasComposeTheme
import com.example.listadetarefascompose.view.ListaTarefas
import com.example.listadetarefascompose.view.SalvarTarefa

// ### TELA DE ROTAS ### //

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaDeTarefasComposeTheme {

                // Esta classe controla a navegação entre telas do aplicativo

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "ListaTarefas") {
                    composable(
                        route = "ListaTarefas"
                    ) {
                        ListaTarefas(navController)
                    }
                    composable(
                        route = "SalvarTarefa"
                    ) {
                        SalvarTarefa(navController)
                    }
                }

            }
        }
    }
}