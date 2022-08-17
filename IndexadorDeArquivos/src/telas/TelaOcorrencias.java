package telas;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import utils.BibliotecaDeMetodos;

/**
 * A classe TelaOcorrencias representa uma interface gr�fica que permite a gera��o
 do relat�rio de �ndice Invertido, sendo este decrescente por n�mero de
 ocorr�ncias.
 *
 * @author Alisson
 */
public class TelaOcorrencias extends javax.swing.JInternalFrame { // declara a classe

    /**
     * Campos da classe
     */
    private ArrayList<String> palavras;             // as palavras presentes nos corpus de documentos
    private ArrayList<Integer> numeroDeOcorrencias; // lista de ocorr�ncias por palavra
    private boolean botao;                          // bot�o controlador 

    /**
     * O construtor default para a classe Ocorrencias que inicializa todos os
     * seus campos com valores default.
     */
    public TelaOcorrencias() {
        botao = false;
        palavras = new ArrayList<>();
        numeroDeOcorrencias = new ArrayList<>();
        initComponents();
    }

    public static ArrayList<String> retornaNomeDosDocumentos() {
        File arquivos[];                                // a cole��o de arquivos html
        File diretorio = new File("src\\arquivosHTML"); // o diret�rio raiz dos arquivos html
        arquivos = diretorio.listFiles();               // a cole��o passa a apontar para o diret�rio raiz
        ArrayList<String> nomeArquivo = new ArrayList<>();
        String temp;
        String soma = "";
        int contador = 0;
        Boolean botao = false;
        for (File arquivo : arquivos) {
            temp = arquivo.toString();
            for (int i = 0; i < arquivo.toString().length(); i++) {
                if (temp.charAt(i) == '\\') {
                    contador++;
                }
                if (contador == 2) {
                    if (botao) {
                        soma += temp.charAt(i);
                    } else {
                        botao = true;
                    }
                }
            }
            nomeArquivo.add(soma);
            contador = 0;
            soma = "";
            botao = false;
        }
        return nomeArquivo;
    }

    public static ArrayList<String> retornaConteudoDosDocumentos() {
        File arquivos[];                                // a cole��o de arquivos html
        File diretorio = new File("src\\arquivosHTML"); // o diret�rio raiz dos arquivos html
        arquivos = diretorio.listFiles();               // a cole��o passa a apontar para o diret�rio raiz
        ArrayList<String> conteudo = new ArrayList<>();

        Map<String, Integer> mapPalavras; // usado para contabilizar a frequ�ncia das palavras
        mapPalavras = new HashMap<String, Integer>();

        for (File arquivo : arquivos) { // enquanto n�o percorrer todos os arquivos html, fa�a:
            StringBuilder contentBuilder = new StringBuilder(); // cria uma inst�ncia da classe StringBuilder
            try { // tente:
                BufferedReader in = new BufferedReader(new FileReader(arquivo)); // in possibilita a leitura do arquivo html
                String str; // string auxiliar
                while ((str = in.readLine()) != null) { // enquanto n�o ler toda a linha do arquivo html, fa�a:
                    contentBuilder.append(str);  // adiciona o conte�do em contentBuilder
                }
                in.close(); // fecha in
            } catch (IOException e) { // sen�o der a tentativa, fa�a a exce��o:

            }

            String content = contentBuilder.toString(); // content recebe o conte�do literal do html, em formato de texto
            content = formata(content); // content agora est� com todos os caracteres especiais devidamente representados
            String textoOk = retira(content); // textoOk agora � o arquivo html sem tags html e com caractes especiais ok
            System.out.println(textoOk); // imprime no terminal, para log, o cont�udo formatado do arquivo html
            String textoEmMinusculo = textoOk.toLowerCase(); // transforma todo o conte�do html em letras min�sculas
            conteudo.add(textoEmMinusculo);
        }
        return conteudo;
    }

    /*
    *   O m�todo geraIndiceInvertido() gera a lista de palavra / n�mero de ocorr�ncias apresentando
    *   a tabela ao usu�rio da interface.
     */
    public void geraIndiceInvertido() throws IOException {
        File arquivos[];                                // a cole��o de arquivos html
        File diretorio = new File("src\\arquivosHTML"); // o diret�rio raiz dos arquivos html
        arquivos = diretorio.listFiles();               // a cole��o passa a apontar para o diret�rio raiz

        Map<String, Integer> mapPalavras; // usado para contabilizar a frequ�ncia das palavras
        mapPalavras = new HashMap<String, Integer>();

        for (File arquivo : arquivos) { // enquanto n�o percorrer todos os arquivos html, fa�a:
            StringBuilder contentBuilder = new StringBuilder(); // cria uma inst�ncia da classe StringBuilder
            try { // tente:
                BufferedReader in = new BufferedReader(new FileReader(arquivo)); // in possibilita a leitura do arquivo html
                String str; // string auxiliar
                while ((str = in.readLine()) != null) { // enquanto n�o ler toda a linha do arquivo html, fa�a:
                    contentBuilder.append(str);  // adiciona o conte�do em contentBuilder
                }
                in.close(); // fecha in
            } catch (IOException e) { // sen�o der a tentativa, fa�a a exce��o:

            }

            String content = contentBuilder.toString(); // content recebe o conte�do literal do html, em formato de texto
            content = formataDados(content); // content agora est� com todos os caracteres especiais devidamente representados
            String textoOk = retiraTags(content); // textoOk agora � o arquivo html sem tags html e com caractes especiais ok
            System.out.println(textoOk); // imprime no terminal, para log, o cont�udo formatado do arquivo html
            String textoEmMinusculo = textoOk.toLowerCase(); // transforma todo o conte�do html em letras min�sculas
            textoEmMinusculo = BibliotecaDeMetodos.retiraStopWordsDeUmTexto(textoEmMinusculo);
            // depois aplica a express�o regular
            Pattern p = Pattern.compile("(\\d+)|([a-z����������]+)");
            Matcher m = p.matcher(textoEmMinusculo);

            // neste loop pegamos cada palavra e atualizamos o mapa de frequ�ncias
            while (m.find()) {
                String token = m.group(); // pega um token
                Integer freq = mapPalavras.get(token); // verifica se esse token j� est� no mapa
                if (freq != null) { // se palavra existe, atualiza a frequencia
                    mapPalavras.put(token, freq + 1);
                } else { // se palavra n�o existe, inseri com um novo id e freq = 1
                    mapPalavras.put(token, 1);
                }
            }
        } // fim do for

        /**
         * Este for � respons�vel por preencher o lista de palavras e
         * ocorr�ncias de palavras.
         */
        mapPalavras.entrySet().stream().map((entry) -> {
            getPalavras().add(entry.getKey());
            return entry;
        }).forEachOrdered((entry) -> {
            getNumeroDeOcorrencias().add(entry.getValue());
            //System.out.println("Palavra: " + entry.getKey() + "\tFrequ�ncia=" + entry.getValue());
        });

        /**
         * Este for ordena as palavras na lista de palavras, de forma
         * decrescente, tendo como crit�rio o n�mero de ocorr�ncias
         */
        for (int i = 0; i < getPalavras().size(); i++) { // enquanto n�o percorrer toda a lista de palavras fa�a:
            for (int j = i; j < getPalavras().size(); j++) { // percorre a lista de palavras a partir do elemento do for anterior
                if (getNumeroDeOcorrencias().get(i) < getNumeroDeOcorrencias().get(j)) { // se n�mero de ocorr�ncias da palavra atual for menor que a palavra seguinte, fa�a:
                    String palavra = getPalavras().get(i);           // guarda a palavra atual
                    int frequencia = getNumeroDeOcorrencias().get(i);// guarda o n�mero de ocorr�ncias da palavra atual

                    getPalavras().set(i, getPalavras().get(j));      // atualiza o cont�udo do �ndice da lista de palavras atual
                    getNumeroDeOcorrencias().set(i, getNumeroDeOcorrencias().get(j)); // atualiza o cont�udo do �ndice da lista de n�mero de ocorr�ncias atual

                    getPalavras().set(j, palavra);               // o �ndice seguinte recebe o cont�udo guardado anteriormente
                    getNumeroDeOcorrencias().set(j, frequencia); // o �ndice seguinte recebe o cont�udo guardado anteriormente         
                }
            }
        }

        // imprime no terminal, para log, a lista de palavras/n�mero de ocorr�ncias
        for (int i = 0; i < getPalavras().size(); i++) {
            System.out.println("Palavra: " + getPalavras().get(i) + "\tFrequ�ncia=" + getNumeroDeOcorrencias().get(i));
        }

        if (isBotao()) { // se o bot�o estiver ligado
            limparTabela(); // limpa a tabela de interface do usu�rio
        }

        preencherRelatorio(); // preenche a tabela de interface do usu�rio

        setPalavras(new ArrayList<>()); // seta a lista de palavras para um novo ciclo
        setNumeroDeOcorrencias(new ArrayList<>()); // seta a lista de n�mero de ocorr�ncias para um novo ciclo
    } // fim do m�todo geraIndiceInvertido()

    /**
     * O m�todo limparTabela() limpa a tabela de interface do usu�rio
     * (palavras/n�mero de ocorr�ncias)
     */
    public void limparTabela() {
        DefaultTableModel dm = (DefaultTableModel) getjTocorrencia().getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        setBotao(false); // apaga o botao
    }

    /**
     * O m�todo preencherRelatorio() preenche a tabela de interface do usu�rio.
     */
    public void preencherRelatorio() {
        setBotao(true); // acende o botao
        DefaultTableModel model = (DefaultTableModel) getjTocorrencia().getModel();
        int i = 0;
        for (String palavra : getPalavras()) {
            model.addRow(new Object[]{palavra, getNumeroDeOcorrencias().get(i)});
            i++;
        }
    }

    /**
     * O m�todo formataDados(String) recebe como argumento um texto em html e
     * retorna um texto com todos os caracteres especiais devidamente
     * representados.
     *
     * @param texto um texto em html com caracteres especiais n�o reconhecidos
     * @return um texto em html com os caracteres especiais devidamente
     * representados
     */
    public String formataDados(String texto) {
        texto = texto.replaceAll("&Agrave;", "�");
        texto = texto.replaceAll("&Aacute;", "�");
        texto = texto.replaceAll("&Acirc;", "�");
        texto = texto.replaceAll("&Atilde;", "�");
        texto = texto.replaceAll("&Auml;", "�");
        texto = texto.replaceAll("&Aring;", "�");
        texto = texto.replaceAll("&agrave;", "�");
        texto = texto.replaceAll("&aacute;", "�");
        texto = texto.replaceAll("&acirc;", "�");
        texto = texto.replaceAll("&atilde;", "�");
        texto = texto.replaceAll("&auml;", "�");
        texto = texto.replaceAll("&aring;", "�");
        texto = texto.replaceAll("&Ccedil;", "�");
        texto = texto.replaceAll("&ccedil;", "�");

        texto = texto.replaceAll("&Egrave;", "�");
        texto = texto.replaceAll("&Eacute;", "�");
        texto = texto.replaceAll("&Ecirc;", "�");
        texto = texto.replaceAll("&Euml;", "�");
        texto = texto.replaceAll("&egrave;", "�");
        texto = texto.replaceAll("&eacute;", "�");
        texto = texto.replaceAll("&ecirc;", "�");
        texto = texto.replaceAll("&euml;", "�");
        texto = texto.replaceAll("&Igrave;", "�");
        texto = texto.replaceAll("&Iacute;", "�");
        texto = texto.replaceAll("&Icirc;", "�");
        texto = texto.replaceAll("&Iuml;", "�");
        texto = texto.replaceAll("&igrave;", "�");
        texto = texto.replaceAll("&iacute;", "�");
        texto = texto.replaceAll("&icirc;", "�");
        texto = texto.replaceAll("&iuml;", "�");

        texto = texto.replaceAll("&Ntilde;", "�");
        texto = texto.replaceAll("&ntilde;", "�");
        texto = texto.replaceAll("&Ograve;", "�");
        texto = texto.replaceAll("&Oacute;", "�");
        texto = texto.replaceAll("&Ocirc;", "�");
        texto = texto.replaceAll("&Otilde;", "�");
        texto = texto.replaceAll("&Ouml;", "�");
        texto = texto.replaceAll("&ograve;", "�");
        texto = texto.replaceAll("&oacute;", "�");
        texto = texto.replaceAll("&ocirc;", "�");
        texto = texto.replaceAll("&otilde;", "�");
        texto = texto.replaceAll("&ouml;", "�");
        texto = texto.replaceAll("&Oslash;", "�");
        texto = texto.replaceAll("&oslash;", "�");
        texto = texto.replaceAll("&#140", "?");
        texto = texto.replaceAll("&#156", "?");
        texto = texto.replaceAll("&#138", "?");
        texto = texto.replaceAll("&#154", "?");

        texto = texto.replaceAll("&Ugrave;", "�");
        texto = texto.replaceAll("&Uacute;", "�");
        texto = texto.replaceAll("&Ucirc;", "�");
        texto = texto.replaceAll("&Uuml;", "�");
        texto = texto.replaceAll("&ugrave;", "�");
        texto = texto.replaceAll("&uacute;", "�");
        texto = texto.replaceAll("&ucirc;", "�");
        texto = texto.replaceAll("&uuml;", "�");
        texto = texto.replaceAll("&#181", "�");
        texto = texto.replaceAll("&#215", "�");
        texto = texto.replaceAll("&Yacute;", "�");
        texto = texto.replaceAll("&#159;", "?");
        texto = texto.replaceAll("&yacute;", "�");
        texto = texto.replaceAll("&yuml;", "�");

        texto = texto.replaceAll("&#176", "�");
        texto = texto.replaceAll("&#134", "?");
        texto = texto.replaceAll("&#135", "?");
        texto = texto.replaceAll("&lt", "<");
        texto = texto.replaceAll("&gt", ">");
        texto = texto.replaceAll("&#177", "�");
        texto = texto.replaceAll("&#171", "�");
        texto = texto.replaceAll("&#187", "�");
        texto = texto.replaceAll("&#191", "�");
        texto = texto.replaceAll("&#161", "�");
        texto = texto.replaceAll("&#183", "�");
        texto = texto.replaceAll("&#149", "?");
        texto = texto.replaceAll("&#153", "?");
        texto = texto.replaceAll("&copy", "�");
        texto = texto.replaceAll("&reg", "�");
        texto = texto.replaceAll("&#167", "�");
        texto = texto.replaceAll("&#182", "�");

        texto = texto.replaceAll(";", "");
        texto = texto.replaceAll("&raquo", "");
        texto = texto.replaceAll("&#187", "");
        texto = texto.replaceAll("&#x000BB", "");
        texto = texto.replaceAll("&laquo", "");
        texto = texto.replaceAll("&#xAB", "");
        texto = texto.replaceAll("&#171", "");
        texto = texto.replaceAll("\\.", "");
        texto = texto.replaceAll("\\(", "");
        texto = texto.replaceAll("\\)", "");
        texto = texto.replaceAll("\\?", "");
        texto = texto.replaceAll("\\!", "");
        texto = texto.replaceAll("\\[", "");
        texto = texto.replaceAll("\\]", "");
        texto = texto.replaceAll("\\-", " ");
        texto = texto.replaceAll("�", "");
        texto = texto.replaceAll("�", "");
        texto = texto.replaceAll("", "");
        texto = texto.replaceAll("\\:", "");
        texto = texto.replaceAll(",", "");

        return texto;
    }

    /**
     * O m�todo formataDados(String) recebe como argumento um texto em html e
     * retorna um texto com todos os caracteres especiais devidamente
     * representados.
     *
     * @param texto um texto em html com caracteres especiais n�o reconhecidos
     * @return um texto em html com os caracteres especiais devidamente
     * representados
     */
    public static String formata(String texto) {
        texto = texto.replaceAll("&Agrave;", "�");
        texto = texto.replaceAll("&Aacute;", "�");
        texto = texto.replaceAll("&Acirc;", "�");
        texto = texto.replaceAll("&Atilde;", "�");
        texto = texto.replaceAll("&Auml;", "�");
        texto = texto.replaceAll("&Aring;", "�");
        texto = texto.replaceAll("&agrave;", "�");
        texto = texto.replaceAll("&aacute;", "�");
        texto = texto.replaceAll("&acirc;", "�");
        texto = texto.replaceAll("&atilde;", "�");
        texto = texto.replaceAll("&auml;", "�");
        texto = texto.replaceAll("&aring;", "�");
        texto = texto.replaceAll("&Ccedil;", "�");
        texto = texto.replaceAll("&ccedil;", "�");

        texto = texto.replaceAll("&Egrave;", "�");
        texto = texto.replaceAll("&Eacute;", "�");
        texto = texto.replaceAll("&Ecirc;", "�");
        texto = texto.replaceAll("&Euml;", "�");
        texto = texto.replaceAll("&egrave;", "�");
        texto = texto.replaceAll("&eacute;", "�");
        texto = texto.replaceAll("&ecirc;", "�");
        texto = texto.replaceAll("&euml;", "�");
        texto = texto.replaceAll("&Igrave;", "�");
        texto = texto.replaceAll("&Iacute;", "�");
        texto = texto.replaceAll("&Icirc;", "�");
        texto = texto.replaceAll("&Iuml;", "�");
        texto = texto.replaceAll("&igrave;", "�");
        texto = texto.replaceAll("&iacute;", "�");
        texto = texto.replaceAll("&icirc;", "�");
        texto = texto.replaceAll("&iuml;", "�");

        texto = texto.replaceAll("&Ntilde;", "�");
        texto = texto.replaceAll("&ntilde;", "�");
        texto = texto.replaceAll("&Ograve;", "�");
        texto = texto.replaceAll("&Oacute;", "�");
        texto = texto.replaceAll("&Ocirc;", "�");
        texto = texto.replaceAll("&Otilde;", "�");
        texto = texto.replaceAll("&Ouml;", "�");
        texto = texto.replaceAll("&ograve;", "�");
        texto = texto.replaceAll("&oacute;", "�");
        texto = texto.replaceAll("&ocirc;", "�");
        texto = texto.replaceAll("&otilde;", "�");
        texto = texto.replaceAll("&ouml;", "�");
        texto = texto.replaceAll("&Oslash;", "�");
        texto = texto.replaceAll("&oslash;", "�");
        texto = texto.replaceAll("&#140", "?");
        texto = texto.replaceAll("&#156", "?");
        texto = texto.replaceAll("&#138", "?");
        texto = texto.replaceAll("&#154", "?");

        texto = texto.replaceAll("&Ugrave;", "�");
        texto = texto.replaceAll("&Uacute;", "�");
        texto = texto.replaceAll("&Ucirc;", "�");
        texto = texto.replaceAll("&Uuml;", "�");
        texto = texto.replaceAll("&ugrave;", "�");
        texto = texto.replaceAll("&uacute;", "�");
        texto = texto.replaceAll("&ucirc;", "�");
        texto = texto.replaceAll("&uuml;", "�");
        texto = texto.replaceAll("&#181", "�");
        texto = texto.replaceAll("&#215", "�");
        texto = texto.replaceAll("&Yacute;", "�");
        texto = texto.replaceAll("&#159;", "?");
        texto = texto.replaceAll("&yacute;", "�");
        texto = texto.replaceAll("&yuml;", "�");

        texto = texto.replaceAll("&#176", "�");
        texto = texto.replaceAll("&#134", "?");
        texto = texto.replaceAll("&#135", "?");
        texto = texto.replaceAll("&lt", "<");
        texto = texto.replaceAll("&gt", ">");
        texto = texto.replaceAll("&#177", "�");
        texto = texto.replaceAll("&#171", "�");
        texto = texto.replaceAll("&#187", "�");
        texto = texto.replaceAll("&#191", "�");
        texto = texto.replaceAll("&#161", "�");
        texto = texto.replaceAll("&#183", "�");
        texto = texto.replaceAll("&#149", "?");
        texto = texto.replaceAll("&#153", "?");
        texto = texto.replaceAll("&copy", "�");
        texto = texto.replaceAll("&reg", "�");
        texto = texto.replaceAll("&#167", "�");
        texto = texto.replaceAll("&#182", "�");

        texto = texto.replaceAll(";", "");
        texto = texto.replaceAll("&raquo", "");
        texto = texto.replaceAll("&#187", "");
        texto = texto.replaceAll("&#x000BB", "");
        texto = texto.replaceAll("&laquo", "");
        texto = texto.replaceAll("&#xAB", "");
        texto = texto.replaceAll("&#171", "");
        texto = texto.replaceAll("\\.", "");
        texto = texto.replaceAll("\\(", "");
        texto = texto.replaceAll("\\)", "");
        texto = texto.replaceAll("\\?", "");
        texto = texto.replaceAll("\\!", "");
        texto = texto.replaceAll("\\[", "");
        texto = texto.replaceAll("\\]", "");
        texto = texto.replaceAll("\\-", " ");
        texto = texto.replaceAll("�", "");
        texto = texto.replaceAll("�", "");
        texto = texto.replaceAll("", "");
        texto = texto.replaceAll("\\:", "");
        texto = texto.replaceAll(",", "");

        return texto;
    }

    /**
     * O m�todo retiraTags(String) recebe como argumento um texto em html e
     * retorna um texto correspondente sem as tags html.
     *
     * @param html um texto em html
     * @return um texto correspondente sem as tags html
     */
    public String retiraTags(String html) {
        return Jsoup.parse(html).text();
    }
    
    /**
     * O m�todo retiraTags(String) recebe como argumento um texto em html e
     * retorna um texto correspondente sem as tags html.
     *
     * @param html um texto em html
     * @return um texto correspondente sem as tags html
     */
    public static String retira(String html) {
        return Jsoup.parse(html).text();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPResultado = new javax.swing.JPanel();
        JLResultado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTocorrencia = new javax.swing.JTable();
        jBgravar = new javax.swing.JButton();
        jBlimpar = new javax.swing.JButton();
        jBsair = new javax.swing.JButton();

        setIconifiable(true);
        setTitle("Gerador de �ndice Invertido");

        JLResultado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        JLResultado.setText("Ranking de ocorr�ncias");

        jTocorrencia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTocorrencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Palavra", "Ocorr�ncia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTocorrencia);

        jBgravar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBgravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/gerar.png"))); // NOI18N
        jBgravar.setText("Gerar �ndice ");
        jBgravar.setMaximumSize(new java.awt.Dimension(83, 23));
        jBgravar.setMinimumSize(new java.awt.Dimension(83, 23));
        jBgravar.setPreferredSize(new java.awt.Dimension(83, 23));
        jBgravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarActionPerformed(evt);
            }
        });

        jBlimpar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBlimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/limpar-limpo.png"))); // NOI18N
        jBlimpar.setText("Limpar");
        jBlimpar.setEnabled(false);
        jBlimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBlimparActionPerformed(evt);
            }
        });

        jBsair.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBsair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sair.png"))); // NOI18N
        jBsair.setText("Sair");
        jBsair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBsairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPResultadoLayout = new javax.swing.GroupLayout(jPResultado);
        jPResultado.setLayout(jPResultadoLayout);
        jPResultadoLayout.setHorizontalGroup(
            jPResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPResultadoLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(JLResultado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPResultadoLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPResultadoLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jBgravar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBlimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBsair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
        jPResultadoLayout.setVerticalGroup(
            jPResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPResultadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JLResultado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBsair)
                    .addComponent(jBlimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBgravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPResultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPResultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBlimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBlimparActionPerformed
        limparTabela();
        getjBgravar().setEnabled(true);
        getjBlimpar().setEnabled(false);
    }//GEN-LAST:event_jBlimparActionPerformed

    private void jBsairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBsairActionPerformed
        int op = JOptionPane.showConfirmDialog(this, "Deseja mesmo sair?", "Sair", JOptionPane.OK_CANCEL_OPTION);

        if (op == 0) {
            dispose();
        }
    }//GEN-LAST:event_jBsairActionPerformed

    private void jBgravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBgravarActionPerformed
        try {
            geraIndiceInvertido();
            getjBgravar().setEnabled(false);
            getjBlimpar().setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(TelaOcorrencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBgravarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLResultado;
    private javax.swing.JButton jBgravar;
    private javax.swing.JButton jBlimpar;
    private javax.swing.JButton jBsair;
    private javax.swing.JPanel jPResultado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTocorrencia;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the JLResultado
     */
    public javax.swing.JLabel getJLResultado() {
        return JLResultado;
    }

    /**
     * @param JLResultado the JLResultado to set
     */
    public void setJLResultado(javax.swing.JLabel JLResultado) {
        this.JLResultado = JLResultado;
    }

    /**
     * @return the jBgravar
     */
    public javax.swing.JButton getjBgravar() {
        return jBgravar;
    }

    /**
     * @param jBgravar the jBgravar to set
     */
    public void setjBgravar(javax.swing.JButton jBgravar) {
        this.jBgravar = jBgravar;
    }

    /**
     * @return the jBlimpar
     */
    public javax.swing.JButton getjBlimpar() {
        return jBlimpar;
    }

    /**
     * @param jBlimpar the jBlimpar to set
     */
    public void setjBlimpar(javax.swing.JButton jBlimpar) {
        this.jBlimpar = jBlimpar;
    }

    /**
     * @return the jBsair
     */
    public javax.swing.JButton getjBsair() {
        return jBsair;
    }

    /**
     * @param jBsair the jBsair to set
     */
    public void setjBsair(javax.swing.JButton jBsair) {
        this.jBsair = jBsair;
    }

    /**
     * @return the jPResultado
     */
    public javax.swing.JPanel getjPResultado() {
        return jPResultado;
    }

    /**
     * @param jPResultado the jPResultado to set
     */
    public void setjPResultado(javax.swing.JPanel jPResultado) {
        this.jPResultado = jPResultado;
    }

    /**
     * @return the jScrollPane1
     */
    public javax.swing.JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    /**
     * @param jScrollPane1 the jScrollPane1 to set
     */
    public void setjScrollPane1(javax.swing.JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    /**
     * @return the jTocorrencia
     */
    public javax.swing.JTable getjTocorrencia() {
        return jTocorrencia;
    }

    /**
     * @param jTocorrencia the jTocorrencia to set
     */
    public void setjTocorrencia(javax.swing.JTable jTocorrencia) {
        this.jTocorrencia = jTocorrencia;
    }

    /**
     * @return the palavras
     */
    public ArrayList<String> getPalavras() {
        return palavras;
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
     * @return the botao
     */
    public boolean isBotao() {
        return botao;
    }

    /**
     * @param botao the botao to set
     */
    public void setBotao(boolean botao) {
        this.botao = botao;
    }
}
