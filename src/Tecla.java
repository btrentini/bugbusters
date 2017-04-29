/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author paulo.radtke
 */

/** Classe interna para representar uma tecla do celular. */
public final class Tecla
{
    /** Atributo indica que a tecla acabou de ser pressionada. */
    boolean pressionou;
    /** Atributo indica que a tecla está sendo mantida pressionada. */    
    boolean pressionado;
    /** Atributo indica que a tecla acabou de ser liberada. */    
    boolean liberou;

    /** Construtor vazio da classe. */
    public Tecla()
    {
        reset();
    }
    
    /** Método que atualiza o estado da tecla, baseado em se ela está pressionada no momento ou não. */
    void atualiza(boolean estado)
    {
        if(estado)
        {
            if(!pressionou && !pressionado)
            {
                pressionou=true;
                pressionado=true;
                liberou=false;
            }
            else
            {
                pressionou=false;
                pressionado=true;
                liberou=false;
            }
        }
        else
        {
            if(pressionado && !liberou)
            {
                pressionado=false;
                liberou=true;
                pressionou=false;
            }
            else
            {
                pressionado=false;
                liberou=false;
                pressionou=false;
            }
        }
    }
    
    public void reset()
    {
        pressionado=false;
        liberou=false;
        pressionou=false;
    }
}

