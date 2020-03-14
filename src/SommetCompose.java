import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SommetCompose extends Sommet implements Comparable<SommetCompose>{
    private ArrayList<Sommet> sommetsComposes;

    public ArrayList<Sommet> getSommetsComposes() {
        return sommetsComposes;
    }

    public void setSommetsComposes(ArrayList<Sommet> sommetsComposes) {
        this.sommetsComposes = sommetsComposes;
    }

    @Override
    public int compareTo(SommetCompose obj) {
        int j;boolean continu=false;int number=-1;

            if (this.getSommetsComposes().size()==obj.getSommetsComposes().size()){
               for (Sommet s:this.getSommetsComposes()){
                   j=0;
                   while (j<this.sommetsComposes.size() && !s.equals(obj.getSommetsComposes().get(j))) j++;
                   if (j<this.sommetsComposes.size() && s.equals(obj.getSommetsComposes().get(j))) continu=true;
               }
               if (continu) number=0;else number=-1;
        }
            return number;
    }

    @Override
    public int hashCode() {
        return this.getid().hashCode();
    }
}
