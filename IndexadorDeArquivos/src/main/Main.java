package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import telas.TelaPrincipal;

/**
 * A classe Main permite a execução da aplicação tendo, como único método,
 * o método main.
 * 
 * @author Alisson Jaques
 */
public class Main {
    /**
     * O método main permite a execução da aplicação, ele cria uma instância da 
     * classe TelaPrincipal e faz uso da mesma, possibilitando ao usuário a 
     * interação com o sistema.
     * 
     * @param args os argumentos que podem ser passados ao método, via linha de comando,
     * mas que neste caso serão ignorados
     */
    public static void main(String args[]){
        TelaPrincipal tela;
        try {
            tela = new TelaPrincipal();
            tela.setVisible(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
} // fim do método Main
