package knn.common;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Tomasz Jodko on 2016-04-18.
 */
public class GeneratorkNN {
    private SystemDecyzyjny systemTreningowy;
    private SystemDecyzyjny systemTestowy;
    private SystemDecyzyjny systemRoboczy;
    private Integer maxK;
    private List<ArrayList<Obiekt>> koncepty = new ArrayList<>();

    public void setSystemRoboczy(SystemDecyzyjny systemRoboczy) {
        this.systemRoboczy = systemRoboczy;
    }


    public void setLiczOdleglosc(Function liczOdleglosc) {
        this.liczOdleglosc = liczOdleglosc;
    }

    private Function liczOdleglosc;


    public SystemDecyzyjny getSystemTestowy() {
        return systemTestowy;
    }

    public void setSystemTestowy(SystemDecyzyjny systemTestowy) {
        this.systemTestowy = systemTestowy;
    }

    public SystemDecyzyjny getSystemTreningowy() {
        return systemTreningowy;
    }

    public void setSystemTreningowy(SystemDecyzyjny systemTreningowy) {
        this.systemTreningowy = systemTreningowy;
        wyznaczMaxK();
        generujKoncepty();
    }

    public Integer getMaxK() {
        return maxK;
    }

    public void setMaxK(Integer maxK) {
        this.maxK = maxK;
    }


    public GeneratorkNN() {
    }

    private void wyznaczMaxK() {
        List<Integer> klasyDecyzyjne = new ArrayList<>();
        for (Obiekt obiekt : this.systemTreningowy.getObiekty()) {
            klasyDecyzyjne.add(obiekt.getKlasaDecyzyjna());
        }

        Set<Integer> unikalne = new HashSet<Integer>();
        for (int i = 0; i < this.systemTreningowy.getObiekty().size(); i++) {
            unikalne.add(this.systemTreningowy.getObiekty().get(i).getKlasaDecyzyjna());
        }
        List<Integer> unikalneKlasy = new ArrayList<Integer>(unikalne);
        Integer najmniejszaIloscWystapien = Integer.MAX_VALUE;
        for (Integer klasa : unikalneKlasy) {
            Integer iloscWystapien = Collections.frequency(klasyDecyzyjne, klasa);
            if (iloscWystapien < najmniejszaIloscWystapien) {
                najmniejszaIloscWystapien = iloscWystapien;
            }
        }
        this.maxK = najmniejszaIloscWystapien;
    }

    private void generujKoncepty() {
        this.koncepty.clear();
        Set<Integer> unikalne = new HashSet<Integer>();
        for (int i = 0; i < this.systemTreningowy.getObiekty().size(); i++) {
            unikalne.add(this.systemTreningowy.getObiekty().get(i).getKlasaDecyzyjna());
        }
        List<Integer> klasyDecyzyjne = new ArrayList<Integer>(unikalne);

        for (int i = 0; i < klasyDecyzyjne.size(); i++) {
            ArrayList<Obiekt> koncept = new ArrayList<Obiekt>();
            for (int j = 0; j < this.systemTreningowy.getObiekty().size(); j++) {
                if (this.systemTreningowy.getObiekty().get(j).getKlasaDecyzyjna() == klasyDecyzyjne.get(i)) {
                    koncept.add(this.systemTreningowy.getObiekty().get(j));
                }
            }
            this.koncepty.add(koncept);

        }

    }

    public void klasyfikujEuklidesowa(Integer k) {
        for (Obiekt testowy : this.systemRoboczy.getObiekty()) {
            List<Double> odleglosciOdKonceptow = new ArrayList<>();
            for (ArrayList<Obiekt> koncept : this.koncepty) {
                List<Double> odleglosci = new ArrayList<>();
                for (Obiekt treningowy : koncept) {
                    odleglosci.add(Metryki.OdlegloscEuklidesowa(testowy, treningowy));
                }
                Collections.sort(odleglosci);
                Double sumaKnajmniejszych = 0.0;
                for (int i = 0; i < k; i++) {
                    sumaKnajmniejszych += odleglosci.get(i);
                }
                odleglosciOdKonceptow.add(sumaKnajmniejszych);
            }
            if (Collections.frequency(odleglosciOdKonceptow, Collections.min(odleglosciOdKonceptow)) == 1) {
                int najlepszyKoncept = odleglosciOdKonceptow.indexOf(Collections.min(odleglosciOdKonceptow));
                testowy.setKlasaDecyzyjna(this.koncepty.get(najlepszyKoncept).get(0).getKlasaDecyzyjna());
            } else {
                testowy.setKlasaDecyzyjna(Integer.MAX_VALUE);
            }
        }
    }

    public void klasyfikujManhattan(Integer k) {
        for (Obiekt testowy : this.systemRoboczy.getObiekty()) {
            List<Double> odleglosciOdKonceptow = new ArrayList<>();
            for (ArrayList<Obiekt> koncept : this.koncepty) {
                List<Double> odleglosci = new ArrayList<>();
                for (Obiekt treningowy : koncept) {
                    odleglosci.add(Metryki.OdlegloscManhattan(testowy, treningowy));
                }
                Collections.sort(odleglosci);
                Double sumaKnajmniejszych = 0.0;
                for (int i = 0; i < k; i++) {
                    sumaKnajmniejszych += odleglosci.get(i);
                }
                odleglosciOdKonceptow.add(sumaKnajmniejszych);
            }
            if (Collections.frequency(odleglosciOdKonceptow, Collections.min(odleglosciOdKonceptow)) == 1) {
                int najlepszyKoncept = odleglosciOdKonceptow.indexOf(Collections.min(odleglosciOdKonceptow));
                testowy.setKlasaDecyzyjna(this.koncepty.get(najlepszyKoncept).get(0).getKlasaDecyzyjna());
            } else {
                testowy.setKlasaDecyzyjna(Integer.MAX_VALUE);
            }
        }
    }

    public void klasyfikujCanberra(Integer k) {
        for (Obiekt testowy : this.systemRoboczy.getObiekty()) {
            List<Double> odleglosciOdKonceptow = new ArrayList<>();
            for (ArrayList<Obiekt> koncept : this.koncepty) {
                List<Double> odleglosci = new ArrayList<>();
                for (Obiekt treningowy : koncept) {
                    odleglosci.add(Metryki.OdlegloscCanberra(testowy, treningowy));
                }
                Collections.sort(odleglosci);
                Double sumaKnajmniejszych = 0.0;
                for (int i = 0; i < k; i++) {
                    sumaKnajmniejszych += odleglosci.get(i);
                }
                odleglosciOdKonceptow.add(sumaKnajmniejszych);
            }
            if (Collections.frequency(odleglosciOdKonceptow, Collections.min(odleglosciOdKonceptow)) == 1) {
                int najlepszyKoncept = odleglosciOdKonceptow.indexOf(Collections.min(odleglosciOdKonceptow));
                testowy.setKlasaDecyzyjna(this.koncepty.get(najlepszyKoncept).get(0).getKlasaDecyzyjna());
            } else {
                testowy.setKlasaDecyzyjna(Integer.MAX_VALUE);
            }
        }
    }

    public void klasyfikujCzebyszewa(Integer k) {
        for (Obiekt testowy : this.systemRoboczy.getObiekty()) {
            List<Double> odleglosciOdKonceptow = new ArrayList<>();
            for (ArrayList<Obiekt> koncept : this.koncepty) {
                List<Double> odleglosci = new ArrayList<>();
                for (Obiekt treningowy : koncept) {
                    odleglosci.add(Metryki.OdlegloscCzebyszewa(testowy, treningowy));
                }
                Collections.sort(odleglosci);
                Double sumaKnajmniejszych = 0.0;
                for (int i = 0; i < k; i++) {
                    sumaKnajmniejszych += odleglosci.get(i);
                }
                odleglosciOdKonceptow.add(sumaKnajmniejszych);
            }
            if (Collections.frequency(odleglosciOdKonceptow, Collections.min(odleglosciOdKonceptow)) == 1) {
                int najlepszyKoncept = odleglosciOdKonceptow.indexOf(Collections.min(odleglosciOdKonceptow));
                testowy.setKlasaDecyzyjna(this.koncepty.get(najlepszyKoncept).get(0).getKlasaDecyzyjna());
            } else {
                testowy.setKlasaDecyzyjna(Integer.MAX_VALUE);
            }
        }
    }

    public void klasyfikujPearsona(Integer k) {
        for (Obiekt testowy : this.systemRoboczy.getObiekty()) {
            List<Double> odleglosciOdKonceptow = new ArrayList<>();
            for (ArrayList<Obiekt> koncept : this.koncepty) {
                List<Double> odleglosci = new ArrayList<>();
                for (Obiekt treningowy : koncept) {
                    odleglosci.add(Metryki.OdlegloscPearsona(testowy, treningowy));
                }
                Collections.sort(odleglosci);
                Double sumaKnajmniejszych = 0.0;
                for (int i = 0; i < k; i++) {
                    sumaKnajmniejszych += odleglosci.get(i);
                }
                odleglosciOdKonceptow.add(sumaKnajmniejszych);
            }
            if (Collections.frequency(odleglosciOdKonceptow, Collections.min(odleglosciOdKonceptow)) == 1) {
                int najlepszyKoncept = odleglosciOdKonceptow.indexOf(Collections.min(odleglosciOdKonceptow));
                testowy.setKlasaDecyzyjna(this.koncepty.get(najlepszyKoncept).get(0).getKlasaDecyzyjna());
            } else {
                testowy.setKlasaDecyzyjna(Integer.MAX_VALUE);
            }
        }
    }


    private Double calcAcc(Integer klasa, Integer iloscWystapien) {
        Double trafienia = 0.0;
        Integer ilosc = iloscWystapien;
        for (int i = 0; i < this.systemTestowy.getObiekty().size(); i++) {
            if (this.systemTestowy.getObiekty().get(i).getKlasaDecyzyjna() == klasa && this.systemRoboczy.getObiekty().get(i).getKlasaDecyzyjna() == klasa) {
                trafienia++;
            } else if (this.systemTestowy.getObiekty().get(i).getKlasaDecyzyjna() == klasa && this.systemRoboczy.getObiekty().get(i).getKlasaDecyzyjna() == Integer.MAX_VALUE) {
                ilosc--;
            }
        }
        return trafienia / ilosc;
    }

    private Double calcCov(Integer klasa, Integer iloscWystapien) {
        Double trafienia = 0.0;
        for (int i = 0; i < this.systemTestowy.getObiekty().size(); i++) {
            if (this.systemTestowy.getObiekty().get(i).getKlasaDecyzyjna() == klasa && this.systemRoboczy.getObiekty().get(i).getKlasaDecyzyjna() != Integer.MAX_VALUE) {
                trafienia++;
            }
        }
        return trafienia / iloscWystapien;
    }

    private Double calcTPR(Integer klasa) {
        Double trafienia = 0.0;
        Double licz = 0.0;
        for (int i = 0; i < this.systemTestowy.getObiekty().size(); i++) {
            if (this.systemRoboczy.getObiekty().get(i).getKlasaDecyzyjna() != Integer.MAX_VALUE) {
                if (this.systemTestowy.getObiekty().get(i).getKlasaDecyzyjna() == klasa && this.systemRoboczy.getObiekty().get(i).getKlasaDecyzyjna() == klasa) {
                    trafienia++;
                    licz++;
                } else if (this.systemRoboczy.getObiekty().get(i).getKlasaDecyzyjna() == klasa && this.systemTestowy.getObiekty().get(i).getKlasaDecyzyjna() != klasa) {
                    licz++;
                }
            }
        }
        return trafienia / licz;
    }


    @Override
    public String toString() {
        String result = "";
        result += this.systemRoboczy.toString();
        result += "\n";

        List<Integer> klasyDecyzyjne = new ArrayList<>();
        for (Obiekt obiekt : this.systemTestowy.getObiekty()) {
            klasyDecyzyjne.add(obiekt.getKlasaDecyzyjna());
        }
        Set<Integer> unikalne = new HashSet<Integer>(klasyDecyzyjne);
        List<Integer> unikalneKlasy = new ArrayList<Integer>(unikalne);
        result += "=========================================\n";
        result += "Klasa:  Ilosc:   Acc:     Cov:      TPR: \n";
        result += "=========================================\n";
        for (Integer klasa : unikalneKlasy) {
            Integer iloscWystapien = Collections.frequency(klasyDecyzyjne, klasa);
            result += klasa + "         " + iloscWystapien + "         " + calcAcc(klasa, iloscWystapien) + "         " + calcCov(klasa, iloscWystapien) + "         " + calcTPR(klasa) + "\n";
        }
        result += "=========================================\n";
        return result;
    }

}
