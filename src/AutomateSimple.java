import java.util.ArrayList;
import java.util.Scanner;

public class AutomateSimple {

    private ArrayList<String> alphabet;
    private Sommet S0;
    private ArrayList<Sommet> etats;
    private ArrayList<Sommet> finaux;
    private ArrayList<Transition> transitions;

    public AutomateSimple(){}

    public AutomateSimple(Sommet init)
    {
        this.S0=init;
        this.alphabet= new ArrayList<String>();
        this.etats= new ArrayList<Sommet>();
        this.finaux= new ArrayList<Sommet>();
        this.transitions= new ArrayList<Transition>();
    }
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
    public AutomateSimple mirroir (){
        Sommet S0=this.getS0();
        S0.setEtat(EEtat.FINAL);
        ArrayList <Sommet> sommets=this.getFinaux();
        for (Sommet s: sommets) {
            s.setEtat(EEtat.SIMPLE);
        }
        this.getFinaux().clear();
        this.getFinaux().add(S0);
        /*rajouter un etat initial*/
        Sommet initial=new Sommet("newS0",EEtat.INITIAL);
        for (Sommet s:sommets) {
            Transition transition=new Transition(initial,s,"&",false);
            initial.getTrans_sortantes().add(transition);
        }
        this.setS0(initial);
        ArrayList<Transition> transitions = this.getTransitions();
        for (Transition t : transitions){
            Sommet tmp = t.getSrc();
            t.setSrc(t.getDest());
            t.setDest(tmp);
        }
        return this;
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
        source.getTrans_sortantes().add(transition);
        destination.getTrans_entrantes().add(transition);
            System.out.println("0- Quitter \n1-Continuer");
            choix=scanner.nextInt();
        }
        return automate;
     }
     public int nb_occurrence(String letter,Sommet s){
        int nb_occ=0;
        for (Transition tr :s.getTrans_sortantes()){
            if (tr.getTrans().equals(letter)){
                nb_occ++;
            }
        }
        return nb_occ;
     }
    public ArrayList<Sommet> nb_occurrences(String letter,Sommet s){
        ArrayList<Sommet> destination=new ArrayList<>();
        for (Transition tr :s.getTrans_sortantes()){
            if (tr.getTrans().equals(letter)){
                destination.add(tr.getDest());
            }
        }
        return destination;
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
    public AutomateSimple Reduire(){
        ArrayList<Sommet> accessible = new ArrayList<Sommet>();
        ArrayList<Sommet> coaccessible = new ArrayList<Sommet>();
        ArrayList<Transition> trans = new ArrayList<Transition>();
        ArrayList<Transition> sortants = new ArrayList<Transition>();
        ArrayList<Transition> entrants = new ArrayList<Transition>();
        AutomateSimple AReduit = new AutomateSimple(this.S0);
        AReduit.setAlphabet(alphabet);
        AReduit.setEtats(etats);
        AReduit.setFinaux(finaux);
        AReduit.setTransitions(transitions);
        Sommet s0= this.S0;
        int tete=0;
        int cpt=0;
        Sommet s=s0;
        accessible.add(s);
        sortants=s.getTrans_sortantes();
        trans=transitions;
        Transition t=trans.get(cpt);
        boolean stop= false;
        while(!stop)
        {
            sortants=s.getTrans_sortantes();
            for (Transition tr : sortants)
            {
                accessible.add(tr.getDest());
            }
            System.out.println(accessible);
            System.out.println(tete);
            tete++;
            if (tete < accessible.size()) {
                s = accessible.get(tete);
            }
            else{
                stop= true;
            }
        }
        //la procédure de coacessible
        stop=false;
        tete=0;
        for (Sommet f: finaux)
        {
            coaccessible.add(f);
            s=f;
            while(!stop)
            {
                entrants=s.getTrans_entrantes();
                for (Transition tr : entrants)
                {
                    coaccessible.add(tr.getSrc());
                }
                tete++;
                if (tete < coaccessible.size()) {
                    s = coaccessible.get(tete);
                }
                else{
                    stop= true;
                }
            }

        }

        ArrayList<Transition> at= new ArrayList<Transition>();
        ArrayList<Sommet> as= new ArrayList<Sommet>();
        for(Sommet ss: AReduit.getEtats()) {
            if (accessible.contains(ss) && coaccessible.contains(ss))
            {
                as.add(ss);
            }

        }
        for(Transition tt : AReduit.getTransitions())
        {
            if((as.contains(tt.getDest()))&& (as.contains(tt.getSrc())))
            {
                at.add(tt);
            }
        }

        AReduit.setTransitions(at);
        AReduit.setEtats(as);


        //eliminer les etats non accessibles et non coacessibles
  /*      ArrayList<Transition> at= AReduit.getTransitions();
        for(Sommet ss: AReduit.getEtats())
        {
            if (!accessible.contains(ss) || !coaccessible.contains(ss))
            {
                System.out.println("before for");
                for(Transition tt : at)
                {
                    System.out.println("after ofr");
                    if((tt.getDest()==ss)|| (tt.getSrc()==ss))
                    {

                        AReduit.transitions.remove(tt);
                    }
                }
                AReduit.etats.remove(ss);
            }
        }*/
        return AReduit;


    }

    public AutomateSimple Complet()
    {
        AutomateSimple AComplet = new AutomateSimple(this.S0);
        AComplet.setAlphabet(alphabet);
        AComplet.setEtats(etats);
        AComplet.setFinaux(finaux);
        AComplet.setTransitions(transitions);
        boolean trouv=false;
        Sommet puits= new Sommet("puits", EEtat.SIMPLE);
        for (Sommet s: AComplet.getEtats()) {
            for (String a : AComplet.getAlphabet()) {
                trouv= false;
                for (Transition t : s.getTrans_sortantes()) {
                    if (t.getTrans().equals(a)) {
                        trouv = true;
                    }
                }

                if (!trouv) {
                    Transition ajout = new Transition(s, puits, a, false);
                    AComplet.transitions.add(ajout);
                }

            }

        }

        Transition tpuit;
        for (String a : AComplet.alphabet)
        {
            tpuit= new Transition(puits, puits, a, true);
            AComplet.transitions.add(tpuit);

        }
        AComplet.etats.add(puits);

        return AComplet;
    }

    public AutomateSimple Complement()
    {
        AutomateSimple AComplement= new AutomateSimple(this.S0);
        AComplement.setAlphabet(alphabet);
        AComplement.setEtats(etats);
        ArrayList<Sommet> efinaux = new ArrayList<Sommet>();
        AComplement.setFinaux(efinaux);
        AComplement.setTransitions(transitions);
        AComplement= AComplement.Complet();
        for ( Sommet e : AComplement.etats)
        {
            if (e.getEtat()==EEtat.FINAL)
            {
                e.setEtat(EEtat.SIMPLE);
            }
            else{
                if (e.getEtat()==EEtat.SIMPLE)
                {
                    e.setEtat(EEtat.FINAL);
                    AComplement.finaux.add(e);

                }
            }
        }
        return AComplement;
    }

     public AutomateDeterministe deterministe(){
        AutomateDeterministe deterministe=new AutomateDeterministe();
        ArrayList<Sommet> sommets =new ArrayList<Sommet>();
        ArrayList<Transition> transitions=new ArrayList<Transition>();
        ArrayList<Sommet> finaux=new ArrayList<Sommet>();
        sommets.add(this.getS0());
        int i=0;
       //try {
            while (i<sommets.size()){
                System.out.println("\nBoucle principale\n");
                Sommet si=sommets.get(i);
                ArrayList<String> strings=new ArrayList<>();
                if (si.getClass().equals(SommetCompose.class)){//sommet Compose
                    System.out.println("je suis un sommet compose");
                    SommetCompose sc=(SommetCompose) si;
                    for (Sommet s:sc.getSommetsComposes()) {
                        for (Transition trans:s.getTrans_sortantes())
                            if (!strings.contains(trans.getTrans()))strings.add(trans.getTrans());
                    }
                    for (String letter : this.getAlphabet()) {
                        if (strings.contains(letter)) {
                            SommetCompose scmp = new SommetCompose();

                            for (Sommet sommet : sc.getSommetsComposes()) {
                                ArrayList<Sommet> destination = new ArrayList<>();
                                int nb_occ=this.nb_occurrence(letter, sommet);
                                destination=this.nb_occurrences(letter,sommet);
                                System.out.println(destination.toString());
                                if (nb_occ > 1) {
                                    System.out.println("je suis un sommet compose cree doka");

                                    scmp.setSommetsComposes(destination);
                                    scmp.setId("S"+i+3);
                                    int k = 0;
                                    boolean stop = false;
                                    while (k < this.getFinaux().size() && k < destination.size() && !stop) {
                                        if (scmp.getSommetsComposes().contains(this.getFinaux().get(k))) {
                                            stop = true;
                                            scmp.setEtat(EEtat.FINAL);
                                            finaux.add(scmp);
                                            System.out.println("\n"+finaux.get(0).getid()+" FINAAAL");
                                            deterministe.setFinaux(finaux);

                                        }
                                        k++;
                                    }
                                    if (!stop) scmp.setEtat(EEtat.SIMPLE);
                                    if (scmp.getSommetsComposes().size()==0) scmp.setSommetsComposes(destination);
                                    //
                                    scmp.getSommetsComposes().addAll(destination);


                                } else {
                                    if (nb_occ!=0){
                                        //
                                        scmp.setSommetsComposes(destination);
                                        int m=0;
                                        while (m<this.getFinaux().size() && !this.getFinaux().get(m).equals(destination)) m++;
                                        if (m<this.getFinaux().size() && this.getFinaux().get(m).equals(destination)){
                                            scmp.setEtat(EEtat.FINAL);
                                            deterministe.getFinaux().add(scmp);
                                        }

                                    }
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
                    for (Transition tr:si.getTrans_sortantes()) {if (!strings.contains(tr.getTrans())) strings.add(tr.getTrans());}
                    for (String letter : this.getAlphabet()){
                        System.out.println("je suis a la lettre "+letter+"\n");
                        if (strings.contains(letter)){

                            System.out.println("Oui y a a");
                            ArrayList<Sommet> destination=new ArrayList<Sommet>();
                            int nb_occ=this.nb_occurrence(letter,si);
                            destination=this.nb_occurrences(letter,si);
                            if (nb_occ>1) {
                                System.out.println("je suis un sommet compose cree");

                                SommetCompose sommetCompos=new SommetCompose();
                                sommetCompos.setSommetsComposes(destination);
                                System.out.println(destination.toString());
                                if (!sommets.contains(sommetCompos)){
                                    int k=0; boolean stop=false;
                                    while (k<destination.size()&& !stop){
                                        if ((sommetCompos.getSommetsComposes().contains(this.getFinaux().get(k))) || (sommetCompos.getSommetsComposes().get(k).equals(this.getFinaux().get(0)))){stop=true;
                                            sommetCompos.setEtat(EEtat.FINAL);
                                            sommetCompos.setId("S"+i+2);
                                            finaux.add(sommetCompos);
                                            System.out.println("\n"+finaux.get(0).getid()+" FINAAAL");
                                            deterministe.setFinaux(finaux);
                                        }
                                        k++;
                                    }
                                    if (!stop) sommetCompos.setEtat(EEtat.SIMPLE);
                                    sommets.add(sommetCompos);
                                    deterministe.getEtats().addAll(sommets);
                                    Transition transition=new Transition(si,sommetCompos,letter,false);
                                    transitions.add(transition);
                                    deterministe.getTransitions().addAll(transitions);

                                }}else{
                                if (nb_occ!=0) {
                                    if (!sommets.contains(si)) sommets.add(si);//pour ne pas doubler le mm sommet
                                    if (!si.getTransitionByLetter(letter).getBoucle() && !sommets.contains(si.getTransitionByLetter(letter).getDest())) {
                                        Sommet so = si.getTransitionByLetter(letter).getDest();
                                        sommets.add(so);
                                        int n = 0;
                                        while (n < this.getFinaux().size() && !this.getFinaux().get(n).equals(so)) n++;
                                        if (this.getFinaux().get(n).equals(so)) {
                                            so.setEtat(EEtat.FINAL);
                                            if (deterministe.nbFinaux()!=0) deterministe.getFinaux().add(so);
                                        }
                                    }

                                    deterministe.setEtats(sommets);
                                    transitions.add(si.getTransitionByLetter(letter));
                                    deterministe.setTransitions(transitions);
                                }
                                }

                            }
                        }}
                i++;
                }
       /*}

        catch (NullPointerException e){
            System.out.println("une chose n'a pas ete initialisee,veuillez verifier...");
        }*/
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

