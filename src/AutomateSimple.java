import java.util.ArrayList;
import java.util.Scanner;

public class AutomateSimple {

    private ArrayList<String> alphabet;
    private Sommet S0;
    private ArrayList<Sommet> etats;
    private ArrayList<Sommet> finaux;
    private ArrayList<Transition> transitions;

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    public Sommet getS0(){
        return this.S0;
    }
    public ArrayList<Sommet> getEtats(){
        return this.etats;
    }

    public ArrayList<Sommet> getFinaux() {
        return finaux;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setS0(Sommet s0) {
        S0 = s0;
    }

    public Sommet getSommetById(String id){
        int i=0;
        while ((i<this.getEtats().size())&&(!this.getEtats().get(i).getid().equals(id)) ){
            i++;
        }
        if (i<this.getEtats().size())return this.getEtats().get(i);
        else return null;
    }
    public AutomateSimple mirroir (AutomateSimple automate){
        Sommet S0=automate.getS0();
        S0.setEtat(EEtat.FINAL);
        ArrayList <Sommet> sommets=automate.getFinaux();
        for (Sommet s: sommets) {
            s.setEtat(EEtat.INITIAL);
        }
        /*rajouter un etat initial*/
        Sommet initial=new Sommet("newS0",EEtat.INITIAL);
        for (Sommet s:sommets) {
            Transition transition=new Transition(initial,s,"&",false);
            initial.getTrans_sortantes().add(transition);
        }
        automate.setS0(initial);
        ArrayList<Transition> transitions = automate.getTransitions();
        for (Transition t : transitions){
            Sommet tmp = t.getSrc();
            t.setSrc(t.getDest());
            t.setDest(tmp);
        }
        return automate;
    }
    public boolean reconnaissance_mot(String mot){
        int i=0; boolean stop=false; Sommet Si=new Sommet();boolean result =false;
        ArrayList<Transition> transitions_Si=this.S0.getTrans_sortantes();
        while (i<mot.length()){
            if (i==0) Si=S0;
            else {
            transitions_Si=Si.getTrans_sortantes();}
            int j=0;
            if (transitions_Si.size()!=0){
                while (j<transitions_Si.size()&&!stop){
                    if (transitions_Si.get(j).getTrans().equals(mot.charAt(i))) {stop=true; Si=transitions_Si.get(j).getDest();}
                    j++;
                }}
            i++;
            if (this.finaux.contains(Si)) result =true ;
            else result=false;
        }
        return result;
    }
    public static AutomateSimple CreerAutomateSimple()
    {
        AutomateSimple automate=new AutomateSimple();
        automate.S0=new Sommet();
        automate.alphabet= new ArrayList<String>();
        automate.etats= new ArrayList<Sommet>();
        automate.finaux= new ArrayList<Sommet>();
        automate.transitions= new ArrayList<Transition>();

        Scanner scanner=new Scanner(System.in);
        System.out.println("Introduire l'ensemble X des alphabets : ");
        String alpha=scanner.nextLine();
        String [] parts=alpha.split(" ");
        ArrayList<String> alphabet=new ArrayList<String>();
        for ( int i=0;i<parts.length;i++) alphabet.add(parts[i]);
        automate.setAlphabet(alphabet);
        System.out.println("Introduire l'ensemble des etats : ");
        System.out.print("Nombre d'etats : \n");
        int nb_etat=scanner.nextInt();
        for (int i=1;i<=nb_etat;i++){
            System.out.print("  -> id-sommet :\n");
            String noun=scanner.next();
            System.out.print("  -> etat du sommet (1:initial | 2:simple | 3:final) : choisir un numero ");
            int choice =scanner.nextInt();
            switch (choice){
                case 1 :
                    Sommet si=new Sommet(noun,EEtat.INITIAL);
                    automate.getEtats().add(si);
                    automate.setS0(si);
                    break;
                case 2 :
                    Sommet ss=new Sommet(noun,EEtat.SIMPLE);
                    automate.getEtats().add(ss);
                    break;
                case 3:
                    Sommet sf=new Sommet(noun,EEtat.FINAL);
                    automate.getEtats().add(sf);
                    automate.getFinaux().add(sf);
                    break;
            }
        }
        System.out.println("Saisir l'ensemble des instructions : \n");
        int choix=1; int num=1;
        while (choix!=0){
        System.out.print("Ensemble des sommets : ");
        for (Sommet s :automate.getEtats()) System.out.print(s.getid()+" ");
            System.out.println("\n*********Instruction "+num+"*********");
            num++;
        System.out.print("\nSommet source :");
        String src=scanner.next();
        System.out.print("Sommet destination : ");
        String dst=scanner.next();
        System.out.print("la transition (une lettre de l'alphabet) :");
        String trans=scanner.next();
        boolean boucle;
        if (src.equals(dst)) boucle=true;
        else boucle=false;
        Sommet source= automate.getSommetById(src);
        Sommet destination = automate.getSommetById(dst);
        Transition transition=new Transition(source,destination,trans,boucle);
        automate.getTransitions().add(transition);
            System.out.println("0- Quitter \n1-Continuer");
            choix=scanner.nextInt();
        }
        return automate;
     }
     public void afficheAutomateSimple(){
         System.out.println("Ensemble de l'alphabet :"+this.getAlphabet().toString());
         System.out.println("Etat initial :"+this.getS0().getid());
         System.out.print("Etats finaux : ");

         for (int i=0;i<this.getFinaux().size();i++) System.out.print(this.getFinaux().get(i).getid()+", ");
         for (Transition tr:this.getTransitions()) System.out.println("\n"+tr.getSrc().getid()+" -> ["+tr.getTrans()+"] -> "+tr.getDest().getid());

     }

    public static void main(String[] args) {
        System.out.println("/*------ Creation d'un automate simple ------*/");
        AutomateSimple automateSimple= AutomateSimple.CreerAutomateSimple();
        automateSimple.afficheAutomateSimple();
    }
}

