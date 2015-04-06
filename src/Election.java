/**
 * Created by Alex on 4/6/2015.
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
public class Election {
    static double maxSeats = 308;
    static  String line = "------------------------------------------------------------------";

    public static void main(String[] args) {
        ArrayList<Party> parties2011 = new ArrayList<Party>();

        //Based on 2011 election
        Party CON = new Party("Conservatives", 166, 39.62);
        Party NDP = new Party("NDP", 103, 30.63);
        Party LIB = new Party("Liberals", 34, 18.91);
        Party BLQ = new Party("Bloque", 4, 6.04);
        Party GPC = new Party("Green", 1, 3.91);
        parties2011.add(CON);
        parties2011.add(NDP);
        parties2011.add(LIB);
        parties2011.add(BLQ);
        parties2011.add(GPC);
        ArrayList<Party> mmpResults = makeMMP(parties2011);

        System.out.println("Actual Results (FPTP):");
        System.out.println(line);
        printElection(parties2011);
        System.out.println("\n");
        System.out.println("Predicted MMP Results:");
        System.out.println(line);
        printElection(mmpResults);

    }

    public static ArrayList<Party> makeMMP(ArrayList<Party> parties) {
        double filledSeats = 0;
        ArrayList<Party> results = new ArrayList<Party>();

        for (Party p : parties) {
            Party temp = p.clone();
            temp.numOfSeats = temp.numOfSeats / 2;
            filledSeats = filledSeats + temp.numOfSeats;
            results.add(temp);
        }
        while (filledSeats < maxSeats) {
            //calculate how short each parties current representation falls in respect to their popVote.
            double wFall = 0;
            int wInd = 0;
            for (int i = 0; i < results.size(); i++) {
                Party temp = results.get(i);
                double tFall = temp.popVote - 100*(temp.numOfSeats / filledSeats);
                if (tFall > wFall) {
                    wFall = tFall;
                    wInd = i;
                }
            }
            //Add 1 seat to the party whose proportional representation is lowest beneath their popVote
            results.get(wInd).numOfSeats++;
            filledSeats++;
        }
        return results;
    }

    public static void printElection(ArrayList<Party> parties) {
        DecimalFormat prettyPercent = new DecimalFormat("#.00");
        Object[][] table = new String[parties.size()][];
        String[] head = new String[] {"Party", "Seats", "%PopVote", "%ActualRep", "+/-"};


        //Fill printing table
        for (int i = 0; i < parties.size(); i++){
            table[i] = new String[5];
            table[i][0] = parties.get(i).name;
            table[i][1] = Integer.toString(parties.get(i).numOfSeats);
            table[i][2] = Double.toString(parties.get(i).popVote);
            table[i][3] = prettyPercent.format(100 * (parties.get(i).numOfSeats / maxSeats));
            double diff = 100 * (parties.get(i).numOfSeats / maxSeats)- parties.get(i).popVote;
            if(diff > 0){
                table[i][4] = "+" + prettyPercent.format((100 * (parties.get(i).numOfSeats / maxSeats))- parties.get(i).popVote);
            }
            else {
                table[i][4] = prettyPercent.format((100 * (parties.get(i).numOfSeats / maxSeats)) - parties.get(i).popVote);
            }
        }

        //Print table
        System.out.format("%-15s%-15s%-15s%-15s%-15s\n", head);
        System.out.println(line);
        for(Object[] row : table){
            System.out.format("%-15s%-15s%-15s%-15s%-15s\n", row);
        }
        System.out.println("");

        boolean majority = false;
        for (Party p : parties) {
            if (p.numOfSeats >= 155) {
                System.out.println(p.name + " can form a majority government with " + p.numOfSeats + " seats.");
                majority = true;
            }
        }
        if (!majority) {
            Party plurality = parties.get(0);
            for (Party p : parties) {
                if (p.numOfSeats > plurality.numOfSeats) {
                    plurality = p;
                }
            }
            System.out.println(plurality.name + " have the plurality of seats at " + plurality.numOfSeats + " seats and can form a minority government.");
        }
        System.out.println("");
    }
}