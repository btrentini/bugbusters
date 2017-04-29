/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author paulo.radtke
 */

// Importa o pacote que tem o GameCanvas
import javax.microedition.lcdui.game.*;

public final class Teclado {
    
   /** Indica o estado da tecla de cima. */
   public Tecla cima;
   /** Indica o estado da tecla de baixo. */
   public Tecla baixo;
   /** Indica o estado da tecla da esquerda. */
   public Tecla esquerda;
   /** Indica o estado da tecla da direita. */
   public Tecla direita;
   /** Indica o estado da tecla de tiro. */
   public Tecla tiro;
   /** Indica o estado da tecla game A. */
   public Tecla gameA;
   /** Indica o estado da tecla game B. */
   public Tecla gameB;
   /** Indica o estado da tecla game C. */
   public Tecla gameC;
   /** Indica o estado da tecla game D. */
   public Tecla gameD;
   
   /** Atributo privado da instância do singleton. */
   private static Teclado instancia=null;
   
   private Teclado()
   {
       cima = new Tecla();
       baixo = new Tecla();
       esquerda = new Tecla();
       direita = new Tecla();
       tiro = new Tecla();
       gameA = new Tecla();
       gameB = new Tecla();
       gameC = new Tecla();
       gameD = new Tecla();    
   }

   /** Método que retorna a instância do singleton do Teclado.*/
   public static Teclado pegaInstancia()
   {
       if(instancia == null)
       {
           instancia = new Teclado();
           return instancia;
       }
       else
           return instancia;     
   }
   
   /** Método para resertar o teclado. */
   public void reset()
   {
        cima.reset();
        baixo.reset();
        esquerda.reset();
        direita.reset();
        tiro.reset();
        gameA.reset();
        gameB.reset();
        gameC.reset();
        gameD.reset();
   }
   
   /** Método que atualiza o estado das teclas do Teclado.*/   
   public void atualiza(int valTeclado)
   {
       if((valTeclado & GameCanvas.UP_PRESSED) != 0)
           cima.atualiza(true);
       else
           cima.atualiza(false);
       if((valTeclado & GameCanvas.DOWN_PRESSED) != 0)
           baixo.atualiza(true);
       else
           baixo.atualiza(false);
       if((valTeclado & GameCanvas.LEFT_PRESSED) != 0)
           esquerda.atualiza(true);
       else
           esquerda.atualiza(false);
       if((valTeclado & GameCanvas.RIGHT_PRESSED) != 0)
           direita.atualiza(true);
       else
           direita.atualiza(false);
       if((valTeclado & GameCanvas.FIRE_PRESSED) != 0)
           tiro.atualiza(true);
       else
           tiro.atualiza(false);
       if((valTeclado & GameCanvas.GAME_A_PRESSED) != 0)
           gameA.atualiza(true);
       else
           gameA.atualiza(false);
       if((valTeclado & GameCanvas.GAME_B_PRESSED) != 0)
           gameB.atualiza(true);
       else
           gameB.atualiza(false);
       if((valTeclado & GameCanvas.GAME_C_PRESSED) != 0)
           gameC.atualiza(true);
       else
           gameC.atualiza(false);
       if((valTeclado & GameCanvas.GAME_D_PRESSED) != 0)
           gameD.atualiza(true);
       else
           gameD.atualiza(false);       
   }
}

