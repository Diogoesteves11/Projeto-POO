import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LevantamentoPesos extends Atividade implements Serializable{
    private int carga;
    private int numeroRepeticoes;
    private int series;

    public LevantamentoPesos() {
        super();
        this.carga = 0;
        this.numeroRepeticoes = 0;
        this.series = 1;
    }
    public LevantamentoPesos(String nome, String tipo, int tempoGasto, LocalDate data, int carga, int numeroRepeticoes, int series) {
        super(nome,tipo,tempoGasto,data);
        this.carga = carga;
        this.numeroRepeticoes = numeroRepeticoes;
        this.series = series;
    }
    public LevantamentoPesos(LevantamentoPesos p) {
        super(p);
        this.carga = p.getCarga();
        this.numeroRepeticoes = p.getNumeroRepeticoes();
        this.series = p.getSeries();
    }
    public int getCarga() {
        return this.carga;
    }
    public void setCarga(int p) {
        this.carga = p;
    }

    public int getNumeroRepeticoes() {
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
        return super.toString() + "\n -Carga: " + this.carga + "\n -Repetições: " + this.numeroRepeticoes + "\n -Séries: " + this.series + "\n -Dificuldade: " + (this.isHard() ? "Dificil": "Normal") ;
    }
    public Atividade clone() {
        return new LevantamentoPesos(this);
    }
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass()!=o.getClass()) return false;
        LevantamentoPesos p = (LevantamentoPesos) o;
        return this.carga == p.getCarga() &&  this.numeroRepeticoes == p.getNumeroRepeticoes() && this.series == p.getSeries() && super.equals(o);
    }
    public double calculaCaloriasGastas(Utilizador utilizador) {
        double idade = ChronoUnit.YEARS.between(utilizador.getDataNascimento(),LocalDate.now());
        double calorias = ((getCarga()/10) * (getNumeroRepeticoes()/10) * (getSeries()/10) * getTempoGasto() * idade * utilizador.fatorMultiplicativo() * utilizador.getPeso()) / 800;
        return calorias;
    }
    public boolean isHard() {
        int limiteCarga = 60;
        int limiteRepeticoes = 15;
        int limiteSeries = 3;

        if(this.getCarga() > limiteCarga || this.getNumeroRepeticoes() > limiteRepeticoes || this.getSeries() > limiteSeries) {
            return true;
        } else {
            return false;
        }
    }
}