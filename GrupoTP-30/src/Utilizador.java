import java.lang.StringBuilder;
import java.sql.Array;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;

public abstract class Utilizador implements Serializable{
    private String nome;
    private String username;
    private String password;
    private double peso;
    private String morada;
    private String email;
    private int freq_cardiaca_media;
    private double calorias;
    private ArrayList<Atividade> listaAtividades;
    private ArrayList<Atividade> atividadesFuturas;
    private ArrayList<PlanoTreino> planosFuturos;
    private ArrayList<PlanoTreino> listaPlanosTreino;
    private LocalDate data_nascimento;
    private String tipo;

    public Utilizador(){
        this.nome = "";
        this.username = "";
        this.password = "";
        this.morada = "";
        this.email = "";
        this.peso = 0.0;
        this.freq_cardiaca_media = 0;
        this.calorias = 0;
        this.listaAtividades = new ArrayList<>();
        this.listaPlanosTreino = new ArrayList<>();
        this.atividadesFuturas = new ArrayList<>();
        this.planosFuturos = new ArrayList<>();
        this.data_nascimento = null;
        this.tipo = "";
    }
    public Utilizador(String nome, String codigo_utilizador,String password ,String morada,double peso,String email, int freq_cardiaca_media,double calorias,ArrayList<Atividade> atvs,ArrayList<Atividade> atvFut, LocalDate data_nascimento, String tipo, ArrayList<PlanoTreino> planos, ArrayList<PlanoTreino> planosFuturos) {
        this.nome = nome;
        this.username = codigo_utilizador;
        this.password = password;
        this.peso = peso;
        this.email = email;
        this.freq_cardiaca_media = freq_cardiaca_media;
        this.calorias = calorias;
        this.listaAtividades = new ArrayList<>(atvs);
        this.listaPlanosTreino = new ArrayList<>(planos);
        this.atividadesFuturas = new ArrayList<>(atvFut);
        this.planosFuturos = new ArrayList<>(planosFuturos);
        this.data_nascimento = data_nascimento;
        this.tipo = tipo;
    }
    public Utilizador(Utilizador u){
        this.nome = u.getNome();
        this.username = u.getCodigoUtilizador();
        this.password = u.getPassword();
        this.morada = u.getMorada();
        this.email = u.getEmail();
        this.peso = u.getPeso();
        this.freq_cardiaca_media = u.getFreqCardiacaMedia();
        this.calorias = u.getCalorias();
        this.listaAtividades = u.getListaAtividades();
        this.atividadesFuturas = u.getAtividadesFuturas();
        this.listaPlanosTreino = u.getPlanosTreino();
        this.planosFuturos = u.getPlanosFuturos();
        this.data_nascimento = u.getDataNascimento();
        this.tipo = u.getTipo();
    }

    public ArrayList<PlanoTreino> getPlanosFuturos(){
        return new ArrayList<PlanoTreino>(this.planosFuturos);
    }
    public ArrayList<Atividade> getAtividadesFuturas(){
        return new ArrayList<Atividade>(this.atividadesFuturas);
    }
    public int getNumeroAtividadesRealizadas() {
        return listaAtividades.size();
    }
    public String getTipo(){
        return this.tipo;
    }
    public String getNome() {
        return nome;
    }
    public double getPeso(){
        return this.peso;
    }
    public ArrayList<PlanoTreino> getPlanosTreino(){
        return new ArrayList<PlanoTreino>(this.listaPlanosTreino);
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCodigoUtilizador() {
        return username;
    }
    public void setCodigoUtilizador(String codigo_utilizador) {
        this.username = codigo_utilizador;
    }
    public void setUserName(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public String getUserName(){
        return this.username;
    }
    public  String getMorada() {
        return this.morada;
    }
    public void setMorada(String morada) {
        this.morada = morada;
    }

   
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFreqCardiacaMedia() {
        return freq_cardiaca_media;
    }

    public void setFreqCardiacaMedia(int freq_cardiaca_media) {
        this.freq_cardiaca_media = freq_cardiaca_media;
    }
    public double getCalorias() {
        return this.calorias;
    }
    public void setCalorias(double calorias) {
        this.calorias += calorias; // calorias sao acumuladas
    }
    private boolean pesoValido(double peso) {
        return peso >= 3 && peso <= 300;
    }
    public boolean setPeso(double peso){
        if(pesoValido(peso)) {
            this.peso = peso;
            return true;
        } else {
            return false;
        }
    }

    public boolean hasHardActivity(LocalDate date){
        Iterator<Atividade> it = this.listaAtividades.iterator();
        while(it.hasNext()){
            Atividade atividade = it.next();
            if(atividade.getData().equals(date)){
                if(atividade.isHard()){
                    return true;
                }
            }
        }
        Iterator<Atividade> it2 = this.atividadesFuturas.iterator();
        while(it2.hasNext()){
            Atividade atividade = it2.next();
            if(atividade.getData().equals(date)){
                if(atividade.isHard()){
                    return true;
                }
            }
        }
        return false;
    }
    public ArrayList<Atividade> getListaAtividades() {
        return this.listaAtividades;
    }

    public void setListaAtividades(ArrayList<Atividade> listaAtividades) {
        this.listaAtividades = listaAtividades;
    }

    public LocalDate getDataNascimento() {
        return data_nascimento;
    }

    public void setDataNascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public void setPlanoTreino(ArrayList<PlanoTreino> plano){
        this.listaPlanosTreino = plano;
    }

    public void addPlanoUser(PlanoTreino plano){
        this.listaPlanosTreino.add(plano);
    }
    public void addActivityUser(Atividade atividade){
        this.listaAtividades.add(atividade);
    }

    public void addAtividadeFutura(Atividade atividade){
        if(this.atividadesFuturas == null) {
            this.atividadesFuturas = new ArrayList<>();
        }
        this.atividadesFuturas.add(atividade);
    }

    public void addPlanoFuturo(PlanoTreino plano){
        if(this.planosFuturos == null){
            this.planosFuturos = new ArrayList<>();
        }
        this.planosFuturos.add(plano);
    }

    public void processaAtividades(LocalDate data){
        Iterator<Atividade> it= this.atividadesFuturas.iterator();
        while(it.hasNext()){
            Atividade atividade = it.next();
            if(data.isAfter(atividade.getData())) {
                it.remove();
                this.listaAtividades.add(atividade);
                setCalorias(atividade.calculaCaloriasGastas(this));
            }
        }
    }
    public void processaPlanos(LocalDate data){
        Iterator<PlanoTreino> it= this.planosFuturos.iterator();
        while(it.hasNext()){
            PlanoTreino plano = it.next();
            if(data.isAfter(plano.getData())) {
                it.remove();
                this.listaPlanosTreino.add(plano);
                setCalorias(plano.calculaCalorias(this));
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resumo da conta:\n")
                .append("\nNome: ").append(this.nome)
                .append("\nUsername: ").append(this.username)
                .append("\nPeso: ").append(this.peso).append("kg")
                .append("\nMorada: ").append(this.morada)
                .append("\nEmail: '").append(this.email).append("'")
                .append("\nFrequência cardiaca média: ").append(this.freq_cardiaca_media)
                .append("\nCalorias totais gastas: ").append(this.calorias).append("kcal")
                .append("\nPreparação: ").append(this.tipo)
                .append("\nAtividades realizadas: ");

        for (Atividade atividade : listaAtividades) {
            sb.append("\n").append(atividade);
        }
        sb.append("\n----------------");

        sb.append("\nAtividades Futuras: ");
        if (atividadesFuturas != null && !atividadesFuturas.isEmpty()) {
            for (Atividade atividade : atividadesFuturas) {
                sb.append("\n").append(atividade);
            }
        } else {
            sb.append("Não existem atividades futuras\n");
        }
        sb.append("\n----------------");

        sb.append("\nPlanos de treino:");
        for (PlanoTreino plano : listaPlanosTreino) {
            sb.append("\n\tDate: ").append(plano.getData())
                    .append(", Repetições na semana: ").append(plano.getIteracoes())
                    .append(", Activities: ");
            for (Atividade atividade : plano.getAtividades()) {
                sb.append("\n").append(atividade);
            }
        }
        sb.append("\n----------------");

        sb.append("\nPlanos Futuros:");
        if (planosFuturos != null && !planosFuturos.isEmpty()) {
            for (PlanoTreino plano : planosFuturos) {
                sb.append("\n\tDate: ").append(plano.getData())
                        .append(", Repetições na semana: ").append(plano.getIteracoes())
                        .append(", Atividades: ");

                for (Atividade atividade : plano.getAtividades()) {
                    sb.append("\n\t\t").append(atividade);
                }
            }
        } else {
            sb.append("Não existem planos futuros\n");
        }
        sb.append("\n------------------");

        return sb.toString();
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador u = (Utilizador) o;
        return this.nome.equals(u.getNome())
                && this.username.equals(u.getCodigoUtilizador())
                && this.morada.equals(u.getMorada())
                && this.email.equals(u.getEmail())
                && this.freq_cardiaca_media == u.getFreqCardiacaMedia()
                && this.calorias == u.getCalorias()
                && this.listaAtividades.equals(u.getListaAtividades());
    }


    public abstract Utilizador clone();
    public abstract double fatorMultiplicativo();

}