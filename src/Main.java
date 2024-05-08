import java.util.Scanner;
import java.io.File;
public class Main{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Menu m = new Menu();
        boolean in_program = true;


        while(in_program){
            m.clearTerminal();
            m.printLogo();
            m.printStartMenu();
            int input = sc.nextInt();

            switch(input){
                case 1: m.printSignInMenu();break;
                case 2:{
                    File file = new File("../Backup.ser"); // nao faz sentido fazer login se não houver ficheiro

                    if (file.exists()) {
                        String username = m.printLogInMenu();
                        if (username != null) {
                            while (m.printAppMainMenu(username)) ;
                        }
                        break;
                    }
                    break;
                }
                case 3: in_program = false;break;
                default: System.out.println("Action not possible");
            }
        }
    }
}