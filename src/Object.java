import java.util.ArrayList;

public class Object {

    private ArrayList<Coordinate> coordinates;

    private ArrayList<Polygon> polygons;

    private ArrayList<Coordinate> normals;

    public Object(){
        coordinates=new ArrayList<Coordinate>();
        polygons=new ArrayList<Polygon>();
        normals=new ArrayList<Coordinate>();
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates){
        this.coordinates=coordinates;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void addCoordinate(Coordinate coordinate) {
        coordinates.add(coordinate);
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public ArrayList<Coordinate> getNormals() {
        return normals;
    }

    public void addNormal(Coordinate normal) {
        normals.add(normal);
    }

}
