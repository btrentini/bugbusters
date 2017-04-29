/*
 * JogoCanvas.java - Vers�o MapaBinario
 *
 * Created on 6 de Agosto de 2007
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import java.io.*;
import java.util.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.*;

/**
 *
 * @author PV
 */
public abstract class CanvasBase extends GameCanvas implements Runnable{
    
  
    /** O pai da aplica��o */
    private MIDlet pai;
    
    /** Atributo booleano que indica quando a thread do jogo deve rodar ou n�o. */
    private boolean rodando = false;
    /** Atributo que indica a dimens�o horizontal da tela do aparelho. */
    protected int largura;
    /** Atributo que indica a dimens�o vertical da tela do aparelho. */
    protected int altura;      
    /** Indica se carregou ou n�o os dados. */
    protected boolean carregado = false;
    /** O estado da aplica��o. Pode possuir um dos valores:
     * -1: encerra a aplica��o
     * 0: splash
     * 1: menu do jogo
     * 2: tela do jogo
     * 3: tela de recordes
     * 4: tela de cr�ditos
     * 5: tela de ajuda
     * 6: tela de opções
     * 7: tela de abertura
     * 8: tela de gameover
     */
    protected int estado = 0;
    /** A fila de eventos. */
    public FilaEvento fila;
            
    
    /** Creates a new instance of HYPECanvas */
    public CanvasBase(MIDlet pai) {
        super(true);
        setFullScreenMode(true);
        // Obt�m as dimens�es da tela do dispositivo
	largura = getWidth();
	altura = getHeight();  
        // Chama o carregamento da configuracao
        carregaConfiguracao();
        this.pai = pai;
        // Inicia a fila
        fila = new FilaEvento();
    }
    
    /** M�todo para carregar as informa��es do jogo. */
    protected abstract void carregaDados();
    /** M�todo para carregar dados de configura��o. */
    protected abstract void carregaConfiguracao();
    /** M�todo para salvar dados de configura��o. */
    protected abstract void gravaConfiguracao();

    /** M�todo para desenhar o texto Carregando na tela. */
    protected void desenhaCarregando(Graphics g)
    {
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, largura, altura);
        g.setColor(255,255,255);
        g.drawString("Carregando ...", largura/2, altura/2, Graphics.HCENTER | Graphics.BASELINE);
        flushGraphics();
    }
    
    /** M�todo para iniciar o jogo */
    public void inicia()
    {
        // Cria uma nova thread baseada neste objeto
        Thread t = new Thread(this);
        // Indica que a thread deve rodar
        rodando=true;
        // Inicia a thread do jogo
        t.start();
    }

    /** M�todo para pausar o jogo */
    public void pausa()
    {
        // Indica que a thread deve parar de rodar
        rodando=false;
    }

    /** M�todo da thread do jogo */
    public void run()
    {
        // Coloca em tela cheia
        setFullScreenMode(true);
        // Recupera o contexto gr�fico
        Graphics g = getGraphics();
        // REcupera o teclado
        Teclado t = Teclado.pegaInstancia();
        // Carrega os dados
        if(!carregado)
            carregaDados();
        // Vari�veis para contar o tempo passado durante a l�gica do jogo e 
        // sincronizar tudo a 20 quadros por segundo
        long tInicial, diferenca;       
        
        // Roda a roda!
        while(rodando && estado != -1)
        {
            // L� o tempo inicial para este quadro
            tInicial=System.currentTimeMillis();
            // Atualiza o teclado
            t.atualiza(getKeyStates());
            switch(estado)
            {
                case 0:
                    desenhaTelaSplash(g);
                    break;
                case 1:
                    desenhaTelaMenu(g);
                    break;
                case 2:
                    desenhaTelaJogo(g);
                    break;
                case 3:
                    desenhaTelaRecordes(g);
                    break;
                case 4:
                    desenhaTelaCreditos(g);
                    break;
                case 5:
                    desenhaTelaAjuda(g);
                    break;
                case 6:
                    desenhaTelaOpcoes(g);
                    break;
                case 7:    
                    desenhaTelaAbertura(g);
                    break;
                case 8:
                    desenhaTelaGameOver(g);
                    break;
                default:
                    g.setColor(0,0,255);
                    g.fillRect(0, 0, largura, altura);
                    g.setColor(255, 255, 255);
                    g.drawString("Estado inv�lido.", largura/2, altura/2, Graphics.HCENTER | Graphics.BASELINE);
                    break;
            }
            // Completa o desenho da tela
            this.flushGraphics();
                       
            // Calcula o tempo que levou no quadro
            diferenca=System.currentTimeMillis()-tInicial;
            // Se levou menos que 50 ms (20 quadros por segundo)
            if(diferenca < 50)
            {
                // D� um delay no jogo para rodar a 20fps
                try
                {
                    Thread.sleep(50-diferenca);
                }
                catch(InterruptedException e){}
            }
            else
            {
                // D� um delay no jogo para outros eventos
                try
                {
                    Thread.sleep(1);
                }
                catch(InterruptedException e){}
            }
            // Salva a configura��o se o estado � -1
            if(estado == -1)
            {
                gravaConfiguracao();
                pai.notifyDestroyed();
            }
        }
    }

    // M�todo para carregar uma imagem. Simples e eficiente.
    protected Image carregaImg(String img)
    {
        try
        {
            // Carrega a tela de splash
            Image i = Image.createImage(img);
            return i;
        }
        catch(java.io.IOException e)
        {
            return null;
        }            
    }


    
    protected abstract void desenhaTelaSplash(Graphics g);
    protected abstract void desenhaTelaMenu(Graphics g);
    protected abstract void desenhaTelaJogo(Graphics g);
    protected abstract void desenhaTelaRecordes(Graphics g);
    protected abstract void desenhaTelaCreditos(Graphics g);
    protected abstract void desenhaTelaAjuda(Graphics g);
    protected abstract void desenhaTelaOpcoes(Graphics g);
    protected abstract void desenhaTelaAbertura(Graphics g);
    protected abstract void desenhaTelaGameOver(Graphics g);
}
