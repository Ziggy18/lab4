import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class ObjectUtils {

    // считывание объекта из .obj файла
    public static Object ObjectFromFile(String filename) throws Exception {

        Object object=new Object();
        String string;
        Scanner lineScanner;

        FileReader fileReader= new FileReader(filename);
        Scanner fileScanner = new Scanner(fileReader);

        while (fileScanner.hasNextLine()) {

            string=fileScanner.nextLine();

            // пропускаем пустые строки
            if(string.length()<3) {
                continue;
            }

            // если это строка вершины
            if(string.charAt(0)=='v'&&string.charAt(1)==' '){
                lineScanner = new Scanner(string.substring(1)).useLocale(Locale.ENGLISH);
                // считываем координаты
                object.addCoordinate(new Coordinate(lineScanner.nextDouble(),lineScanner.nextDouble(),lineScanner.nextDouble()));
            }

            // если это строка нормали
            if(string.charAt(0)=='v'&&string.charAt(1)=='n'){
                lineScanner = new Scanner(string.substring(2)).useLocale(Locale.ENGLISH);
                // считываем трёхмерные координаты
                object.addNormal(new Coordinate(lineScanner.nextDouble(),lineScanner.nextDouble(),lineScanner.nextDouble()));
            }

            // если это строка полигона
            else if(string.charAt(0)=='f'){
                Polygon currPolygon=new Polygon();
                int v=0, vt=0, vn=0, k=0, currInt=0;
                boolean isNeg=false;
                // проходим по всей строке
                for(int i=2; i<string.length(); i++){
                    char currChar=string.charAt(i);
                    // если закончили считывать текущее значение
                    if(currChar==' '||currChar=='/'){
                        // если мы считали число
                        if(currInt!=0){
                            if(isNeg) currInt*=-1;
                            // понимаем, за какой компонент вершины отвечает данное число
                            if(k==0) v=currInt;
                            else if(k==1) vt=currInt;
                            else vn=currInt;
                            currInt=0;
                        }
                        isNeg=false;
                        // если закончили считывать вершины - сохраняем её
                        if(currChar==' '){
                            k=0;
                            if(v!=0)
                                currPolygon.addPeak(new Peak(v, vt, vn));
                            v=0; vt=0; vn=0;
                        }
                        else k++;
                    }
                    // если текущий символ '-' - запоминаем это
                    else if(currChar=='-') isNeg = true;
                        // если считываем цифру, записываем ее в текущее число
                    else if(currChar>='0'&&currChar<='9')
                        currInt=currInt*10+currChar-'0';
                }
                // после считывания строки сохраняем последнюю вершину при необходимости
                if(currInt!=0){
                    if(isNeg) currInt*=-1;
                    // понимаем, за какой компонент вершины отвечает данное число
                    if(k==0) v=currInt;
                    else if(k==1) vt=currInt;
                    else vn=currInt;
                    currInt=0;
                }
                if(v!=0)
                    currPolygon.addPeak(new Peak(v, vt, vn));
                // добавляем полигон в список полигонов
                object.addPolygon(currPolygon);
            }
        }
        fileReader.close();
        return object;
    }

    public static Picture ObjectToPicture(Object Object, int w, int h, Camera camera, Light light){
        double ex=1/Math.tan(camera.getXAngle()/2);
        double ey=1/Math.tan(camera.getYAngle()/2);
        double n=camera.getN();
        double f=camera.getF();
        Coordinate camDirection=MathTools.rotate(new Coordinate(0,0,-1), camera.getAlpha(), camera.getBeta(),0);

        Coordinate xe=MathTools.rotate(new Coordinate(1,0,0), camera.getAlpha(), camera.getBeta(), 0);
        Coordinate ye=MathTools.rotate(new Coordinate(0,1,0), camera.getAlpha(), camera.getBeta(), 0);
        Coordinate ze=MathTools.rotate(new Coordinate(0,0,1), camera.getAlpha(), camera.getBeta(), 0);
        double[][] A=new double[][]{
                {xe.getX(), ye.getX(), ze.getX()},
                {xe.getY(), ye.getY(), ze.getY()},
                {xe.getZ(), ye.getZ(), ze.getZ()}};
        double[][] AInv=MathTools.inversion(A,3);
        double[][] newO = new double[][]{
                {camera.getPosition().getX()},
                {camera.getPosition().getY()},
                {camera.getPosition().getZ()}};
        double[][] oldO = MathTools.matMul(MathTools.scalMul(AInv,3,3,-1),newO, 3, 3, 1);

        Picture picture=new Picture(w,h);
        ArrayList<Coordinate> coordinates=Object.getCoordinates();
        ArrayList<Coordinate> normals=Object.getNormals();
        ArrayList<Polygon> polygons= Object.getPolygons();
        // проходим по всем полигонам
        for(int i=0; i<polygons.size();i++){
            ArrayList<Peak> peaks = polygons.get(i).getPeaks();
            // рисуем все треугольники с двумя соседними вершинами и нулевой вершиной
            int[] v=new int[3];
            int[] vn=new int[3];
            v[0]=peaks.get(0).getP()>0?peaks.get(0).getP()-1:coordinates.size()+peaks.get(0).getP();
            vn[0]=peaks.get(0).getPn()>0?peaks.get(0).getPn()-1:coordinates.size()+peaks.get(0).getPn();
            for(int j=1; j<peaks.size()-1; j++){
                v[2]=peaks.get(j).getP()>0?peaks.get(j).getP()-1:coordinates.size()+peaks.get(j).getP();
                v[1]=peaks.get(j+1).getP()>0?peaks.get(j+1).getP()-1:coordinates.size()+peaks.get(j+1).getP();
                vn[2]=peaks.get(j).getPn()>0?peaks.get(j).getPn()-1:coordinates.size()+peaks.get(j).getPn();
                vn[1]=peaks.get(j+1).getPn()>0?peaks.get(j+1).getPn()-1:coordinates.size()+peaks.get(j+1).getPn();
                Coordinate normal=MathTools.normal(coordinates.get(v[0]),coordinates.get(v[1]),coordinates.get(v[2]));
                Coordinate[] vNormals=new Coordinate[3];
                double[] brightness=new double[3];
                for(int k=0; k<3; k++){
                    vNormals[k]=normals.get(vn[k]);
                    brightness[k]=-MathTools.normDotProduct(vNormals[k],light.getDirection());
                }
                if(MathTools.normDotProduct(normal,camDirection)<=2){
                    Coordinate[] tri=new Coordinate[3];
                    for(int k=0; k<3; k++){
                        Coordinate coordinate=MathTools.ncs(coordinates.get(v[k]),AInv,oldO);
                        double distance = Math.abs(coordinate.getZ());
                        tri[k]=new Coordinate(0,0,0);
                        tri[k].setX((1+ex*coordinate.getX()/distance)*w/2);
                        tri[k].setY((1+ey*coordinate.getY()/distance)*w/2);
                        tri[k].setZ((f+n)/(f-n)-2*f*n/((f-n)*distance));
                    }

                    PictureUtils.drawTriangleZg(picture, tri, brightness, light.getColor());
                }
            }
        }
        return picture;
    }
}