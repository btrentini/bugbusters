/*
 * Ator.java
 *
 * Created on 11 de Agosto de 2007, 08:45
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
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.*;


public abstract class Ator extends Sprite
{
    /** O Canvas Base que contém o jogo. */
    protected CanvasBase jogo=null;
    /** Constante para indicar que um ator est� surgindo. */
    public static final int ATOR_NASCENDO  = 0;
    /** Constante para indicar que um ator est� morto. */
    public static final int ATOR_MORTO     = 1;
    /** Constante para indicar que um ator está inativo e deve ser removido. */
    public static final int ATOR_ENCERRADO  = 2;
    /** Constante para indicar o valor inicial dos estados do personagem derivado. */
    public static final int ATOR_PROG_INI   = 3;
    /** Constante para indicar o número de variáveis auxiliares. */
    protected static final int NUM_AUXILIARES = 8;
    
    /** O estado atual do Ator */
    public int estado;
    /** Indica se está no estado pela primeira vez. */
    protected boolean estadoInicio;
    /** A coordenada x do ator no mapa. */
    public int x;
    /** A coordenada y do ator no mapa.*/
    public int y;
    /** A velocidade do ator no eixo x. */
    protected int dx;
    /** A velocidade do ator no eixo y.*/
    protected int dy;
    /** A largura do bounding box do Ator. */
    public int largura;
    /** A ALTURA do bounding box do Ator. */
    public int altura;
    /* A coordenada de refer�ncia x dentro do sprite para o in�cio do bounding box.*/
    protected int xref;
    /* A coordenada de refer�ncia x dentro do sprite para o in�cio do bounding box.*/
    protected int yref;
    /** Qual a animação atual. */
    private int numAnim=0;
    /** O tempo entre quadros de anima��o. */
    private int tquadro;
    /** O tempo atual do quadro de anima��o; */
    private int tatual;
    /** O Quadro atual da anima��o. Necess�rio para determinar QUANDO a anima��o encerra. */
    private int quadroatual;
    /** Os quadros da anima��o do Ator. */
    private int quadros[][];
    
    /** A velocidade inicial no eixo y. Usado para pular. */
    private int vini;
    /** O tempo da queda do pesonagem, em quadros por segundo. */
    private int tqueda;
    /** O temporizador do personagem. */
    protected int temporizador=0;
    /** A direção do personagem em graus. */
    protected int direcao=0;
    /** Variáveis auxiliares. Depende do personagem em desenvolvimento. */
    protected int auxiliares[];
    
    /** as vidas do personagem. */
    public int vidas=0;
    /** A energia do personagem. */
    public int energia=0;
    /** A energia de quando o personagem é criado. */
    public int energiaInicial=0;
    
    public int tempoPadrao = 80;
    
    /** Fila de eventos do perosnagem. */
    FilaEvento fila;
    
    /** Cria uma nova inst�ncia do Ator. Os par�metros s�o como seguem:
     *
     *      Image imagem: uma inst�ncia de imagem para cria o sprite do Ator.
     *      int largbloco: a largura do bloco do ator em pixels. Usado para dividir a imagem.
     *      int altbloco: a altura do bloco do ator em pixels. Usado para dividir a imagem.
     *      int largura: a largura do bounding box do ator, usado para colis�o com cen�rio.
     *      int altura: a altura do bounding box, usado para colis�o com o cen�rio.
     *      int xref: o ponto x dentro dos quadros do sprite aonde come�a o bounding box.
     *      int yref: o ponto y dentro dos quadros do sprite aonde come�a o bounding box.
     *      int quadros[][]: vetor de vetores de anima��o. Cada vetor representa a anima��o de um estado.    
     */
    public Ator(Image imagem, int largbloco, int altbloco,
            int largura, int altura, int xref, int yref,
            int quadros[][], int tquadro, 
            int x, int y, int direcao,
            CanvasBase cb) 
    {
        super(imagem, largbloco, altbloco);
        // Inicia o Ator
        fila = new FilaEvento();
        auxiliares = new int[NUM_AUXILIARES];
        this.x = x;
        this.y = y;
        dx = 0;
        dy = 0;
        this.largura = largura;
        this.altura = altura;
        this.xref = xref;
        this.yref = yref;
        this.quadros = quadros;
        this.tquadro = tquadro;
        this.direcao=direcao;
        this.trocaEstado(this.ATOR_NASCENDO);
        // Zera a velocidade de queda
        vini = 0;
        tqueda = 0;

        
        jogo = cb;
    }

    /** Construtor de cópia do Ator. Recebe o ator e a sua nova posição inicial. */
    public Ator(Ator a, int x, int y, int direcao) 
    {
        super(a);
        // Inicia o Ator
        fila = new FilaEvento();
        auxiliares = new int[NUM_AUXILIARES];

        this.x = x;
        this.y = y;
        this.direcao = direcao;
        dx = 0;
        dy = 0;
        this.largura = a.largura;
        this.altura = a.altura;
        this.xref = a.xref;
        this.yref = a.yref;
        this.quadros = a.quadros;
        this.tquadro = a.tquadro;
        this.trocaEstado(Ator.ATOR_NASCENDO);
        // Zera a velocidade de queda
        vini = 0;
        tqueda = 0;
        jogo = a.jogo;
    }
    
    
    /**     M�todo que anima um ator. Respons�vel por controlar o tempo de cada quadro de anima��o e 
     * por indicar quando a anima��o recome�a. */
    protected final void animaAtor()
    {
        // Incrementa o tempo do quadro atual e diz que est� no meio da anima��o
        tatual++;
        // Testa se n�o deve trocar o quadro. Se for o caso, retorna
        if(tatual<tquadro)
            return;
        // Se for falso, avan�a para o pr�ximo quadro e zera a temporiza��o
        this.nextFrame();
        this.quadroatual++;
        tatual=0;
        
        //System.out.println("ANIMA ATOR - NumA: " + numAnim);
        //System.out.println("ANIMA ATOR - QuadroAtual: " + quadroatual);
        
        // Testa se o quadro atual est� dentro dos limites da anima��o. Se estiver, retorna.
        if(this.quadroatual < this.quadros[numAnim].length)
            return;
        // Se chegou ao final da seq��ncia, indica isso em estadoanim e zera a contagem
        this.quadroatual = 0;
        fila.envia(Evento.EVT_RECOMECOU_ANIMACAO, 0, 0, 0, 0);
    }
    
    /**     M�todo que desloca um ator no cen�rio. */
    protected void deslocaAtor()
    {
        // Atualiza a posi��o do elemento
            x+=dx;
            if(x>=734)
                x=734;
            if(x<0)
                x=0;
            
            y+=dy;
            if(y>=517)
                y=517;
            if(y<0)
                y=0;
        // Altera a posi��o do sprite
            this.setPosition(x,y);
          
        /*    
        // Verifica se colidiu com alguma armadilha, fim ou meio de fase
        if(mapa != null)
        {
            if(colidiuMarcaMapa(TileMap.BLOCO_MORTE, mapa))
                fila.envia(Evento.EVT_COLIDIU_ARMADILHA, 0, 0, 0, 0);
            if(colidiuMarcaMapa(TileMap.BLOCO_CHECKPOINT, mapa))
                fila.envia(Evento.EVT_COLIDIU_CHECKPOINT, 0, 0, 0, 0);
            if(colidiuMarcaMapa(TileMap.BLOCO_FIM, mapa))
                fila.envia(Evento.EVT_COLIDIU_FIMFASE, 0, 0, 0, 0);            
        }*/
    }
    
    /** M�todo que troca o estado de um ator. Recebe o valor novoEstado para mudar 
     o estado atual do ator. */
    public final void trocaEstado(int novoEstado)
    {
        // Se o novo estado é morto ou encerrado, coloca invisível
        if(novoEstado == Ator.ATOR_ENCERRADO || novoEstado == Ator.ATOR_MORTO)
            setVisible(false);
        // Vê se deve voltar a ser visível
        if(estado == ATOR_MORTO || estado == ATOR_ENCERRADO)
            if(novoEstado != ATOR_MORTO && novoEstado != ATOR_ENCERRADO)
                setVisible(true);
        // Coloca o novo estado
        this.estado = novoEstado;
        estadoInicio=true;    
        // Envia um evento nulo só para processar o início do evento
        fila.envia(Evento.EVT_NULO, 0, 0, 0, 0);
    }
    
    /** O método que troca para outra animação. */
    protected final void trocaAnimacao(int numAnim)
    {
        this.numAnim = numAnim;
        // Copia os quadros de anima��o do sprite
        this.setFrameSequence(quadros[numAnim]);
        // Zera a contagem de tempo e dos quadros
        this.tatual = 0;
        this.quadroatual = 0;
    }
    
    /**     M�todo que realiza a l�gica do Ator. A ser sobrescrito nas classes especializadas. 
     *      O m�todo recebe o estado das teclas do celular, a partir do GameCanvas. Isto � necess�rio
     * para jogadores, que dependem de eventos de teclado para reagirem. Nos demais casos, basta passar
     * um valor 0. */
    abstract protected void processaEvento(Evento ev);
    
    public boolean atualizaAtor(Sprite mapa, int largura, int altura)
    {
        // Se já está encerrado, corta
        if(estado == Ator.ATOR_ENCERRADO)
            return false;
        if(energia<=0){
            this.vidas--;
            this.energia = energiaInicial;
        }
        if(vidas==0){
            trocaEstado(ATOR_MORTO);
        }
        
         // Padr�o do m�todo: SEMPRE anima o ator antes de qualquer coisa
        this.animaAtor();
        // Padr�o do m�todo: SEMPRE movimenta o jogador no final
        this.deslocaAtor();
        
        // Calcula o temporizador
        if(temporizador>0)
            if(--temporizador==0)
                fila.envia(Evento.EVT_TEMPO, 0, 0, 0, 0);
        
        // Invoca o processamento de eventos se necessário
        Evento ev;
        while((ev=fila.proximo())!=null)
            processaEvento(ev);
        
        // Vê se encerrou agora
        if(estado == Ator.ATOR_ENCERRADO)
            return false;
        else
            return true;
    }
    
    public boolean colideAtor(Ator a)
    {
        boolean colide = false;
        // TEsta se os bounding boxes estão sobrepostos
        // Este está à esquerda de a
        if(x <= a.x && a.x - x < largura)
        {
            // Testa agora quando a está para cima
            if(y <= a.y && a.y-y < altura)
                colide=true;
            else if(y > a.y && y-a.y < a.altura)
                colide=true;
        }
        else if(x - a.x < a.largura)
        {
            // Testa agora quando a está para cima
            if(y <= a.y && a.y-y < altura)
                colide=true;
            else if(y > a.y && y-a.y < a.altura)
                colide=true;            
        }
        if(colide)
        {
            if(collidesWith(a, true))
            {
                fila.envia(Evento.EVT_BATEU_PERSONAGEM, 0, 0, 0, 0);
                a.fila.envia(Evento.EVT_BATEU_PERSONAGEM, 0, 0, 0, 0);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
}
