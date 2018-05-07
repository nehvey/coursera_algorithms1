import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BaseballElimination {

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int teams = in.readInt();
        String line;
        while ((line = in.readLine()) != null) {
            if (!line.isEmpty()) {
                String[] split = line.split(" ");
                List<String> sanitizedSplit = new ArrayList<>();
                for (String s : split) {
                    if (!s.isEmpty()) {
                        sanitizedSplit.add(s);
                    }
                }

                String name = sanitizedSplit.get(0);
                System.out.println(name);
                int w = Integer.parseInt(sanitizedSplit.get(1));
                int l = Integer.parseInt(sanitizedSplit.get(2));
                int r = Integer.parseInt(sanitizedSplit.get(3));

                for (int i = 4; i < teams + 4; i++) {
                    System.out.println(sanitizedSplit.get(i));
                }
            }
        }


    }

    // number of teams
    public int numberOfTeams() {
        return -1;
    }

    // all teams
    public Iterable<String> teams() {
        return null;
    }

    // number of wins for given team
    public int wins(String team) {
        return -1;
    }

    // number of losses for given team
    public int losses(String team) {
        return -1;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return -1;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return -1;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
