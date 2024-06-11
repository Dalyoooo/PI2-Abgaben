package de.uni_bremen.pi2;

import java.util.Arrays;

/**
 * @author Dalia Benamar
 */
public class DoodlePP {
    /**
     * Die Methode berechnet für eine Menge von Studierenden eine Zuordnung zu einer Menge von Tutorien.
     * Jede Studierende kann nur genau einem Tutorium zugewiesen werden. Studierende geben an, welche
     * Tutorien für sie überhaupt infrage kommen und können nur diesen Tutorien zugewiesen werden.
     * Studierende werden anhand ihres Indexes im ersten Parameter der Methode identifiziert und Tutorien
     * anhand ihres Indexes im zweiten Parameter identifiziert, d.h. beides sind eigentlich nur Zahlen.
     *
     * @param studentAvailabilities Das zweidimensionale Array enthält für jede Studierende (1. Dimension),
     *                              an welchen Tutorien sie teilnehmen könnte (2. Dimension). Die Reihenfolge der zweiten
     *                              Dimension entspricht der Reihenfolge im nächsten Parameter.
     * @param tutorialCapacities    Enthält für jedes Tutorium, wie viele Plätze es gibt. Die Methode
     *                              aktualisiert die Einträge, so dass auch nach dem Aufruf noch vermerkt, wie viele Plätze
     *                              dann noch pro Tutorium verfügbar sind.
     * @return Ein Array, in dem für jede Studierende steht, welchem Tutorium sie zugeordnet wurde. Gab
     * es keine Lösung, wird null zurückgegeben.
     */
    public static int[] distribute(final boolean[][] studentAvailabilities, final int[] tutorialCapacities) {

        // Überprüfen, ob die Eingabeparameter gültig sind
        if ((studentAvailabilities == null) || (tutorialCapacities == null) || (studentAvailabilities.length == 0) || (tutorialCapacities.length == 0)) {
            return null;
        }

        // Array erstellen, um Tutorium für jeden Studierenden zuzuweisen
        int[] assigned = new int[studentAvailabilities.length];

        // Das zugewiesene Array mit -1 initialisieren, um anzuzeigen, dass kein Studierender bisher einem Tutorium zugeordnet wurde
        Arrays.fill(assigned, -1);

        // Über jeden Studierenden iterieren
        for (int student = 0; student < studentAvailabilities.length; ) {

            do {
                // Nächstes Tutorium für den aktuellen Studierenden auswählen
                ++assigned[student];

                /*Dieses Kommentar ist nochmal für mein eigenes Verständnis:)
                 Diese Bedingung überprüft, ob der aktuelle Studierende einem bestimmten Tutorium zugewiesen werden kann.
                 Dabei überprüft "assigned[student] < tutorialCapacities.length", ob der zugewiesene Wert für das Tutorium
                 des aktuellen Studierenden innerhalb des gültigen Bereichs der Tutorienkapazitäten liegt.
                 Dies stellt sicher, dass wir nicht über das Array "tutorialCapacities" hinausgehen.
                 (!studentAvailabilities[student][assigned[student]] || tutorialCapacities[assigned[student]] == 0) Überprüft zwei Bedingungen
                 "!studentAvailabilities[student][assigned[student]]" ist ein boolescher Wert, der angibt,
                 ob der Student am Tutorium teilnehmen kann (true) oder nicht (false).
                 Mit ! wird dieser Wert negiert, d.h. wenn der Student nicht am Tutorium teilnehmen kann, wird die Bedingung erfüllt.
                "tutorialCapacities[assigned[student]] == 0" Überprüft, ob das zugewiesene Tutorium voll ist, d.h. keine freien Plätze mehr hat.
                */

            } while (assigned[student] < tutorialCapacities.length && (!studentAvailabilities[student][assigned[student]] || tutorialCapacities[assigned[student]] == 0));

            // Überprüfen, ob der Student einem Tutorium zugewiesen werden kann
            if ((assigned[student] < tutorialCapacities.length)) {

                // Plätze werden reduziert
                --tutorialCapacities[assigned[student]];

                // ein Student kommt dazu
                ++student;

            } else {
                // Wenn der aktuelle Studierende keinem Tutorium zugewiesen werden kann, wird sein Zuweisungswert auf -1 gesetzt.
                assigned[student] = -1;
                // Der Index des Studierenden wird um eins verringert, um zum vorherigen Studierenden zurückzukehren.
                student--;
                // Wenn der Index des Studierenden kleiner als 0 wird, bedeutet dies, dass alle Studierenden überprüft wurden, aber keine Zuweisung möglich war.
                // In diesem Fall wird null zurückgegeben, um anzuzeigen, dass es keine Lösung gibt.
                if (student < 0) {

                    return null;
                }
                // Da der vorherige Studierende keinem Tutorium zugewiesen werden konnte, wird die Kapazität des entsprechenden Tutoriums um eins erhöht.
                tutorialCapacities[assigned[student]]++;

            }


        }// Rückgabe des Arrays 'assigned', das die Zuordnung der Studierenden zu den Tutorien enthält
        return assigned;


    }


}

/**
 * Aufgaben:
 * Aufgabe 2:
 * 10 Students auf 10 Tutorials:
 * Der disabled Test läuft nicht durch, da er sehr viele Möglichkeiten gibt (wenn man die Wahlen der Studenten
 * erstmal nicht beachtet sollten es 100 über 10 Möglichkeiten, also 17310309456440 sein
 * (Binomialkoeffizient, da ziehen ohne zurücklegen und ohne Berücksichtigung der Reihenfolge)), die durchgangen
 * werden können. Dieser Wert wird nicht exakt passen, zeigt aber die ungefähre Größenordnung, in der wir uns
 * befinden. Da die letzte Person nur in die ersten paar Tutorien eingetragen werden kann, muss der Algorithmus
 * extrem weit zurückrechnen, weshalb das durchlaufen so lange braucht. Wenn man die letzte Person in der Liste an
 * den Anfang packt, muss der Algorithmus nicht so viel rechnen und der Test läuft durch. Um solche Situationen zu
 * vermeiden, kann die Liste vorher einmal manuell durchgegangen werden und Einträge die vermehrt zu den ersteren
 * Tutorien können nach vorne und die, die eher zu den späteren können eher nach hinten gelegt werden.
 *
 * Aufgabe 3:
 * Der worst-case ist, dass jede mögliche Kombination einmal durchgegangen werden muss und erst die allerletzte
 * Kombination zu einer erfolgreichen Aufteilung führt. Wir haben insgesamt s Studenten und t Tutorien, unser
 * Aufwand wäre dementsprechend O(s*t), wobei allerdings beachtet werden muss, dass die Studenten nur den Tutorien
 * zugeordnet werden können, die sie gewählt haben.
 *
 * Aufgabe4:
 * backward: Äußeres + Schleife: O(1+1+1+j), set()-Methode: O(1+1+1+1) -> O(j)
 * forward: Äußeres O(1+j), inneres: O(1+log(j)) -> O(j+log(j)), mit log(j), da durch die capacity am Anfang
 * deutlich häufiger erhöht werden muss, als mit höherem j, wodurch ein logarithmischer Verlauf entsteht.
 *
 */

