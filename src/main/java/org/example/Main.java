package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws MatrixException {
        boolean inputGraph = true;
        while(inputGraph) {
            Random rn = new Random();
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.setLevel(Level.ALL);
            Scanner scanner = new Scanner(System.in);
            logger.info("Enter filename (don't forget the .csv)");

            String filename = scanner.nextLine();

            AdjacencyMatrix matrix = new AdjacencyMatrix(0).importMatrix(filename);
            Graph graph = new Graph(0).importAdjacencyMatrix(matrix);

            StringBuilder sb = new StringBuilder();

            sb.append("\nAdjazent Matrix:\n");
            sb.append(graph.adjacencyMatrix);

            sb.append("\nArtikulationen:\n");
            sb.append(graph.findArticulationPoints());

            sb.append("\nBrücken:\n");
            sb.append(graph.printBridges());

            sb.append("\nZusammenhangskomponenten:\n");
            sb.append(graph.printConnectedComponents());

            int src = rn.nextInt(graph.V);
            int dest = rn.nextInt(graph.V);
            sb.append("\nkürzester Pfad (").append(src).append("-").append(dest).append(")\n");
            sb.append(graph.findShortestPath(src, dest));

            sb.append("\nBeste Färbung:\n");
            sb.append(Arrays.toString(graph.greedyColoring()));

            if (graph.isBipartite()) {
                sb.append("\nDer Graph ist bipartite");
            } else {
                sb.append("\nDer Graph ist NICHT bipartite");
            }

            if (graph.isCompleteGraph()) {
                sb.append("\nDer Graph ist vollständig");
            } else {
                sb.append("\nDer Graph ist NICHT vollständig");
            }

            logger.info(String.valueOf(sb));

            for (int i = 0; i < 1; i++) {
                Scanner again = new Scanner(System.in);
                logger.info("Möchten Sie noch einen Graphen berechnen? (J/N)");

                String answer = again.nextLine();

                if ("N".equals(answer)) {
                    inputGraph = false;
                } else if (!"J".equals(answer)) {
                    i--;
                }
            }
        }
    }
}