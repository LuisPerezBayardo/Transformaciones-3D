package transformaciones;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Transformaciones extends JFrame implements Runnable {

    Thread hilo;
    private BufferedImage buffer;
    private Graphics graPixel;
    private Image offScreenImage;
    private Graphics offScreenGraphics;
    public BufferStrategy strategy;
    public int numTexto = 0;
    private punto punto1Piramide, punto2Piramide, punto3Piramide, punto4Piramide;
    private punto punto1Cubo, punto2Cubo, punto3Cubo, punto4Cubo, punto5Cubo, punto6Cubo, punto7Cubo, punto8Cubo;
    private int d1 = 1, d2 = 1, d3 = 1;
    ArrayList<dibujo> arrayDibujos = new ArrayList<dibujo>();
    dibujo piramide1 = new dibujo("Piramide1");
    dibujo cubo1 = new dibujo("Cubo1");
    dibujo cp1 = new dibujo("cp1");
    dibujo cp2 = new dibujo("cp2");
    dibujo esfera1 = new dibujo("esfera1");

    
    
    Transformaciones() {
        this.setSize(1650, 1080);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buffer = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        graPixel = (Graphics2D) buffer.createGraphics();
        this.setVisible(true);
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        graPixel = (Graphics2D) buffer.createGraphics();

        offScreenImage = createImage(this.getSize().width, this.getSize().height);
        offScreenGraphics = offScreenImage.getGraphics();

        createBufferStrategy(2);
        strategy = getBufferStrategy();

        piramide1.getArrayFiguras().add(new cuerpoGeometrico("Piramide", 0, Color.MAGENTA, Color.MAGENTA));
        //se ingresan los puntos 3D

        piramide1.getArrayFiguras().get(0).addPunto(new punto(123, 10, 0));
        piramide1.getArrayFiguras().get(0).addPunto(new punto(430, 0, 0));
        piramide1.getArrayFiguras().get(0).addPunto(new punto(300, 200, 0));
        piramide1.getArrayFiguras().get(0).addPunto(new punto(450, 20, 200));
        //el eje para rotacion
        piramide1.setEje(new punto(40, -280, 0));
        arrayDibujos.add(piramide1);

        //punto proyeccion
        cp1.getArrayFiguras().add(new cuerpoGeometrico("Punto", 0, Color.MAGENTA, Color.MAGENTA));
        //se ingresan los puntos 3D
        cp1.getArrayFiguras().get(0).addPunto(new punto(150, 20, 50));

        //el eje para rotacion
        cp1.setEje(new punto(1, 1, 1));
        arrayDibujos.add(cp1);

        cp2.getArrayFiguras().add(new cuerpoGeometrico("Punto", 0, Color.MAGENTA, Color.MAGENTA));
        //se ingresan los puntos 3D
        cp2.getArrayFiguras().get(0).addPunto(new punto(250, 250, 1));

        //el eje para rotacion
        cp2.setEje(new punto(1, 1, 1));
        arrayDibujos.add(cp2);

        punto referencia = new punto(50, 0, 0);
        int distancia = 150;
        cubo1.getArrayFiguras().add(new cuerpoGeometrico("Cubo", 0, Color.MAGENTA, Color.MAGENTA));
        cubo1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x, referencia.D3y, referencia.D3z));
        cubo1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x + distancia, referencia.D3y, referencia.D3z));
        cubo1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x, referencia.D3y, referencia.D3z + distancia));
        cubo1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x + distancia, referencia.D3y, referencia.D3z + distancia));

        cubo1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x + distancia, referencia.D3y + distancia, referencia.D3z));
        cubo1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x, referencia.D3y + distancia, referencia.D3z));
        cubo1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x, referencia.D3y + distancia, referencia.D3z + distancia));
        cubo1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x + distancia, referencia.D3y + distancia, referencia.D3z + distancia));

        arrayDibujos.add(cubo1);

        referencia = new punto(-100, 50, 50);

        esfera1.getArrayFiguras().add(new cuerpoGeometrico("Esfera", 0, 70, Color.MAGENTA, Color.MAGENTA));
        esfera1.getArrayFiguras().get(0).addPunto(new punto(referencia.D3x, referencia.D3y, referencia.D3z));

        arrayDibujos.add(esfera1);
        /*       
        
        punto1Cubo=new punto(100,100,0); 
        punto2Cubo=new punto(100,200,0); 
        punto3Cubo=new punto(200,100,0); 
        punto4Cubo=new punto(200,200,0); 
        punto5Cubo=new punto(100,100,50); 
        punto6Cubo=new punto(100,200,50); 
        punto7Cubo=new punto(200,100,50); 
        punto8Cubo=new punto(200,200,50);
         */

        hilo = new Thread(this);
        hilo.start();
    }
    
    
    
    
    
    
    
    
    
    
    
    

    public void putPixel(int x, int y, Color c, Graphics g) {
        buffer.setRGB(0, 0, c.getRGB());
        g.drawImage(buffer, x + 600, y + 400, this);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void drawLine(int x1, int y1, int x2, int y2, Color c, Graphics g) {
        double dx, dy, xinc, yinc, x, y;
        int pasos;
        dx = x2 - x1;
        dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) {
            pasos = (int) Math.abs(dx);
        } else {
            pasos = (int) Math.abs(dy);
        }
        xinc = dx / pasos;
        yinc = dy / pasos;
        x = x1;
        y = y1;
        for (int i = 1; i <= pasos; i++) {
            x = x + xinc;
            y = y + yinc;
            putPixel((int) Math.round(x), (int) Math.round(y), c, g);
        }
    }

    
    
    
    
    private void drawRectangle(int x1, int y1, int x2, int y2, Color Relleno, Color Borde, Graphics g) {
        drawLine(x1, y1, x1, y2, Borde, g);
        drawLine(x1, y1, x2, y1, Borde, g);
        drawLine(x2, y1, x2, y2, Borde, g);
        drawLine(x1, y2, x2, y2, Borde, g);
    }

    private void dibujarPiramide(punto p1, punto p2, punto p3, punto p4, Color Relleno, Color Borde, Graphics g) {
        drawLine((int) p1.D2x, (int) p1.D2y, (int) p2.D2x, (int) p2.D2y, Borde, g);
        drawLine((int) p2.D2x, (int) p2.D2y, (int) p3.D2x, (int) p3.D2y, Borde, g);
        drawLine((int) p3.D2x, (int) p3.D2y, (int) p1.D2x, (int) p1.D2y, Borde, g);
        drawLine((int) p4.D2x, (int) p4.D2y, (int) p1.D2x, (int) p1.D2y, Borde, g);
        drawLine((int) p4.D2x, (int) p4.D2y, (int) p2.D2x, (int) p2.D2y, Borde, g);
        drawLine((int) p4.D2x, (int) p4.D2y, (int) p3.D2x, (int) p3.D2y, Borde, g);
    }

    private void dibujarCubo(punto p1, punto p2, punto p3, punto p4, punto p5, punto p6, punto p7, punto p8, Color Relleno, Color Borde, Graphics g) {
        drawLine((int) p1.D2x, (int) p1.D2y, (int) p2.D2x, (int) p2.D2y, Borde, g);
        drawLine((int) p1.D2x, (int) p1.D2y, (int) p3.D2x, (int) p3.D2y, Borde, g);
        drawLine((int) p1.D2x, (int) p1.D2y, (int) p6.D2x, (int) p6.D2y, Borde, g);
        drawLine((int) p2.D2x, (int) p2.D2y, (int) p4.D2x, (int) p4.D2y, Borde, g);
        drawLine((int) p2.D2x, (int) p2.D2y, (int) p5.D2x, (int) p5.D2y, Borde, g);
        drawLine((int) p3.D2x, (int) p3.D2y, (int) p4.D2x, (int) p4.D2y, Borde, g);
        drawLine((int) p3.D2x, (int) p3.D2y, (int) p7.D2x, (int) p7.D2y, Borde, g);
        drawLine((int) p4.D2x, (int) p4.D2y, (int) p8.D2x, (int) p8.D2y, Borde, g);
        drawLine((int) p5.D2x, (int) p5.D2y, (int) p6.D2x, (int) p6.D2y, Borde, g);
        drawLine((int) p5.D2x, (int) p5.D2y, (int) p8.D2x, (int) p8.D2y, Borde, g);
        drawLine((int) p6.D2x, (int) p6.D2y, (int) p7.D2x, (int) p7.D2y, Borde, g);
        drawLine((int) p7.D2x, (int) p7.D2y, (int) p8.D2x, (int) p8.D2y, Borde, g);
    }

    private void dibujarEsfera(ArrayList<punto> p, Color Relleno, Color Borde, Graphics g) {
        for (punto punto : p) {
            putPixel((int) Math.round(punto.D2x + p.get(0).D3x), (int) Math.round(punto.D2y + p.get(0).D3y), Borde, g);
        }
    }
    
    
    
    

    private void Esfera(ArrayList<punto> p, double radio) {
        double u = 0, v = 0, px = 0, py = 0, pz = 0;
        while (u < 6.2832) {
            v = 0;
            while (v < 3.1416) {
                px = radio * Math.cos(u) * Math.sin(v);
                py = radio * Math.sin(u) * Math.sin(v);
                pz = radio * Math.cos(v);
                p.add(new punto(px, py, pz));
                v += .1;
            }
            u += .1;
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void dibujarPunto(punto p1, Color Relleno, Color Borde, Graphics g) {
        putPixel((int) Math.round(p1.D2x), (int) Math.round(p1.D2y), Relleno, g);
    }

    
    
    
    /*@Override
    public void paint(Graphics g) {
    }*/
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void dibujo() {
        //  gra.drawRect(0, 0, WIDTH, HEIGHT);
        BufferStrategy bs = getBufferStrategy(); //Gets the buffer strategy our canvas is currently using
        if (bs == null) { //True if our canvas has no buffer strategy (should only happen once when we first start the game)
            createBufferStrategy(2); //Create a buffer strategy using two buffers (double buffer the canvas)
            return;
        }
        Graphics gra = bs.getDrawGraphics();
        gra = bs.getDrawGraphics();
        //  this.drawRectangle(-600, -400, 500, 500, Color.BLACK, Color.BLUE, gra);//Es el fondo
        gra.fillRect(0, 0, 1500, 1000);
        gra.setColor(Color.WHITE);
        Font myFont = new Font("Courier New", 1, 17);
        gra.setFont(myFont);

        offScreenGraphics.clearRect(0, 0, WIDTH, HEIGHT);
        offScreenGraphics.drawRect(0, 0, WIDTH, HEIGHT);

        for (dibujo d : arrayDibujos) {
            for (cuerpoGeometrico Arrf : d.arrayFiguras) {
                switch (Arrf.figura) {
                    case "Piramide":
                        this.dibujarPiramide(Arrf.puntos.get(0), Arrf.puntos.get(1), Arrf.puntos.get(2), Arrf.puntos.get(3), Arrf.relleno, Arrf.borde, gra);
                        break;
                    case "Cubo":
                        this.dibujarCubo(Arrf.puntos.get(0), Arrf.puntos.get(1), Arrf.puntos.get(2), Arrf.puntos.get(3), Arrf.puntos.get(4), Arrf.puntos.get(5), Arrf.puntos.get(6), Arrf.puntos.get(7), Arrf.relleno, Arrf.borde, gra);
                        break;
                    case "Punto":
                        this.dibujarPunto(Arrf.puntos.get(0), Arrf.relleno, Arrf.borde, gra);
                        break;
                    case "Esfera":
                        this.dibujarEsfera(Arrf.puntos, Arrf.relleno, Arrf.borde, gra);
                        break;
                }
            }
        }
        /*
           for (cuerpoGeometrico Arrf : piramide1.arrayFiguras) {
            for (punto p : Arrf.puntos) {
              this.drawLine((int)p.D2x, (int)p.D2y, (int)cp1.arrayFiguras.get(0).puntos.get(0).D2x,(int)cp1.arrayFiguras.get(0).puntos.get(0).D2y, Color.red, gra);
            }
        }
        */
        gra.dispose();
        bs.show();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    public void conversion() {
        //oblicua (piramide1);
        //oblicua(piramide1);
        //oblicua(cubo1);
        //oblicua(esfera1);       
        //PerspectivaFoco1(esfera1);
        //PerspectivaFoco2(piramide1);
        //dibujo();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void oblicua(dibujo dib, double d1, double d2, double d3) {
        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            if (Arrf.figura == "Esfera") {
                Esfera(Arrf.puntos, Arrf.radio);
            }
            for (punto p : Arrf.puntos) {
                p.D2x = p.D3x - (p.D3z / d3) * d1;
                p.D2y = p.D3y - (p.D3z / d3) * d2;
            }
        }

    }

    
    
    
    
    
    public void paralela(dibujo dib) {
        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            if (Arrf.figura == "Esfera") {
                Esfera(Arrf.puntos, Arrf.radio);
            }
            for (punto p : Arrf.puntos) {
                p.D2x = p.D3x - (p.D3z / 1) * 1;
                p.D2y = p.D3y - (p.D3z / 1) * 1;
            }
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void PerspectivaFoco1(dibujo dib, double c1, double c2, double c3) {
        //    double c1 = 200, c2 = 100, c3 = -200;
        //  double c1 = 900, c2 = 100, c3 = -120;
        /*  c1=  (int)cp1.arrayFiguras.get(0).puntos.get(0).D3x;
        c2=  (int)cp1.arrayFiguras.get(0).puntos.get(0).D3y;
        c3=  (int)cp1.arrayFiguras.get(0).puntos.get(0).D3z; 
        */
        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            if (Arrf.figura == "Esfera") {
                c1 = 20;
                c2 = 20;
                c3 = -100;
                Esfera(Arrf.puntos, Arrf.radio);
            }
            for (punto p : Arrf.puntos) {
                double u = -c3 / (p.D3z - c3);
                p.D2x = c1 + (p.D3x - c1) * u;
                p.D2y = c2 + (p.D3y - c2) * u;
            }
        }
    }

    
    
    
    
    
    public void PerspectivaFoco2(dibujo dib, double angulo, double c3) {
        //    double c1 = 200, c2 = 100, c3 = -200;
        // double c1 = -500, c2 = -200, c3 = -400;
        // double angulo = 45;
        double rad = Math.toRadians(angulo);
        double sen = Math.sin(rad);
        double cos = Math.cos(rad);

        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            for (punto p : Arrf.puntos) {
                p.D2x = (p.D3x * c3) / (-sen - p.D3z * cos + c3);
                p.D2y = (p.D3y * c3) / (-p.D3x * sen - p.D3z * cos + c3);
            }
        }
    }
    
    
    

  
    public void PerspectivaFoco3(dibujo dib, double angulo, double angulo2, double c3) {
        //    double c1 = 200, c2 = 100, c3 = -200;
        //   double c1 = -500, c2 = -200, c3 = -400;
        //  double angulo = 120;
        double rad = Math.toRadians(angulo);
        double sen = Math.sin(rad);
        double cos = Math.cos(rad);

        //double angulo2 = 80;
        double rad2 = Math.toRadians(angulo2);
        double sen2 = Math.sin(rad2);
        double cos2 = Math.cos(rad2);

        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            for (punto p : Arrf.puntos) {
                p.D2x = (p.D3x * c3) / (p.D3x * sen + p.D3y * cos * sen2 + p.D3z * cos * cos2 + c3);
                p.D2y = (p.D3y * c3) / (p.D3x * sen + p.D3y * cos * sen2 + p.D3z * cos * cos2 + c3);
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    @Override
    public void run() {

        try {
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(800);
            
           
            RotacionX(cubo1,40);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(800); 
            
            RotacionZ(cubo1,20);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(800);
            
            RotacionY(cubo1,80);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(800);
             
            RotacionY(cubo1,210);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(1000); 
            
            
            Traslación(cubo1,0,0,500);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(1000);
            Traslación(cubo1,0,200,0);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(800);          
            Traslación(cubo1,200,0,0);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(800);
            
            
            Escalacion(cubo1,1.3,1.3,1.3);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(800);
            Escalacion(cubo1,.5,.5,.5);
            oblicua(cubo1, 1, 2, 3);
            dibujo();
            sleep(800);
        } catch (Exception e) {}
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void Traslación(dibujo dib, double Tx, double Ty, double Tz) {
        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            for (punto p : Arrf.puntos) {
                p.D3x = p.D3x + Tx;
                p.D3y = p.D3y + Ty;
                p.D3z = p.D3z + Tz;
            }
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    public static void Escalacion(dibujo dib, double Sx, double Sy, double Sz) {
        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            for (punto p : Arrf.puntos) {
                p.D3x = p.D3x * Sx;
                p.D3y = p.D3y * Sy;
                p.D3z = p.D3z * Sz;
            }
        }
    }
     
     
     
     
    
    
    
    
    
    
    
     
    public static void RotacionX(dibujo dib, double angulo) {
        double rad = Math.toRadians(angulo);
        double sen = Math.sin(rad);
        double cos = Math.cos(rad);

        int contador=0;
        punto eje=new punto(0,0,0);
         
        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            for (punto p : Arrf.puntos) {
                if(contador==0){
                    eje=new punto(p.D3x,p.D3y,p.D3z);
                }              
                p.D3x=p.D3x-eje.D3x;
                p.D3y=p.D3y-eje.D3y;
                p.D3z=p.D3z-eje.D3z;
                
                p.D3x =p.D3x ; 
                p.D3y = p.D3y * cos- p.D3z*sen;
                p.D3z = p.D3y * sen+ p.D3z*cos;
                
                p.D3x=p.D3x+eje.D3x;
                p.D3y=p.D3y+eje.D3y;
                p.D3z=p.D3z+eje.D3z;
                
                contador++;
            }
        }
    }

     
     
     
    
    
    
    
    
    
    
    public static void RotacionY(dibujo dib, double angulo) {
        double rad = Math.toRadians(angulo);
        double sen = Math.sin(rad);
        double cos = Math.cos(rad);
        int contador=0;
        punto eje=new punto(0,0,0);
         
        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            for (punto p : Arrf.puntos) {
                
                if(contador==0){
                    eje=new punto(p.D3x,p.D3y,p.D3z);
                }  

                p.D3x=p.D3x-eje.D3x;
                p.D3y=p.D3y-eje.D3y;
                p.D3z=p.D3z-eje.D3z;

                p.D3x = p.D3x * cos- p.D3z*sen ; 
                p.D3y = p.D3y ;
                p.D3z = -p.D3x * sen+ p.D3z*cos;

                p.D3x=p.D3x+eje.D3x;
                p.D3y=p.D3y+eje.D3y;
                p.D3z=p.D3z+eje.D3z;
                
                contador++;
            }
        }
    }
    
        
        
        
    
    
    
    
    
    
    public static void RotacionZ(dibujo dib, double angulo) {
        double rad = Math.toRadians(angulo);
        double sen = Math.sin(rad);
        double cos = Math.cos(rad);

        //    p[0] = T.x-125;
        //        p[1] = T.y-120;
        int contador=0;
        punto eje=new punto(0,0,0);
         
        for (cuerpoGeometrico Arrf : dib.arrayFiguras) {
            for (punto p : Arrf.puntos) {
                 
                if(contador==0){
                    eje=new punto(p.D3x,p.D3y,p.D3z);
                }
                
                p.D3x=p.D3x-eje.D3x;
                p.D3y=p.D3y-eje.D3y;
                p.D3z=p.D3z-eje.D3z;
                 
                p.D3x = p.D3x * cos- p.D3y*sen ; 
                p.D3y = p.D3x*sen+ p.D3y*cos ;
                p.D3z =  p.D3z;
                
                p.D3x=p.D3x+eje.D3x;
                p.D3y=p.D3y+eje.D3y;
                p.D3z=p.D3z+eje.D3z;
                
                contador++;
            }
        }
    }
    
         
         
    
    
    
    
    
    
    
    public static void main(String[] args) {
        Transformaciones m = new Transformaciones();
    }
    
}
