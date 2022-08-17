/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Alisson Jaques  
 */
public class Teste {
    public static void main(String[] args) throws IOException{
        BuscaProbabilista busca = new BuscaProbabilista();
        BuscaProbabilistaComDocsRelevantes busca1 = new BuscaProbabilistaComDocsRelevantes();
        
        //busca.geraVetorDeSimilaridade("joão maria sexo");
        ArrayList<String> documentosRelevantes = new ArrayList();
        documentosRelevantes.add("pf0816pu");
        documentosRelevantes.add("pf0863pu");
        documentosRelevantes.add("pf0964pu");
        String consulta = "josé masculino mãe";
        
        busca1.vetorDeSimilaridadeComDocumentosRelevantes(consulta,documentosRelevantes);
        busca.geraVetorDeSimilaridade(consulta);
        
    }
}
