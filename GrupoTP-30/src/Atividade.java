import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public abstract class Atividade implements Serializable, Hard {
   private String nome;
   private String tipo;
   private int tempoGasto;
   private LocalDate data;

   public Atividade() {
      this.nome = "";
      this.tipo = "";
      this.tempoGasto = 0;
      this.data = LocalDate.EPOCH;
   }

   public Atividade(String nome, String tipo, int tempoGasto, LocalDate data) {
      this.nome = nome;
      this.tipo = tipo;
      this.tempoGasto = tempoGasto;
      this.data = data;
   }

   public Atividade (Atividade atv) {

      this.nome = atv.getNome();
      this.tipo = atv.getTipo();
      this.tempoGasto = atv.getTempoGasto();
      this.data = atv.getData();
   }

   public String getNome() {
      return this.nome;
   }
   public void setNome(String nome) {
      this.nome = nome;
   }
   public String getTipo() {
      return this.tipo;
   }
   public void setTipo(String tipo) {
      this.tipo = tipo;
   }
   public int getTempoGasto() {
      return this.tempoGasto;
   }
   public void setTempoGasto(int tempoGasto) {
      this.tempoGasto = tempoGasto;
   }

   public LocalDate getData() {
      return data;
   }

   public void setData(LocalDate data) {
      this.data = data;
   }
   public String toString() {
      return new String("\n->Nome: " + this.nome + "\n -Tipo: " + this.tipo + "\n -Tempo Gasto: " + this.tempoGasto + "\n -Data: " + this.data + " (" + this.data.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ")");
   }
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) return false;
      Atividade atividade = (Atividade) o;
      return this.nome.equals(atividade.getNome()) && this.tipo.equals(atividade.getTipo()) && this.tempoGasto == atividade.getTempoGasto() && this.data.equals(atividade.getData());
   }

   public abstract double calculaCaloriasGastas(Utilizador utilizador);
   public abstract Atividade clone();
}