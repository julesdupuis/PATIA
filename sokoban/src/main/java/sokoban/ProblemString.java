package sokoban;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.problem.operator.Action;

public class ProblemString {
    public ArrayList<Position> positions;

    public static class Position{
        private int x;
        private int y;

        Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        String get(){
            return "p"+x+"_"+y;
        }
    }

    public static class Neighbor{
        private Position a;
        private Position b;

        Neighbor(Position a, Position b){
            this.a = a;
            this.b = b;
        }

        String get(){
            return a.get()+" "+b.get();
        }
    }

    public static class Aligned{
        private Position a;
        private Position b;
        private Position c;

        Aligned(Position a, Position b, Position c){
            this.a = a;
            this.b = b;
            this.c = c;
        }

        String get(){
            return a.get()+" "+b.get()+" "+c.get();
        }
    }

    public String get(String levelString){
        positions = new ArrayList<>();
        ArrayList<Position> goals = new ArrayList<>();
        ArrayList<Position> boxes = new ArrayList<>();
        ArrayList<Position> notboxes = new ArrayList<>();
        Position player = new Position(0, 0);
        ArrayList<Neighbor> neighbors = new ArrayList<>();
        ArrayList<Aligned> aligneds = new ArrayList<>();

        // parsing level
        String[] level = levelString.split("\n");
        // System.err.println(level.length);

        // searching correct level size
        int width = 0;
        final int height = level.length;

        for(int index = 0; index<height; index++){
            int newWidth = level[index].length();
            if(width<newWidth){
                width = newWidth;
            }
        }
        // System.err.println("width height : "+width+", "+height+";");

        // skipping first and last lines containing only walls
        for(int y = 1; y<height-1; y++){
            // searching begining and end of the floor
            int skipEmpty = level[y].indexOf('#');
            int skipEmptyEnd = level[y].lastIndexOf('#');
            // System.err.println("begin end : "+skipEmpty+", "+skipEmptyEnd+";");

            int consecutiveFloorCount = 0;

            for(int x = skipEmpty+1; x<skipEmptyEnd; x++){
                switch(level[y].charAt(x)){
                    case '#':
                        consecutiveFloorCount = 0;
                        break;

                    case ' ':
                        notboxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        consecutiveFloorCount++;
                        break;

                    case '@':
                        player = new Position(x, y);
                        notboxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        consecutiveFloorCount++;
                        break;

                    case '$':
                        boxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        consecutiveFloorCount++;
                        break;

                    case '.':
                        goals.add(new Position(x, y));
                        notboxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        consecutiveFloorCount++;
                        break;

                    case '*':
                        boxes.add(new Position(x, y));
                        goals.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        consecutiveFloorCount++;
                        break;

                    case '+':
                        player = new Position(x, y);
                        goals.add(new Position(x, y));
                        notboxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        consecutiveFloorCount++;
                        break;

                    default:
                        break;
                }
                if(consecutiveFloorCount>=2){
                    neighbors.add(new Neighbor(new Position(x-1, y), new Position(x, y)));
                }
                if(consecutiveFloorCount>=3){
                    aligneds.add(new Aligned(new Position(x-2, y), new Position(x-1, y), new Position(x, y)));
                }
            }
        }

        // vertical neighbor and aligned positions
        for(int x = 1; x<width-1; x++){
            boolean insideFloor = false;
            int consecutiveFloorCount = 0;
            for(int y = 0; y<height-1; y++){
                if(level[y].length()<= x){
                    break;
                }
                if(level[y].charAt(x) == '#'){
                    insideFloor = true;
                    consecutiveFloorCount = 0;
                    continue;
                }
                if(insideFloor){
                    if(level[y].charAt(x) != '#'){
                        consecutiveFloorCount++;
                    }
                    if(consecutiveFloorCount>=2){
                        neighbors.add(new Neighbor(new Position(x, y-1), new Position(x, y)));
                    }
                    if(consecutiveFloorCount>=3){
                        aligneds.add(new Aligned(new Position(x, y-2), new Position(x, y-1), new Position(x, y)));
                    }
                }
            }
        }

        // writing resulting problem
        StringBuilder res = new StringBuilder();

        res.append(
            "(define (problem sokoban-problem)\n"+
            "(:domain sokoban)\n"+
            "(:objects "
        );

        for(Position pos : positions){
            res.append(pos.get()+" ");
        }

        res.append(
            "- position)\n"+
            "(:init "
        );

        for(Neighbor neighbor : neighbors){
            res.append("(neighbor "+neighbor.get()+") ");
        }

        for(Aligned aligned : aligneds){
            res.append("(aligned "+aligned.get()+") ");
        }

        res.append("(playeron "+player.get()+") ");

        for(Position box : boxes){
            res.append("(isbox "+box.get()+") ");
        }

        for(Position notbox : notboxes){
            res.append("(notbox "+notbox.get()+") ");
        }

        res.append(
            ")\n"+
            "(:goal (and "
        );

        for(Position goal : goals){
            res.append("(isbox "+goal.get()+") ");
        }

        res.append(")))");

        return res.toString();
    }

    public static char getDirection(Position p1, Position p2){
        Position pDiff = new Position(p1.x - p2.x, p1.y - p2.y);
        if(pDiff.x < 0){
            if(pDiff.y != 0){
                throw new IllegalStateException("no direction for move "+p1.get()+", "+p2.get());
            }
            return 'R';
        }
        if(pDiff.x > 0){
            if(pDiff.y != 0){
                throw new IllegalStateException("no direction for move "+p1.get()+", "+p2.get());
            }
            return 'L';
        }
        if(pDiff.x == 0){
            if(pDiff.y < 0){
                return 'D';
            }
            if(pDiff.y > 0){
                return 'U';
            }
            if(pDiff.y == 0){
                throw new IllegalStateException("no direction for move "+p1.get()+", "+p2.get());
            }
        }
        throw new IllegalStateException("no direction for move "+p1.get()+", "+p2.get());
    }

    public String parseSolution(List<Action> actions){
        StringBuilder res = new StringBuilder();

        for(Action action : actions){
            // System.err.println(action.getName());
            for(int param = 0; param<action.arity(); param++){
                // System.err.println(param+" : "+action.getValueOfParameter(param)+";");
                // System.err.println(positions.get(action.getValueOfParameter(param)).get());
            }
            res.append(getDirection(positions.get(action.getValueOfParameter(0)), positions.get(action.getValueOfParameter(1))));
        }

        return res.toString();
    }
}
