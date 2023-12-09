/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lap9p1_gabrielalexis;

import java.util.Random;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.ArrayList;

public class Gusanito {

    public static int filas;
    public static int columnas;
    public static Random rand = new Random();
    public static int y;
    public static int x;
    public static char[][] matriz;
    public int gusanoX;
    public int gusanoY;
    public int manzanaX;
    public int manzanaY;
    public ArrayList<String> instruccion = new ArrayList<>();

    public Gusanito(int filas, int columnas) {

        this.filas = filas;
        this.columnas = columnas;
        this.matriz = llenarMatriz(filas, columnas);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matriz[i][j] == 'S') {
                    gusanoX = i;
                    gusanoY = j;
                    return;
                }
            }
        }

    }

    public char[][] llenarMatriz(int filas, int columnas) {
        char[][] matriz = new char[filas][columnas];
        Random rand = new Random();
        int cont_obstaculos = 0;
        int max, min;
        if (filas > columnas) {
            max = filas;
            min = columnas;
        } else {
            max = columnas;
            min = filas;
        }
        int cant_obtaculos = rand.nextInt((max - min) + 1) + min;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = ' ';
            }
        }

        while (cont_obstaculos < cant_obtaculos) {
            int randomx = rand.nextInt(filas);
            int randomy = rand.nextInt(columnas);
            if (matriz[randomx][randomy] == ' ') {
                matriz[randomx][randomy] = '#';
                cont_obstaculos++;
            }
        }

        int manzanaX = rand.nextInt(filas);
        int manzanaY = rand.nextInt(columnas);
        while (matriz[manzanaX][manzanaY] != ' ') {
            manzanaX = rand.nextInt(filas);
            manzanaY = rand.nextInt(columnas);
        }

        int serpienteX = rand.nextInt(filas);
        int serpienteY = rand.nextInt(columnas);
        while (matriz[serpienteX][serpienteY] != ' ' || (serpienteX == manzanaX && serpienteY == manzanaY)) {
            serpienteX = rand.nextInt(filas);
            serpienteY = rand.nextInt(columnas);
        }

        matriz[manzanaX][manzanaY] = 'O';
        matriz[serpienteX][serpienteY] = 'S';

        return matriz;
    }

    public int imprimir(char[][] matriz) {
        StringBuilder acum = new StringBuilder();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                acum.append("[").append(matriz[i][j]).append("]");
            }
            acum.append("\n");
        }
        String input = JOptionPane.showInputDialog(null, acum.toString() + "Elige una opción:\n1. Ingresar instruccion\n2. Ejecutar instrucciones");
        int n = Integer.parseInt(input);
        return n;
    }

    public void imprimirMatriz(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }
    }

    public char[][] moverGusanito(int magnitud, String direccion) {
        instruccion.add(magnitud + direccion);

        char[][] newMatriz = new char[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                newMatriz[i][j] = matriz[i][j];
            }
        }

        int proxX = gusanoX;
        int proxY = gusanoY;
        switch (direccion) {
            case "UP":
                proxX -= magnitud;
                break;
            case "DN":
                proxX += magnitud;
                break;
            case "RT":
                proxY += magnitud;
                break;
            case "LT":
                proxY -= magnitud;
                break;
            default:
                System.out.println("Dirección no válida");
                return newMatriz;
        }
        if (proxX >= 0 && proxX < newMatriz.length && proxY >= 0 && proxY < newMatriz[0].length) {
            gusanoX = proxX;
            gusanoY = proxY;
        }
        if (proxX < 0 || proxX >= newMatriz.length || proxY < 0 || proxY >= newMatriz[0].length) {
            JOptionPane.showMessageDialog(null, "La serpiente ha alcanzado el límite!");
            return newMatriz;
        }
        if (newMatriz[proxX][proxY] == 'O') {
            JOptionPane.showMessageDialog(null, "Haz encontrado la manzana!");
            return newMatriz;
        }
        if (newMatriz[proxX][proxY] == '#') {
            JOptionPane.showMessageDialog(null, "Haz chocado con un obstáculo!");
            return newMatriz;
        }
        newMatriz[gusanoX][gusanoY] = ' ';
        newMatriz[proxX][proxY] = 'S';

        return newMatriz;
    }

}
