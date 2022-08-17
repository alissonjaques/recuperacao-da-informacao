package controle;
import java.io.*;
import java.text.*;
import java.util.ArrayList;

import org.apache.log4j.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.tika.*;
import telas.TelaOcorrencias;


public class Indexador {
  private static Logger logger = Logger.getLogger(Indexador.class);
  //{1}
  private String diretorioDosIndices = "src\\indice";
  //{2}
  private String diretorioParaIndexar = "src\\arquivosHTML";
  //{3}
  private IndexWriter writer;
  //{4}
  private Tika tika;

  public void indexaArquivosDoDiretorio() {
    try {
      File diretorio = new File(diretorioDosIndices);
      apagaIndices(diretorio);
      //{5}
      Directory d = new SimpleFSDirectory(diretorio);
      logger.info("Diretório do índice: " + diretorioDosIndices);
      System.out.println("Diretório do índice: " + diretorioDosIndices);
      //{6}
      Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
      //{7}
      IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36,
          analyzer);
      //{8}
      writer = new IndexWriter(d, config);
      long inicio = System.currentTimeMillis();
      indexaArquivosDoDiretorio(new File(diretorioParaIndexar));
      //{12}
      writer.commit();
      writer.close();
      long fim = System.currentTimeMillis();
      logger.info("Tempo para indexar: " + ((fim - inicio) / 1000) + "s");
    } catch (IOException e) {
      logger.error(e);
    }
  }

  private void apagaIndices(File diretorio) {
    if (diretorio.exists()) {
      File arquivos[] = diretorio.listFiles();
      for (File arquivo : arquivos) {
        arquivo.delete();
      }
    }
  }

  public void indexaArquivosDoDiretorio(File raiz) {
    FilenameFilter filtro = new FilenameFilter() {
      @Override
      public boolean accept(File arquivo, String nome) {
        if (nome.toLowerCase().endsWith(".pdf")
            || nome.toLowerCase().endsWith(".odt")
            || nome.toLowerCase().endsWith(".doc")
            || nome.toLowerCase().endsWith(".docx")
            || nome.toLowerCase().endsWith(".ppt")
            || nome.toLowerCase().endsWith(".pptx")
            || nome.toLowerCase().endsWith(".xls")
            || nome.toLowerCase().endsWith(".txt")
            || nome.toLowerCase().endsWith(".rtf")
            || nome.toLowerCase().endsWith(".htm")
            || nome.toLowerCase().endsWith(".html")) {
          return true;
        }
        return false;
      }
    };
    
    ArrayList<String> conteudoDocumentos = TelaOcorrencias.retornaConteudoDosDocumentos();
    int j = 0;
    
    for (File arquivo : raiz.listFiles(filtro)) {
      if (arquivo.isFile()) {
        StringBuffer msg = new StringBuffer();
        msg.append("Indexando o arquivo ");
        msg.append(arquivo.getAbsoluteFile());
        msg.append(", ");
        msg.append(arquivo.length() / 1000);
        msg.append("kb");
        logger.info(msg);
        System.out.println(msg);
        try {
          //{9}
          String textoExtraido = conteudoDocumentos.get(j);
          //System.out.println("Texto gerado:"+textoExtraido);
          indexaArquivo(arquivo, textoExtraido);
          j++;
        } catch (Exception e) {
          logger.error(e);
        }
      } else {
        indexaArquivosDoDiretorio(arquivo);
      }
    }
  }

  private void indexaArquivo(File arquivo, String textoExtraido) {
    SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
    String ultimaModificacao = formatador.format(arquivo.lastModified());
    //{10}
    Document documento = new Document();
    documento.add(new Field("UltimaModificacao", ultimaModificacao,
        Field.Store.YES, Field.Index.NOT_ANALYZED));
    documento.add(new Field("Caminho", arquivo.getAbsolutePath(),
        Field.Store.YES, Field.Index.NOT_ANALYZED));
    documento.add(new Field("Texto", textoExtraido, Field.Store.YES,
        Field.Index.ANALYZED));
    try {
      //{11}
      getWriter().addDocument(documento);
    } catch (IOException e) {
      logger.error(e);
      System.out.println(e);
    }
  }

  public Tika getTika() {
    if (tika == null) {
      tika = new Tika();
    }
    return tika;
  }

  public IndexWriter getWriter() {
    return writer;
  }
}