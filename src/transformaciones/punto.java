package transformaciones;


public class punto {
    double D3x,D3y,D3z,D3radio;
   double D2x,D2y,D2radio;

    public punto(double D3x, double D3y, double D3z) {
        this.D3x = D3x;
        this.D3y = D3y;
        this.D3z = D3z;
    }

    public double getD3x() {
        return D3x;
    }

    public void setD3x(double D3x) {
        this.D3x = D3x;
    }

    public double getD3y() {
        return D3y;
    }

    public void setD3y(double D3y) {
        this.D3y = D3y;
    }

    public double getD3z() {
        return D3z;
    }

    public void setD3z(double D3z) {
        this.D3z = D3z;
    }

    public double getD3radio() {
        return D3radio;
    }

    public void setD3radio(double D3radio) {
        this.D3radio = D3radio;
    }

    public double getD2x() {
        return D2x;
    }

    public void setD2x(double D2x) {
        this.D2x = D2x;
    }

    public double getD2y() {
        return D2y;
    }

    public void setD2y(double D2y) {
        this.D2y = D2y;
    }

    public double getD2radio() {
        return D2radio;
    }

    public void setD2radio(double D2radio) {
        this.D2radio = D2radio;
    }

    public void set2D(double x,double y){
    this.D2x=x;
    this.D2y=y;
  
    }

}
