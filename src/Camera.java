public class Camera {
    // положение камеры
    private Coordinate position;

    // угол наклона камеры вверх-вниз
    private double alpha;
    // угол поворота камеры вокруг вертикальной оси
    private double beta;

    // горизонтальный и вертикальный углы объектива камеры
    private double xAngle;
    private double yAngle;

    // расстояние до передней и задней плоскостей. объекты за их пределами не отображаются
    private double n;
    private double f;

    public Camera(Coordinate position, double alpha, double beta, double xAngle, double yAngle, double n, double f) {
        this.position = position;
        this.alpha=alpha;
        this.beta=beta;
        this.xAngle=xAngle;
        this.yAngle=yAngle;
        this.n=n;
        this.f=f;
    }

    public Coordinate getPosition() {
        return position;
    }

    public double getXAngle() {
        return xAngle;
    }

    public double getYAngle() {
        return yAngle;
    }

    public double getN() {
        return n;
    }

    public double getF() {
        return f;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }
}
