/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author radtke
 */
public class FilaEvento {
    /** O tamanho máximo de uma fila de eventos. */
    public final int MAX_FILA = 50;  
    /** Fila de eventos. */
    private Evento eventos[];
    /** marca o início da fila no vetor. */
    private int inicio = 0;
    /** Indica o tamanho da fila. */
    private int tamanho = 0;
    
    public FilaEvento()
    {
        eventos = new Evento[MAX_FILA];
        // Aloca os eventos
        for(int i=0;i<MAX_FILA;i++)
            eventos[i] = new Evento();
        limpa();
    }
    
    /** Método para adicionar um evento na fila. */
    public boolean envia(int tipo, int subtipo, int x, int y, int valor)
    {
        if(tamanho >= MAX_FILA)
            return false;
        // Se tem espaço, adiciona
        eventos[(inicio+tamanho)%MAX_FILA].tipo = tipo;
        eventos[(inicio+tamanho)%MAX_FILA].subtipo = subtipo;
        eventos[(inicio+tamanho)%MAX_FILA].x = x;
        eventos[(inicio+tamanho)%MAX_FILA].y = y;
        eventos[(inicio+tamanho)%MAX_FILA].valor = valor;
        // aumenta o tamanho da fila
        tamanho++;      
        return true;
    }

    /** Método para pegar o primeiro evento da fila. */
    public Evento proximo()
    {
        if(tamanho == 0)
            return null;
        // Volta o primeiro da fila
        Evento ev = eventos[inicio];
        // Passa para o próximo e diminui o tamanho
        inicio=++inicio%MAX_FILA;
        tamanho--;
        
        // Retorna o evento
        return ev;
    }
    
    public void limpa()
    {
        inicio = 0;
        tamanho = 0;
    }
       
}
