import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;


public class McFit implements Serializable {
    private Map<String, Utilizador> userMap;
    private Map<String,Atividade> ListaAtividades; // Lista as atividades disponiveis
    private LocalDate dataAtual;

    public McFit() {
        this.userMap = new HashMap<>();
        this.ListaAtividades = new HashMap<>();
        this.dataAtual = LocalDate.now();
    }

    public McFit(Map<String, Utilizador> userMap, Map<String,Atividade> listaAtividades, ArrayList<PlanoTreino> planos, LocalDate data) {
        this.userMap = userMap;
        this.ListaAtividades = listaAtividades;
        this.dataAtual = data;
    }

    public McFit(McFit app) {
        this.userMap = new HashMap<>(app.getUserMap());
        this.ListaAtividades = new HashMap<>(app.getListaAtividades());
        this.dataAtual = LocalDate.of(app.getData().getYear(), app.getData().getMonth(), app.getData().getDayOfMonth());
    }


    public McFit clone() {
        return new McFit(this);
    }

    public String toString(){
        final StringBuffer sb = new StringBuffer();
        sb.append("Users: ").append(userMap.toString()).append('\n');
        sb.append("Atividades: ").append(ListaAtividades.toString()).append("\n");
        return sb.toString();
    }

    public LocalDate getData(){
        return this.dataAtual;
    }
    public Map<String, Utilizador> getUserMap() {
        return userMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    public Map<String, Atividade> getListaAtividades() {
        return ListaAtividades.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    public ArrayList<Atividade> getAtividadePorTipo(String tipo){
        ArrayList<Atividade> atividades = new ArrayList<>();
        for (Map.Entry<String, Atividade> entry : ListaAtividades.entrySet()) {
            Atividade atv = entry.getValue();
            if (atv.getTipo().equals(tipo)) {
                atividades.add(atv);
            }
        }
        return atividades;
    }
    public void setUserMap(Map<String, Utilizador> userMap) {
        this.userMap =  userMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }
    public void setAtividades(Map<String,Atividade> atvMap) {
        this.ListaAtividades = atvMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(),v->v.getValue().clone()));
    }
    /* Parte dos utilizadores (métodos especificos excluindo getters/setters) */

    public Utilizador getUserByUsername(String username){
        if (this.userMap.containsKey(username))
            return this.userMap.get(username).clone();
        return null;
    }
    public boolean addUser (Utilizador user) {
        if(!existeUserName(user.getUserName())){
            this.userMap.put(user.getUserName(), user.clone());
            return true;
        }
        return false;
    }

    private boolean existeUserName(String userName){
        return this.userMap.containsKey(userName);
    }
    public boolean existsActivity(String nome){return this.ListaAtividades.containsKey(nome);}
    public boolean loginUser(String username, String password){
        if(this.existeUserName(username)){
            Utilizador testUser = getUserByUsername(username);
            return (password.equals(testUser.getPassword()));
        }
        return false;
    }

    /* Parte das Atividades (métodos especificos excluindo getters/setters) */
    public boolean addAtividade (Atividade a) {
        if (!this.ListaAtividades.containsValue(a)) {
            this.ListaAtividades.put(a.getNome(), a.clone());
            return true;
        } else {
            return false;
        }
    }
    public Atividade getAtividadeByName (String name){
        if(this.ListaAtividades.containsKey(name)) {
            return this.ListaAtividades.get(name).clone();

        } else {
            return null;
        }
    }
    public String tipoAtividadeMaisRealizada(String username) {
        Map<String,Integer> contagemPorTipo = new HashMap<>();

        for(Atividade atividade:(getUserByUsername(username).getListaAtividades())) {
            String nome = atividade.getNome();
            contagemPorTipo.put(nome, contagemPorTipo.getOrDefault(nome,0)+1);
        }

        int max = 0;
        String tipoMaisRelizado = null;
        for(Map.Entry<String,Integer> entry : contagemPorTipo.entrySet()) {
            if(entry.getValue() > max) {
                max = entry.getValue();
                tipoMaisRelizado = entry.getKey();
            }
        }
        return tipoMaisRelizado;
    }

    public Utilizador getUtilizadorComMaisCalorias() {
        Utilizador utilizadorComMaisCalorias = null;
        double maxCalorias = Double.MIN_VALUE;

        for (Map.Entry<String, Utilizador> entry : userMap.entrySet()) {
            Utilizador utilizador = entry.getValue();
            if (utilizador.getCalorias() > maxCalorias) {
                maxCalorias = utilizador.getCalorias();
                utilizadorComMaisCalorias = utilizador;
            }
        }
        return utilizadorComMaisCalorias;
    }

    public Utilizador getUtilizadorComMaisAtividades() {
        Utilizador utilizadorComMaisAtividades = null;
        int maxAtividades = Integer.MIN_VALUE;

        for (Map.Entry<String, Utilizador> entry : userMap.entrySet()) {
            Utilizador utilizador = entry.getValue();
            if (utilizador.getNumeroAtividadesRealizadas() > maxAtividades) {
                maxAtividades = utilizador.getNumeroAtividadesRealizadas();
                utilizadorComMaisAtividades = utilizador;
            }
        }

        return utilizadorComMaisAtividades;
    }
    public double getDistanciaPercorrida(String username) {
        Utilizador utilizador = userMap.get(username);
        if (utilizador != null) {
            double distanciaTotal = 0.0;
            for (Atividade atividade : utilizador.getListaAtividades()) {
                if (atividade instanceof Ciclismo) {
                    distanciaTotal += ((Ciclismo) atividade).getDistancia();
                }
                if(atividade instanceof Corrida){
                    distanciaTotal += ((Corrida) atividade).getDistancia();
                }
            }
            return distanciaTotal;
        }
        return 0.0; // Retornar 0 se o utilizador não for encontrado
    }
    public double getAltimetriaTotal(String username) {
        Utilizador utilizador = getUserByUsername(username);
        double altimetriaTotal = 0.0;
        for (Atividade atividade : utilizador.getListaAtividades()) {
            if (atividade instanceof Ciclismo) {
                altimetriaTotal += ((Ciclismo) atividade).getAltimetria();
            }
        }
        return altimetriaTotal;
    }
    public PlanoTreino getPlanoMaisExigente(String username) {
        Utilizador utilizador = userMap.get(username);
        if (utilizador == null) {
            // Usuário não encontrado
            return null;
        }

        PlanoTreino planoMaisExigente = null;
        double maxCalorias = Double.MIN_VALUE;

        for (PlanoTreino plano : utilizador.getPlanosTreino()) {
            double caloriasPlano = 0.0;
            for (Atividade atividade : plano.getAtividades()) {
                caloriasPlano += atividade.calculaCaloriasGastas(utilizador);
            }
            if (caloriasPlano > maxCalorias) {
                maxCalorias = caloriasPlano;
                planoMaisExigente = plano;
            }
        }
        return planoMaisExigente;
    }

    public String availableActivities(){
        StringBuilder sb = new StringBuilder();
        for(String atividade: this.ListaAtividades.keySet()){
            sb.append("-> ").append(atividade).append("\n");
        }
        return (sb.toString());
    }

    public void createStandardActivities(){
        LocalDate data = LocalDate.now();
        Atividade btt = new Ciclismo("BTT", "Distancia/Altimetria", 0, data, 0.0, 0.0);
        Atividade corrida = new Corrida("Corrida", "Distancia", 0, data, 0.0,0);
        Atividade levantamentoPesos = new LevantamentoPesos("LevantamentoPesos", "Repeticao/Peso", 0, data, 0, 0, 0);
        Atividade pilates = new Pilates("Pilates", "Repeticao", 0, data, 0, 0);

        addAtividade(btt);
        addAtividade(corrida);
        addAtividade(levantamentoPesos);
        addAtividade(pilates);
    }

    public void logActivity(Atividade atividade ,String username){
        String tipo = getUserByUsername(username).getTipo();

        Utilizador user = null;
        switch(tipo){
            case "ocasional": user = new UtilizadorOcasional(getUserByUsername(username));break;
            case "amador": user = new UtilizadorAmador(getUserByUsername(username));break;
            case "profissional": user = new UtilizadorProfissional(getUserByUsername(username));break;
        }

        if(this.dataAtual.isBefore(atividade.getData())){
            user.addAtividadeFutura(atividade);
        } else {
            user.setCalorias(atividade.calculaCaloriasGastas(user));
            user.addActivityUser(atividade);
        }
        this.userMap.replace(username,user); // replace the previous user
    }

    /* Planos Treino*/
    public void geradorPlanos(McFit app,String username, String tipo, int iteracoes, LocalDate data, int num_atividades){
        PlanoTreino plano = new PlanoTreino();
        Utilizador user = getUserByUsername(username);

        plano = plano.gerarExerciciosAleatorios(app,user, tipo, num_atividades, data);
        plano.setIteracoes(iteracoes);
        plano.setData(data);

        if(this.dataAtual.isBefore(data)){
            user.addPlanoFuturo(plano);
        } else {
            user.addPlanoUser(plano);
            user.setCalorias(plano.calculaCalorias(user));
        }
        this.userMap.replace(username, user);
    }

    public void criadorPlanos(McFit app, String username, String tipo, int iteracoes, int num_atividades, double caloriasMinimas, LocalDate data) {
    Random random = new Random();

    Utilizador user = getUserByUsername(username);
    PlanoTreino plano = new PlanoTreino();
    plano.setIteracoes(iteracoes);
    ArrayList<Atividade> atividades = app.getAtividadePorTipo(tipo);

    while (plano.calculaCalorias(user) < caloriasMinimas && plano.getAtividades().size() < num_atividades) {
        Atividade novaAtividade = null;
        switch (tipo) {
            case "Distancia":
                novaAtividade = plano.gerarAtividadeAleatoriaCorrida(atividades, random, data);
                break;
            case "Distancia/Altimetria":
                novaAtividade = plano.gerarAtividadeAleatoriaCiclismo(atividades, random, data);
                break;
            case "Repeticao":
                novaAtividade = plano.gerarAtividadeAleatoriaPilates(atividades, random, data);
                break;
            case "Repeticao/Pesos":
                novaAtividade = plano.gerarAtividadeAleatoriaLevantamentoPesos(atividades, random, data);
                break;
        }
        if(novaAtividade.isHard() && plano.getTamanho() != 0) {
            if(!plano.hasHard()){
                if(!user.hasHardActivity(data)){
                    plano.addAtividade(novaAtividade);
                } else {
                    continue;
                }
            } else{
                continue;
            }
        } else {
            plano.addAtividade(novaAtividade);
        }
    }
    plano.setData(data);

    if (this.dataAtual.isBefore(data)) {
        user.addPlanoFuturo(plano);
    } else {
        user.addPlanoUser(plano);
        user.setCalorias(plano.calculaCalorias(user));
    }
    this.userMap.replace(username, user);
}

    /* data */
    
    public void avancarDia(McFit app){
        if(this.dataAtual != null){
            this.dataAtual = this.dataAtual.plusDays(1);
            for(Utilizador user : app.userMap.values()){
                user.processaAtividades(this.dataAtual);
                user.processaPlanos(this.dataAtual);
            }
        }
    }
    public void avancarParaData(McFit app,LocalDate data){
        if(this.dataAtual.isBefore(data)){
            this.dataAtual = data;
            for(Utilizador user : app.userMap.values()) {
                user.processaAtividades(this.dataAtual);
                user.processaPlanos(this.dataAtual);
            }
        }
    }

    public void guardar() {
        try {
            FileOutputStream fileOut = new FileOutputStream("../Backup.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.flush();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("Saving error");
            i.printStackTrace();
        }
    }

    public McFit carregar() {
        McFit app = new McFit();
        try {
            FileInputStream fileIn = new FileInputStream("../Backup.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            app = (McFit) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Loading error");
            e.printStackTrace();
        }
        return app;
    }

}
