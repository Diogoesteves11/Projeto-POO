import java.time.temporal.ChronoUnit;
import java.io.Serializable;
import java.time.LocalDate;

public class Corrida extends Atividade implements Serializable{
    private double distancia;
    private double ritmoMedio;

    public Corrida() {
        super();
        this.distancia = 0.0;
        this.ritmoMedio = 0;
    }
    public Corrida(String nome, String tipo, int tempoGasto, LocalDate data, double distancia, double ritmoMedio) {
        super(nome,tipo,tempoGasto,data);
        this.distancia = distancia;
        this.ritmoMedio = ritmoMedio;
    }
    public Corrida(Corrida d) {
        super(d);
        this.distancia = d.getDistancia();
        this.ritmoMedio = d.getRitmoMedio();

    }
    public double getDistancia() {
        return this.distancia;
    }
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    public double getRitmoMedio() {
        return this.ritmoMedio;
    }
    public void setRitmoMedio(double ritmoMedio) {
        this.ritmoMedio = ritmoMedio;
    }
    public String toString() {
        return super.toString() + "\n -Distancia: " + this.distancia + "\n -Ritmo Médio: " + this.ritmoMedio + "\n -Dificuldade: " + (this.isHard() ? "Dificil": "Normal") ;
    }
    public Atividade clone() {
        return new Corrida(this);
    }
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass() ) return false;
        Corrida d = (Corrida) o;
        return this.distancia == d.getDistancia()  && this.ritmoMedio == d.getRitmoMedio() && super.equals(o);
    }
    public double calculaCaloriasGastas(Utilizador utilizador) {
        double idade = ChronoUnit.YEARS.between(utilizador.getDataNascimento(),LocalDate.now());
        double calorias = (0.035 * utilizador.getPeso()) + (utilizador.fatorMultiplicativo() * 2 * this.distancia * idade);
        return calorias;
    }
    public boolean isHard() {
        double limiteDistancia = 50;
        int limiteRitmoMedio = 4; //min/km
        if(this.getDistancia() > limiteDistancia || this.getRitmoMedio() < limiteRitmoMedio) {
            return true;
        } else {
            return false;
        }

    }

}