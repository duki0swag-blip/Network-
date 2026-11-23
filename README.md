# CART3R Network Lab

Aplicación Android sencilla para medir el tiempo que tarda en establecerse una conexión TCP con un host y puerto que el usuario indique.

## Qué hace
- Permite introducir un host/IP y un puerto TCP.
- Mide el tiempo de conexión (timeout de 5 segundos).
- Muestra el resultado en pantalla.

## Qué NO hace
- No intercepta ni modifica tráfico de otras aplicaciones.
- No bloquea paquetes ni altera conexiones de juegos.
- No evade sistemas anti-trampas.

## Requisitos
- Android Studio compatible con Android Gradle Plugin 8.13.x.
- JDK 17.
- Android 12 o superior (API 31+).

## Abrir y compilar
1. Descarga o clona este repositorio.
2. En Android Studio, selecciona **Open** y abre la carpeta `CART3R-Network-Lab`.
3. Espera a que Gradle termine la sincronización.
4. Conecta un dispositivo Android o inicia un emulador.
5. Pulsa **Run**.

Usa únicamente destinos y servicios para los que tengas autorización. La prueba realiza una conexión TCP normal al host y puerto elegidos.
