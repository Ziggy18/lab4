public class Light {
    // вектор направления света. может быть любой длины
    private Coordinate direction;
    // цвет света. для получения яркости [0,1] необходимо каждую компоненту разделить на 255
    private Color color;

    public Light(){
        this.direction=new Coordinate(0,0,1);
        this.color=new Color(255, 255, 255);
    }

    public Light(Coordinate direction, Color color){
        this.direction=direction;
        this.color=color;
    }

    public Coordinate getDirection() {
        return direction;
    }

    public void setDirection(Coordinate direction) {
        this.direction = direction;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
