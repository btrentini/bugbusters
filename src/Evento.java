/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PV
 */
public class Evento {
    public static final int EVT_NULO               = -1;
    public static final int EVT_PRESSIONOU_CIMA    = 0;
    public static final int EVT_PRESSIONOU_BAIXO   = 1;
    public static final int EVT_PRESSIONOU_ESQ     = 2;
    public static final int EVT_PRESSIONOU_DIR     = 3;
    public static final int EVT_PRESSIONOU_BOTAO1  = 4;
    public static final int EVT_PRESSIONOU_BOTAO2  = 5;
    public static final int EVT_PRESSIONOU_BOTAO3  = 6;
    public static final int EVT_PRESSIONOU_BOTAO4  = 7;
    public static final int EVT_LIBEROU_CIMA       = 8;
    public static final int EVT_LIBEROU_BAIXO      = 9;
    public static final int EVT_LIBEROU_ESQ        = 10;
    public static final int EVT_LIBEROU_DIR        = 11;
    public static final int EVT_LIBEROU_BOTAO1     = 12;
    public static final int EVT_LIBEROU_BOTAO2     = 13;
    public static final int EVT_LIBEROU_BOTAO3     = 14;
    public static final int EVT_LIBEROU_BOTAO4     = 15;
    public static final int EVT_ENTROU_TELA        = 16;
    public static final int EVT_SAIU_TELA          = 17;
    public static final int EVT_ENTROU_MAPA        = 18;
    public static final int EVT_SAIU_MAPA_CIMA     = 19;
    public static final int EVT_SAIU_MAPA_BAIXO    = 20;
    public static final int EVT_SAIU_MAPA_ESQ      = 21;
    public static final int EVT_SAIU_MAPA_DIR      = 22;
    public static final int EVT_BATEU_PAREDE_CIMA  = 23;
    public static final int EVT_BATEU_PAREDE_BAIXO = 24;
    public static final int EVT_BATEU_PAREDE_ESQ   = 25;
    public static final int EVT_BATEU_PAREDE_DIR   = 26;
    public static final int EVT_BATEU_PERSONAGEM   = 27;
    public static final int EVT_RECOMECOU_ANIMACAO = 29;
    public static final int EVT_TOPO_PULO          = 30;
    public static final int EVT_COLIDIU_ARMADILHA  = 31;
    public static final int EVT_COLIDIU_FIMFASE    = 32;
    public static final int EVT_COLIDIU_CHECKPOINT = 33;
    public static final int EVT_TEMPO              = 34;
    
    /** Eventos que s�o gerados pelo personagem. */
    public static final int EVT_CRIA_PERSONAGEM    = 50;
    public static final int EVT_FIM_FASE_VITORIA   = 51;
    public static final int EVT_FIM_FASE_DERROTA   = 52;
    public static final int EVT_PERSONAGEM_ATIRANDO= 53;
    
    /* Eventos das armas */
    public static final int EVT_CRIA_SPRAY = 70;
    public static final int EVT_CANCELA_SPRAY = 71;
    public static final int EVT_CRIA_MATAMOSCA = 72;
    public static final int EVT_CANCELA_MATAMOSCA = 73;
    public static final int EVT_CRIA_METRALHADORA = 74;
    public static final int EVT_CANCELA_METRALHADORA = 75;
    public static final int EVT_CRIA_SUPERBOMBA = 76;
    public static final int EVT_CANCELA_SUPERBOMBA = 77;
    public static final int EVT_TROCA_ARMA = 78;
    
    /* Eventos dos inimigos */
    public static final int EVT_CRIA_BARATA = 80;
    public static final int EVT_MATA_BARATA = 81;
    public static final int EVT_CRIA_BESOURO = 82;
    public static final int EVT_MATA_BESOURO = 83;
    public static final int EVT_CRIA_JOANINHA = 84;
    public static final int EVT_MATA_JOANINHA = 85;
    public static final int EVT_CRIA_BORBOLETA = 86;
    public static final int EVT_MATA_BORBOLETA = 87;
    public static final int EVT_CRIA_MOSCA = 88;
    public static final int EVT_MATA_MOSCA = 89;
    public static final int EVT_CRIA_SUPERBOSS = 90;
    public static final int EVT_MATA_SUPERBOSS = 91;
    public static final int EVT_INIMIGO_ATACANDO = 92;
    
    /** Eventos do jogo. */
    public static final int EVT_PROG_INI           = 100;
    
    public int tipo;
    public int subtipo;
    public int x, y;
    public int valor;
}
