/*
 * Jogo.java
 *
 * Created on 20 de Outubro de 2006, 23:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author PV
 */
public class Jogo extends MIDlet {

    /** Refer�ncia para o GameCanvas do jogo */
    private CanvasBase canvas;
    /** Refer�ncia para o display do celular */
    private Display display;

    /** Creates a new instance of Jogo */
    public Jogo() {
        // Recupera o display do celular
	display = Display.getDisplay(this);    
        // Cria o canvas do jogo
        canvas = new JogoCanvas(this);
    }
    
    public void startApp() {
        // Associa o canvas do jogo ao display do celular
        display.setCurrent(canvas);
        // Inicia a thread do canvas
        canvas.inicia();
    }
    
    public void pauseApp() {
        // Pausa o jogo
        canvas.pausa();
    }
    
    public void destroyApp(boolean unconditional) {
        // Pausa o jogo antes de encerrar (mata a thread)
        canvas.pausa();
    }   
}