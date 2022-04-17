public class Main {

    public static void main(String[] args) {
        try {
            // считываем объект из .obj файла
            Object stormTrooper = ObjectUtils.ObjectFromFile("objects/StormTrooper.obj");
            Camera camera=new Camera(
                    new Coordinate(-1.5,-1.2,3),
                    Math.PI*0.1, Math.PI*0.14,
                    Math.PI*0.45, Math.PI*0.45,
                    1, 10);
            Light light=new Light(new Coordinate(-0.5,-0.8,-1), new Color(200, 200, 200));
            Picture picture = ObjectUtils.ObjectToPicture(stormTrooper,1000,1000, camera, light);
            PictureUtils.Save(picture, "pictures/picture.png");

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
