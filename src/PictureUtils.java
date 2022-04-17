import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

// методы для работы с изображением
public class PictureUtils {

    // сохранение изображения в файл
    public static void Save(Picture picture, String filename) throws IOException {
        BufferedImage png = new BufferedImage(picture.getW(), picture.getH(), TYPE_INT_RGB);
        for (int i = 0; i < picture.getW(); i++) {
            for (int j = 0; j < picture.getH(); j++) {
                Color color = picture.getColorArray()[i][j];
                png.setRGB(i, j, new java.awt.Color(color.getR(), color.getG(), color.getB()).getRGB());
            }
        }
        ImageIO.write(png, "png", new File(filename));
    }

    // отрисовка одного пикселя
    public static void Pixel(Picture picture, int x, int y, Color color) {
        int h = picture.getH();
        picture.getColorArray()[x][h - y - 1] = color;
    }

    // отрисовка треугольника с z-буффером
    public static Picture TriangleZ(Picture picture, Coordinate xtr, Coordinate ytr, Coordinate ztr, Color color) {
        int xmin = (int) Math.round(Math.min(xtr.getX(), Math.min(xtr.getY(), xtr.getZ())));
        int ymin = (int) Math.round(Math.min(ytr.getX(), Math.min(ytr.getY(), ytr.getZ())));
        int xmax = (int) Math.round(Math.max(xtr.getX(), Math.max(xtr.getY(), xtr.getZ()))) + 1;
        int ymax = (int) Math.round(Math.max(ytr.getX(), Math.max(ytr.getY(), ytr.getZ()))) + 1;
        if (xmin < 0) xmin = 0;
        if (xmax > picture.getW()) xmax = picture.getW();
        if (ymin < 0) ymin = 0;
        if (ymax > picture.getH()) ymax = picture.getH();
        double z = 0;
        for (int i = xmin; i < xmax; i++)
            for (int j = ymin; j < ymax; j++) {
                Coordinate coordinate = MathTools.barycentric(i, j, xtr, ytr);
                if (coordinate.getX() >= 0 && coordinate.getY() >= 0 && coordinate.getZ() >= 0) {
                    z = coordinate.getX() * ztr.getX() + coordinate.getY() * ztr.getY() + coordinate.getZ() * ztr.getZ();
                    if (z < picture.getZBuffer(i, j)) {
                        Pixel(picture, i, j, color);
                        picture.setZBuffer(i, j, z);
                    }
                }
            }
        return picture;
    }

    public static Picture drawTriangleZg(Picture picture, Coordinate[] tri, double[] brightness, Color color){
        int xmin = (int)Math.round(Math.min(tri[0].getX(), Math.min(tri[1].getX(), tri[2].getX())));
        int ymin = (int)Math.round(Math.min(tri[0].getY(), Math.min(tri[1].getY(), tri[2].getY())));
        int xmax = (int)Math.round(Math.max(tri[0].getX(), Math.max(tri[1].getX(), tri[2].getX())))+1;
        int ymax = (int)Math.round(Math.max(tri[0].getY(), Math.max(tri[1].getY(), tri[2].getY())))+1;
        if(xmin<0) xmin=0;
        if(xmax> picture.getW()) xmax=picture.getW();
        if(ymin<0) ymin=0;
        if(ymax> picture.getH()) ymax=picture.getH();
        double z;
        for(int i=xmin; i<xmax; i++)
            for(int j=ymin; j<ymax; j++){
                Coordinate coord=MathTools.barycentric(i,j,new Coordinate(tri[0].getX(),tri[1].getX(),tri[2].getX()),new Coordinate(tri[0].getY(),tri[1].getY(),tri[2].getY()));
                if(coord.getX()>=0&&coord.getY()>=0&&coord.getZ()>=0){
                    z=coord.getX()* tri[0].getZ()+ coord.getY()* tri[1].getZ()+ coord.getZ()* tri[2].getZ();
                    if(z>=-1&&z<=1&&z<picture.getZBuffer(i,j)){
                        double currBrightness=coord.getX()*brightness[0]+ coord.getY()*brightness[1]+ coord.getZ()*brightness[2];
                        if(currBrightness<0) currBrightness=0;
                        Pixel(picture,i,j,new Color((int)Math.round(color.getR()*currBrightness),(int)Math.round(color.getG()*currBrightness),(int)Math.round(color.getB()*currBrightness)));
                        picture.setZBuffer(i,j,z);
                    }
                }
            }
        return picture;
    }
}
