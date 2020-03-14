import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Veuillez entrer votre Automate");
        System.out.println("");
        AutomateSimple A =AutomateSimple.CreerAutomateSimple();
        A.afficheAutomate();
        System.out.println("Choisir ce que vous voulez faire sur votre automate:");
        System.out.println(" 1.Réduire l'automate");
        System.out.println(" 2.Faire le complement");
        System.out.println(" 3.Faire le miroir");
        System.out.println(" 4.Vérifier la reconnaissance de mots");
        System.out.println(" 5.Déterminiser l'automate");

        Scanner sc= new Scanner(System.in);
        int i=sc.nextInt();
        switch(i)
        {
            case 1:
                AutomateSimple AR= A.Reduire();
                AR.afficheAutomate();
                break;

            case 2:
                AutomateSimple AC = A.Complement();
                AC.afficheAutomate();
                break;

            case 3:
                AutomateSimple AMr=A.mirroir();
                AMr.afficheAutomate();
                break;

            case 4:
                System.out.println("Veuillez introduire le mot :");
                String word=sc.nextLine();
                if (A.reconnaissance_mot(word)) System.out.println("Le mot "+word+" appartient au langage généré par cet automate");
                else System.out.println("Le mot "+word+" n'appartient pas au langage généré par cet automate");
                break;

            case 5:
                AutomateDeterministe ADet=A.deterministe();
                ADet.afficheAutomate();
                break;

        }

    }





}
