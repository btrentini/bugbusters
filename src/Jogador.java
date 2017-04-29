/*
 * Jogador.java
 *
 * Created on 12 de Agosto de 2007, 01:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author paulo.radtke
 */

import java.io.*;
import java.util.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.*;
import javax.microedition.pim.Event;


public class Jogador extends Ator
{
    // Declara��o dos estados do jogador. O primeiro DEVE ser Ator.ATOR_PROG_INI.
    protected static final int JOGADOR_INVULNERAVEL    = 3;
    protected static final int JOGADOR_PARADO          = 4;
    protected static final int JOGADOR_ANDANDO         = 5;
    protected static final int JOGADOR_VITORIA         = 6;
    protected static final int JOGADOR_CAIDO           = 7;
    
    protected static final int VJOGADOR                = 8;
    
    // Vetor de armas
    Vector armas;
    // Arma atual
    Arma armaAtual;
    // Indice do vetor de armas atual
    int seletorArma;
    
    
    /** Os quadros do jogador. */
    protected static final int quadrosJogador[][] =
    {
        {0},       // 0: Spray sem alvo
        {0},       // 1: Spray com alvo
    };
    
    /** Cria uma nova inst�ncia de jogador. Recebe a imagem a ser utilizada, mais as coordenadas
     x,y do jogador no cen�rio. */
    public Jogador(Image imagem, int x, int y, int direcao, CanvasBase cb)
    {
        super(imagem, 66, 66, 66, 66, 0, 0, quadrosJogador, 1,
                x, y, direcao, cb);
    }
    
    /** Construtor de cópia. */
    public Jogador(Jogador j, int x, int y, int direcao, Vector armas)
    {
        super(j, x, y, direcao);
        vidas = 3;
        energia = 100;
        this.energiaInicial = energia;
        seletorArma = 0;
        this.armas = armas;
        armaAtual = (Arma)armas.elementAt(seletorArma);
    }
    
    /** O m�todo respons�vel por dar vida ao jogador na tela, de acordo com as teclas. */
    protected void processaEvento(Evento ev)
    {
        // Escolhe o que faz de acordo com o estado
        switch(estado)
        {
            case ATOR_NASCENDO:
                // Coloca o ator no primeiro estado
                trocaEstado(this.JOGADOR_INVULNERAVEL);
                // Guarda as coordenadas iniciais no vetor de auxiliares
                auxiliares[0] = x;
                auxiliares[1] = y;
                break;                   
            case JOGADOR_INVULNERAVEL:
                if(estadoInicio)
                {
                    trocaAnimacao(0);
                    temporizador=10;
                    // Muda a velocidade do personagem
                    dx=dy=0;
                    // Recupera a posição x,y do vetor de auxiliares
                    x = auxiliares[0];
                    y = auxiliares[1];
                    estadoInicio=false;                    
                }
                // como aqui trata apenas UM tipo de evento, não precisa de um switch
                // Se acabar o tempo de invulnerabilidade
                switch(ev.tipo) 
                {
                    case Evento.EVT_TEMPO:
                        // Muda de estado
                        trocaEstado(JOGADOR_PARADO);
                        // Por default, a direção é olhando para baixo
                        direcao=270;
                        break;
                }
                break;
            case JOGADOR_PARADO:
                if(estadoInicio)
                {
                    // Troca a animação de acordo com a direção na qual o personagem olha
                    switch(direcao)
                    {
                        case 0:
                            trocaAnimacao(0);
                            break;
                        case 90:
                            trocaAnimacao(0);
                            break;
                        case 180:
                            trocaAnimacao(0);
                            break;
                        case 270:
                        default:
                            trocaAnimacao(0);
                            break;
                    }
                    dx=dy=0;
                    estadoInicio=false;
                }
                switch(ev.tipo)
                {
                    case Evento.EVT_PRESSIONOU_BAIXO:
                        trocaEstado(JOGADOR_ANDANDO);
                        direcao = 270;
                        break;
                    case Evento.EVT_PRESSIONOU_CIMA:
                        trocaEstado(JOGADOR_ANDANDO);
                        direcao = 90;                        
                        break;
                    case Evento.EVT_PRESSIONOU_ESQ:
                        trocaEstado(JOGADOR_ANDANDO);
                        direcao = 180;                        
                        break;
                    case Evento.EVT_PRESSIONOU_DIR:
                        trocaEstado(JOGADOR_ANDANDO);
                        direcao = 0;                        
                        break;
                    case Evento.EVT_PRESSIONOU_BOTAO1:
                        jogo.fila.envia(Evento.EVT_PERSONAGEM_ATIRANDO, 0, 0, 0, 0);
                        break;
                    case Evento.EVT_PRESSIONOU_BOTAO2:
                        //troca para proxima arma
                        trocaArma(+1);
                        break;
                    case Evento.EVT_PRESSIONOU_BOTAO3:
                        jogo.fila.envia(Evento.EVT_PRESSIONOU_BOTAO3, 0, 0, 0, 0);
                        break;
                    case Evento.EVT_PRESSIONOU_BOTAO4:
                        //troca para arma anterior
                        trocaArma(-1);
                        break;
                    case Evento.EVT_TROCA_ARMA:
                        jogo.fila.envia(Evento.EVT_TROCA_ARMA, 0, 0, 0, 0);
                }        
                break;
            case JOGADOR_ANDANDO:
                if(estadoInicio)
                {
                    // por default, não se desloca
                    dx=dy=0;
                    // Troca a animação de acordo com a direção na qual o personagem olha
                    switch(direcao)
                    {
                        case 0:
                            trocaAnimacao(0);
                            dx=VJOGADOR;
                            break;
                        case 90:
                            trocaAnimacao(0);
                            dy=-VJOGADOR;
                            break;
                        case 180:
                            trocaAnimacao(0);
                            dx=-VJOGADOR;
                            break;
                        case 270:
                        default:
                            dy=VJOGADOR;
                            trocaAnimacao(0);
                            break;
                    }
                    estadoInicio=false;
                }
                // Processa o evento
                switch(ev.tipo)
                {
                    case Evento.EVT_PRESSIONOU_BAIXO:
                        switch(direcao)
                        {
                            case 0:
                                direcao=315;
                                dy=VJOGADOR;
                                break;
                            case 180:
                                direcao=225;
                                dy=VJOGADOR;
                                break;
                        }
                        break;
                    case Evento.EVT_LIBEROU_BAIXO:
                        if(direcao==270)
                            trocaEstado(JOGADOR_PARADO);
                        else
                        {
                            switch(direcao)
                            {
                                case 225:
                                    direcao=180;
                                    dy=0;
                                    break;
                                case 315:
                                    direcao=0;
                                    dy=0;
                                    break;                                    
                            }
                        } 
                        break;
                        
                        case Evento.EVT_PRESSIONOU_ESQ:
                        switch(direcao)
                        {
                            case 90:
                                direcao=135;
                                dx=-VJOGADOR;
                                break;
                            case 270:
                                direcao=225;
                                dx=-VJOGADOR;
                                break;
                        }
                        break;
                        case Evento.EVT_LIBEROU_ESQ:
                        if(direcao==180)
                            trocaEstado(JOGADOR_PARADO);
                        else
                        {
                            switch(direcao)
                            {
                                case 135:
                                    direcao = 90;
                                    dx=0;
                                    break;
                                case 225:
                                    direcao = 270;
                                    dx=0;
                                    break;                                    
                            }
                        }
                        break;
                        
                        case Evento.EVT_PRESSIONOU_CIMA:
                        switch(direcao)
                        {
                            case 0:
                                direcao = 45;
                                dy=-VJOGADOR;
                                break;
                            case 180:
                                direcao = 135;
                                dy=-VJOGADOR;
                                break;
                        }
                        break;
                        case Evento.EVT_LIBEROU_CIMA:
                        if(direcao==90)
                            trocaEstado(JOGADOR_PARADO);
                        
                        else
                        {
                            switch(direcao)
                            {
                                case 45:
                                    direcao = 0;
                                    dy=0;
                                    break;
                                case 135:
                                    direcao = 180;
                                    dy=0;
                                    break;                                    
                            }
                        }
                        break;
                        
                        case Evento.EVT_PRESSIONOU_DIR:
                        switch(direcao)
                        {
                            case 90:
                                direcao = 45;
                                dx=VJOGADOR;
                                break;
                            case 270:
                                direcao = 315;
                                dx=VJOGADOR;
                                break;
                        }
                        break;
                        case Evento.EVT_LIBEROU_DIR:
                        if(direcao==0)
                            trocaEstado(JOGADOR_PARADO);
                        else
                        {
                            switch(direcao)
                            {
                                case 45:
                                    direcao = 90;
                                    dx=0;
                                    break;
                                case 315:
                                    direcao = 270;
                                    dx=0;
                                    break;                                    
                            }
                        }
                        break;
                    // Se colidiu com um checkpoint, guarda o x,y para reiniciar
                    case Evento.EVT_COLIDIU_CHECKPOINT:
                        auxiliares[0]=x;
                        auxiliares[1]=y;
                        // Se tivesse algo especial a fazer com o checkpoint, faria aqui
                        break;
                    // Se bateu com uma armadilha
                    case Evento.EVT_COLIDIU_ARMADILHA:
                        // Muda o estado
                        trocaEstado(JOGADOR_CAIDO);
                        break;
                    case Evento.EVT_COLIDIU_FIMFASE:
                        trocaEstado(JOGADOR_VITORIA);
                        break;
                    case Evento.EVT_PRESSIONOU_BOTAO1:
                        jogo.fila.envia(Evento.EVT_PERSONAGEM_ATIRANDO, 0, 0, 0, 0);
                        break; 
                    case Evento.EVT_PRESSIONOU_BOTAO2:
                        //troca para proxima arma
                        trocaArma(+1);
                        break;    
                    case Evento.EVT_PRESSIONOU_BOTAO3:
                        jogo.fila.envia(Evento.EVT_PRESSIONOU_BOTAO3, 0, 0, 0, 0);
                    break;
                    case Evento.EVT_PRESSIONOU_BOTAO4:
                        //troca para arma anterior
                        trocaArma(-1);
                        break;
                    case Evento.EVT_TROCA_ARMA:
                        jogo.fila.envia(Evento.EVT_TROCA_ARMA, 0, 0, 0, 0);    
                }
                break;
            case ATOR_MORTO:

                jogo.fila.envia(Evento.EVT_FIM_FASE_DERROTA, 0, 0, 0, 0);
                break;
        }    
    
    }
    protected boolean mataInimigo(Ator inimigo){
        if(colideAtor(inimigo)==true){
            inimigo.energia -= armaAtual.getPotencia();
            if(inimigo.energia <= 0)
                inimigo.trocaEstado(ATOR_MORTO);                
            return true;
        }
        return false;
    }
    
    protected void kickAll(Vector inimigos){
        Inimigo bug;
        for(int cont=0; cont<inimigos.size();cont++){
            bug = (Inimigo)inimigos.elementAt(cont);
            bug.energia = 0;
            bug.trocaEstado(ATOR_MORTO); 
        }
    }
    
    protected void killAll(Vector inimigos, int xview, int yview){
        Inimigo bug;
        for(int cont=0; cont<inimigos.size();cont++){
            bug = (Inimigo)inimigos.elementAt(cont);
            if((bug.x>=xview)&&(bug.x<=(xview+240))){
                if((bug.y>=yview)&&(bug.y<=(yview+250))){
                    bug.energia = 0;
                    bug.trocaEstado(ATOR_MORTO);
                }
            }
        }
    }
    
    protected void trocaArma(int seletor){
        boolean selecionado = false;
        Arma temp = armaAtual;
        do{
            seletorArma += seletor;
            if(seletorArma >= armas.size())
                seletorArma = 0;
            else if(seletorArma < 0)
                seletorArma = armas.size() - 1;
            
            Arma newGun = (Arma) armas.elementAt(seletorArma);    
            
            if(newGun.estaDisponivel()==true){
               armaAtual.setVisible(false); 
               armaAtual = newGun;
               atualizaArma(armaAtual, temp);
               selecionado = true;
               fila.envia(Evento.EVT_TROCA_ARMA, 0, 0, 0, 0);
            }
        }while(selecionado==false);
    }
    
    protected void atualizaArma(Arma atual, Arma antiga){
        atual.x = antiga.x;
        atual.y = antiga.y;
        this.setPosition(x, y);
        armaAtual.setVisible(true);
    }
    
    public void tiraEnergia(int vAtaque){
        this.energia -= vAtaque;
    }
}
