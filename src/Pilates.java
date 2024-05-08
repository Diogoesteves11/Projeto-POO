import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Pilates extends Atividade implements Serializable {
    private int numeroRepeticoes;
    private int series;


    public Pilates() {
        super();
        this.numeroRepeticoes = 0;
        this.series = 1;
    }
    public Pilates(String nome, String tipo, int tempoGasto, LocalDate data, int numeroRepeticoes, int series) {
        super(nome,tipo,tempoGasto,data);
        this.numeroRepeticoes = numeroRepeticoes;
        this.series = series;
    }


    public Pilates(Pilates reps) {
        super(reps);
        this.numeroRepeticoes = reps.getNumeroRepeticoes();
        this.series = reps.getSeries();
    }

    public int getNumeroRepeticoes(){
        return this.numeroRepeticoes;
    }
    public void setNumeroRepeticoes(int nr) {
        this.numeroRepeticoes = nr;
    }
    public int getSeries() {
        return this.series;
    }
    public void setSeries(int s) {
        this.series = s;
    }
    public String toString() {
        return super.toString() + " \n -Número de Repetições: " + this.numeroRepeticoes + " \n -Séries: " + this.series + "\n -Dificuldade: " + (this.isHard() ? "Dificil": "Normal");
    }
    public Atividade clone() {
        return new Pilates(this);
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || this.getClass() != o.getClass() ) return false;
        Pilates r = (Pilates) o;
        return this.numeroRepeticoes == r.getNumeroRepeticoes() && this.series == r.getSeries() && super.equals(o);
    }
    public double calculaCaloriasGastas(Utilizador utilizador) {
        double idade = ChronoUnit.YEARS.between(utilizador.getDataNascimento(),LocalDate.now());
        double calorias = ((getNumeroRepeticoes()/20) * (getSeries()/20) * getTempoGasto() * idade * utilizador.fatorMultiplicativo()* utilizador.getPeso()) / 100; //polimorfismo fatorMultiplicativo
        return calorias;
    }
    public boolean isHard() {
        int limiteRepeticoes = 15;
        int limiteSeries = 3;

        if(this.getNumeroRepeticoes() > limiteRepeticoes || this.getSeries() > limiteSeries){
            return true;

        } else {
            return false;
        }
    }
}