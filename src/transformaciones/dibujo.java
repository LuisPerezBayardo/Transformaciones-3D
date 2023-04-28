package transformaciones;

import java.util.ArrayList;


public class dibujo {
    ArrayList <cuerpoGeometrico> arrayFiguras= new ArrayList<cuerpoGeometrico>();
    String nombreFig;
    punto eje;
    Boolean ConRelleno=true;

    
    public dibujo(String nombreFig) {
        this.nombreFig = nombreFig;
    }
    
    public dibujo(){}

    
    public ArrayList<cuerpoGeometrico> getArrayFiguras() {
        return arrayFiguras;
    }

    public void setArrayFiguras(ArrayList<cuerpoGeometrico> arrayFiguras) {
        this.arrayFiguras = arrayFiguras;
    }

    public String getNombreFig() {
        return nombreFig;
    }

    public void setNombreFig(String nombreFig) {
        this.nombreFig = nombreFig;
    }

    public punto getEje() {
        return eje;
    }

    public void setEje(punto eje) {
        this.eje = eje;
    }

    public Boolean getConRelleno() {
        return ConRelleno;
    }

    public void setConRelleno(Boolean ConRelleno) {
        this.ConRelleno = ConRelleno;
    }
 
}
