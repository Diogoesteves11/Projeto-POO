import java.time.LocalDate;
import java.util.ArrayList;
import java.io.*;


public class UtilizadorAmador extends Utilizador implements Serializable{

   public UtilizadorAmador(){
        super();
    }
    public UtilizadorAmador(String nome, String username, String password,double peso ,String morada,String email, int freqCardiaca,double calorias, ArrayList<Atividade> atividades,LocalDate data ,String tipo,ArrayList<PlanoTreino> planos, ArrayList<Atividade> atvFut, ArrayList<PlanoTreino> plFut) {
        super(nome,username,password,morada,peso, email, freqCardiaca,calorias, atividades,atvFut,data,tipo,planos,plFut);
    }

    public UtilizadorAmador(Utilizador outroAmador){
        super(outroAmador);
    }

    public Utilizador clone(){
        return new UtilizadorAmador(this);
    }
    public String toString() {
        return super.toString();
    }
    
    public double fatorMultiplicativo() {
        if(this.getFreqCardiacaMedia() > 85){
            return 1.5;
        }
        else return 1.2;
    }
}