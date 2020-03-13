public class Transition {
    private Sommet src;
    private Sommet dest;
    private String trans;
    private boolean boucle;

    public Transition(Sommet src, Sommet dest, String trans, boolean boucle)
    {
        this.dest=dest;
        this.src=src;
        this.trans=trans;
        this.boucle=boucle;
    }
    /**
     * @return the dest
     */
    public Sommet getDest() {
        return dest;
    }
    /**
     * @return the src
     */
    public Sommet getSrc() {
        return src;
    }
    /**
     * @return the trans
     */
    public String getTrans() {
        return trans;
    }
    /**
     * @param dest the dest to set
     */
    public void setDest(Sommet dest) {
        this.dest = dest;
    }
    /**
     * @param src the src to set
     */
    public void setSrc(Sommet src) {
        this.src = src;
    }

    /**
     * @param trans the trans to set
     */
    public void setTrans(String trans) {
        this.trans = trans;
    }
    /**
     * @param boucle the boucle to set
     */
    public void setBoucle(boolean boucle) {
        this.boucle = boucle;
    }
    /**
     * @return the boucle
     */
    public boolean getBoucle() {
        return boucle;
    }
}
