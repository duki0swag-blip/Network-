package com.cart3r.networklab

import java.net.InetSocketAddress
import java.net.Socket
import kotlin.system.measureTimeMillis

object TcpProbe {
    fun measure(host: String, port: Int): String {
        return try {
            var elapsed = 0L
            Socket().use { socket ->
                elapsed = measureTimeMillis {
                    socket.connect(InetSocketAddress(host, port), 5000)
                }
            }
            "Conexión TCP establecida.\nTiempo de conexión: ${elapsed} ms\nDestino: $host:$port"
        } catch (e: Exception) {
            "No se pudo conectar a $host:$port.\nMotivo: ${e.localizedMessage ?: "error de conexión"}"
        }
    }
}
