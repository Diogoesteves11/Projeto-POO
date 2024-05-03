import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Ciclismo extends Atividade implements Serializable{
    private double distancia;
    private double altimetria;

    public Ciclismo(){
        super();
        this.distancia = 0.0;
        this.altimetria = 0.0;
    }

    public Ciclismo(String nome, String tipo, int tempoGasto, LocalDate data, double distancia, double altimetria) {
        super(nome,tipo,tempoGasto,data);
        this.distancia = distancia;
        this.altimetria = altimetria;
    }
    public Ciclismo(Ciclismo da) {
        super(da);
        this.distancia = da.getDistancia();
        this.altimetria = da.getAltimetria();

    }

    public double getDistancia() {
        return this.distancia;
    }
    public void setDistanciaCiclismo(double d) {
        this.distancia = d;
    }
    public  double getAltimetria() {
        return this.altimetria;
    }

    public void  setAltimetriaBTT(double a){
        this.altimetria=a;
    }
    public String toString() {
        return super.toString() + "\n -Distancia: " + this.distancia + "\n -Altimetria: " + this.altimetria + "\n -Dificuldade: " + (this.isHard() ? "Dificil": "Normal");
    }
    public Atividade clone() {
        return new Ciclismo(this);
    }
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass() ) return false;
        Ciclismo d = (Ciclismo) o;
        return super.equals(o) && this.distancia == d.getDistancia()  && this.altimetria == d.getAltimetria();
    }
    public double calculaCaloriasGastas(Utilizador utilizador) {
        double idade = ChronoUnit.YEARS.between(utilizador.getDataNascimento(),LocalDate.now());
        double calorias = ((getDistancia()/10) * (getAltimetria()/10) * getTempoGasto() * idade * utilizador.fatorMultiplicativo() * utilizador.getPeso()) / 1750;
        return calorias;
    }
    public boolean isHard() {
        double limiteDistancia = 50; //50 km
        double limiteAltimetria = 500; //500m

        if(this.getDistancia()>limiteDistancia || this.getAltimetria() > limiteAltimetria) {
            return true;
        } else {
            return false;
        }
    }
}