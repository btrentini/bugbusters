/*
 * JogoCanvas.java - Vers�o MapaBinario
 *
 * Created on 6 de Agosto de 2007
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import com.sun.cldc.i18n.uclc.DefaultCaseConverter;
import com.sun.midp.io.j2me.mms.DatagramImpl.SubclassedDatagramReader;
import java.io.*;
import java.util.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.*;
import javax.microedition.rms.*;



/**
 *
 * @author PV
 */
public class JogoCanvas extends CanvasBase{
    
    // O atributo para o layer manager
    LayerManager gerente;
    
    /* VARIAVEIS DO JOGADOR */
    // O personagem principal
    Jogador modeloJogador;
    // O jogador usado durante o jogo
    Jogador jogador;
    
    
    /* VARIAVEIS DAS ARMAS */
    // Objeto utilizado durante o jogo
    Arma peteleco;
    // Objeto utilizado durante o jogo
    Arma spray;
    // Objeto utilizado durante o jogo
    Arma mataMosca;
    // Objeto utilizado durante o jogo
    Arma metralhadora;
    // Objeto utilizado durante o jogo
    Arma bomba;
    
    // Vetor de armas padrao
    Vector modeloArmas;
    // Vetor de armas
    Vector armas;
    
    /* VARIAVEIS DO INIMIGO BARATA */
    // Modelo da barata
    Inimigo modeloBarata;
    // O inimigo usado durante o jogo
    Inimigo barata;
    //Vector de Barata
    Vector vetBaratas;
    // Variável que define quantas baratas devem ser criadas
    int nBaratas = 0;
    
    /* VARIAVEIS DO INIMIGO BESOURO */
    // Modelo do Besouro
    Inimigo modeloBesouro;
    // O inimigo usado durante o jogo
    Inimigo besouro;
    //Vector de Besouro
    Vector vetBesouros;
    // Variável que define quantos besouros devem ser criados
    int nBesouro = 0;  
    
    /* VARIAVEIS DO INIMIGO JOANINHA */ 
    // Modelo do Joaninha
    Inimigo modeloJoaninha;
    // O inimigo usado durante o jogo
    Inimigo joaninha;
    //Vector de Joaninhas
    Vector vetJoaninhas;
    // Variável que define quantas joaninhas devem ser criadas
    int nJoaninha = 0; 
    
    /* VARIAVEIS DO INIMIGO BORBOLETA */  
    // Modelo do Borboleta
    Inimigo modeloBorboleta;
    // O inimigo usado durante o jogo
    Inimigo borboleta;
    //Vector de Borboletas
    Vector vetBorboletas;
    // Variável que define quantas borboletas devem ser criadas
    int nBorboleta = 0;
    
    /* VARIAVEIS DO INIMIGO MOSCA */
    // Modelo do Mosca
    Inimigo modeloMosca;
    // O inimigo usado durante o jogo
    Inimigo mosca;
    //Vector de Moscas
    Vector vetMoscas;
    // Variável que define quantas moscas devem ser criadas
    int nMosca = 0;
    
    /* VARIAVEIS DO INIMIGO SUPERBOSS */
    // Modelo do SuperBoss
    Inimigo modeloSuperBoss;
    // O inimigo usado durante o jogo
    Inimigo superBoss;
    // Variável que define se existe um superboss
    int nSuperBoss = 0;
    
    /* VETOR DE DIREÇÕES */
    final int [] direcoes = {0,45,90,135,180,225,270,315};
    
    // Variável booleana que pausa o jogo
    boolean gamePaused = false;
    
    /** Para procurar personagens no mapa. */
    int posicao[] = new int[1];
    
    /** A tela de título do jogo. Usada como pano de fundo em todos menus.*/
    Image titulo=null;
    /** A tela de splash **/
    Image splash = null;
    /** A tela de Abertura do jogo **/
    Image abertura = null;
    /** A tela de gameover **/
    Image gameover = null;    
    /** O console do jogo **/
    Image console = null;
    /** O atributo do mapa **/
    Sprite mapa=null;
    /** O sprite da mira. */
    Sprite mira=null; 
    /** A abelha do menu **/
    Image bee = null;
        
    /** Vetor de strings com os textos do menu principal. */
    String menuPrincipal[] = { "JOGAR", "OPÇÕES", "CRÉDITO" , "RECORDES" , "AJUDA", "SAIR"};
    /** Variável que indica a seleção atual do menu principal. */
    int opcaoMenuPrincipal=0;
    
    /** Vetor de strings com os textos do menu pause. */
    String menuPause[] = { "VOLTAR AO JOGO", "OPÇÕES", "AJUDA", "ABANDONAR JOGO"};
    /** Variável que indica a seleção atual do menu pause. */
    int opcaoMenuPause=0;
    
    /** Vetor de strings com os textos do menu opções. */
    String menuOptions[] = { "SOM", "ZERAR RECORDS", "VOLTAR"};
    /** Variável que indica a seleção atual do menu opções. */
    int opcaoMenuOptions=0;
    
     /** Atributo que indica o deslocamento no eixo x do desenho. */
    int offx = 0;
    /** Atributo que indica o deslocamento no eixo y do desenho. */
    int offy = 0;
    /** temporizador para tela de splash e abertura */
    int temporizaSplash = 30;
    int temporizaAbertura = 30;
    int temporizaGameOver = 50;
    
    /** Variaveis da pontuação **/ 
    int pontosRecorde = 0;
    int pontos = 0;
    
    /** O gerador de números aleatórios. */
    Random aleatorio = null;
    
    // Os recursos de áudio da aplicação
    /** Variavel logica que define se o som está habilitado ou não**/
    boolean som = false;
    /** O vetor com o áudio da aplicação. */   
    Player vetorAudio[];
    /** A playlist de áudio da aplicação. */
    String playlist[] = {"/GhostBusters.mid","/baddog.wav"};
    /** Os tipos MIME da playlist de áudio da aplicação. */
    String playlistMime[] = {"audio/midi","audio/x-wav"};

    /** Variaveis utilizadas para controlar o deslocamento da tela **/
    int deslocX;
    int deslocY;
  
    int temp = 40;
    
    // constantes para os tipos de personagem
    public static final int JOGADOR     = 1;
    public static final int BARATA      = 2;
    public static final int BESOURO     = 3;
    public static final int JOANINHA    = 4;
    public static final int BORBOLETA   = 5;
    public static final int MOSCA       = 6;
    public static final int SUPERBOSS   = 7;
    
    /** Creates a new instance of HYPECanvas */
    public JogoCanvas(MIDlet pai) {
        super(pai);
        carregaConfiguracao();
    }
    
    protected void carregaDados()
    {
        desenhaCarregando(getGraphics());
        
        // Carrega a imagem do splah
        splash = carregaImg("/splash.png");
        // Carrega a imagem da abertura
        abertura = carregaImg("/abertura2.png");
        // Carrega a imagem de gameover
        gameover = carregaImg("/gameover.png");
        // Carrega a imagem do menu principal
        titulo = carregaImg("/menu.png");
        // Carrega a imagem da abelha menu principal
        bee = carregaImg("/bee_02.png");
        // Carrega a imagem do console
        console = carregaImg("/console.png");
        
        // Carrega o mapa
        Image temp = carregaImg("/cozinha.png");
        mapa = new Sprite(temp);
        
        // Cria o vetor de audio
        vetorAudio = new Player[playlist.length];
        for(int i=0;i<playlist.length;i++)
            vetorAudio[i] = carregaAudio(playlist[i], playlistMime[i]);
              
        modeloArmas = new Vector(5);
        String [] sons = new String[2];
        String [] sonsMime = new String[2];
                
         // Carrega o som da arma
        sons [0] = "/peteleco.wav";
        sonsMime [0] = "audio/x-wav";
        sons [1] = "/blank.wav";
        sonsMime [1] = "audio/x-wav";
        // Carrega a imagem
        temp = carregaImg("/peteleco.png");
        peteleco = new Arma("peteleco",temp, 66, 66, 0, 0, 1, -99, modeloJogador.VJOGADOR, true, sons, sonsMime);
        modeloArmas.addElement(peteleco);
        
         // Carrega o som da arma
        sons [0] = "/spray.wav";
        sonsMime [0] = "audio/x-wav";
        sons [1] = "/spray_reload.wav";
        sonsMime [1] = "audio/x-wav";
        // Carrega a imagem
        temp = carregaImg("/spray.png");
        spray = new Arma("spray",temp, 66, 66, 0, 0, 1, 1, modeloJogador.VJOGADOR, true, sons, sonsMime);
        modeloArmas.addElement(spray);
        
         // Carrega o som da arma
        sons [0] = "/mataMosca.wav";
        sonsMime [0] = "audio/x-wav";
        sons [1] = "/blank.wav";
        sonsMime [1] = "audio/x-wav";
        // Carrega a imagem
        temp = carregaImg("/mataMosca.png");
        mataMosca = new Arma("mataMosca",temp, 66, 66, 0, 0, 1, 1, modeloJogador.VJOGADOR, true, sons, sonsMime);
        modeloArmas.addElement(mataMosca);
        
         // Carrega o som da arma
        sons [0] = "/metralhadora.wav";
        sonsMime [0] = "audio/x-wav";
        sons [1] = "/gun_reload.wav";
        sonsMime [1] = "audio/x-wav";
        // Carrega a imagem
        temp = carregaImg("/metralhadora.png");
        metralhadora = new Arma("metralhadora",temp, 66, 66, 0, 0, 10, 1, modeloJogador.VJOGADOR, true, sons, sonsMime);
        modeloArmas.addElement(metralhadora);
        
         // Carrega o som da arma
        sons [0] = "/bomba.wav";
        sonsMime [0] = "audio/x-wav";
        sons [1] = "/blank.wav";
        sonsMime [1] = "audio/x-wav";
        // Carrega a imagem
        temp = carregaImg("/bomba.png");
        bomba = new Arma("bomba",temp, 66, 66, 0, 0, 1, 1, modeloJogador.VJOGADOR, true, sons, sonsMime);
        modeloArmas.addElement(bomba);
        
        // Carrega o modelo do personagem do jogador
        temp = carregaImg("/mira.png");
        modeloJogador = new Jogador(temp, 0, 0, 0, this);      
        
        // Carrega o modelo da barata
        temp = carregaImg("/mosquito_30x40.png");
        modeloBarata = new Inimigo(temp, 0, 0, 0, this, 30, 40);
        
        // Carrega o modelo do besouro
        temp = carregaImg("/besouro_01.png");
        modeloBesouro = new Inimigo(temp, 0, 0, 0, this, 20, 20);
        
        // Carrega o modelo da borboleta
        temp = carregaImg("/borboleta_01.png");
        modeloBorboleta = new Inimigo(temp, 0, 0, 0, this, 25, 23);
        
        // Carrega o modelo da joaninha
        temp = carregaImg("/quadroJoaninha.png");
        modeloJoaninha = new Inimigo(temp, 0, 0, 0, this, 35, 35);
        
        // Carrega o modelo da mosca
        temp = carregaImg("/mosca_30x40.png");
        modeloMosca = new Inimigo(temp, 0, 0, 0, this, 30, 40);
        
        // Carrega o modelo do SuperBoss
        temp = carregaImg("/superBoss_01.png");
        modeloSuperBoss = new Inimigo(temp, 0, 0, 0, this, 27, 28);
        
        /** Inicia o gerador de números aleatórios **/
        aleatorio= new Random();
        aleatorio.setSeed(System.currentTimeMillis());
        
        // Cria um LayerManager
        gerente = new LayerManager();
        carregado = true;
    }
    
    protected void desenhaTelaJogo(Graphics g)
    {
     if(gamePaused == false){
                
            int oldX = jogador.x;
            int oldY = jogador.y;
                    
            // Recupera o teclado
            Teclado t = Teclado.pegaInstancia();
            // Processa o controle
            processaTeclasJogador(jogador);
   
            
            // Atualiza o personagem
            jogador.atualizaAtor(mapa,240,250);        
            
            // som do cachorro
            if(som){
                if((jogador.x>520)&&(jogador.x<550))
                    if((jogador.y>285)&&(jogador.y<310))
                        reproduzAudio(vetorAudio[1], -1);
                    else
                        encerraAudio(vetorAudio[1]);
                else
                        encerraAudio(vetorAudio[1]);
            }
            
            // Desloca o mapa
            deslocaMapa(oldX, oldY);
            
            // Desloca a arma
            jogador.armaAtual.deslocArma(jogador.x, jogador.y, deslocX, deslocY);
            
            Evento ev;
            // Verifica os eventos enviados para o jogo
            while((ev=fila.proximo())!=null)
            {
                switch(ev.tipo)
                {
                        // jogador trocou de arma
                    case Evento.EVT_TROCA_ARMA:
                        gerente.remove(jogador.armaAtual);
                        gerente.insert(jogador.armaAtual,1);
                        break;   
                    
                        /** Caso o jogador tenha atirado, testa se acertou algum inimigo **/
                    case Evento.EVT_PERSONAGEM_ATIRANDO:
                        
                        if(jogador.armaAtual.atira()==false)
                            jogador.trocaArma(+1);
                        
                        // Se jogador atirou a bomba, mata todos inimigos da tela
                        if(jogador.armaAtual.nome.compareTo("bomba")==0){
                            System.out.println("bomba");
                            jogador.killAll(vetBaratas, deslocX, deslocY);
                            jogador.killAll(vetBesouros, deslocX, deslocY);
                            jogador.killAll(vetBorboletas, deslocX, deslocY);
                            jogador.killAll(vetJoaninhas, deslocX, deslocY);
                            jogador.killAll(vetMoscas, deslocX, deslocY);
                        }else{
                            for(int cont=0; cont<vetBaratas.size(); cont++){
                                Inimigo aux = (Inimigo) vetBaratas.elementAt(cont);
                                jogador.mataInimigo(aux);

                            } 

                            for(int cont=0; cont<vetBesouros.size(); cont++){
                                Inimigo aux = (Inimigo) vetBesouros.elementAt(cont);
                                jogador.mataInimigo(aux);
                            }

                            for(int cont=0; cont<vetBorboletas.size(); cont++){
                                Inimigo aux = (Inimigo) vetBorboletas.elementAt(cont);
                                jogador.mataInimigo(aux);
                            }

                            for(int cont=0; cont<vetJoaninhas.size(); cont++){
                                Inimigo aux = (Inimigo) vetJoaninhas.elementAt(cont);
                                jogador.mataInimigo(aux);
                            }

                            for(int cont=0; cont<vetMoscas.size(); cont++){
                                Inimigo aux = (Inimigo) vetMoscas.elementAt(cont);
                                jogador.mataInimigo(aux);
                            }

                            if(nSuperBoss==-1)
                                jogador.mataInimigo(superBoss);
                        }
                        break; 
                        
                        /** Caso o jogador tenha pausado o jogo - "BOTÃO 7" **/
                    case Evento.EVT_PRESSIONOU_BOTAO3:
                        if(vetorAudio[0].getState()==Player.STARTED)
                            encerraAudio(vetorAudio[0]);
                        gamePaused = true; 
                        break;
                        
                        // game over
                    case Evento.EVT_FIM_FASE_DERROTA:
                        gravaConfiguracao();
                        
                        jogador.kickAll(vetBaratas);
                        vetBaratas.removeAllElements();
                        jogador.kickAll(vetBesouros);
                        vetBesouros.removeAllElements();
                        jogador.kickAll(vetBorboletas);
                        vetBorboletas.removeAllElements();
                        jogador.kickAll(vetJoaninhas);
                        vetJoaninhas.removeAllElements();
                        jogador.kickAll(vetMoscas);
                        vetMoscas.removeAllElements();
                        
                        gerente.remove(jogador.armaAtual);
                        jogador.setVisible(false);
                        
                        gamePaused=false;
                        estado=8;
                        break;
                        
                }
            }
            
            if(estado!=8){
                processaEventosPontuação(jogador);

                // teste para verificar se todas baratas estão criadas
                if(vetBaratas.size() < nBaratas){
                    fila.envia(Evento.EVT_CRIA_BARATA , 1, 1, 1, 1);
                }
                // teste para verificar se todos besouros estão criados
                if(vetBesouros.size() < nBesouro){
                    fila.envia(Evento.EVT_CRIA_BESOURO , 1, 1, 1, 1);            
                }
                // teste para verificar se todas joaninhas estão criadas
                if(vetJoaninhas.size() < nJoaninha){
                    fila.envia(Evento.EVT_CRIA_JOANINHA , 1, 1, 1, 1);
                }
                // teste para verificar se todas borboletas estão criadas
                if(vetBorboletas.size() < nBorboleta){
                    fila.envia(Evento.EVT_CRIA_BORBOLETA , 1, 1, 1, 1);
                }
                // teste para verificar se todas moscas estão criadas
                if(vetMoscas.size() < nMosca){
                    fila.envia(Evento.EVT_CRIA_MOSCA , 1, 1, 1, 1);
                }
                // cria SuperBoss
                if(nSuperBoss==1){
                    fila.envia(Evento.EVT_CRIA_SUPERBOSS , 1, 1, 1, 1);
                    nSuperBoss = -1;
                }

                while((ev=fila.proximo())!=null)
                {
                    int xRand;
                    int yRand;
                    int ind;
                    int temporizacao;
                    switch(ev.tipo)
                    {
                        /** Caso seja para cria as baratas **/
                        case Evento.EVT_CRIA_BARATA:
                            System.out.println("barata");
                            xRand = 50+aleatorio.nextInt(700);
                            yRand = 50+aleatorio.nextInt(483);
                            ind = aleatorio.nextInt(8);
                            temporizacao = aleatorio.nextInt(50);
                            barata = new Inimigo(modeloBarata, xRand, yRand, direcoes[ind], 3, 5, temporizacao);
                            vetBaratas.addElement(barata);
                            gerente.insert(barata,1);
                            break;

                        /** Caso seja para cria os besouros **/    
                        case Evento.EVT_CRIA_BESOURO:
                            System.out.println("besouro");
                            xRand = 50+aleatorio.nextInt(700);
                            yRand = 50+aleatorio.nextInt(483);
                            ind = aleatorio.nextInt(8);
                            temporizacao = aleatorio.nextInt(50);
                            besouro = new Inimigo(modeloBesouro, xRand, yRand, direcoes[ind], 3, 5, temporizacao);
                            vetBesouros.addElement(besouro);
                            gerente.insert(besouro,1);
                            break;

                        /** Caso seja para cria as joaninhas **/
                        case Evento.EVT_CRIA_JOANINHA:
                            System.out.println("joaninha");
                            xRand = 50+aleatorio.nextInt(700);
                            yRand = 50+aleatorio.nextInt(483);
                            ind = aleatorio.nextInt(8);
                            temporizacao = aleatorio.nextInt(50);
                            joaninha = new Inimigo(modeloJoaninha, xRand, yRand, direcoes[ind], 3, 5, temporizacao);
                            vetJoaninhas.addElement(joaninha);
                            gerente.insert(joaninha,1);
                            break;    

                        /** Caso seja para cria as borboletas **/
                        case Evento.EVT_CRIA_BORBOLETA:
                            System.out.println("borboleta");
                            xRand = 50+aleatorio.nextInt(700);
                            yRand = 50+aleatorio.nextInt(483);
                            ind = aleatorio.nextInt(8);
                            temporizacao = aleatorio.nextInt(50);
                            borboleta = new Inimigo(modeloBorboleta, xRand, yRand, direcoes[ind], 3, 5, temporizacao);
                            vetBorboletas.addElement(borboleta);
                            gerente.insert(borboleta,1);
                            break; 

                        /** Caso seja para cria as moscas **/
                        case Evento.EVT_CRIA_MOSCA:
                            System.out.println("mosca");
                            xRand = 50+aleatorio.nextInt(700);
                            yRand = 50+aleatorio.nextInt(483);
                            ind = aleatorio.nextInt(8);
                            temporizacao = aleatorio.nextInt(50);
                            mosca = new Inimigo(modeloMosca, xRand, yRand, direcoes[ind], 3, 5, temporizacao);
                            vetMoscas.addElement(mosca);
                            gerente.insert(mosca,1);
                            break;

                            /** Caso seja para cria o Super Boss **/
                        case Evento.EVT_CRIA_SUPERBOSS:
                            System.out.println("SuperBoss");
                            xRand = 50+aleatorio.nextInt(700);
                            yRand = 50+aleatorio.nextInt(483);
                            ind = aleatorio.nextInt(8);
                            temporizacao = aleatorio.nextInt(50);
                            superBoss = new Inimigo(modeloSuperBoss, xRand, yRand, direcoes[ind], 10, 5, temporizacao);
                            gerente.insert(superBoss,1);
                            break; 

                        default: 
                            System.out.println("default");
                            break;
                    }
                }

                // Atualiza os inimigos
                atualizaInimigos(g);
            }
            
            if(pontos>pontosRecorde)
                pontosRecorde = pontos;
            
            /** Preenche o resto da tela de preto **/
            g.drawImage(console, 0, 0, Graphics.LEFT | Graphics.TOP);

            g.setColor(0,0,0);
            /** Desenha o placar **/
            g.drawString("Pontos: "+ pontos, 20, 37, Graphics.LEFT | Graphics.TOP);
            /** Desenha as vidas **/
            g.drawString(""+jogador.vidas, 221, 4, Graphics.RIGHT | Graphics.TOP);
            /** Desenha a munição **/
            int municaoAtual = jogador.armaAtual.getMunicao();
            if(municaoAtual==-99)
                g.drawString("-", 221, 31, Graphics.RIGHT | Graphics.TOP);
            else
                g.drawString(""+municaoAtual, 221, 31, Graphics.RIGHT | Graphics.TOP);
            /** Desenha a mensagem de saída **/
            //g.drawString("Pausa", 215, 300, Graphics.RIGHT | Graphics.BASELINE);

            g.setColor(255,255,255);
            g.drawString("E:", 13, 4, Graphics.LEFT | Graphics.TOP);
            g.fillRect(29, 6,jogador.energiaInicial+2, 12);
            g.setColor(24,210,4);
            g.fillRect(30, 7,(int)((100/jogador.energiaInicial)*jogador.energia), 10);
            /** Desenha o layer manager **/
            gerente.paint(g, 0, 60);
            g.setColor(255, 0, 0);
        }
     else{         
         desenhaTelaPause(g);
     }
    }
    
    protected void processaEventosPontuação(Jogador j){
        
        if(pontos<30000){
            nBaratas = 0;
            nBesouro = 0;
            nMosca = 0;
            nBorboleta = 0;
            nJoaninha = 10;
        }else if(pontos < 30000){
            nBaratas = 10;
            nBesouro = 10;
            nMosca = 5;
            nBorboleta = 2;
            nJoaninha = 1;
        }else if(pontos < 1000){
            nBaratas = 10;
            nBesouro = 10;
            nMosca = 5;
            nBorboleta = 5;
            nJoaninha = 5;
        
        }
        
        /*
        // item 1: a cada 250 pontos - um spray (400ml)
        if(pontos%250==0)
            fila.envia(Evento.EVT_CRIA_SPRAY, 1, 1, 1, 1);
        
        // item 2: possuir item 1 + 500 pontos - um mata-mosca (ilimitado)
        if(pontos/500==0)
            fila.envia(Evento.EVT_CRIA_MATAMOSCA, 1, 1, 1, 1);
        
        // item 3: possuir item 2 + 1000 pontos - metralhadora (150 tiros)
        // item 4: possuir item 2 + 1000 pontos - 150 tiros para metralhadora
        if(pontos/1000==0)
            fila.envia(Evento.EVT_CRIA_METRALHADORA, 1, 1, 1, 1);
       
        // item 5: possuir item 3 + 2000 pontos - super bomba
        if(pontos/2000==0)
            fila.envia(Evento.EVT_CRIA_SUPERBOMBA, 1, 1, 1, 1);
        
        // item 6: a cada 10000 pontos - recarrega vida (100%)
        if(pontos/10000==0)
            j.energia = 100;
        
        
        // item 7: a cada 15000 pontos - super bug 
        if((pontos%1000==0)&&(pontos>0)){    
            fila.envia(Evento.EVT_CRIA_SUPERBOSS, 1, 1, 1, 1);
            System.out.println("EV SuperBoss");
            if(nSuperBoss != -1)
                nSuperBoss = 1;
        }
        */        
    }
    
    protected void atualizaInimigos(Graphics g){
        // Altera a posição das baratas            
        for(int cont=0; cont<vetBaratas.size(); cont++){
            Inimigo aux = (Inimigo) vetBaratas.elementAt(cont);
            
            // logica para que o inimigo mude de direção.
            aux.tempDirecao--;
            if(aux.tempDirecao <= 0){
                int ind = aleatorio.nextInt(8);
                aux.direcao = direcoes[ind];
                aux.tempDirecao = aux.tempoPadrao;
                //System.out.println("nova direcao: " + aux.direcao);
            }
           
            // testa se inimigo está morto e acrescenta os pontos
            if(aux.atualizaInimigo(aux)==false){
                vetBaratas.removeElementAt(cont);
                pontos += 30;
            }
           
            if(aux.estado==aux.INIMIGO_ATACANDO){
                jogador.tiraEnergia(1);
            }
        }
        
        // Altera a posição das besouros            
        for(int cont=0; cont<vetBesouros.size(); cont++){
            Inimigo aux = (Inimigo) vetBesouros.elementAt(cont);
            
            // logica para que o inimigo mude de direção.
            aux.tempDirecao--;
            if(aux.tempDirecao <= 0){
                int ind = aleatorio.nextInt(8);
                aux.direcao = direcoes[ind];
                aux.tempDirecao = aux.tempoPadrao;
                //System.out.println("nova direcao: " + aux.direcao);
            }
            
            // testa se inimigo está morto e acrescenta os pontos
            if(aux.atualizaInimigo(aux)==false){
                vetBesouros.removeElementAt(cont);
                pontos += 100;
            }
            
            if(aux.estado==aux.INIMIGO_ATACANDO){
                jogador.tiraEnergia(1);
            }
        }
        
        // Altera a posição das borboletas            
        for(int cont=0; cont<vetBorboletas.size(); cont++){
            Inimigo aux = (Inimigo) vetBorboletas.elementAt(cont);
            
                    
            // logica para que o inimigo mude de direção.
            aux.tempDirecao--;
            if(aux.tempDirecao <= 0){
                int ind = aleatorio.nextInt(8);
                aux.direcao = direcoes[ind];
                aux.tempDirecao = aux.tempoPadrao;
                //System.out.println("nova direcao: " + aux.direcao);
            }
            
            // testa se inimigo está morto e acrescenta os pontos
            if(aux.atualizaInimigo(aux)==false){
                vetBorboletas.removeElementAt(cont);
                pontos += 80;
            }
            
            
        }
        
        // Altera a posição das joaninha            
        for(int cont=0; cont<vetJoaninhas.size(); cont++){
            Inimigo aux = (Inimigo) vetJoaninhas.elementAt(cont);
            Evento ev;
            
           // logica para que o inimigo mude de direção.
            aux.tempDirecao--;
            if(aux.tempDirecao <= 0){
                int ind = aleatorio.nextInt(8);
                aux.direcao = direcoes[ind];
                aux.tempDirecao = aux.tempoPadrao;
                //System.out.println("nova direcao: " + aux.direcao);
            }
            
            // testa se inimigo está morto e acrescenta os pontos
            if(aux.atualizaInimigo(aux)==false){
                vetJoaninhas.removeElementAt(cont);
                pontos += 50;
            }
            
            while((ev=fila.proximo())!=null)
            {
                switch(ev.tipo)
                {
                    case Evento.EVT_INIMIGO_ATACANDO:
                        //System.out.println("EV. X: " + ev.x);
                        //System.out.println("EV. Y: " + ev.y);
                        //System.out.println("EV. POT: " + ev.valor);
                        if((ev.x>=deslocX)&&(ev.x < (deslocX + 250)-aux.largura))
                            if((ev.y>=deslocY)&&(ev.y < (deslocY + 240)-aux.altura))
                                jogador.energia -= ev.valor;
                        break;
                        
                   default:
                       System.out.println("MET. ATUALIZA I. - EV: " + ev.tipo);
                       break;
                }
            }
        }
        
        // Altera a posição das mosca            
        for(int cont=0; cont<vetMoscas.size(); cont++){
            Inimigo aux = (Inimigo) vetMoscas.elementAt(cont);
            
            // logica para que o inimigo mude de direção.
            aux.tempDirecao--;
            if(aux.tempDirecao <= 0){
                int ind = aleatorio.nextInt(8);
                aux.direcao = direcoes[ind];
                aux.tempDirecao = aux.tempoPadrao;
                //System.out.println("nova direcao: " + aux.direcao);
            }
            
            // testa se inimigo está morto e acrescenta os pontos
            if(aux.atualizaInimigo(aux)==false){
                vetMoscas.removeElementAt(cont);
                pontos += 90;
            }
            
            if(aux.estado==aux.INIMIGO_ATACANDO){
                jogador.tiraEnergia(1);
            }
        }
        
        // Altera a posição do SuperBoss
        if(nSuperBoss==-1){
            superBoss.setPosition(superBoss.x,superBoss.y);
            
            // testa se inimigo está morto e acrescenta os pontos
            if((superBoss.x==-50)&&(superBoss.y==-50)){
                pontos += 2500;
                nSuperBoss = 0;
            }       
        }
    }
    
    protected void deslocaMapa(int oldX, int oldY){
        // define a posição de visualização do layer manager
            
                if(jogador.x > oldX)
                    if(jogador.x+66 >=deslocX+200)
                        deslocX+=jogador.VJOGADOR;
                        if(deslocX+240>=800)
                            deslocX=560;
                
                if(jogador.x < oldX)
                    if(jogador.x-deslocX <=50)
                        deslocX-=jogador.VJOGADOR;
                        if(deslocX<=0)
                            deslocX=0;
                
                if(jogador.y > oldY)
                    if(jogador.y+66 >= deslocY+170)
                        deslocY+=jogador.VJOGADOR;
                        if(deslocY+250>=583)
                            deslocY = 333;
                
                if(jogador.y < oldY)
                    if(jogador.y-deslocY <=50)
                        deslocY-=jogador.VJOGADOR;
                        if(deslocY<=0)
                            deslocY = 0;
            
            
            gerente.setViewWindow(deslocX, deslocY, 240, 250);
    }
    
    protected void processaTeclasJogador(Jogador j)
    {
        Teclado t = Teclado.pegaInstancia();
        if(t.cima.pressionado)
            j.fila.envia(Evento.EVT_PRESSIONOU_CIMA, 0, 0, 0, 0);
        if(t.baixo.pressionado)
            j.fila.envia(Evento.EVT_PRESSIONOU_BAIXO, 0, 0, 0, 0);
        if(t.esquerda.pressionado)
            j.fila.envia(Evento.EVT_PRESSIONOU_ESQ, 0, 0, 0, 0);
        if(t.direita.pressionado)
            j.fila.envia(Evento.EVT_PRESSIONOU_DIR, 0, 0, 0, 0);
        if(t.cima.liberou)
            j.fila.envia(Evento.EVT_LIBEROU_CIMA, 0, 0, 0, 0);
        if(t.baixo.liberou)
            j.fila.envia(Evento.EVT_LIBEROU_BAIXO, 0, 0, 0, 0);
        if(t.esquerda.liberou)
            j.fila.envia(Evento.EVT_LIBEROU_ESQ, 0, 0, 0, 0);
        if(t.direita.liberou)
            j.fila.envia(Evento.EVT_LIBEROU_DIR, 0, 0, 0, 0);
        if(t.tiro.pressionou)
            j.fila.envia(Evento.EVT_PRESSIONOU_BOTAO1, 0, 0, 0, 0);
        if(t.gameB.pressionou)
            j.fila.envia(Evento.EVT_PRESSIONOU_BOTAO2, 0, 0, 0, 0);
        if(t.gameC.pressionou)
            j.fila.envia(Evento.EVT_PRESSIONOU_BOTAO3, 0, 0, 0, 0);
        if(t.gameD.pressionou)
            j.fila.envia(Evento.EVT_PRESSIONOU_BOTAO4, 0, 0, 0, 0);
    }
    
    // tela de splash LAB Games - temporizada
    protected void desenhaTelaSplash(Graphics g)
    {
        // Recupera o teclado
        Teclado t = Teclado.pegaInstancia();
        // Realiza a lógica da tela
        
        if(temporizaSplash>0){
            g.drawImage(splash, 0, 0, Graphics.LEFT | Graphics.TOP);
            temporizaSplash--;
        }       
        if(temporizaSplash == 0){     
            estado = 7;
        }
          
        g.setColor(255,255,255);
        g.drawString("APRESENTA...", 130, 300, Graphics.HCENTER | Graphics.BASELINE);
    }

    // tela de abertura do jogo - temporizada
    protected void desenhaTelaAbertura(Graphics g)
    {
        // Recupera o teclado
        Teclado t = Teclado.pegaInstancia();
        // Realiza a lógica da tela
        
        if(temporizaAbertura > 0){
            g.drawImage(abertura, 0, 0, Graphics.LEFT | Graphics.TOP);
            temporizaAbertura--;
        } 
        if(temporizaAbertura == 0)
            estado = 1;
    }
    
    // tela de GameOver do jogo - temporizada
    protected void desenhaTelaGameOver(Graphics g)
    {
        // Recupera o teclado
        Teclado t = Teclado.pegaInstancia();
        // Realiza a lógica da tela
        
        if(temporizaGameOver > 0){
            g.drawImage(gameover, 0, 0, Graphics.LEFT | Graphics.TOP);
            temporizaGameOver--;
        } 
        if(temporizaGameOver == 0){
            temporizaGameOver = 60;
            estado = 1;
        }
    }
    
    
    // tela do menu principal
    protected void desenhaTelaMenu(Graphics g)
    {
        Teclado t = Teclado.pegaInstancia();
        // Testa se o som está habilitado ou não
        if(som)
            reproduzAudio(vetorAudio[0], -1);
        else
            encerraAudio(vetorAudio[0]);
        
        // Lógica do menu. De acordo com a tecla pressionada, muda a opção selecionada
        if(t.cima.pressionou)
        {
            // Testa se não chegou no limite superior. Se chegou, passa para a última opção
            if(--opcaoMenuPrincipal<0)
                opcaoMenuPrincipal=menuPrincipal.length-1;
        }else if(t.baixo.pressionou)
        {
            // Testa se não chegou no limite inferior. Se chegou, passa para a última opção
            if(++opcaoMenuPrincipal>=menuPrincipal.length)
                opcaoMenuPrincipal=0;
        }
        // caso tenha pressionado um botão, troca de estado de acordo com a opção selecionada
        if(t.tiro.pressionou)
        {
            switch(opcaoMenuPrincipal)
            {
                case 0:
                    // Caso seja a iniciar jogo 
                    // Adiciona itens no LayerManager
                    encerraAudio(vetorAudio[0]);
                    armas = modeloArmas;
                    jogador = new Jogador(modeloJogador,0,0,0, armas);
                    //jogador.setVisible(true);
                    
                    gerente.append(jogador);
                    gerente.append(mapa);
                    gerente.insert(jogador.armaAtual, 1);
                    
                    pontos = 0;
                    deslocX = 0;
                    deslocY = 0;
                    
                    //inicializa os Vectors
                    vetBaratas = new Vector(5);
                    vetBesouros = new Vector(5);
                    vetBorboletas = new Vector(1,5);
                    vetJoaninhas = new Vector(1,5);
                    vetMoscas = new Vector(1,5);
                    
                    gamePaused = false;
                    estado=2;
                    break;
                case 1:
                    // Caso seja a tela de opções
                    estado=6;
                    break;
                case 2:
                    // caso seja a tela de créditos
                    estado=4;
                    break;
                case 3:
                    // caso seja a tela de Recordes
                    estado=3;
                    break;
                case 4: 
                    // caso seja a tela seja ajuda
                    estado=5;
                    break;
                case 5:
                    // Caso seja para encerrar
                    encerraAudio(vetorAudio[0]);
                    gravaConfiguracao();
                    estado = -1;
                    break;
            }
        }
        
        // A partir daqui, desenha a tela
        // DEsenha a tela de fundo
       g.drawImage(titulo, 0, 0, Graphics.LEFT | Graphics.TOP);

       // Desenha as opções do menu
       for(int i=0;i<menuPrincipal.length;i++)
       {
           if(i==opcaoMenuPrincipal){
               g.setColor(255,255,255);
               g.drawImage(bee, 75, 160+i*16, Graphics.HCENTER | Graphics.TOP);
           }
           else
               g.setColor(100,100,100);
           g.drawString(menuPrincipal[i], 120, 180+i*16, Graphics.HCENTER | Graphics.BASELINE);
       }
       // Desenha os créditos básicos do jogo
       g.setColor(128,128,255);
       g.drawString("@ By PV´s Students", 120, 300, Graphics.HCENTER | Graphics.BASELINE);
    }
    
    protected void desenhaTelaPause(Graphics g)
    {
        Teclado t = Teclado.pegaInstancia();
        
        // Testa se o som está habilitado ou não
        if(som)   
            reproduzAudio(vetorAudio[0], -1);
        else
            encerraAudio(vetorAudio[0]);
        
        // Lógica do menu. De acordo com a tecla pressionada, muda a opção selecionada
        if(t.cima.pressionou)
        {
            // Testa se não chegou no limite superior. Se chegou, passa para a última opção
            if(--opcaoMenuPause<0)
                opcaoMenuPause=menuPause.length-1;
        }else if(t.baixo.pressionou)
        {
            // Testa se não chegou no limite inferior. Se chegou, passa para a última opção
            if(++opcaoMenuPause>=menuPause.length)
                opcaoMenuPause=0;
        }
        
        // caso tenha pressionado um botão, troca de estado de acordo com a opção selecionada
        if(t.tiro.pressionou)
        {
            switch(opcaoMenuPause)
            {
                case 0:
                    // Caso seja voltar jogo 
                    encerraAudio(vetorAudio[0]);
                    gamePaused = false;
                    break;
                case 1:
                    // Caso seja a tela de opções
                    estado = 6;
                    break;
                case 2:
                    // caso seja a tela de ajuda
                    estado = 5;
                    break;
                case 3:
                    // Caso seja para abandonar a partida e voltar para o menu principal
                    encerraAudio(vetorAudio[0]);
                    gravaConfiguracao();
                    gamePaused=false;
                    jogador.estado = Ator.ATOR_MORTO;
                    jogador.setVisible(false);
                    estado = 1;
                    break;
            }
        }
        
        // A partir daqui, desenha a tela
        // DEsenha a tela de fundo
       g.drawImage(titulo, 0, 0, Graphics.LEFT | Graphics.TOP);
       g.setColor(17,159,41);
       g.drawString("** Jogo pausado **", 120, 175, Graphics.HCENTER | Graphics.BASELINE);
        
       // Desenha as opções do menu
       for(int i=0;i<menuPause.length;i++)
       {
           if(i==opcaoMenuPause)
               g.setColor(255,255,255);
           else
               g.setColor(100,100,100);
           g.drawString(menuPause[i], 120, 196+i*16, Graphics.HCENTER | Graphics.BASELINE);
       }
       // Desenha os créditos básicos do jogo
       g.setColor(128,128,255);
       g.drawString("@ By PV´s Students", 120, 300, Graphics.HCENTER | Graphics.BASELINE);
    }
    
    
    // tela de recordes
    protected void desenhaTelaRecordes(Graphics g)
    {
        // Recupera o teclado
        Teclado t = Teclado.pegaInstancia();
        // Realiza a lógica da tela
        if(t.tiro.pressionou)
            estado=1;
        // desenha a tela
        g.setColor(0,0,0);
        g.drawImage(titulo, offx, offy, Graphics.LEFT | Graphics.TOP);
        g.drawString("Melhor pontuação: " + pontosRecorde, 100, 200, Graphics.HCENTER | Graphics.BASELINE);
        g.drawString("5 para sair", 120, 300, Graphics.HCENTER | Graphics.BASELINE);
    }

    // tela de ajuda
    protected void desenhaTelaAjuda(Graphics g)
    {
         // Recupera o teclado
        Teclado t = Teclado.pegaInstancia();
        
        // Realiza a lógica da tela
        if(t.tiro.pressionou){
            if(gamePaused==true)
                estado=2;
            else    
                estado=1;
        }
        // desenha a tela
        g.setColor(0,0,0);
        g.drawImage(titulo, offx, offy, Graphics.LEFT | Graphics.TOP);
        g.drawString("Ajuda!", 120, 200, Graphics.HCENTER | Graphics.BASELINE);
        g.drawString("5 para sair", 120, 300, Graphics.HCENTER | Graphics.BASELINE);
    }

    // tela de creditos
    protected void desenhaTelaCreditos(Graphics g)
    {
        Teclado t = Teclado.pegaInstancia();
        // Realiza a lógica da tela
        if(t.tiro.pressionou)
            estado=1;
        // desenha a tela
        g.setColor(0,0,0);
        g.drawImage(titulo, offx, offy, Graphics.LEFT | Graphics.TOP);
        g.drawString("Desenvolvedores:", 80, 185, Graphics.LEFT | Graphics.BASELINE);
        g.drawString("André N. S.", 90, 210, Graphics.LEFT | Graphics.BASELINE);
        g.drawString("Bruno J. S.", 90, 225, Graphics.LEFT | Graphics.BASELINE);
        g.drawString("Luiz Felipe V. L.", 90, 240, Graphics.LEFT | Graphics.BASELINE);
        g.drawString("5 para sair", 120, 300, Graphics.HCENTER | Graphics.BASELINE);
    }
    
    // tela de opções
    protected void desenhaTelaOpcoes(Graphics g)
    {
         // Recupera o teclado
        Teclado t = Teclado.pegaInstancia();

        // Testa se o som está habilitado ou não
        if(som)
                reproduzAudio(vetorAudio[0], -1);
        else
            encerraAudio(vetorAudio[0]);
        
        // Lógica do menu. De acordo com a tecla pressionada, muda a opção selecionada
        if(t.cima.pressionou)
        {
            // Testa se não chegou no limite superior. Se chegou, passa para a última opção
            if(--opcaoMenuOptions<0)
                opcaoMenuOptions=menuOptions.length-1;
        }else if(t.baixo.pressionou)
        {
            // Testa se não chegou no limite inferior. Se chegou, passa para a última opção
            if(++opcaoMenuOptions>=menuOptions.length)
                opcaoMenuOptions=0;
        }

        // caso tenha pressionado um botão, troca de estado de acordo com a opção selecionada
        if(t.tiro.pressionou)
        {
            switch(opcaoMenuOptions)
            {
                case 0:
                    // Caso seja LIGAR/DESLIGAR o som
                    if(som)
                        som=false;
                    else
                        som=true;
                    break;
                case 1:
                    // Caso seja zerar os records
                    pontosRecorde = 0;
                    gravaConfiguracao();
                    break;
                case 2:
                    // caso seja voltar
                    if(gamePaused==true)
                        estado=2;
                    else    
                        estado=1;
                    break;
            }
        }

        // A partir daqui, desenha a tela
        // DEsenha a tela de fundo
        g.drawImage(titulo, 0, 0, Graphics.LEFT | Graphics.TOP);
         g.setColor(17,159,41);
        g.drawString("** Aperte 5 (tiro) para alterar/selecionar **", 18, 180, Graphics.LEFT | Graphics.BASELINE);
        
        // Desenha as opções do menu
        for(int i=0;i<menuOptions.length;i++)
        {
            if(i==opcaoMenuOptions)
                g.setColor(255,255,255);
            else
                g.setColor(100,100,100);
            g.drawString(menuOptions[i], 110, 196+i*16, Graphics.HCENTER | Graphics.BASELINE);
        }
        if(som){
            g.setColor(100,100,100);
            g.drawString(" -> on", 150, 196, Graphics.HCENTER | Graphics.BASELINE);
        }else{
            g.setColor(100,100,100);
            g.drawString(" -> off", 150, 196, Graphics.HCENTER | Graphics.BASELINE);
        }
        // Desenha os créditos básicos do jogo
        g.setColor(128,128,255);
        g.drawString("@ By PV´s Students", 120, 300, Graphics.HCENTER | Graphics.BASELINE);
    }
    
    /** M�todo para carregar dados de configura��o. */
    protected void carregaConfiguracao()
    {
        String temp="0";
        try
        {
            // Abre o RecordStore
           RecordStore rs = RecordStore.openRecordStore("placar", false);
           // Cria a enumeração
           RecordEnumeration re = rs.enumerateRecords(null, null, true);
           if(re.numRecords()!=0)
           {
                int identificador = re.nextRecordId();
                temp = new String(rs.getRecord(identificador));
            }
            else
                System.out.println("Nao existe o recorde ainda. Mantendo o anterior.");
            rs.closeRecordStore();
            // Ajusta o recorde
            pontosRecorde=Integer.parseInt(temp);               
        }
        catch(Exception e)
        {
            System.out.println("Nao existe o recorde ainda. Mantendo o anterior.");
        }
    }

    /** M�todo para salvar dados de configura��o. */
    protected void gravaConfiguracao()
    {
        try
        {
            String dado=""+pontosRecorde;
            RecordStore rs = RecordStore.openRecordStore("placar", true);
            RecordEnumeration re = rs.enumerateRecords(null, null, true);
            if(re.numRecords()!=0)
            {
                // Pega o primeiro registro
                int id = re.nextRecordId();
                rs.setRecord(id, dado.getBytes(), 0, dado.getBytes().length);
            }
            else
                rs.addRecord(dado.getBytes(),0, dado.getBytes().length);
            rs.closeRecordStore();
        }
        catch(Exception e){}
    }
    
    // METODOS DE AUDIO 
    /** Método para carregar o audio **/ 
    protected Player carregaAudio(String arquivo, String tipoMime)
    {
        InputStream is=getClass().getResourceAsStream(arquivo);
        Player p=null;
        try
        {
            if(is != null)
            {
                p = Manager.createPlayer(is, tipoMime);
                // Precarerga tudo
                p.start();
                p.stop();
                p.setMediaTime(0);
            }
        }      
        catch(Exception e)
        {
	// Aqui, deveria tratar o erro, como não tem console, deixa pra lá
        }
        return p;
    }
    
    /** Método para reproduzir o audio **/ 
    protected void reproduzAudio(Player audio, int repete)
    {
        // Toca a música
        try
        {
            audio.setLoopCount(repete);
            audio.start();
        }
        catch(Exception e)
        {
            // Aqui, deveria tratar o erro, como não tem console, deixa pra lá
        }        
    }
    
    /** Método para encerrar o audio **/ 
    protected void encerraAudio(Player audio)
    {
        // Pára a música
        try
        {
            audio.stop();
            audio.setMediaTime(0);
        }
        catch(MediaException e)
        {
            // Aqui, deveria tratar o erro, como não tem console, deixa pra lá
        }
    }
    
    /** Método para pausar o audio **/ 
    protected void pausaAudio(Player audio)
    {
        // Pára a música
        try
        {
            audio.stop();
        }
        catch(MediaException e)
        {
            // Aqui, deveria tratar o erro, como não tem console, deixa pra lá
        }
    }
}
