package controle;

import java.io.*;
import java.util.ArrayList;
import org.apache.log4j.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

public class Buscador {
    private static Logger logger = Logger.getLogger(Buscador.class);
    private String diretorioDoIndice = "src\\indice";
    
    public int retornaQuantidadeTermoDocumento(String parametro){
        try {
            Directory diretorio = new SimpleFSDirectory(new File(diretorioDoIndice));
            
            IndexReader leitor = IndexReader.open(diretorio);
            
            IndexSearcher buscador = new IndexSearcher(leitor);
            Analyzer analisador = new StandardAnalyzer(Version.LUCENE_36);
            
            QueryParser parser = new QueryParser(Version.LUCENE_36, "Texto", analisador);
            Query consulta = parser.parse(parametro);
            TopDocs resultado = buscador.search(consulta, 150);
            int totalDeOcorrencias = resultado.totalHits;
            buscador.close();
            return totalDeOcorrencias;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }
    
    public ArrayList<String> buscaComParser(String parametro) {
        try {
            Directory diretorio = new SimpleFSDirectory(new File(diretorioDoIndice));
            //{1}
            IndexReader leitor = IndexReader.open(diretorio);
            //{2}
            IndexSearcher buscador = new IndexSearcher(leitor);
            Analyzer analisador = new StandardAnalyzer(Version.LUCENE_36);
            //{3}
            QueryParser parser = new QueryParser(Version.LUCENE_36, "Texto", analisador);
            Query consulta = parser.parse(parametro);
            long inicio = System.currentTimeMillis();
            //{4}
            TopDocs resultado = buscador.search(consulta, 150);
            long fim = System.currentTimeMillis();
            int totalDeOcorrencias = resultado.totalHits;
            logger.info("Total de documentos encontrados:" + totalDeOcorrencias);
            System.out.println("Total de documentos encontrados:" + totalDeOcorrencias);
            logger.info("Tempo total para busca: " + (fim - inicio) + "ms");
            System.out.println("Tempo total para busca: " + (fim - inicio) + "ms");

            ArrayList<String> nomeArquivo = new ArrayList<>();

            int i = 0;
            //{5}
            for (ScoreDoc sd : resultado.scoreDocs) {
                Document documento = buscador.doc(sd.doc);
                logger.info("Caminho:" + documento.get("Caminho"));
                //System.out.println("Caminho:" + documento.get("Caminho"));
                System.out.println("Nome do documento: " + retornaNomeDocumento(documento.get("Caminho")));
                nomeArquivo.add(retornaNomeDocumento(documento.get("Caminho")));
                logger.info("Última modificação:" + documento.get("UltimaModificacao"));
                //System.out.println("Última modificação:" + documento.get("UltimaModificacao"));
                logger.info("Score:" + sd.score);
                System.out.println("Score:" + sd.score);
                logger.info("--------");
                System.out.println("--------");
                i++;
            }
            buscador.close();
            System.out.println(consulta);
            return nomeArquivo;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
    
    public String retornaNomeDocumento(String diretorio){
        String nomeDocumento = "";
        Boolean botao = false;
        
        for(int i = diretorio.length()-1; i>=0; i--){
            if(diretorio.charAt(i)=='\\'){
                break;
            }            
            
            if(botao){
                nomeDocumento += diretorio.charAt(i);
            }
            
            if(diretorio.charAt(i)=='.'){
                botao = true;
            }
            else{
                
            }
        }
        
        StringBuilder invertido = new StringBuilder(nomeDocumento);
        nomeDocumento = invertido.reverse().toString();
        
        return nomeDocumento;
    }
}
