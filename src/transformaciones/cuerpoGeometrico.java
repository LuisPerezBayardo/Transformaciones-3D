package transformaciones;

import java.awt.Color;
import java.util.ArrayList;

public class cuerpoGeometrico {
    String figura;
    ArrayList<punto> puntos =  new ArrayList<punto>();
   
    int indice;
    double radio,radio2;
    Color relleno,borde;
    punto eje;
    
    public cuerpoGeometrico(String figura, int indice, Color relleno, Color borde) {
       this.figura = figura;
       this.indice = indice;
       this.relleno = relleno;
       this.borde = borde;

   }
     
   public cuerpoGeometrico(String figura, int indice,double radio, Color relleno, Color borde) {
        this.figura = figura;
        this.indice = indice;
        this.relleno = relleno;
        this.borde = borde;
        this.radio=radio;
    }
     
   
    public ArrayList<punto> getPuntos() {
        return puntos;
    }

    public void setPuntos(ArrayList<punto> puntos) {
        this.puntos = puntos;
    }
    
    public void addPunto(punto p){
    puntos.add(p);
    }
    
}

