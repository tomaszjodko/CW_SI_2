package knn.common;

/**
 * Created by Tomasz Jodko on 2016-04-18.
 */
public class Metryki{

    static public Double OdlegloscEuklidesowa(Obiekt testowy, Obiekt treningowy){
        Double odleglosc = 0.0;
        for (int i = 0; i<testowy.getAtrybuty().size() ; i++){
            odleglosc+=Math.pow(testowy.getAtrybuty().get(i) - treningowy.getAtrybuty().get(i),2);
        }
        return Math.sqrt(odleglosc);
    }

    static public Double OdlegloscManhattan(Obiekt testowy, Obiekt treningowy){
        Double odleglosc = 0.0;
        for (int i = 0; i<testowy.getAtrybuty().size() ; i++){
            odleglosc+=Math.abs(testowy.getAtrybuty().get(i) - treningowy.getAtrybuty().get(i));
        }
        return odleglosc;
    }

    static public Double OdlegloscCanberra(Obiekt testowy, Obiekt treningowy){
        Double odleglosc = 0.0;
        for (int i = 0; i<testowy.getAtrybuty().size() ; i++){
            odleglosc+=Math.abs((double) (testowy.getAtrybuty().get(i) - treningowy.getAtrybuty().get(i))/(double) (testowy.getAtrybuty().get(i) + treningowy.getAtrybuty().get(i)));
        }
        return odleglosc;
    }

    static public Double OdlegloscCzebyszewa(Obiekt testowy, Obiekt treningowy){
        Double max = Double.MIN_VALUE;
        for (int i = 0; i<testowy.getAtrybuty().size() ; i++){
            Double temp = Math.abs(testowy.getAtrybuty().get(i).doubleValue() - treningowy.getAtrybuty().get(i).doubleValue());
            if(temp > max){
                max = temp;
            }
        }
        return max;
    }


    static public Double OdlegloscPearsona(Obiekt testowy, Obiekt treningowy){
        double n = (double) testowy.getAtrybuty().size();
        Double xDaszek = xDaszek(testowy,n);
        Double yDaszek = yDaszek(treningowy,n);
        Double result = 0.0;
        for (int i = 0; i<n ; i++){
            result+=((testowy.getAtrybuty().get(i) - xDaszek)/mianownikX(testowy,n,i,xDaszek))*((treningowy.getAtrybuty().get(i) - yDaszek)/mianownikY(treningowy,n,i,yDaszek));
        }
        return 1-Math.abs((double)(1/n)*result);
    }

    private static Double xDaszek(Obiekt testowy, double n){
        Double result = 0.0;
        for (int i = 0; i<n ; i++ ){
            result+=(double)testowy.getAtrybuty().get(i);
        }
        return (1/n)*result;
    }

    private static Double yDaszek(Obiekt treningowy, double n){
        Double result = 0.0;
        for (int i = 0; i<n ; i++ ){
            result+=(double)treningowy.getAtrybuty().get(i);
        }
        return (1/n)*result;
    }

    private static Double mianownikX(Obiekt testowy, double n, Integer i, Double xDaszek){
        Double result = 0.0;
        for (int j = 0; j<n ; j++ ){
            result+=Math.pow(testowy.getAtrybuty().get(i)-xDaszek,2);
        }
        return Math.sqrt((1/n)*result);
    }

    private static Double mianownikY(Obiekt treningowy, double n, Integer i, Double yDaszek){
        Double result = 0.0;
        for (int j = 0; j<n ; j++ ){
            result+=Math.pow(treningowy.getAtrybuty().get(i)-yDaszek,2);
        }
        return Math.sqrt((1/n)*result);
    }
}
