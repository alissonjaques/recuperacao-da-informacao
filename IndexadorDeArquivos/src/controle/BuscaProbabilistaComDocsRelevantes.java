package controle;

import java.io.IOException;
import java.util.ArrayList;
import utils.BibliotecaDeMetodos;

/**
 *
 * @author Alisson
 */
public class BuscaProbabilistaComDocsRelevantes extends BuscaProbabilista{
    
    private double[] vetorDeSimilaridade;
    private ArrayList<String> nomeDosDocumentos;
    
    public BuscaProbabilistaComDocsRelevantes() throws IOException{
        super();
        nomeDosDocumentos = BibliotecaDeMetodos.retornaNomeDosDocumentos();
        vetorDeSimilaridade =  vetorDeSimilaridade = new double[143];
    }
    
    public int[] vetorRel(ArrayList<String> documentosRelevantes) {
        int[] vetorRel = new int[143];
        preencheComZeros(vetorRel);

        System.out.println("Vetor de Documentos Relavantes: ");
        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < documentosRelevantes.size(); j++) {
                if (documentosRelevantes.get(j).equals(getNomeDosDocumentos().get(i))) {
                    vetorRel[i] = 1;
                }
            }

            System.out.println(vetorRel[i]);
        }

        return vetorRel;
    }
    
    public double[] vetorR(ArrayList<String> documentosRelevantes, String consulta) {
        String termos[] = consulta.split(" ");
        int[] r = new int[termos.length];
        double[] vetorR = new double[termos.length];
        int[][] matrizBinaria = matrizBinaria(consulta);
        int contador = 0;
        
        // pensar
        int k = 0;
        for (int i = 0; i < documentosRelevantes.size(); i++) {
            for (int j = 0; j < 143; j++) {
                if (documentosRelevantes.get(i).equals(getNomeDosDocumentos().get(j))) {
                    if (matrizBinaria[j][k] == 1) {
                        contador++;
                    }                    
                }
            }
            r[i] = contador;
            contador = 0;
            
        }

        for (int i = 0; i < r.length; i++) {
            if (r[i] > 0) {
                vetorR[i] = (double) r[i] - 0.1;
                getN().add(144);
            } else {
                vetorR[i] = (double) r[i];
                getN().add(143);
            }
        }

        System.out.println("\nVetor de r: ");

        for (int i = 0; i < vetorR.length; i++) {
            System.out.println(vetorR[i]);
        }
        return vetorR;
    }

    public double[] vetorPesosComDocumentosRelevantes(double[] vetorR, int[] vetorN,
            String consulta, ArrayList<String> documentosRelevantes) {

        String termos[] = consulta.split(" ");
        double[] vetorDePesosR = new double[termos.length];

        int[] vetorRel = vetorRel(documentosRelevantes);
        int R = calculaR(vetorRel);

        for (int i = 0; i < vetorDePesosR.length; i++) {
            vetorDePesosR[i] = Math.log10((vetorR[i] * (getN().get(i) - R - vetorN[i]
                    + vetorR[i])) / ((vetorN[i] - vetorR[i]) * (R - vetorR[i])));
            if(vetorDePesosR[i]==Double.NEGATIVE_INFINITY){
                vetorDePesosR[i] = -10.0;
            }
        }
        
        System.out.println("\nVetor de Pesos com Documentos Relevantes: ");
        for(int i = 0; i < vetorDePesosR.length; i++){
            System.out.println(vetorDePesosR[i]);
        }
        return vetorDePesosR;
    }

    public int calculaR(int[] vetorRel) {
        int R = 0;

        for (int i = 0; i < vetorRel.length; i++) {
            if (vetorRel[i] == 1) {
                R++;
            }
        }

        return R;
    }
    
    public void vetorDeSimilaridadeComDocumentosRelevantes(String consulta, 
            ArrayList<String> documentosRelevantes){
        
        String termos[] = consulta.split(" ");
        int[][] matrizBinaria = matrizBinaria(consulta);
        double[] vetorR = vetorR(documentosRelevantes,consulta);
        int[] vetorN = vetorN(matrizBinaria, consulta);
        double [] vetorDePesos = vetorPesosComDocumentosRelevantes(vetorR,vetorN,consulta,documentosRelevantes);
        
        preencheComZeros(vetorDeSimilaridade);
        
        for(int i = 0; i < 143; i++){
            for(int j = 0; j<termos.length; j++){
                getVetorDeSimilaridade()[i] += vetorDePesos[j]*matrizBinaria[i][j];
            }
        }
        
        
        setNomeDosDocumentos(BibliotecaDeMetodos.retornaNomeDosDocumentos());
        
        for (int i = 0; i < getNomeDosDocumentos().size(); i++) {
            for (int j = i; j < getNomeDosDocumentos().size(); j++) {
                if (getVetorDeSimilaridade()[i] < getVetorDeSimilaridade()[j]) {
                    String nomeDocumento = getNomeDosDocumentos().get(i);
                    double similaridade = getVetorDeSimilaridade()[i];

                    getNomeDosDocumentos().set(i, getNomeDosDocumentos().get(j));
                    getVetorDeSimilaridade()[i] = getVetorDeSimilaridade()[j];

                    getNomeDosDocumentos().set(j, nomeDocumento);
                    getVetorDeSimilaridade()[j] = similaridade;
                }
            }
        }

        System.out.println("\nDocumentos Ordenados pela Similaridade: ");
        for (int i = 0; i < getNomeDosDocumentos().size(); i++) {
            System.out.println("Documento: " + getNomeDosDocumentos().get(i) + "\tSimilaridade: " + getVetorDeSimilaridade()[i]);
        }
        
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
