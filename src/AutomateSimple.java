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

    public void setFinaux(ArrayList<Sommet> finaux) {
        this.finaux = finaux;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
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

    public void setEtats(ArrayList<Sommet> etats) {
        this.etats = etats;
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
     public int nb_occurrence(String letter,Sommet s,ArrayList<Sommet> destinations){
        int nb_occ=0;
        for (Transition tr :s.getTrans_sortantes()){
            if (tr.getTrans().equals(letter)){
                nb_occ++;
                destinations.add(tr.getDest());
            }
        }
        return nb_occ;
     }

     public void afficheAutomate(){
         System.out.println("\nEnsemble de l'alphabet :"+this.getAlphabet().toString());
         System.out.println("Etat initial :"+this.getS0().getid());
         System.out.print("Etats finaux : ");

         for (int i=0;i<this.getFinaux().size();i++) System.out.print(this.getFinaux().get(i).getid()+", ");
         for (Transition tr:this.getTransitions()) {System.out.println("\n");
             if (tr.getSrc().getClass().equals(SommetCompose.class)) {
                 System.out.print("{");
                 SommetCompose sc=(SommetCompose) tr.getSrc();
                 for (Sommet s:sc.getSommetsComposes()) System.out.print(s.getid()+", ");}else System.out.print(tr.getSrc().getid());

                 System.out.print(" -> ["+tr.getTrans()+"] -> ");

             if (tr.getDest().getClass().equals(SommetCompose.class)) {
                 SommetCompose sc=(SommetCompose) tr.getDest();
                 for (Sommet s:sc.getSommetsComposes()) System.out.print(s.getid()+", ");
                 System.out.print("}");}
             else System.out.print(tr.getDest().getid());

             }}


     public AutomateDeterministe deterministe(){
        AutomateDeterministe deterministe=new AutomateDeterministe();
        ArrayList<Sommet> sommets =new ArrayList<Sommet>();
        ArrayList<Transition> transitions=new ArrayList<Transition>();
        ArrayList<Sommet> finaux=new ArrayList<Sommet>();
        sommets.add(this.getS0());
        int i=0;
        try {
            while (i<sommets.size()){
                Sommet si=sommets.get(i);
                ArrayList<String> strings=new ArrayList<>();
                if (si.getClass().equals(SommetCompose.class)){ //sommet Compose
                    SommetCompose sc=(SommetCompose) si;
                    for (Sommet s:sc.getSommetsComposes()) {
                        for (Transition trans:s.getTrans_sortantes())
                            if (!strings.contains(trans.getTrans()))strings.add(trans.getTrans());
                    }
                    for (String letter : this.getAlphabet()) {
                        if (strings.contains(letter)) {
                            SommetCompose scmp = new SommetCompose();

                            for (Sommet sommet : sc.getSommetsComposes()) {
                                ArrayList<Sommet> destination = new ArrayList<Sommet>();
                                int nb_occ=this.nb_occurrence(letter, sommet, destination);
                                if (nb_occ > 1) {
                                    scmp.setSommetsComposes(destination);
                                    int k = 0;
                                    boolean stop = false;
                                    while (k < this.getFinaux().size() && k < destination.size() && !stop) {
                                        if (scmp.getSommetsComposes().contains(this.getFinaux().get(k))) {
                                            stop = true;
                                            scmp.setEtat(EEtat.FINAL);
                                            finaux.add(scmp);
                                            deterministe.setFinaux(finaux);
                                            System.out.println("\n"+this.getFinaux().get(k));
                                        }
                                        k++;
                                    }
                                    if (!stop) scmp.setEtat(EEtat.SIMPLE);
                                    if (scmp.getSommetsComposes().size()==0) scmp.setSommetsComposes(destination);
                                    else if (!scmp.getSommetsComposes().contains(destination))scmp.getSommetsComposes().addAll(destination);


                                } else {
                                    if (nb_occ!=0){
                                        if (scmp.getSommetsComposes().size()==0) scmp.setSommetsComposes(destination);
                                        else if (!scmp.getSommetsComposes().contains(destination))scmp.getSommetsComposes().addAll(destination);}
                                }
                            }
                            if (!sommets.contains(scmp))sommets.add(scmp);
                            deterministe.setEtats(sommets);
                            Transition transition = new Transition(si, scmp, letter, false);
                            transitions.add(transition);
                            deterministe.setTransitions(transitions);
                        }
                    }
                }else { //Sommet simple
                    for (Transition tr:si.getTrans_sortantes()) if (!strings.contains(tr.getTrans()))strings.add(tr.getTrans());
                    for (String letter : this.getAlphabet()){
                        if (strings.contains(letter)) {
                            ArrayList<Sommet> destination=new ArrayList<Sommet>();
                            int nb_occ=this.nb_occurrence(letter,si,destination);
                            if (nb_occ>1) {
                                SommetCompose sommetCompos=new SommetCompose();
                                sommetCompos.setSommetsComposes(destination);
                                if (!sommets.contains(sommetCompos)){
                                    int j=0; boolean stop=false;
                                    while (j<destination.size()&& !stop){
                                        if ((sommetCompos.getSommetsComposes().contains(this.getFinaux().get(j))) || (sommetCompos.getSommetsComposes().get(j).equals(this.getFinaux().get(0)))){stop=true;
                                            sommetCompos.setEtat(EEtat.FINAL);
                                            finaux.add(sommetCompos);
                                            deterministe.setFinaux(finaux);
                                        }
                                        j++;
                                    }
                                    if (!stop) sommetCompos.setEtat(EEtat.SIMPLE);
                                    sommets.add(sommetCompos);
                                    Transition transition=new Transition(si,sommetCompos,letter,false);
                                    transitions.add(transition);
                                    deterministe.setTransitions(transitions);

                                }}else{
                                if (nb_occ!=0){
                                    if (!sommets.contains(si))sommets.add(si);//pour ne pas doubler le mm sommet
                                    if (!si.getTransitionByLetter(letter).getBoucle()&& !sommets.contains(si.getTransitionByLetter(letter).getDest()))sommets.add(si.getTransitionByLetter(letter).getDest());
                                    deterministe.setEtats(sommets);
                                    transitions.add(si.getTransitionByLetter(letter));
                                    deterministe.setTransitions(transitions);
                                }

                            }
                        }}
                }
                i++;
            }
        }
        catch (NullPointerException e){
            System.out.println("une chose n'a pas ete initialisee,veuillez verifier...");
        }
        deterministe.setAlphabet(this.alphabet);
        deterministe.setS0(this.S0);
        return deterministe;

     }

    public static void main(String[] args) {
        System.out.println("/*------ Creation d'un automate simple ------*/");
        AutomateSimple automateSimple= AutomateSimple.CreerAutomateSimple();
        automateSimple.afficheAutomate();
        AutomateDeterministe autoD=automateSimple.deterministe();
        autoD.afficheAutomate();
    }
}

