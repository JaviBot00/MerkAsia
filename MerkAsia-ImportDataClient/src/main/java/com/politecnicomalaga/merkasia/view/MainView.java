package com.politecnicomalaga.merkasia.view;

import com.politecnicomalaga.merkasia.controller.ImportController;

import java.util.Scanner;

public class MainView {

    private final ImportController controller = new ImportController();
    private final Scanner scanner = new Scanner(System.in);

    public void mostrar() {
        System.out.println("=== Cliente importación de datos ===");
//        System.out.print("Ruta del fichero CSV: ");
//        String ruta = scanner.nextLine().trim();

        System.out.println("Importando datos...");
//        controller.importar(ruta);
        controller.importar("../data/data.csv");
        System.out.println("Proceso finalizado.");
    }
}
