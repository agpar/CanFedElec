/**
 * Created by Alex on 4/6/2015.
 */
public class Party {
    public String name;
    public int numOfSeats;
    public double popVote;
    public Party(){}
    public Party(String name, int numOfSeats, double popVote){
        this.name = name;
        this.numOfSeats = numOfSeats;
        this.popVote = popVote;
    }
    public Party clone(){
        Party p = new Party();
        p.name = this.name;
        p.numOfSeats = this.numOfSeats;
        p.popVote = this.popVote;
        return p;
    }
}
