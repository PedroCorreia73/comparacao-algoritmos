import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Random;

public class TesteTarefa {

    public static void main(String[] args) {
        int tamanhoDoVetor = Integer.parseInt(args[0]); // A Machion pediu 100mil, 200mil, 400mil, 800mil e 1,6milhão.
        int quantidadeDeIteracoes = Integer.parseInt(args[1]); // A Machion pediu 10 iterações para 1,6milhão e 30 para o resto.
        long tempoInicio = new Date().getTime();
        for (int i = 0; i < quantidadeDeIteracoes; i++) {
            System.out.println("Teste: " + (i + 1));
            Thread[] threads = new Thread[5];
            NossoVetor vetorBuscas = new NossoVetor(tamanhoDoVetor);
            vetorBuscas.preencheVetor();
            long numeroProcurado = vetorBuscas.buscaPeloIndice(
                new Random().nextInt(tamanhoDoVetor)
            );

            Thread bubbleThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        NossoVetor v = new NossoVetor(tamanhoDoVetor);
                        v.copiaVetor(vetorBuscas);
                        long contadores = v.bubbleSort();
                        File arquivo = new File(
                            "bubblesort/" + tamanhoDoVetor + ".txt"
                        );
                        try {
                            FileWriter writer;
                            if (arquivo.createNewFile()) {
                                writer = new FileWriter(arquivo);
                            } else {
                                writer = new FileWriter(arquivo, true);
                            }
                            writer.write(Long.toString(contadores) + "\n");
                            writer.close();
                        } catch (Exception e) {}
                    }
                }
            );
            Thread insertionThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        NossoVetor v = new NossoVetor(tamanhoDoVetor);
                        v.copiaVetor(vetorBuscas);
                        long contadores = v.insertionSort();
                        File arquivo = new File(
                            "insertionsort/" + tamanhoDoVetor + ".txt"
                        );
                        try {
                            FileWriter writer;
                            if (arquivo.createNewFile()) {
                                writer = new FileWriter(arquivo);
                            } else {
                                writer = new FileWriter(arquivo, true);
                            }
                            writer.append(Long.toString(contadores) + "\n");
                            writer.close();
                        } catch (Exception e) {}
                    }
                }
            );
            Thread selectionThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        NossoVetor v = new NossoVetor(tamanhoDoVetor);
                        v.copiaVetor(vetorBuscas);
                        long contadores = v.selectionSort();
                        File arquivo = new File(
                            "selectionsort/" + tamanhoDoVetor + ".txt"
                        );
                        try {
                            FileWriter writer;
                            if (arquivo.createNewFile()) {
                                writer = new FileWriter(arquivo);
                            } else {
                                writer = new FileWriter(arquivo, true);
                            }
                            writer.write(Long.toString(contadores) + "\n");
                            writer.close();
                        } catch (Exception e) {}
                    }
                }
            );
            Thread linearSearch = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        long contadores = vetorBuscas.buscaLinear(
                            numeroProcurado
                        );
                        File arquivo = new File(
                            "linearsort/" + tamanhoDoVetor + ".txt"
                        );
                        try {
                            FileWriter writer;
                            if (arquivo.createNewFile()) {
                                writer = new FileWriter(arquivo);
                            } else {
                                writer = new FileWriter(arquivo, true);
                            }
                            writer.write(Long.toString(contadores) + "\n");
                            writer.close();
                        } catch (Exception e) {}
                    }
                }
            );
            Thread binarySearch = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        NossoVetor copiaVetorBuscas = new NossoVetor(
                            tamanhoDoVetor
                        );
                        copiaVetorBuscas.copiaVetor(vetorBuscas);
                        long contadores = copiaVetorBuscas.insertionSort();
                        contadores += copiaVetorBuscas.buscaBinaria(
                            numeroProcurado
                        );
                        File arquivo = new File(
                            "binarysort/" + tamanhoDoVetor + ".txt"
                        );
                        try {
                            FileWriter writer;
                            if (arquivo.createNewFile()) {
                                writer = new FileWriter(arquivo);
                            } else {
                                writer = new FileWriter(arquivo, true);
                            }
                            writer.write(Long.toString(contadores) + "\n");
                            writer.close();
                        } catch (Exception e) {}
                    }
                }
            );
            threads[0] = bubbleThread;
            threads[1] = insertionThread;
            threads[2] = selectionThread;
            threads[3] = linearSearch;
            threads[4] = binarySearch;
            for (var thread : threads) {
                thread.start();
            }
            try {
                for (var thread : threads) {
                    thread.join();
                }
            } catch (Exception e) {
                System.out.println("Erro ao esperar");
            }
        }
        long tempoFim = new Date().getTime();
        long tempoTotal = tempoFim - tempoInicio;
        System.out.println("Tempo Total: " + tempoTotal + "ms");
    }
}
