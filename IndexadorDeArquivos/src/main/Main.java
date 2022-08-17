package main;

import telas.TelaPrincipal;

/**
 * A classe Main permite a execu��o da aplica��o tendo, como �nico m�todo,
 * o m�todo main.
 * 
 * @author Alisson Jaques
 */
public class Main {
    /**
     * O m�todo main permite a execu��o da aplica��o, ele cria uma inst�ncia da 
     * classe TelaPrincipal e faz uso da mesma, possibilitando ao usu�rio a 
     * intera��o com o sistema.
     * 
     * @param args os argumentos que podem ser passados ao m�todo, via linha de comando,
     * mas que neste caso ser�o ignorados
     */
    public static void main(String args[]){
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);    
    }
} // fim do m�todo Main
