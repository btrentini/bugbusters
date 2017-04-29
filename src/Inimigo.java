/**
 * @author Students
 * André N. Schuster
 * Bruno J. Souza
 * Luiz F. Lemberg
 * 
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.*;

public class Inimigo extends Ator{
// Declaração dos estados do inimigo. O primeiro DEVE ser Ator.ATOR_PROG_INI.
    protected static final int INIMIGO_PARADO          = 3;
    protected static final int INIMIGO_ANDANDO         = 4;
    protected static final int INIMIGO_ATACANDO        = 5;
    
    protected static final int VINIMIGO                = 3;
    
    // Define o quanto que o bug tira de vida do jogador
    int potAtaque = 0;
    
    // Variaveis contadoras de temporização
    int tempDirecao = 0;
    int temporizacao = 0;
    
    /** Os quadros do inimigo. */
    protected static final int quadrosInimigo[][] =
    {
        {0,1},                        // 0: Joaninha andando para cima
        {2,3},                        // 1: Joaninha andando para baixo       
        {4,5},                        // 2: Joaninha andando para direita
        {6,7},                        // 3: Joaninha andando para esquerda
        {8,9},                        // 4: Joaninha andando para diag. sup direita
        {10,11},                      // 5: Joaninha andando para diag. sup esquerda
        {12,13},                      // 6: Joaninha andando para diag. inf direita
        {14,15},                      // 7: Joaninha andando para diag. inf esquerda
        {16}                          // 8: Joaninha morta  
    };
    
    /** Cria uma nova instância do inimigo. Recebe a imagem a ser utilizada, mais as coordenadas
     x,y do inimigo no cenário. */
    public Inimigo(Image imagem, int x, int y, int direcao, CanvasBase cb, int alt, int larg)
    {
        super(imagem, alt, larg, alt, larg, 0, 0, quadrosInimigo, 1,
                x, y, direcao, cb);
    }
    
    /** Construtor de cópia. */
    public Inimigo(Inimigo j, int x, int y, int direcao, int energia, int potAtaque, int temporizacao)
    {
        super(j, x, y, direcao);
        this.energia = energia;
        this.energiaInicial = energia;
        this.tempDirecao = tempoPadrao;
        this.temporizacao = temporizacao;
        this.potAtaque = potAtaque;
    }
    
    /** O método responsável por dar vida ao inimigo na tela */
    protected void processaEvento(Evento ev)
    {
        // Escolhe o que faz de acordo com o estado
        switch(estado)
        {
            case ATOR_NASCENDO:
                // Coloca o ator no primeiro estado
                trocaEstado(this.INIMIGO_ANDANDO);
                // Guarda as coordenadas iniciais no vetor de auxiliares
                auxiliares[0] = x;
                auxiliares[1] = y;
                temporizador=10;
                break;                   
            case INIMIGO_PARADO:
                // inimigo parado para atacar.
                if(estadoInicio)
                {
                    // Troca a animação de acordo com a direção na qual o personagem olha
                    switch(direcao)
                    {
                        case 0:
                            trocaAnimacao(2);
                            break;
                        case 45:
                            trocaAnimacao(4);
                            break;
                        case 90:
                            trocaAnimacao(0);
                            break;
                        case 135:
                            trocaAnimacao(5);
                            break;
                        case 180:
                            trocaAnimacao(3);
                            break;
                        case 225:
                            trocaAnimacao(7);
                            break;
                        case 270:
                            trocaAnimacao(1);
                            break;
                        case 315:
                            trocaAnimacao(6);
                            break;
                        
                        default:System.out.println("PROC. EV INIMIGO PARADO: " + ev.tipo);
                            break;
                    }
                    dx=dy=0;
                    estadoInicio=false;
                }
                temporizacao--;
                if(temporizacao<=0)
                    trocaEstado(INIMIGO_ATACANDO);
                break;
            case INIMIGO_ANDANDO:
                if(estadoInicio)
                {
                    // por default, não se desloca
                    dx=dy=0;
                    
                    // Troca a animação de acordo com a direção na qual o inimigo olha
                    switch(direcao)
                    {
                        case 0:
                            trocaAnimacao(2);
                            dx=VINIMIGO;
                            break;
                        case 45:
                            trocaAnimacao(4);
                            dx=VINIMIGO;
                            dy=-VINIMIGO;
                            break;
                        case 90:
                            trocaAnimacao(0);
                            dy=-VINIMIGO;
                            break;
                        case 135:
                            trocaAnimacao(5);
                            dx=-VINIMIGO;
                            dy=-VINIMIGO;
                            break;
                        case 180:
                            trocaAnimacao(3);
                            dx=-VINIMIGO;
                            break;
                        case 225:
                            trocaAnimacao(7);
                            dx=-VINIMIGO;
                            dy=VINIMIGO;
                            break;
                        case 270:
                            trocaAnimacao(1);
                            dy=VINIMIGO;
                            break;
                        case 315:
                            trocaAnimacao(6);
                            dx=VINIMIGO;
                            dy=VINIMIGO;
                            break;
                        
                        default:System.out.println("PROC. EV INIMIGO ANDANDO: " + ev.tipo);
                            break;
                            
                    }
                    temporizacao--;
                    if(temporizacao<=0){
                        temporizacao = 10;
                        this.trocaEstado(INIMIGO_PARADO);
                    }
                    //estadoInicio=false;
                    //System.out.println("MET. processaEV - dir: " + direcao);
                }
                break;
            case INIMIGO_ATACANDO:
                // inimigo ataca e volta a andar
                jogo.fila.envia(Evento.EVT_INIMIGO_ATACANDO, 0, x, y, potAtaque);           
                temporizacao = 40;
                trocaEstado(INIMIGO_ANDANDO);                
                break;
        }
    } 
    
    protected boolean atualizaInimigo(Inimigo aux){
    
            // testa se inimigo está morto e acrescenta os pontos
            if(estado==ATOR_MORTO){
                return false;
            }else{
                // Altera a posição dos inimigos
                this.deslocaAtor();
                // Padr�o do m�todo: SEMPRE anima o ator antes de qualquer coisa
                aux.animaAtor();
                // Calcula o temporizador
                if(temporizador>0)
                    if(--temporizador==0)
                        fila.envia(Evento.EVT_TEMPO, 0, 0, 0, 0);
                
                //System.out.println("MET. atualiza - temp: " + temporizador);
                // Invoca o processamento de eventos se necessário
                Evento ev;
                
                while((ev=fila.proximo())!=null){
                    processaEvento(ev);
                }                
                
                if(estado==ATOR_MORTO)
                    return false;
                else{
                    return true;
                } 
            }
    }
    protected void deslocaAtor()
    {
        x+=dx;
        y+=dy;
        
        
        if(x>=750){
            //System.out.println("DIREITA");
            //System.out.println("posição:" + x + "," + y);
            if(y<=50)
                direcao = 225;
            else if(y>=533)
                direcao = 135;
            else
                direcao = 180;
            
            this.tempDirecao = this.tempoPadrao;
        }
       
        if(x<=50){
            //System.out.println("ESQUERDA");
            //System.out.println("posição:" + x + "," + y);
            if(y<=50)
                direcao = 315;
            else if(y>=533)
                direcao = 45;
            else
                direcao = 0;
            
            this.tempDirecao = this.tempoPadrao;
        }
            
        if(y>=533){
            //System.out.println("BAIXO");
            //System.out.println("posição:" + x + "," + y);
            if(x<=50)
                direcao = 45;
            else if(x>=750)
                direcao = 135;
            else
                direcao = 90;
            
            this.tempDirecao = this.tempoPadrao;
        }
        if(y<=50){
            //System.out.println("CIMA");
            //System.out.println("posição:" + x + "," + y);
            if(x<=50)
                direcao = 315;
            else if(x>=750)
                direcao = 225;
            else
                direcao = 270;
            
            this.tempDirecao = this.tempoPadrao;
        }
            
        // Altera a posi��o do sprite
            this.setPosition(x,y);
    }
}
