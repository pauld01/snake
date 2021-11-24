package main;

public class SnakePart {
    public int x,y;
    public int dir;

    public SnakePart(int x, int y, int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void move(){
        if(this.dir == 37 || this.dir == 39){ //si la direction est horizontale
            this.x += (this.dir == 37) ? -1 : 1;
            if(this.x > 13){
                this.x = -1;
            }else if(this.x < -1){
                this.x = 13;
            }
        }else{
            this.y += (this.dir == 38) ? -1 : 1;
            if(this.y > 13){
                this.y = -1;
            }else if(this.y < -1){
                this.y = 13;
            }
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new SnakePart(x, y, dir);
    }
}
