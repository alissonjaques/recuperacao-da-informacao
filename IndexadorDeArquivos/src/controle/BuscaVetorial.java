package controle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.BibliotecaDeMetodos;

/**
 *
 * @author Alisson Jaques
 */
public class BuscaVetorial {

    private ArrayList<String> nomeDosDocumentos;
    private ArrayList<String> conteudoDosDocumentos;
    private ArrayList<String> palavras;
    private ArrayList<Integer> numeroDeOcorrencias;
    private ArrayList<Integer> maiorDeCadaDocumento;
    private ArrayList<Double> normas;
    private ArrayList<Double> qxDj;
    private ArrayList<Double> similaridades;

    public BuscaVetorial() throws UnsupportedEncodingException, IOException {
        palavras = new ArrayList();
        maiorDeCadaDocumento = new ArrayList();
        numeroDeOcorrencias = new ArrayList();
        similaridades = new ArrayList();
        normas = new ArrayList();
        qxDj = new ArrayList();
        nomeDosDocumentos = BibliotecaDeMetodos.retornaNomeDosDocumentos();
        conteudoDosDocumentos = BibliotecaDeMetodos.retiraStopWords();
    }

    public void ordenaPorSimilaridade(String consulta) {
        double[][] matrizTfIdf = tfIdf(ocorrenciasEmArquivo(consulta), consulta);

        double somaQuadrado = 0;
        setNormas(new ArrayList());
        // prenche a lista de normas, referentes a cada documento:
        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < quantidadeDeTermos(consulta); j++) {
                somaQuadrado += matrizTfIdf[i][j] * matrizTfIdf[i][j];
            }
            getNormas().add(Math.sqrt(somaQuadrado));
            somaQuadrado = 0;
        }

        System.out.println("\nArray de Normas: \n");
        getNormas().forEach((normas1) -> {
            System.out.println(normas1);
        });

        // preenche a lista de q*dj, referentes a cada documento:
        somaQuadrado = 0;
        setQxDj(new ArrayList());
        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < quantidadeDeTermos(consulta); j++) {
                somaQuadrado += matrizTfIdf[i][j];
            }
            getQxDj().add(somaQuadrado);
            somaQuadrado = 0;
        }

        System.out.println("\nArray de q x dj : \n");
        getQxDj().forEach((qxdjs) -> {
            System.out.println(qxdjs);
        });

        double cosseno;
        setSimilaridades(new ArrayList());
        for (int i = 0; i < 143; i++) {
            if (normaDeQ(consulta) == 0.0 || getNormas().get(i) == 0.0) {
                cosseno = 0.0;
                getSimilaridades().add(cosseno);
            } else {
                cosseno = (getQxDj().get(i)) / (normaDeQ(consulta) * getNormas().get(i));
                getSimilaridades().add(cosseno);
            }
        }

        System.out.println("\nArray de similaridade : \n");
        getSimilaridades().forEach((similaridade) -> {
            System.out.println(similaridade);
        });

        setNomeDosDocumentos(BibliotecaDeMetodos.retornaNomeDosDocumentos());
        
        /**
         * Este for ordena as palavras na lista de palavras, de forma
         * decrescente, tendo como critério o número de ocorrências
         */
        for (int i = 0; i < getNomeDosDocumentos().size(); i++) { // enquanto não percorrer toda a lista de palavras faça:
            for (int j = i; j < getNomeDosDocumentos().size(); j++) { // percorre a lista de palavras a partir do elemento do for anterior
                if (getSimilaridades().get(i) < getSimilaridades().get(j)) { // se número de ocorrências da palavra atual for menor que a palavra seguinte, faça:
                    String nomeDocumento = getNomeDosDocumentos().get(i);           // guarda a palavra atual
                    double similaridade = getSimilaridades().get(i);// guarda o número de ocorrências da palavra atual

                    getNomeDosDocumentos().set(i, getNomeDosDocumentos().get(j));      // atualiza o contéudo do índice da lista de palavras atual
                    getSimilaridades().set(i, getSimilaridades().get(j)); // atualiza o contéudo do índice da lista de número de ocorrências atual

                    getNomeDosDocumentos().set(j, nomeDocumento);               // o índice seguinte recebe o contéudo guardado anteriormente
                    getSimilaridades().set(j, similaridade); // o índice seguinte recebe o contéudo guardado anteriormente         
                }
            }
        }

        System.out.println("\nDocumentos recuperados, ordenados de forma decrescente pela similaridade: \n");
        int i = 0;
        for (String nomes : getNomeDosDocumentos()) {
            System.out.println("Nome documento: " + nomes + "\t" + "Similaridade: " + getSimilaridades().get(i));
            i++;
        }

    }

    public double normaDeQ(String consulta) {
        double normaDeQ = Math.sqrt(quantidadeDeTermos(consulta));
        return normaDeQ;
    }

    public double[][] tfIdf(int[][] ocorrenciaEmArquivo, String consulta) {
        double[][] tfIdf = new double[143][quantidadeDeTermos(consulta)];
        Buscador busca = new Buscador();
        String termos[] = consulta.split(" ");

        int[][] freq = ocorrenciasEmArquivo(consulta);

        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < quantidadeDeTermos(consulta); j++) {
                if (busca.retornaQuantidadeTermoDocumento(termos[j]) == 0) {
                    tfIdf[i][j] = 0.0;
                } else {
                    // (0.5 + 0.5*(freq/freqMax) + log N/n
                    tfIdf[i][j] = (0.5 + 0.5 * ((double) freq[i][j] / (double) getMaiorDeCadaDocumento().get(i)))
                            * Math.log10((double) 143 / (double) busca.retornaQuantidadeTermoDocumento(termos[j]));

                }
            }
        }

        System.out.println("\nMatriz TF-IDF :\n");
        for (int i = 0; i < 143; i++) {
            for (int j = 0; j < quantidadeDeTermos(consulta); j++) {
                System.out.print(tfIdf[i][j] + "\t");
                if (j == quantidadeDeTermos(consulta) - 1) {
                    System.out.println();
                }

            }
        }

        return tfIdf;
    }

    public int quantidadeDeTermos(String consulta) {
        int contador = 0;
        for (int i = 0; i < consulta.length(); i++) {
            if (consulta.charAt(i) == ' ' && i == 0) {
            } else if (consulta.charAt(i) == ' ' && i == consulta.length() - 1) {
            } else if (consulta.charAt(i) == ' ') {
                contador++;
            }
        }

        return contador + 1;
    }

    public int[][] ocorrenciasEmArquivo(String consulta) {
        int ocorrenciasDosTermos[][] = new int[143][quantidadeDeTermos(consulta)];
        int linha = 0, coluna = 0;
        String termos[] = consulta.split(" ");
        ArrayList<String> conteudo = BibliotecaDeMetodos.retiraStopWords();

        Map<String, Integer> mapPalavras; // usado para contabilizar a frequência das palavras
        mapPalavras = new HashMap<String, Integer>();

        for (String conteudos : conteudo) { // enquanto não percorrer todos os arquivos html, faça:
            // depois aplica a expressão regular
            Pattern p = Pattern.compile("(\\d+)|([a-záéíóúçãõôê]+)");
            Matcher m = p.matcher(conteudos);

            // neste loop pegamos cada palavra e atualizamos o mapa de frequências
            while (m.find()) {
                String token = m.group(); // pega um token
                Integer freq = mapPalavras.get(token); // verifica se esse token já está no mapa
                if (freq != null) { // se palavra existe, atualiza a frequencia
                    mapPalavras.put(token, freq + 1);
                } else { // se palavra não existe, inseri com um novo id e freq = 1
                    mapPalavras.put(token, 1);
                }
            }

            /**
             * Este for é responsável por preencher o lista de palavras e
             * ocorrências de palavras.
             */
            mapPalavras.entrySet().stream().map((entry) -> {
                getPalavras().add(entry.getKey());
                return entry;
            }).forEachOrdered((entry) -> {
                getNumeroDeOcorrencias().add(entry.getValue());
                //System.out.println("Palavra: " + entry.getKey() + "\tFrequência=" + entry.getValue());
            });

            /**
             * Este for ordena as palavras na lista de palavras, de forma
             * decrescente, tendo como critério o número de ocorrências
             */
            for (int i = 0; i < getPalavras().size(); i++) { // enquanto não percorrer toda a lista de palavras faça:
                for (int j = i; j < getPalavras().size(); j++) { // percorre a lista de palavras a partir do elemento do for anterior
                    if (getNumeroDeOcorrencias().get(i) < getNumeroDeOcorrencias().get(j)) { // se número de ocorrências da palavra atual for menor que a palavra seguinte, faça:
                        String palavra = getPalavras().get(i);           // guarda a palavra atual
                        int frequencia = getNumeroDeOcorrencias().get(i);// guarda o número de ocorrências da palavra atual

                        getPalavras().set(i, getPalavras().get(j));      // atualiza o contéudo do índice da lista de palavras atual
                        getNumeroDeOcorrencias().set(i, getNumeroDeOcorrencias().get(j)); // atualiza o contéudo do índice da lista de número de ocorrências atual

                        getPalavras().set(j, palavra);               // o índice seguinte recebe o contéudo guardado anteriormente
                        getNumeroDeOcorrencias().set(j, frequencia); // o índice seguinte recebe o contéudo guardado anteriormente         
                    }
                }
            }

            getMaiorDeCadaDocumento().add(getNumeroDeOcorrencias().get(0));

            int i = 0;

            for (int j = 0; j < quantidadeDeTermos(consulta); j++) {
                for (String palavra : getPalavras()) {
                    if (termos[j].equals(palavra)) {
                        ocorrenciasDosTermos[linha][coluna] = getNumeroDeOcorrencias().get(i);
                    }

                    i++;
                }
                i = 0;
                coluna++;
                if (coluna > quantidadeDeTermos(consulta) - 1) {
                    coluna = 0;
                    linha++;
                }
            }

            setPalavras(new ArrayList());
            setNumeroDeOcorrencias(new ArrayList());
            mapPalavras = new HashMap<String, Integer>();
        } // fim do for

        return ocorrenciasDosTermos;
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

    /**
     * @return the conteudoDosDocumentos
     */
    public ArrayList<String> getConteudoDosDocumentos() {
        return conteudoDosDocumentos;
    }

    /**
     * @param conteudoDosDocumentos the conteudoDosDocumentos to set
     */
    public void setConteudoDosDocumentos(ArrayList<String> conteudoDosDocumentos) {
        this.conteudoDosDocumentos = conteudoDosDocumentos;
    }

    /**
     * @return the palavras
     */
    public ArrayList<String> getPalavras() {
        return palavras;
    }

    /**
     * @return the normas
     */
    public ArrayList<Double> getNormas() {
        return normas;
    }

    /**
     * @param normas the normas to set
     */
    public void setNormas(ArrayList<Double> normas) {
        this.normas = normas;
    }

    /**
     * @return the qxDj
     */
    public ArrayList<Double> getQxDj() {
        return qxDj;
    }

    /**
     * @param qxDj the qxDj to set
     */
    public void setQxDj(ArrayList<Double> qxDj) {
        this.qxDj = qxDj;
    }

    /**
     * @param palavras the palavras to set
     */
    public void setPalavras(ArrayList<String> palavras) {
        this.palavras = palavras;
    }

    /**
     * @return the numeroDeOcorrencias
     */
    public ArrayList<Integer> getNumeroDeOcorrencias() {
        return numeroDeOcorrencias;
    }

    /**
     * @param numeroDeOcorrencias the numeroDeOcorrencias to set
     */
    public void setNumeroDeOcorrencias(ArrayList<Integer> numeroDeOcorrencias) {
        this.numeroDeOcorrencias = numeroDeOcorrencias;
    }

    /**
     * @return the maiorDeCadaDocumento
     */
    public ArrayList<Integer> getMaiorDeCadaDocumento() {
        return maiorDeCadaDocumento;
    }

    /**
     * @param maiorDeCadaDocumento the maiorDeCadaDocumento to set
     */
    public void setMaiorDeCadaDocumento(ArrayList<Integer> maiorDeCadaDocumento) {
        this.maiorDeCadaDocumento = maiorDeCadaDocumento;
    }

    /**
     * @return the similaridades
     */
    public ArrayList<Double> getSimilaridades() {
        return similaridades;
    }

    /**
     * @param similaridades the similaridades to set
     */
    public void setSimilaridades(ArrayList<Double> similaridades) {
        this.similaridades = similaridades;
    }
}
