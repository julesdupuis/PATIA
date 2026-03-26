package sokoban;

import java.util.ArrayList;

public class ProblemString {
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

    public static String get(String levelString){
        ArrayList<Position> positions = new ArrayList<>();
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
        System.err.println("width height : "+width+", "+height+";");

        // skipping first and last lines containing only walls
        for(int y = 1; y<height-1; y++){
            // searching begining and end of the floor
            int skipEmpty = level[y].indexOf('#');
            int skipEmptyEnd = level[y].lastIndexOf('#');
            System.err.println("begin end : "+skipEmpty+", "+skipEmptyEnd+";");

            for(int x = skipEmpty+1; x<skipEmptyEnd; x++){
                switch(level[y].charAt(x)){
                    case '#':
                        break;

                    case ' ':
                        notboxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        break;

                    case '@':
                        player = new Position(x, y);
                        notboxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        break;

                    case '$':
                        boxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        break;

                    case '.':
                        goals.add(new Position(x, y));
                        notboxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        break;

                    case '*':
                        boxes.add(new Position(x, y));
                        goals.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        break;

                    case '+':
                        player = new Position(x, y);
                        goals.add(new Position(x, y));
                        notboxes.add(new Position(x, y));
                        positions.add(new Position(x, y));
                        break;

                    default:
                        break;
                }
            }
        }

        // writing resulting problem
        StringBuilder res = new StringBuilder();

        res.append(
            "(define (problem sokoban-problem)\n"+
            "(:domain sokoban)\n"+
            "(objects "
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
}
