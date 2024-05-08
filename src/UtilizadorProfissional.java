import java.util.ArrayList;
import java.time.LocalDate;
import java.io.*;
public class UtilizadorProfissional extends Utilizador implements Serializable{
   public UtilizadorProfissional(){
       super();
   }
   public UtilizadorProfissional(String nome, String idUsuario,String password, String morada,double peso,String email, int freqCardiaca,double calorias,ArrayList<Atividade> atividades, LocalDate data, String tipo, ArrayList<PlanoTreino> planos, ArrayList<Atividade> atvFut, ArrayList<PlanoTreino> planoFut) {
      super(nome, idUsuario,password,morada,peso, email, freqCardiaca,calorias, atividades,atvFut, data, tipo, planos, planoFut);
  }
   public UtilizadorProfissional(Utilizador u){
       super(u);
   }

   public Utilizador clone(){
       return new UtilizadorProfissional((this));
   }

   public String toString() {
    return super.toString();
 }

   public double fatorMultiplicativo() {
       if(this.getFreqCardiacaMedia() > 85){
           return 1.7;
       }
       else return 1.4;
   }
}