import java.util.Scanner;
import java.io.File;
import java.io.Console;
import java.time.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Menu{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private Scanner sc;
    public Menu() {
        this.sc = new Scanner(System.in);
    }

    public void clearTerminal(){
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else {
            System.out.print("\u001b[2J\u001b[H");
            System.out.flush();
        }
    }
    public void printLogo(){
        System.out.println(ANSI_YELLOW+"                                        ");
        System.out.println(",--.   ,--.      ,------.,--.  ,--.     ");
        System.out.println("|   `.'   | ,---.|  .---'`--',-'  '-.   ");
        System.out.println("|  |'.'|  || .--'|  `--, ,--.'-.  .-'   ");
        System.out.println(ANSI_WHITE+"|  |   |  |\\ `--.|  |`   |  |  |  |     ");
        System.out.println("`--'   `--' `---'`--'    `--'  `--'     ");
        System.out.println("                                        "+ANSI_RESET);
    }

    public void printStartMenu(){
        System.out.println("------------- Welcome -------------");
        System.out.println("\n1. Sign-in");
        System.out.println("\n2. Log-in");
        System.out.println("\n3. Sair");
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);

    }
    public boolean dataNascimentoValida(LocalDate data) {
        return data.isBefore(LocalDate.now());
    }

    public boolean freqCardiacaMediaValida(int freqCardiacaMedia) {
        return freqCardiacaMedia > 0 && freqCardiacaMedia < 210; 
    }
    public boolean tipoValido(String tipo) {
        return tipo.equals("amador") || tipo.equals("ocasional") || tipo.equals("profissional");
    }
    public void printSignInMenu(){
        clearTerminal();
        String tipo = new String();
        while(!tipoValido(tipo)) {
        
            System.out.println("\nPreparação (ocasional, amador, profissional): ");;
            try{
    
            tipo = sc.next();
            } catch (IllegalArgumentException e) {
                System.out.println("Introduz um tipo válido:\n");
                
            }
        }
        
        Utilizador newUser = null;

        switch(tipo){
            case "ocasional": newUser = new UtilizadorOcasional();break;
            case "amador":  newUser = new UtilizadorAmador();break;
            case "profissional": newUser = new UtilizadorProfissional();break;
            default: System.out.println("Tipo inválido");
        }

        System.out.println("\nUsername: ");
        String username = sc.next();
        sc.nextLine();
        System.out.println("\nNome: ");
        String name = sc.nextLine();
        System.out.println("\nPassword: ");
        String password = sc.nextLine();
        System.out.println("\nMorada: ");
        String morada = sc.nextLine();
        int freq = 0;
        boolean freqCardiacaValida = false;
        
        while (!freqCardiacaValida) {
            System.out.println("\nFrequência cardíaca média: ");
            try {
                freq = sc.nextInt();
                if (freqCardiacaMediaValida(freq)) {
                    freqCardiacaValida = true;
                } else {
                    System.out.println("Frequência cardíaca média inválida. Por favor, introduza um valor inteiro válido");
                }
            } catch (Exception e) {
                System.out.println("Frequência cardíaca média inválida. Por favor, introduza um valor inteiro válido.");
                sc.nextLine(); 
            }
        }
        double peso = 0.0;
        while(!newUser.setPeso(peso)) {
        
        System.out.println("\nPeso: ");
        try{

        peso = sc.nextDouble();
        } catch (IllegalArgumentException e) {
            continue;
            }
        }
        
        boolean dataValida = false;
        LocalDate data = null;

        while (!dataValida) {
            System.out.println("\nData nascimento (YYYY-MM-DD): ");
            try {
                data = LocalDate.parse(sc.next());
                if (dataNascimentoValida(data)) {
                    dataValida = true;
                } 
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Por favor, introduza uma data no formato YYYY-MM-DD.");
            }
        }
    
        
        System.out.println("\nEmail: ");
        String email = sc.next();

        while (!isValidEmail(email)) {
            System.out.println("Email inválido. Por favor, insira um email válido: ");
            email = sc.next();
        }

        File file = new File("../Backup.ser");
        McFit app = null;

        if (file.exists()) {
            McFit fileinfo = new McFit();
            fileinfo = fileinfo.carregar();
            app = new McFit(fileinfo);
        } else {
            app = new McFit();
            app.createStandardActivities(); // inicia as atividades standard. É necessário ser aqui para que o map das atividades nao fique nulo no primeiro registo
            
        }

        newUser.setPassword(password);
        newUser.setUserName(username);
        newUser.setNome(name);
        newUser.setMorada(morada);
        newUser.setFreqCardiacaMedia(freq);
        newUser.setDataNascimento(data);
        newUser.setTipo(tipo);
        newUser.setEmail(email);

        while(!app.addUser(newUser)){
            System.out.println("\nUsername já existe!");
            System.out.println("\nUsername: ");
            String newUsername = sc.next();
            newUser.setUserName(newUsername);
        }
        app.guardar();
    }
    public String printLogInMenu(){
        clearTerminal();
        System.out.println("------------- Login -------------");
        System.out.println("\nUsername: ");
        String username = sc.next();
        System.out.println("\nPassword: ");
        String password = sc.next();

        McFit app = new McFit();
        app = app.carregar();

        if(app.loginUser(username,password)) return username;
        return null;
    }

    public boolean printAppMainMenu(String username){
        clearTerminal();
        McFit app = new McFit();
        app = app.carregar();

        Utilizador user = app.getUserByUsername(username);

        System.out.println("------------- Main Menu -------------");
        System.out.println("\nBem-vindo, " + user.getUserName() + "\n");
        System.out.println("\nData: " + app.getData());
        System.out.println("\n1. Conta"); // user info menu
        System.out.println("\n2. Atividades"); // available activities
        System.out.println("\n3. Planos de Treino"); // available training plans of the user
        System.out.println("\n4. Estatisticas"); // estatisticas
        System.out.println("\n5. Avançar dia"); // avança um dia à data do sistema
        System.out.println("\n6. Avançar para data");
        System.out.println("\n7. Guardar e sair"); // Creates a app backup and returns to the main menu

        int input = sc.nextInt();
        switch(input){
            case 1: while(printUserResume(app,username));break;
            case 2: while(printActivitiesMenu(app,username));break;
            case 3: while(printMenuPlanosTreino(app,username));break;
            case 4: while(printStatsMenu(app,username));break;
            case 5: app.avancarDia(app);app.guardar();break;
            case 6: {
                System.out.println("\nIntroduza data (YYYY-MM-DD): ");
                LocalDate newDate = LocalDate.parse(sc.next());
                app.avancarParaData(app,newDate);
                app.guardar();
                break;
            }
            case 7: app.guardar();return false;
            default: System.out.println("Ação inválida");
        }
        return true;
    }

    public boolean printUserResume(McFit app, String username){
        clearTerminal();
        Utilizador user = app.getUserByUsername(username);
        System.out.println("\n------------- Utilizador -------------");
        System.out.println("\n" + user.toString());

        System.out.println("\nPressione 'q' para voltar");

        String input = sc.next();
        if(input.equals("q")) return false;
        return true;
    }

    public boolean printActivitiesMenu(McFit app, String username){
        StringBuilder sb = new StringBuilder();
        sb.append("------------- Atividades -------------\n")
                .append("\n1. Registar atividade ").append("\n")
                .append("\n2. Listar atividades existentes ").append("\n")
                .append("\n3. Criar atividade personalizada ").append("\n")
                .append("\n\nPressione 'q' para voltar");
        System.out.println(sb.toString());
        String input = sc.next();
        switch(input){
            case "1": logActivity(app, username);break;
            case "2": while(ListAvailableActivities(app));break;
            case "3": printCriadorAtividade(app,username);break;
            case "q": app.guardar(); return false;
            default: System.out.println("Ação inválida");
        }
        return true;
    }

    public void printCriadorAtividade(McFit app, String username){
        System.out.println("------------- Criardor Atividade -------------");
        boolean correct_type = false;
        String tipo = null;

        while(!correct_type){
            System.out.println("Escolha tipo da atividade (Distancia, Distancia/Altimetria, Repeticao, Repeticao/Pesos): ");
            tipo = sc.next();
            if(tipo.equals("Distancia") || tipo.equals("Distancia/Altimetria") || tipo.equals("Repeticao") || tipo.equals("Repeticao/Pesos")) correct_type = true;
        }
        System.out.println("Nome");
        String nome = sc.next();

        int duracao = 0;
        LocalDate data = LocalDate.now();

        switch(tipo){
            case "Distancia/Altimetria": {
                Ciclismo newActivity = new Ciclismo();
                double distancia = 0.0;
                double altimetria = 0.0;

                newActivity.setNome(nome);
                newActivity.setTipo(tipo);
                newActivity.setTempoGasto(duracao);
                newActivity.setData(data);
                newActivity.setDistanciaCiclismo(distancia);
                newActivity.setAltimetriaBTT(altimetria);

                app.addAtividade(newActivity);
                break;
            }
            case "Distancia": {
                Corrida newActivity = new Corrida();
                double distancia = 0.0;
                int ritmo = 0;

                newActivity.setNome(nome);
                newActivity.setTipo(tipo);
                newActivity.setTempoGasto(duracao);
                newActivity.setData(data);
                newActivity.setDistancia(distancia);
                newActivity.setRitmoMedio(ritmo);

                app.addAtividade(newActivity);
                break;
            }
            case "Repeticao/Pesos": {
                LevantamentoPesos newActivity = new LevantamentoPesos();
                int carga = 0;
                int rep = 0;
                int series = 0;

                newActivity.setNome(nome);
                newActivity.setTipo(tipo);
                newActivity.setTempoGasto(duracao);
                newActivity.setData(data);
                newActivity.setCarga(carga);
                newActivity.setNumeroRepeticoes(rep);
                newActivity.setSeries(series);

                app.addAtividade(newActivity);
                break;
            }
            case "Repeticao": {
                Pilates newActivity = new Pilates();
                int rep = 0;
                int series = 0;

                newActivity.setNome(nome);
                newActivity.setTipo(tipo);
                newActivity.setTempoGasto(duracao);
                newActivity.setData(data);
                newActivity.setNumeroRepeticoes(rep);
                newActivity.setSeries(series);

                app.addAtividade(newActivity);
                break;
            }
        }
        app.guardar();
    }

    public boolean ListAvailableActivities(McFit app){
        clearTerminal();
        StringBuilder sb = new StringBuilder();
        sb.append("------------- Atividades Disponíveis -------------\n")
            .append(app.availableActivities());
        System.out.println(sb.toString());
        System.out.println("\nPressione 'q' para voltar");

        String input = sc.next();
        if(input.equals("q")) return false;
        return true;
    }
    public void logActivity(McFit app, String username){
        boolean correct_type = false;
        String tipo = null;

        while(!correct_type){
            System.out.println("Escolha tipo da atividade (Distancia, Distancia/Altimetria, Repeticao, Repeticao/Pesos): ");
            tipo = sc.next();
            if(tipo.equals("Distancia") || tipo.equals("Distancia/Altimetria") || tipo.equals("Repeticao") || tipo.equals("Repeticao/Pesos")) correct_type = true;
        }

        boolean correct_activity = false;
        String nome = null;
        while(!correct_activity){
            System.out.println("Nome : ");
            nome = sc.next();
            if(app.existsActivity(nome)){
                correct_activity = true;
            } else {
                System.out.println("Atividade não suportada");
            }
        }
        System.out.println("Duração: ");
        int duracao = sc.nextInt();
        System.out.println("Data: (YYYY-MM-DD): ");
        LocalDate data = LocalDate.parse(sc.next());


        switch(tipo){
            case "Distancia/Altimetria": {
                Ciclismo newActivity = new Ciclismo();
                System.out.println("Distancia Percorrida (km): ");
                double distancia = sc.nextDouble();
                System.out.println("Altimetria (m): ");
                double altimetria = sc.nextDouble();

                newActivity.setNome(nome);
                newActivity.setTipo(tipo);
                newActivity.setTempoGasto(duracao);
                newActivity.setData(data);
                newActivity.setDistanciaCiclismo(distancia);
                newActivity.setAltimetriaBTT(altimetria);

                app.logActivity(newActivity,username);
                break;
            }
            case "Distancia": {
                Corrida newActivity = new Corrida();
                System.out.println("Distancia Percorrida (km): ");
                double distancia = sc.nextDouble();
                System.out.println("Ritmo medio (min/km): ");
                int ritmo = sc.nextInt();

                newActivity.setNome(nome);
                newActivity.setTipo(tipo);
                newActivity.setTempoGasto(duracao);
                newActivity.setData(data);
                newActivity.setDistancia(distancia);
                newActivity.setRitmoMedio(ritmo);

                app.logActivity(newActivity,username);
                break;
            }
            case "Repeticao/Pesos": {
                LevantamentoPesos newActivity = new LevantamentoPesos();
                System.out.println("Carga (kg): ");
                int carga = sc.nextInt();
                System.out.println("Repetições: ");
                int rep = sc.nextInt();
                System.out.println("Series: ");
                int series = sc.nextInt();

                newActivity.setNome(nome);
                newActivity.setTipo(tipo);
                newActivity.setTempoGasto(duracao);
                newActivity.setData(data);
                newActivity.setCarga(carga);
                newActivity.setNumeroRepeticoes(rep);
                newActivity.setSeries(series);

                app.logActivity(newActivity,username);
                break;
            }
            case "Repeticao": {
                Pilates newActivity = new Pilates();
                System.out.println("Repetições: ");
                int rep = sc.nextInt();
                System.out.println("Series: ");
                int series = sc.nextInt();

                newActivity.setNome(nome);
                newActivity.setTipo(tipo);
                newActivity.setTempoGasto(duracao);
                newActivity.setData(data);
                newActivity.setNumeroRepeticoes(rep);
                newActivity.setSeries(series);

                app.logActivity(newActivity,username);
                break;
            }
        }
        app.guardar();
    }

    public boolean printStatsMenu(McFit app, String username){
        clearTerminal();
        Utilizador recordCalorias = app.getUtilizadorComMaisCalorias();
        Utilizador recordAtividades = app.getUtilizadorComMaisAtividades();

        System.out.println("------------- Estatisticas -------------");

        if (recordCalorias != null) {
            System.out.println("\nRecord de calorias: " + recordCalorias.getUserName() + "(" + recordCalorias.getCalorias() + "kcal)");
        } else {
            System.out.println("\nRecord de calorias: Nenhum utilizador registado");
        }

        if (recordAtividades != null) {
            System.out.println("\nRecord de atividades realizadas: " + recordAtividades.getUserName() + "(" + recordAtividades.getNumeroAtividadesRealizadas() + " atividade(s))");
        } else {
            System.out.println("\nRecord de atividades realizadas: Nenhum utilizador registado");
        }

        System.out.println("\nAtividade mais realizada: " + app.tipoAtividadeMaisRealizada(username));

        System.out.println("\nDistância total percorrida: " + app.getDistanciaPercorrida(username) + "km");
        System.out.println("\nAltimetria total: " + app.getAltimetriaTotal(username) + "m");

        PlanoTreino planoMaisExigente = app.getPlanoMaisExigente(username);
        if (planoMaisExigente != null) {
            System.out.println("\nPlano de treino mais exigente: " + planoMaisExigente.toString());
        } else {
            System.out.println("\nPlano de treino mais exigente: Nenhum plano de treino realizado pelo utilizador");
        }

        System.out.println("\nPressione 'q' para voltar");
        String input = sc.next();
        return !input.equals("q");
    }

    public boolean printMenuPlanosTreino(McFit app, String username){
        clearTerminal();
        System.out.println("------------- Planos Treino -------------");
        System.out.println("\n1.Gerar um novo plano de treino");
        System.out.println("\n2.Criar um plano de treino customizado");
        System.out.println("\n3.Pressione 'q' para voltar");

        String input = sc.next();
        switch(input){
            case "1":printGeradorPlanosTreino(app, username);break;
            case "2":printCriadorPlanoTreino(app,username);break;
            case "q":app.guardar();return false;
        }
        return true;
    }

    private void printGeradorPlanosTreino(McFit app, String username){
        clearTerminal();
        System.out.println("------------- Gerador de Planos -------------");
        System.out.println("Introduza data de realização (YYYY-MM-DD): ");
        LocalDate data = LocalDate.parse(sc.next());
        System.out.println("Introduza número de vezes na semana que deseja fazer o plano: ");
        int iteracoes = sc.nextInt();
        boolean correct_type = false;
        String tipo = new String();
        while(!correct_type){
            System.out.println("Introduza tipo do plano (Distancia, Distancia/Altimetria, Repeticao, Repeticao/Pesos): ");
            tipo = sc.next();
            if(tipo.equals("Distancia") || tipo.equals("Distancia/Altimetria") || tipo.equals("Repeticao") || tipo.equals("Repeticao/Pesos")) correct_type = true;
        }
        System.out.println("Introduza numero de atividades que deseja: ");
        int num_atividades = sc.nextInt();

        for(int i = 0; i < iteracoes; i++){
            app.geradorPlanos(app,username,tipo,iteracoes,data,num_atividades);
            data = data.plusDays(2);
        }

        app.guardar();
    }

    private void printCriadorPlanoTreino(McFit app, String username){
        clearTerminal();
        System.out.println("\n------------- Criador de Planos inteligente -------------");
        System.out.println("Introduza data de realização (YYYY-MM-DD): ");
        LocalDate data = LocalDate.parse(sc.next());
        System.out.println("Introduza máximo de atividades diárias (máximo 3): ");
        int num_atividades = sc.nextInt();
        System.out.println("Introduza minimo de calorias que deseja gastar: ");
        double calorias = sc.nextDouble();
        boolean correct_type = false;
        String tipo = null;
        while(!correct_type){
            System.out.println("Introduza tipo do plano (Distancia, Distancia/Altimetria, Repeticao, Repeticao/Pesos): ");
            tipo = sc.next();
            if(tipo.equals("Distancia") || tipo.equals("Distancia/Altimetria") || tipo.equals("Repeticao") || tipo.equals("Repeticao/Pesos")) correct_type = true;
        }
        System.out.println("Introduza nº de vezes na semana do plano: ");
        int iteracoes = sc.nextInt();

        for(int i = 0; i < iteracoes; i++){
            app.criadorPlanos(app,username,tipo,iteracoes, num_atividades,calorias,data);
            data = data.plusDays(2);
        }
        app.guardar();
    }
    
}