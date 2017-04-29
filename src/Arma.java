
import javax.microedition.lcdui.Image;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author students
 */

import java.io.*;
import java.util.*;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.*;

public class Arma extends Sprite{
    String nome;
    private int largbloco;
    private int altbloco;
    int x;
    int y;
    private int potencia;
    private int municao;
    private int varma;
    private boolean disponivel;
    /** O vetor com o áudio da aplicação. */   
    Player vetorAudio[];
    /** A playlist de áudio da aplicação. */
    String playlist[] = null;
    /** Os tipos MIME da playlist de áudio da aplicação. */
    String playlistMime[] = null;
        
    public Arma(String nome,Image imagem, int largbloco, int altbloco, int x, int y, int potencia, int municao, int varma, boolean disponivel, 
            String [] playlist, String [] playlistMime){
        super(imagem, largbloco, altbloco);
        this.nome = nome;
        this.x = x;
        this.y = x;
        this.potencia = potencia;
        this.municao = municao;
        this.varma = varma;
        this.disponivel = disponivel;
        this.playlist = playlist;
        this.playlistMime = playlistMime;
        // Cria o vetor de audio
        vetorAudio = new Player[playlist.length];
        for(int i=0;i<playlist.length;i++)
            vetorAudio[i] = carregaAudio(playlist[i], playlistMime[i]);
    }
    
    public Arma(Arma a, int x, int y){
        super(a);
        this.x = x;
        this.y = y;
        this.nome = nome;
        this.x = x;
        this.y = x;
        this.potencia = potencia;
        this.municao = municao;
        this.varma = varma;
        this.disponivel = disponivel;
        // Cria o vetor de audio
        vetorAudio = new Player[playlist.length];
        for(int i=0;i<playlist.length;i++)
            vetorAudio[i] = carregaAudio(playlist[i], playlistMime[i]);
    }
    
    public void deslocArma(int xmira, int ymira, int xview, int yview){
        if(ymira>=430)
            y= yview + 184 + (ymira-429);
        else
            y= yview + 184;
        x= xmira;
        
        this.setPosition(x, y);
    }
    
    public boolean atira(){
        if(this.estaDisponivel()){
            this.setFrame(0);
            this.reproduzAudio(vetorAudio[0], 1);
            if(municao==1){
                municao--;
                this.setDisponivel(false);           
                return false;
            }else{    
                if(municao==-99)
                    return true;
                else{
                    municao--;
                    return true;
                }
            }
        }else
            return false;
    }
    
    public boolean estaDisponivel(){
        return disponivel;
    }
    
    public void setDisponivel(boolean estado){
        disponivel = estado;
    }
    
    public int getPotencia(){
        return potencia;
    }
    
    public int getMunicao(){
        return municao;
                
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
