package codeFiles;
/**
 * Asvin and Alex
 * Due date: Jan 22
 * This is the class for the monsters for level 1
 */

public class Level1Mon implements Comparable<Level1Mon>{

    int x,y;
    String type;

    // constructor
    public Level1Mon(int x, int y, String type){
        this.x=x;
        this.y=y;
        this.type=type;

    }

    public int compareTo(Level1Mon m){
        if(this.x==m.x){
            return(this.y-m.y);
        }
        else{
            return(this.x-m.x);
        }
    }

    public boolean equals(Object o){
        Level1Mon mon = (Level1Mon) o;
        return(this.x==mon.x&&this.y==mon.y);
    }

}

