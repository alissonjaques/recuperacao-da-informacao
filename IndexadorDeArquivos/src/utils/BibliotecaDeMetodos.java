package utils;

import java.io.BufferedReader;
import java.io.File;
import org.apache.commons.lang3.StringUtils;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static telas.TelaOcorrencias.formata;
import static telas.TelaOcorrencias.retira;

/**
 *
 * @author licin
 */
public class BibliotecaDeMetodos {

    public static ArrayList<String> retornaNomeDosDocumentos() {
        File arquivos[];                                // a coleção de arquivos html
        File diretorio = new File("src\\arquivosHTML"); // o diretório raiz dos arquivos html
        arquivos = diretorio.listFiles();               // a coleção passa a apontar para o diretório raiz
        ArrayList<String> nomeArquivo = new ArrayList<>();
        String temp;
        for (File arquivo : arquivos) {
            temp = arquivo.toString();
            nomeArquivo.add(retornaNomeDocumento(temp));
        }

        return nomeArquivo;
    }

    public static String retornaNomeDocumento(String diretorio) {
        String nomeDocumento = "";
        Boolean botao = false;

        for (int i = diretorio.length() - 1; i >= 0; i--) {
            if (diretorio.charAt(i) == '\\') {
                break;
            }

            if (botao) {
                nomeDocumento += diretorio.charAt(i);
            }

            if (diretorio.charAt(i) == '.') {
                botao = true;
            } else {

            }
        }

        StringBuilder invertido = new StringBuilder(nomeDocumento);
        nomeDocumento = invertido.reverse().toString();

        return nomeDocumento;
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
            String textoEmMinusculo = textoOk.toLowerCase(); // transforma todo o conteúdo html em letras minúsculas
            conteudo.add(textoEmMinusculo);
        }
        return conteudo;
    }

    public static ArrayList<String> retiraStopWords() {
        ArrayList<String> conteudo = retornaConteudoDosDocumentos();
        ArrayList<String> conteudoSemStopWords = new ArrayList();

        for (int i = 0; i < conteudo.size(); i++) {
            String texto = conteudo.get(i);
            texto = StringUtils.replaceAll(texto, " de ", " ");
            texto = StringUtils.replaceAll(texto, " a ", " ");
            texto = StringUtils.replaceAll(texto, " o ", " ");
            texto = StringUtils.replaceAll(texto, " que ", " ");
            texto = StringUtils.replaceAll(texto, " e ", " ");
            texto = StringUtils.replaceAll(texto, " do ", " ");
            texto = StringUtils.replaceAll(texto, " da ", " ");
            texto = StringUtils.replaceAll(texto, " em ", " ");
            texto = StringUtils.replaceAll(texto, " um ", " ");
            texto = StringUtils.replaceAll(texto, " para ", " ");
            texto = StringUtils.replaceAll(texto, " é ", " ");
            texto = StringUtils.replaceAll(texto, " com ", " ");
            texto = StringUtils.replaceAll(texto, " não ", " ");
            texto = StringUtils.replaceAll(texto, " uma ", " ");
            texto = StringUtils.replaceAll(texto, " os ", " ");
            texto = StringUtils.replaceAll(texto, " no ", " ");
            texto = StringUtils.replaceAll(texto, " se ", " ");
            texto = StringUtils.replaceAll(texto, " na ", " ");
            texto = StringUtils.replaceAll(texto, " por ", " ");
            texto = StringUtils.replaceAll(texto, " mais ", " ");
            texto = StringUtils.replaceAll(texto, " as ", " ");
            texto = StringUtils.replaceAll(texto, " dos ", " ");
            texto = StringUtils.replaceAll(texto, " como ", " ");
            texto = StringUtils.replaceAll(texto, " mas ", " ");
            texto = StringUtils.replaceAll(texto, " foi ", " ");
            texto = StringUtils.replaceAll(texto, " ao ", " ");
            texto = StringUtils.replaceAll(texto, " ele ", " ");
            texto = StringUtils.replaceAll(texto, " das ", " ");
            texto = StringUtils.replaceAll(texto, " tem ", " ");
            texto = StringUtils.replaceAll(texto, " à ", " ");
            texto = StringUtils.replaceAll(texto, " seu ", " ");
            texto = StringUtils.replaceAll(texto, " sua ", " ");
            texto = StringUtils.replaceAll(texto, " ou ", " ");
            texto = StringUtils.replaceAll(texto, " ser ", " ");
            texto = StringUtils.replaceAll(texto, " quando ", " ");
            texto = StringUtils.replaceAll(texto, " muito ", " ");
            texto = StringUtils.replaceAll(texto, " há ", " ");
            texto = StringUtils.replaceAll(texto, " nos ", " ");
            texto = StringUtils.replaceAll(texto, " já ", " ");
            texto = StringUtils.replaceAll(texto, " está ", " ");
            texto = StringUtils.replaceAll(texto, " eu ", " ");
            texto = StringUtils.replaceAll(texto, " também ", " ");
            texto = StringUtils.replaceAll(texto, " só ", " ");
            texto = StringUtils.replaceAll(texto, " pelo ", " ");
            texto = StringUtils.replaceAll(texto, " pela ", " ");
            texto = StringUtils.replaceAll(texto, " até ", " ");
            texto = StringUtils.replaceAll(texto, " isso ", " ");
            texto = StringUtils.replaceAll(texto, " ela ", " ");
            texto = StringUtils.replaceAll(texto, " entre ", " ");
            texto = StringUtils.replaceAll(texto, " era ", " ");
            texto = StringUtils.replaceAll(texto, " depois ", " ");
            texto = StringUtils.replaceAll(texto, " sem ", " ");
            texto = StringUtils.replaceAll(texto, " mesmo ", " ");
            texto = StringUtils.replaceAll(texto, " aos ", " ");
            texto = StringUtils.replaceAll(texto, " ter ", " ");
            texto = StringUtils.replaceAll(texto, " seus ", " ");
            texto = StringUtils.replaceAll(texto, " quem ", " ");
            texto = StringUtils.replaceAll(texto, " nas ", " ");
            texto = StringUtils.replaceAll(texto, " me ", " ");
            texto = StringUtils.replaceAll(texto, " esse ", " ");
            texto = StringUtils.replaceAll(texto, " eles ", " ");
            texto = StringUtils.replaceAll(texto, " estão ", " ");
            texto = StringUtils.replaceAll(texto, " você ", " ");
            texto = StringUtils.replaceAll(texto, " tinha ", " ");
            texto = StringUtils.replaceAll(texto, " foram ", " ");
            texto = StringUtils.replaceAll(texto, " essa ", " ");
            texto = StringUtils.replaceAll(texto, " num ", " ");
            texto = StringUtils.replaceAll(texto, " nem ", " ");
            texto = StringUtils.replaceAll(texto, " suas ", " ");
            texto = StringUtils.replaceAll(texto, " meu ", " ");
            texto = StringUtils.replaceAll(texto, " às ", " ");
            texto = StringUtils.replaceAll(texto, " minha ", " ");
            texto = StringUtils.replaceAll(texto, " têm ", " ");
            texto = StringUtils.replaceAll(texto, " numa ", " ");
            texto = StringUtils.replaceAll(texto, " pelos ", " ");
            texto = StringUtils.replaceAll(texto, " elas ", " ");
            texto = StringUtils.replaceAll(texto, " havia ", " ");
            texto = StringUtils.replaceAll(texto, " seja ", " ");
            texto = StringUtils.replaceAll(texto, " qual ", " ");
            texto = StringUtils.replaceAll(texto, " será ", " ");
            texto = StringUtils.replaceAll(texto, " nós ", " ");
            texto = StringUtils.replaceAll(texto, " tenho ", " ");
            texto = StringUtils.replaceAll(texto, " lhe ", " ");
            texto = StringUtils.replaceAll(texto, " deles ", " ");
            texto = StringUtils.replaceAll(texto, " essas ", " ");
            texto = StringUtils.replaceAll(texto, " esses ", " ");
            texto = StringUtils.replaceAll(texto, " pelas ", " ");
            texto = StringUtils.replaceAll(texto, " este ", " ");
            texto = StringUtils.replaceAll(texto, " fosse ", " ");
            texto = StringUtils.replaceAll(texto, " dele ", " ");
            texto = StringUtils.replaceAll(texto, " tu ", " ");
            texto = StringUtils.replaceAll(texto, " te ", " ");
            texto = StringUtils.replaceAll(texto, " vocês ", " ");
            texto = StringUtils.replaceAll(texto, " vos ", " ");
            texto = StringUtils.replaceAll(texto, " lhes ", " ");
            texto = StringUtils.replaceAll(texto, " meus ", " ");
            texto = StringUtils.replaceAll(texto, " minhas ", " ");
            texto = StringUtils.replaceAll(texto, " teu ", " ");
            texto = StringUtils.replaceAll(texto, " tua ", " ");
            texto = StringUtils.replaceAll(texto, " teus ", " ");
            texto = StringUtils.replaceAll(texto, " tuas ", " ");
            texto = StringUtils.replaceAll(texto, " nosso ", " ");
            texto = StringUtils.replaceAll(texto, " nossa ", " ");
            texto = StringUtils.replaceAll(texto, " nossos ", " ");
            texto = StringUtils.replaceAll(texto, " nossas ", " ");
            texto = StringUtils.replaceAll(texto, " dela ", " ");
            texto = StringUtils.replaceAll(texto, " delas ", " ");
            texto = StringUtils.replaceAll(texto, " esta ", " ");
            texto = StringUtils.replaceAll(texto, " estes ", " ");
            texto = StringUtils.replaceAll(texto, " estas ", " ");
            texto = StringUtils.replaceAll(texto, " aquele ", " ");
            texto = StringUtils.replaceAll(texto, " aquela ", " ");
            texto = StringUtils.replaceAll(texto, " aqueles ", " ");
            texto = StringUtils.replaceAll(texto, " aquelas ", " ");
            texto = StringUtils.replaceAll(texto, " isto ", " ");
            texto = StringUtils.replaceAll(texto, " aquilo ", " ");
            texto = StringUtils.replaceAll(texto, " estou ", " ");
            texto = StringUtils.replaceAll(texto, " está ", " ");
            texto = StringUtils.replaceAll(texto, " estamos ", " ");
            texto = StringUtils.replaceAll(texto, " estão ", " ");
            texto = StringUtils.replaceAll(texto, " estive ", " ");
            texto = StringUtils.replaceAll(texto, " esteve ", " ");
            texto = StringUtils.replaceAll(texto, " estivemos ", " ");
            texto = StringUtils.replaceAll(texto, " estiveram ", " ");
            texto = StringUtils.replaceAll(texto, " estava ", " ");
            texto = StringUtils.replaceAll(texto, " estávamos ", " ");
            texto = StringUtils.replaceAll(texto, " estavam ", " ");
            texto = StringUtils.replaceAll(texto, " estivera ", " ");
            texto = StringUtils.replaceAll(texto, " estivéramos ", " ");
            texto = StringUtils.replaceAll(texto, " esteja ", " ");
            texto = StringUtils.replaceAll(texto, " estejamos ", " ");
            texto = StringUtils.replaceAll(texto, " estejam ", " ");
            texto = StringUtils.replaceAll(texto, " estivesse ", " ");
            texto = StringUtils.replaceAll(texto, " estivéssemos ", " ");
            texto = StringUtils.replaceAll(texto, " estivessem ", " ");
            texto = StringUtils.replaceAll(texto, " estiver ", " ");
            texto = StringUtils.replaceAll(texto, " estivermos ", " ");
            texto = StringUtils.replaceAll(texto, " estiverem ", " ");
            texto = StringUtils.replaceAll(texto, " hei ", " ");
            texto = StringUtils.replaceAll(texto, " há ", " ");
            texto = StringUtils.replaceAll(texto, " havemos ", " ");
            texto = StringUtils.replaceAll(texto, " hão ", " ");
            texto = StringUtils.replaceAll(texto, " houve ", " ");
            texto = StringUtils.replaceAll(texto, " houvemos ", " ");
            texto = StringUtils.replaceAll(texto, " houveram ", " ");
            texto = StringUtils.replaceAll(texto, " houvera ", " ");
            texto = StringUtils.replaceAll(texto, " houvéramos ", " ");
            texto = StringUtils.replaceAll(texto, " haja ", " ");
            texto = StringUtils.replaceAll(texto, " hajamos ", " ");
            texto = StringUtils.replaceAll(texto, " hajam ", " ");
            texto = StringUtils.replaceAll(texto, " houvesse ", " ");
            texto = StringUtils.replaceAll(texto, " houvéssemos ", " ");
            texto = StringUtils.replaceAll(texto, " houvessem ", " ");
            texto = StringUtils.replaceAll(texto, " houver ", " ");
            texto = StringUtils.replaceAll(texto, " houvermos ", " ");
            texto = StringUtils.replaceAll(texto, " houverem ", " ");
            texto = StringUtils.replaceAll(texto, " houverei ", " ");
            texto = StringUtils.replaceAll(texto, " houverá ", " ");
            texto = StringUtils.replaceAll(texto, " houveremos ", " ");
            texto = StringUtils.replaceAll(texto, " houverão ", " ");
            texto = StringUtils.replaceAll(texto, " houveria ", " ");
            texto = StringUtils.replaceAll(texto, " houveríamos ", " ");
            texto = StringUtils.replaceAll(texto, " houveriam ", " ");
            texto = StringUtils.replaceAll(texto, " sou ", " ");
            texto = StringUtils.replaceAll(texto, " somos ", " ");
            texto = StringUtils.replaceAll(texto, " são ", " ");
            texto = StringUtils.replaceAll(texto, " era ", " ");
            texto = StringUtils.replaceAll(texto, " éramos ", " ");
            texto = StringUtils.replaceAll(texto, " eram ", " ");
            texto = StringUtils.replaceAll(texto, " fui ", " ");
            texto = StringUtils.replaceAll(texto, " foi ", " ");
            texto = StringUtils.replaceAll(texto, " fomos ", " ");
            texto = StringUtils.replaceAll(texto, " foram ", " ");
            texto = StringUtils.replaceAll(texto, " fora ", " ");
            texto = StringUtils.replaceAll(texto, " fôramos ", " ");
            texto = StringUtils.replaceAll(texto, " seja ", " ");
            texto = StringUtils.replaceAll(texto, " sejamos ", " ");
            texto = StringUtils.replaceAll(texto, " sejam ", " ");
            texto = StringUtils.replaceAll(texto, " fosse ", " ");
            texto = StringUtils.replaceAll(texto, " fôssemos ", " ");
            texto = StringUtils.replaceAll(texto, " fossem ", " ");
            texto = StringUtils.replaceAll(texto, " for ", " ");
            texto = StringUtils.replaceAll(texto, " formos ", " ");
            texto = StringUtils.replaceAll(texto, " forem ", " ");
            texto = StringUtils.replaceAll(texto, " serei ", " ");
            texto = StringUtils.replaceAll(texto, " será ", " ");
            texto = StringUtils.replaceAll(texto, " seremos ", " ");
            texto = StringUtils.replaceAll(texto, " serão ", " ");
            texto = StringUtils.replaceAll(texto, " seria ", " ");
            texto = StringUtils.replaceAll(texto, " seríamos ", " ");
            texto = StringUtils.replaceAll(texto, " seriam ", " ");
            texto = StringUtils.replaceAll(texto, " tenho ", " ");
            texto = StringUtils.replaceAll(texto, " tem ", " ");
            texto = StringUtils.replaceAll(texto, " temos ", " ");
            texto = StringUtils.replaceAll(texto, " tém ", " ");
            texto = StringUtils.replaceAll(texto, " tinha ", " ");
            texto = StringUtils.replaceAll(texto, " tínhamos ", " ");
            texto = StringUtils.replaceAll(texto, " tinham ", " ");
            texto = StringUtils.replaceAll(texto, " tive ", " ");
            texto = StringUtils.replaceAll(texto, " teve ", " ");
            texto = StringUtils.replaceAll(texto, " tivemos ", " ");
            texto = StringUtils.replaceAll(texto, " tiveram ", " ");
            texto = StringUtils.replaceAll(texto, " tivera ", " ");
            texto = StringUtils.replaceAll(texto, " tivéramos ", " ");
            texto = StringUtils.replaceAll(texto, " tenha ", " ");
            texto = StringUtils.replaceAll(texto, " tenhamos ", " ");
            texto = StringUtils.replaceAll(texto, " tenham ", " ");
            texto = StringUtils.replaceAll(texto, " tivesse ", " ");
            texto = StringUtils.replaceAll(texto, " tivéssemos ", " ");
            texto = StringUtils.replaceAll(texto, " tivessem ", " ");
            texto = StringUtils.replaceAll(texto, " tiver ", " ");
            texto = StringUtils.replaceAll(texto, " tivermos ", " ");
            texto = StringUtils.replaceAll(texto, " tiverem ", " ");
            texto = StringUtils.replaceAll(texto, " terei ", " ");
            texto = StringUtils.replaceAll(texto, " terá ", " ");
            texto = StringUtils.replaceAll(texto, " teremos ", " ");
            texto = StringUtils.replaceAll(texto, " terão ", " ");
            texto = StringUtils.replaceAll(texto, " teria ", " ");
            texto = StringUtils.replaceAll(texto, " teríamos ", " ");
            texto = StringUtils.replaceAll(texto, " teriam ", " ");
            texto = StringUtils.replaceAll(texto, " x ", " ");
            texto = StringUtils.replaceAll(texto, " c ", " ");
            texto = StringUtils.replaceAll(texto, " b ", " ");
            texto = StringUtils.replaceAll(texto, " s ", " ");
            texto = StringUtils.replaceAll(texto, " n ", " ");
            texto = StringUtils.replaceAll(texto, " 1 ", " ");
            texto = StringUtils.replaceAll(texto, " 01 ", " ");
            texto = StringUtils.replaceAll(texto, " 6 ", " ");
            texto = StringUtils.replaceAll(texto, " 4 ", " ");
            texto = StringUtils.replaceAll(texto, " 23 ", " ");
            texto = StringUtils.replaceAll(texto, " 3 ", " ");
            texto = StringUtils.replaceAll(texto, " 11 ", " ");
            texto = StringUtils.replaceAll(texto, " 18 ", " ");
            texto = StringUtils.replaceAll(texto, " f ", " ");
            texto = StringUtils.replaceAll(texto, " i ", " ");
            texto = StringUtils.replaceAll(texto, " I ", " ");
            texto = StringUtils.replaceAll(texto, " 2 ", " ");
            texto = StringUtils.replaceAll(texto, " j ", " ");
            texto = StringUtils.replaceAll(texto, " 03 ", " ");
            texto = StringUtils.replaceAll(texto, " 08 ", " ");
            texto = StringUtils.replaceAll(texto, " 24 ", " ");
            texto = StringUtils.replaceAll(texto, " 15 ", " ");
            texto = StringUtils.replaceAll(texto, " 07 ", " ");
            texto = StringUtils.replaceAll(texto, " 09 ", " ");
            texto = StringUtils.replaceAll(texto, " 5 ", " ");

            conteudoSemStopWords.add(texto);
        }
        return conteudoSemStopWords;
    }

    public static String retiraStopWordsDeUmTexto(String texto) {
        String semStopWords = texto;
        semStopWords = StringUtils.replaceAll(semStopWords, " de ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " a ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " o ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " que ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " e ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " do ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " da ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " em ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " um ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " para ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " é ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " com ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " não ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " uma ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " os ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " no ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " se ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " na ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " por ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " mais ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " as ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " dos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " como ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " mas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " foi ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " ao ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " ele ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " das ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " à ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " seu ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " sua ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " ou ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " ser ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " quando ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " muito ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " há ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " nos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " já ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " está ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " eu ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " também ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " só ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " pelo ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " pela ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " até ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " isso ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " ela ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " entre ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " era ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " depois ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " sem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " mesmo ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " aos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " ter ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " seus ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " quem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " nas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " me ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " esse ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " eles ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estão ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " você ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tinha ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " foram ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " essa ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " num ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " nem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " suas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " meu ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " às ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " minha ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " têm ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " numa ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " pelos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " elas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " havia ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " seja ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " qual ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " será ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " nós ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tenho ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " lhe ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " deles ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " essas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " esses ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " pelas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " este ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " fosse ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " dele ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tu ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " te ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " vocês ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " vos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " lhes ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " meus ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " minhas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " teu ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tua ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " teus ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tuas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " nosso ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " nossa ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " nossos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " nossas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " dela ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " delas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " esta ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estes ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " aquele ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " aquela ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " aqueles ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " aquelas ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " isto ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " aquilo ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estou ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " está ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estão ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estive ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " esteve ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estivemos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estiveram ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estava ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estávamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estavam ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estivera ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estivéramos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " esteja ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estejamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estejam ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estivesse ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estivéssemos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estivessem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estiver ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estivermos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " estiverem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " hei ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " há ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " havemos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " hão ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houve ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houvemos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houveram ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houvera ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houvéramos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " haja ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " hajamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " hajam ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houvesse ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houvéssemos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houvessem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houver ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houvermos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houverem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houverei ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houverá ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houveremos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houverão ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houveria ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houveríamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " houveriam ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " sou ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " somos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " são ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " era ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " éramos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " eram ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " fui ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " foi ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " fomos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " foram ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " fora ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " fôramos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " seja ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " sejamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " sejam ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " fosse ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " fôssemos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " fossem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " for ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " formos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " forem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " serei ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " será ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " seremos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " serão ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " seria ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " seríamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " seriam ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tenho ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " temos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tém ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tinha ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tínhamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tinham ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tive ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " teve ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tivemos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tiveram ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tivera ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tivéramos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tenha ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tenhamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tenham ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tivesse ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tivéssemos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tivessem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tiver ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tivermos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " tiverem ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " terei ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " terá ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " teremos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " terão ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " teria ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " teríamos ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " teriam ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " x ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " c ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " b ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " s ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " n ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 1 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 01 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 6 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 4 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 23 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 3 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 11 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 18 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " f ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " i ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " I ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 2 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " j ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 03 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 08 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 24 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 15 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 07 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 09 ", " ");
        semStopWords = StringUtils.replaceAll(semStopWords, " 5 ", " ");
        
        return semStopWords;
    }
}
