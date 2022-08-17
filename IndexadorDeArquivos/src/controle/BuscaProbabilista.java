package controle;

import java.io.IOException;
import java.util.ArrayList;
import utils.BibliotecaDeMetodos;

/**
 *
 * @author Alisson Jaques
 */
public class BuscaProbabilista {

    private BuscaVetorial base;
    private double[] vetorDeSimilaridade;
    private ArrayList<String> nomeDosDocumentos;
    private ArrayList<Integer> N;

    public BuscaProbabilista() throws IOException {
        base = new BuscaVetorial();
        nomeDosDocumentos = BibliotecaDeMetodos.retornaNomeDosDocumentos();
        vetorDeSimilaridade = new double[143];
        N = new ArrayList();
    }

    public int[][] matrizBinaria(String consulta) {
        int[][] ocorrenciasEmArquivos = getBase().ocorrenciasEmArquivo(consulta);
        int[][] matrizBinaria = new int[143][getBase().quantidadeDeTermos(consulta)];
        String termos[] = consulta.split(" ");

        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < termos.length; j++) {
                if (ocorrenciasEmArquivos[i][j] > 0) {
                    matrizBinaria[i][j] = 1;
                } else {
                    matrizBinaria[i][j] = 0;
                }
            }
        }
        System.out.println("Matriz Binaria:");
        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < termos.length; j++) {
                System.out.print(matrizBinaria[i][j] + "\t");
            }
            System.out.println();
        }
        return matrizBinaria;
    }

    public int[] vetorN(int[][] matrizBinaria, String consulta) {
        String termos[] = consulta.split(" ");
        int[] vetorN = new int[termos.length];

        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < termos.length; j++) {
                if (matrizBinaria[i][j] == 1) {
                    vetorN[j] = vetorN[j] + 1;
                }
            }
        }

        System.out.println("\nVetor de n:");
        for (int i = 0; i < vetorN.length; i++) {
            System.out.print(vetorN[i] + "\t");
        }
        System.out.println();
        return vetorN;
    }

    public double[] vetorDePesos(int[] vetorN, String consulta) {
        String termos[] = consulta.split(" ");
        double[] vetorDePesos = new double[termos.length];
        double log10;
        for (int i = 0; i < vetorN.length; i++) {
            log10 = Math.log10((((double) vetorN[i]) / 143));
            if (log10 < 0) {
                vetorDePesos[i] = (-1) * log10;
            } else {
                vetorDePesos[i] = (-1) * log10;
            }
        }

        System.out.println("\nVetor de Pesos: ");

        for (int i = 0; i < vetorN.length; i++) {
            System.out.print(vetorDePesos[i] + "\t");
        }

        System.out.println();
        return vetorDePesos;
    }

    public void geraVetorDeSimilaridade(String consulta) {
        String termos[] = consulta.split(" ");
        int[][] matrizBinaria = matrizBinaria(consulta);
        double[] vetorDePesos = vetorDePesos(vetorN(matrizBinaria, consulta), consulta);
        
        preencheComZeros(vetorDeSimilaridade);
        
        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < termos.length; j++) {
                vetorDeSimilaridade[i] += (double) vetorDePesos[j] * (double) matrizBinaria[i][j];
            }
        }
        
        nomeDosDocumentos = BibliotecaDeMetodos.retornaNomeDosDocumentos();
        
        for (int i = 0; i < getNomeDosDocumentos().size(); i++) {
            for (int j = i; j < getNomeDosDocumentos().size(); j++) {
                if (vetorDeSimilaridade[i] < vetorDeSimilaridade[j]) {
                    String nomeDocumento = getNomeDosDocumentos().get(i);
                    double similaridade = vetorDeSimilaridade[i];

                    getNomeDosDocumentos().set(i, getNomeDosDocumentos().get(j));
                    vetorDeSimilaridade[i] = vetorDeSimilaridade[j];

                    getNomeDosDocumentos().set(j, nomeDocumento);
                    vetorDeSimilaridade[j] = similaridade;
                }
            }
        }

        System.out.println("\nDocumentos Ordenados pela Similaridade: ");
        for (int i = 0; i < getNomeDosDocumentos().size(); i++) {
            System.out.println("Documento: " + getNomeDosDocumentos().get(i) + "\tSimilaridade: " + vetorDeSimilaridade[i]);
        }
    }
    
    public void preencheComZeros(int[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = 0;
        }
    }
    
     public void preencheComZeros(double[] vetor) {
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = 0.0;
        }
    }

    
    /**
     * @return the N
     */
    public ArrayList<Integer> getN() {
        return N;
    }

    /**
     * @param N the N to set
     */
    public void setN(ArrayList<Integer> N) {
        this.N = N;
    }

    /**
     * @return the base
     */
    public BuscaVetorial getBase() {
        return base;
    }

    /**
     * @param base the base to set
     */
    public void setBase(BuscaVetorial base) {
        this.base = base;
    }

    /**
     * @return the vetorDeSimilaridade
     */
    public double[] getVetorDeSimilaridade() {
        return vetorDeSimilaridade;
    }

    /**
     * @param vetorDeSimilaridade the vetorDeSimilaridade to set
     */
    public void setVetorDeSimilaridade(double[] vetorDeSimilaridade) {
        this.vetorDeSimilaridade = vetorDeSimilaridade;
    }

    /**
     * @return the nomeDosDocumentos
     */
    public ArrayList<String> getNomeDosDocumentos() {
        return nomeDosDocumentos;
    }

    /**
     * @param nomeDosDocumentos the nomeDosDocumentos to set
     */
    public void setNomeDosDocumentos(ArrayList<String> nomeDosDocumentos) {
        this.nomeDosDocumentos = nomeDosDocumentos;
    }
}
