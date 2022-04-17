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

    public Camera() {
        this.position = new Coordinate(0,0,0);
        alpha=0;
        beta=0;
        this.xAngle=Math.PI/2;
        this.yAngle=Math.PI/2;
        this.n=1;
        this.f=Math.pow(10,4);
    }

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

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public double getXAngle() {
        return xAngle;
    }

    public void setXAngle(double xAngle) {
        this.xAngle = xAngle;
    }

    public double getYAngle() {
        return yAngle;
    }

    public void setYAngle(double yAngle) {
        this.yAngle = yAngle;
    }

    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }
}
