import java.util.ArrayList;
import java.time.LocalDate;
import java.io.*;
public class UtilizadorOcasional extends Utilizador implements Serializable{
   public UtilizadorOcasional(){
        super();
    }
    public UtilizadorOcasional(String nome, String idUsuario,String password ,String morada,double peso, String email, int freqCardiaca,double calorias,ArrayList<Atividade> atividades, LocalDate data, String tipo, ArrayList<PlanoTreino> planos, ArrayList<Atividade> atvFut, ArrayList<PlanoTreino> plFut) {
      super(nome, idUsuario,password,morada,peso,email, freqCardiaca,calorias, atividades,atvFut ,data, tipo,planos, plFut);
  }

    public UtilizadorOcasional(Utilizador u){
        super(u);
    }

    public Utilizador clone(){
        return new UtilizadorOcasional(this);
    }
    public String toString() {
      return super.toString();
   }

    public double fatorMultiplicativo() {
        if(this.getFreqCardiacaMedia() > 85){
            return 1.3;
        }
        else return 1.1;
    }
}
