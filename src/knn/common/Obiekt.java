package knn.common;

import java.util.Hashtable;

/**
 * Created by Tomasz Jodko on 2016-04-17.
 */
public class Obiekt {

    private Hashtable<Integer, Integer> atrybuty;



    private Integer klasaDecyzyjna;


    public Obiekt(String linia) {
        // tworzenie obiektu z pojedynczej lini z pliku z systemem decyzyjnym
        String[] pocieta = linia.split(" ");
        int dlugosc = pocieta.length;
        this.klasaDecyzyjna = Integer.parseInt(pocieta[dlugosc - 1]);
        Hashtable<Integer, Integer> atrybuty = new Hashtable<Integer, Integer>();
        for (int i = 0; i < dlugosc - 1; i++) {
            atrybuty.put(i, Integer.parseInt(pocieta[i]));
        }
        this.atrybuty = atrybuty;

    }


    public int getKlasaDecyzyjna() {
        return this.klasaDecyzyjna;
    }

    public void setKlasaDecyzyjna(int klasaDecyzyjna) {
        this.klasaDecyzyjna = klasaDecyzyjna;
    }

    public Hashtable<Integer, Integer> getAtrybuty() {
        return this.atrybuty;
    }


    // zwraca true jezeli obiekty naleza do roznych klas decyzyjnych
    public static boolean rozniaSieKlasa(Obiekt ob1, Obiekt ob2) {
        if (ob1.getKlasaDecyzyjna() != ob2.getKlasaDecyzyjna()) {
            return true;
        } else {
            return false;
        }
    }




    @Override
    public String toString() {
        String wynik = "";
        for (int i = 0; i < atrybuty.size(); i++) {
            wynik += "  " + atrybuty.get(i) + " ";
        }
        if(this.klasaDecyzyjna == Integer.MAX_VALUE){
            wynik += "  x";
        }
        else {
            wynik += "  " + this.klasaDecyzyjna;
        }
        return wynik;
    }
}
