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
 * A classe TelaOcorrencias representa uma interface gráfica que permite a geração
 do relatório de Índice Invertido, sendo este decrescente por número de
 ocorrências.
 *
 * @author Alisson
 */
public class TelaOcorrencias extends javax.swing.JInternalFrame { // declara a classe

    /**
     * Campos da classe
     */
    private ArrayList<String> palavras;             // as palavras presentes nos corpus de documentos
    private ArrayList<Integer> numeroDeOcorrencias; // lista de ocorrências por palavra
    private boolean botao;                          // botão controlador 

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
        File arquivos[];                                // a coleção de arquivos html
        File diretorio = new File("src\\arquivosHTML"); // o diretório raiz dos arquivos html
        arquivos = diretorio.listFiles();               // a coleção passa a apontar para o diretório raiz
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
        File arquivos[];                                // a coleção de arquivos html
        File diretorio = new File("src\\arquivosHTML"); // o diretório raiz dos arquivos html
        arquivos = diretorio.listFiles();               // a coleção passa a apontar para o diretório raiz
        ArrayList<String> conteudo = new ArrayList<>();

        Map<String, Integer> mapPalavras; // usado para contabilizar a frequência das palavras
        mapPalavras = new HashMap<String, Integer>();

        for (File arquivo : arquivos) { // enquanto não percorrer todos os arquivos html, faça:
            StringBuilder contentBuilder = new StringBuilder(); // cria uma instância da classe StringBuilder
            try { // tente:
                BufferedReader in = new BufferedReader(new FileReader(arquivo)); // in possibilita a leitura do arquivo html
                String str; // string auxiliar
                while ((str = in.readLine()) != null) { // enquanto não ler toda a linha do arquivo html, faça:
                    contentBuilder.append(str);  // adiciona o conteúdo em contentBuilder
                }
                in.close(); // fecha in
            } catch (IOException e) { // senão der a tentativa, faça a exceção:

            }

            String content = contentBuilder.toString(); // content recebe o conteúdo literal do html, em formato de texto
            content = formata(content); // content agora está com todos os caracteres especiais devidamente representados
            String textoOk = retira(content); // textoOk agora é o arquivo html sem tags html e com caractes especiais ok
            System.out.println(textoOk); // imprime no terminal, para log, o contéudo formatado do arquivo html
            String textoEmMinusculo = textoOk.toLowerCase(); // transforma todo o conteúdo html em letras minúsculas
            conteudo.add(textoEmMinusculo);
        }
        return conteudo;
    }

    /*
    *   O método geraIndiceInvertido() gera a lista de palavra / número de ocorrências apresentando
    *   a tabela ao usuário da interface.
     */
    public void geraIndiceInvertido() throws IOException {
        File arquivos[];                                // a coleção de arquivos html
        File diretorio = new File("src\\arquivosHTML"); // o diretório raiz dos arquivos html
        arquivos = diretorio.listFiles();               // a coleção passa a apontar para o diretório raiz

        Map<String, Integer> mapPalavras; // usado para contabilizar a frequência das palavras
        mapPalavras = new HashMap<String, Integer>();

        for (File arquivo : arquivos) { // enquanto não percorrer todos os arquivos html, faça:
            StringBuilder contentBuilder = new StringBuilder(); // cria uma instância da classe StringBuilder
            try { // tente:
                BufferedReader in = new BufferedReader(new FileReader(arquivo)); // in possibilita a leitura do arquivo html
                String str; // string auxiliar
                while ((str = in.readLine()) != null) { // enquanto não ler toda a linha do arquivo html, faça:
                    contentBuilder.append(str);  // adiciona o conteúdo em contentBuilder
                }
                in.close(); // fecha in
            } catch (IOException e) { // senão der a tentativa, faça a exceção:

            }

            String content = contentBuilder.toString(); // content recebe o conteúdo literal do html, em formato de texto
            content = formataDados(content); // content agora está com todos os caracteres especiais devidamente representados
            String textoOk = retiraTags(content); // textoOk agora é o arquivo html sem tags html e com caractes especiais ok
            System.out.println(textoOk); // imprime no terminal, para log, o contéudo formatado do arquivo html
            String textoEmMinusculo = textoOk.toLowerCase(); // transforma todo o conteúdo html em letras minúsculas
            textoEmMinusculo = BibliotecaDeMetodos.retiraStopWordsDeUmTexto(textoEmMinusculo);
            // depois aplica a expressão regular
            Pattern p = Pattern.compile("(\\d+)|([a-záéíóúçãõôê]+)");
            Matcher m = p.matcher(textoEmMinusculo);

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
        } // fim do for

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

        // imprime no terminal, para log, a lista de palavras/número de ocorrências
        for (int i = 0; i < getPalavras().size(); i++) {
            System.out.println("Palavra: " + getPalavras().get(i) + "\tFrequência=" + getNumeroDeOcorrencias().get(i));
        }

        if (isBotao()) { // se o botão estiver ligado
            limparTabela(); // limpa a tabela de interface do usuário
        }

        preencherRelatorio(); // preenche a tabela de interface do usuário

        setPalavras(new ArrayList<>()); // seta a lista de palavras para um novo ciclo
        setNumeroDeOcorrencias(new ArrayList<>()); // seta a lista de número de ocorrências para um novo ciclo
    } // fim do método geraIndiceInvertido()

    /**
     * O método limparTabela() limpa a tabela de interface do usuário
     * (palavras/número de ocorrências)
     */
    public void limparTabela() {
        DefaultTableModel dm = (DefaultTableModel) getjTocorrencia().getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        setBotao(false); // apaga o botao
    }

    /**
     * O método preencherRelatorio() preenche a tabela de interface do usuário.
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
     * O método formataDados(String) recebe como argumento um texto em html e
     * retorna um texto com todos os caracteres especiais devidamente
     * representados.
     *
     * @param texto um texto em html com caracteres especiais não reconhecidos
     * @return um texto em html com os caracteres especiais devidamente
     * representados
     */
    public String formataDados(String texto) {
        texto = texto.replaceAll("&Agrave;", "À");
        texto = texto.replaceAll("&Aacute;", "Á");
        texto = texto.replaceAll("&Acirc;", "Â");
        texto = texto.replaceAll("&Atilde;", "Ã");
        texto = texto.replaceAll("&Auml;", "Ä");
        texto = texto.replaceAll("&Aring;", "Å");
        texto = texto.replaceAll("&agrave;", "à");
        texto = texto.replaceAll("&aacute;", "á");
        texto = texto.replaceAll("&acirc;", "â");
        texto = texto.replaceAll("&atilde;", "ã");
        texto = texto.replaceAll("&auml;", "ä");
        texto = texto.replaceAll("&aring;", "å");
        texto = texto.replaceAll("&Ccedil;", "Ç");
        texto = texto.replaceAll("&ccedil;", "ç");

        texto = texto.replaceAll("&Egrave;", "È");
        texto = texto.replaceAll("&Eacute;", "É");
        texto = texto.replaceAll("&Ecirc;", "Ê");
        texto = texto.replaceAll("&Euml;", "Ë");
        texto = texto.replaceAll("&egrave;", "è");
        texto = texto.replaceAll("&eacute;", "é");
        texto = texto.replaceAll("&ecirc;", "ê");
        texto = texto.replaceAll("&euml;", "ë");
        texto = texto.replaceAll("&Igrave;", "Ì");
        texto = texto.replaceAll("&Iacute;", "Í");
        texto = texto.replaceAll("&Icirc;", "Î");
        texto = texto.replaceAll("&Iuml;", "Ï");
        texto = texto.replaceAll("&igrave;", "ì");
        texto = texto.replaceAll("&iacute;", "í");
        texto = texto.replaceAll("&icirc;", "î");
        texto = texto.replaceAll("&iuml;", "ï");

        texto = texto.replaceAll("&Ntilde;", "Ñ");
        texto = texto.replaceAll("&ntilde;", "ñ");
        texto = texto.replaceAll("&Ograve;", "Ò");
        texto = texto.replaceAll("&Oacute;", "Ó");
        texto = texto.replaceAll("&Ocirc;", "Ô");
        texto = texto.replaceAll("&Otilde;", "Õ");
        texto = texto.replaceAll("&Ouml;", "Ö");
        texto = texto.replaceAll("&ograve;", "ò");
        texto = texto.replaceAll("&oacute;", "ó");
        texto = texto.replaceAll("&ocirc;", "ô");
        texto = texto.replaceAll("&otilde;", "õ");
        texto = texto.replaceAll("&ouml;", "ö");
        texto = texto.replaceAll("&Oslash;", "Ø");
        texto = texto.replaceAll("&oslash;", "ø");
        texto = texto.replaceAll("&#140", "?");
        texto = texto.replaceAll("&#156", "?");
        texto = texto.replaceAll("&#138", "?");
        texto = texto.replaceAll("&#154", "?");

        texto = texto.replaceAll("&Ugrave;", "Ù");
        texto = texto.replaceAll("&Uacute;", "Ú");
        texto = texto.replaceAll("&Ucirc;", "Û");
        texto = texto.replaceAll("&Uuml;", "Ü");
        texto = texto.replaceAll("&ugrave;", "ù");
        texto = texto.replaceAll("&uacute;", "ú");
        texto = texto.replaceAll("&ucirc;", "û");
        texto = texto.replaceAll("&uuml;", "ü");
        texto = texto.replaceAll("&#181", "µ");
        texto = texto.replaceAll("&#215", "×");
        texto = texto.replaceAll("&Yacute;", "Ý");
        texto = texto.replaceAll("&#159;", "?");
        texto = texto.replaceAll("&yacute;", "ý");
        texto = texto.replaceAll("&yuml;", "ÿ");

        texto = texto.replaceAll("&#176", "°");
        texto = texto.replaceAll("&#134", "?");
        texto = texto.replaceAll("&#135", "?");
        texto = texto.replaceAll("&lt", "<");
        texto = texto.replaceAll("&gt", ">");
        texto = texto.replaceAll("&#177", "±");
        texto = texto.replaceAll("&#171", "«");
        texto = texto.replaceAll("&#187", "»");
        texto = texto.replaceAll("&#191", "¿");
        texto = texto.replaceAll("&#161", "¡");
        texto = texto.replaceAll("&#183", "·");
        texto = texto.replaceAll("&#149", "?");
        texto = texto.replaceAll("&#153", "?");
        texto = texto.replaceAll("&copy", "©");
        texto = texto.replaceAll("&reg", "®");
        texto = texto.replaceAll("&#167", "§");
        texto = texto.replaceAll("&#182", "¶");

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
        texto = texto.replaceAll("«", "");
        texto = texto.replaceAll("»", "");
        texto = texto.replaceAll("", "");
        texto = texto.replaceAll("\\:", "");
        texto = texto.replaceAll(",", "");

        return texto;
    }

    /**
     * O método formataDados(String) recebe como argumento um texto em html e
     * retorna um texto com todos os caracteres especiais devidamente
     * representados.
     *
     * @param texto um texto em html com caracteres especiais não reconhecidos
     * @return um texto em html com os caracteres especiais devidamente
     * representados
     */
    public static String formata(String texto) {
        texto = texto.replaceAll("&Agrave;", "À");
        texto = texto.replaceAll("&Aacute;", "Á");
        texto = texto.replaceAll("&Acirc;", "Â");
        texto = texto.replaceAll("&Atilde;", "Ã");
        texto = texto.replaceAll("&Auml;", "Ä");
        texto = texto.replaceAll("&Aring;", "Å");
        texto = texto.replaceAll("&agrave;", "à");
        texto = texto.replaceAll("&aacute;", "á");
        texto = texto.replaceAll("&acirc;", "â");
        texto = texto.replaceAll("&atilde;", "ã");
        texto = texto.replaceAll("&auml;", "ä");
        texto = texto.replaceAll("&aring;", "å");
        texto = texto.replaceAll("&Ccedil;", "Ç");
        texto = texto.replaceAll("&ccedil;", "ç");

        texto = texto.replaceAll("&Egrave;", "È");
        texto = texto.replaceAll("&Eacute;", "É");
        texto = texto.replaceAll("&Ecirc;", "Ê");
        texto = texto.replaceAll("&Euml;", "Ë");
        texto = texto.replaceAll("&egrave;", "è");
        texto = texto.replaceAll("&eacute;", "é");
        texto = texto.replaceAll("&ecirc;", "ê");
        texto = texto.replaceAll("&euml;", "ë");
        texto = texto.replaceAll("&Igrave;", "Ì");
        texto = texto.replaceAll("&Iacute;", "Í");
        texto = texto.replaceAll("&Icirc;", "Î");
        texto = texto.replaceAll("&Iuml;", "Ï");
        texto = texto.replaceAll("&igrave;", "ì");
        texto = texto.replaceAll("&iacute;", "í");
        texto = texto.replaceAll("&icirc;", "î");
        texto = texto.replaceAll("&iuml;", "ï");

        texto = texto.replaceAll("&Ntilde;", "Ñ");
        texto = texto.replaceAll("&ntilde;", "ñ");
        texto = texto.replaceAll("&Ograve;", "Ò");
        texto = texto.replaceAll("&Oacute;", "Ó");
        texto = texto.replaceAll("&Ocirc;", "Ô");
        texto = texto.replaceAll("&Otilde;", "Õ");
        texto = texto.replaceAll("&Ouml;", "Ö");
        texto = texto.replaceAll("&ograve;", "ò");
        texto = texto.replaceAll("&oacute;", "ó");
        texto = texto.replaceAll("&ocirc;", "ô");
        texto = texto.replaceAll("&otilde;", "õ");
        texto = texto.replaceAll("&ouml;", "ö");
        texto = texto.replaceAll("&Oslash;", "Ø");
        texto = texto.replaceAll("&oslash;", "ø");
        texto = texto.replaceAll("&#140", "?");
        texto = texto.replaceAll("&#156", "?");
        texto = texto.replaceAll("&#138", "?");
        texto = texto.replaceAll("&#154", "?");

        texto = texto.replaceAll("&Ugrave;", "Ù");
        texto = texto.replaceAll("&Uacute;", "Ú");
        texto = texto.replaceAll("&Ucirc;", "Û");
        texto = texto.replaceAll("&Uuml;", "Ü");
        texto = texto.replaceAll("&ugrave;", "ù");
        texto = texto.replaceAll("&uacute;", "ú");
        texto = texto.replaceAll("&ucirc;", "û");
        texto = texto.replaceAll("&uuml;", "ü");
        texto = texto.replaceAll("&#181", "µ");
        texto = texto.replaceAll("&#215", "×");
        texto = texto.replaceAll("&Yacute;", "Ý");
        texto = texto.replaceAll("&#159;", "?");
        texto = texto.replaceAll("&yacute;", "ý");
        texto = texto.replaceAll("&yuml;", "ÿ");

        texto = texto.replaceAll("&#176", "°");
        texto = texto.replaceAll("&#134", "?");
        texto = texto.replaceAll("&#135", "?");
        texto = texto.replaceAll("&lt", "<");
        texto = texto.replaceAll("&gt", ">");
        texto = texto.replaceAll("&#177", "±");
        texto = texto.replaceAll("&#171", "«");
        texto = texto.replaceAll("&#187", "»");
        texto = texto.replaceAll("&#191", "¿");
        texto = texto.replaceAll("&#161", "¡");
        texto = texto.replaceAll("&#183", "·");
        texto = texto.replaceAll("&#149", "?");
        texto = texto.replaceAll("&#153", "?");
        texto = texto.replaceAll("&copy", "©");
        texto = texto.replaceAll("&reg", "®");
        texto = texto.replaceAll("&#167", "§");
        texto = texto.replaceAll("&#182", "¶");

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
        texto = texto.replaceAll("«", "");
        texto = texto.replaceAll("»", "");
        texto = texto.replaceAll("", "");
        texto = texto.replaceAll("\\:", "");
        texto = texto.replaceAll(",", "");

        return texto;
    }

    /**
     * O método retiraTags(String) recebe como argumento um texto em html e
     * retorna um texto correspondente sem as tags html.
     *
     * @param html um texto em html
     * @return um texto correspondente sem as tags html
     */
    public String retiraTags(String html) {
        return Jsoup.parse(html).text();
    }
    
    /**
     * O método retiraTags(String) recebe como argumento um texto em html e
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
        setTitle("Gerador de Índice Invertido");

        JLResultado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        JLResultado.setText("Ranking de ocorrências");

        jTocorrencia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTocorrencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Palavra", "Ocorrência"
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
        jBgravar.setText("Gerar Índice ");
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
