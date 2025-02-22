package com.example.listadetarefascompose.components

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.listadetarefascompose.ui.theme.Light_Blue
import com.example.listadetarefascompose.ui.theme.White

@Composable
fun Botao(
    onClick: () -> Unit,
    modifier: Modifier,
    texto: String
) {

    Button(
        onClick,
        modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Light_Blue,
            contentColor = White
        )
        ) {
            Text(text = texto, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }

}