package com.politecnicomalaga;

//Ejercicio 1. CSV to BBDD
//Realizar el cliente de importación de datos en Java. Completar el proyecto de la carpeta importDataClient, de manera que este lea el fichero CSV, lo convierta a datos del modelo de la tienda (Clientes, Pedidos, LineaPedido, Productos) y los envíe a la BBDD asociada al backend utilizando el servlet importData. Podéis estructurar este proyecto como queráis, creando una vista "mínima", pero es obligatorio usar OKHttp como librería de trabajo para enviar los datos leídos del fichero, y se aconseja mandar los datos en JSON al servidor para que el servlet lo procese. Para ello, tenéis que utilizar el siguiente código en el cliente:
//
//OkHttpClient client = new OkHttpClient();
//
//String json = "...tu json...";
//
//RequestBody body = RequestBody.create(
//    json,
//    MediaType.parse("application/json")
//);
//
//Request request = new Request.Builder()
//    .url("http://midominio.com/importar")
//    .post(body)
//    .build();
//
//Response response = client.newCall(request).execute();
//
//
//En este caso, como es un POST, y no un GET, no hay que "esperar" nada. Por lo que no hay que "encolar" ni crear una Callback. Para ver que funciona, nos vamos a la BBDD y vemos si están los datos.
//
//EVALUACIÓN:
//    - Lectura del fichero: 2p
//- Transformación (parser) del CSV a modelo: 2p
//- Envío de datos al servidor: 2p

public class Main {
    public static void main(String[] args) {

        System.out.printf("Crea el código necesario para abrir el fichero CSV y mandar los datos al server!");

    }
}
