package com.cart3r.networklab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val Ink = Color(0xFF090B10)
private val Panel = Color(0xFF151A24)
private val Crimson = Color(0xFFFF405B)
private val Muted = Color(0xFFAAB3C2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NetworkLabScreen() }
    }
}

@Composable
private fun NetworkLabScreen() {
    var host by remember { mutableStateOf("1.1.1.1") }
    var port by remember { mutableStateOf("443") }
    var result by remember { mutableStateOf("Listo para realizar una prueba TCP.") }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Ink,
            surface = Panel,
            primary = Crimson,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Ink)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("CART3R", color = Crimson, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text("NETWORK LAB", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
            Text(
                "Diagnóstico básico de conectividad TCP",
                color = Muted,
                fontSize = 14.sp
            )

            Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("DESTINO DE PRUEBA", color = Crimson, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = host,
                        onValueChange = { host = it },
                        label = { Text("Host o IP") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = port,
                        onValueChange = { port = it.filter(Char::isDigit).take(5) },
                        label = { Text("Puerto TCP") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            val p = port.toIntOrNull()
                            if (host.isBlank() || p == null || p !in 1..65535) {
                                result = "Revisa el host y el puerto (1–65535)."
                            } else {
                                busy = true
                                result = "Midiendo conexión…"
                                scope.launch {
                                    val outcome = withContext(Dispatchers.IO) {
                                        TcpProbe.measure(host.trim(), p)
                                    }
                                    result = outcome
                                    busy = false
                                }
                            }
                        },
                        enabled = !busy,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Crimson)
                    ) {
                        Text(if (busy) "PROBANDO…" else "INICIAR PRUEBA")
                    }
                }
            }

            Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("RESULTADO", color = Crimson, fontWeight = FontWeight.Bold)
                    Text(result, color = Color.White, fontSize = 15.sp)
                }
            }

            Text(
                "Esta app solo mide el tiempo de apertura de una conexión TCP al destino indicado. " +
                    "No intercepta, bloquea ni modifica el tráfico de otras aplicaciones.",
                color = Muted,
                fontSize = 12.sp
            )
        }
    }
}
