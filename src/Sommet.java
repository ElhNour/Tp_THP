import java.util.ArrayList;

public class Sommet {
    private String id;
    private EEtat etat;
    private ArrayList<Transition> trans_entrantes;
    private ArrayList<Transition> trans_sortantes;

    public Sommet(){}
    public Sommet(String id, EEtat etat )
    {   this.id=id;
        this.etat=etat;
        this.trans_entrantes= new ArrayList<Transition>();
        this.trans_sortantes= new ArrayList<Transition>();

    }

    public ArrayList<Transition> getTrans_entrantes() {
        return trans_entrantes;
    }

    public ArrayList<Transition> getTrans_sortantes() {
        return trans_sortantes;
    }

    public void setTrans_entrantes(ArrayList<Transition> trans_entrantes) {
        this.trans_entrantes = trans_entrantes;
    }

    public String getid() {
        return this.id;
    }

    public void setid(String id) {
        this.id= id;
    }

    public void setTransSortantes(ArrayList<Transition> TransSortantes ) {
        this.trans_sortantes = TransSortantes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EEtat getEtat() {
        return etat;
    }

    public void setEtat(EEtat etat) {
        this.etat = etat;
    }
}
