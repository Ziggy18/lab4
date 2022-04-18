public class Light {
    // вектор направления света. может быть любой длины
    private Coordinate direction;
    // цвет света. для получения яркости [0,1] необходимо каждую компоненту разделить на 255
    private Color color;

    public Light(Coordinate direction, Color color){
        this.direction=direction;
        this.color=color;
    }

    public Coordinate getDirection() {
        return direction;
    }

    public Color getColor() {
        return color;
    }
}
