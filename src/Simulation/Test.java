package Simulation;

public class Test {
}
/*
    A. Preparacja grafu:
        1. Dla każdego wierzchołka-pokoju, pomiędzy, każdymi dwoma drzwiami liczona jest
            najkrótsza ścieżka pomiędzy nimi i zapisywana jest ona jako wartość w mapie
            dla zadanej pary drzwi

    B. Algorytm poszukiwania ścieżki do wyjścia:
        1. Dla każdego bota:
            1. Obliczenie modyfikatorów poczytalności względem otoczenia
                (stres w tłumie, niebezpieczeństwa, wypadki w okolicy itd.)
            2. Niska Poczytalność określa prawdopodobieństwo wykonania losowego wyboru pomieszczenia
               do którego bot będzie zmierzać. (1 - p)
            3. Jeżeli nie jest losowe następuje następujący algorytm:
               Jeżeli wisdom < 0.5 LUB sanity < 0.5:
                    1. Poszukaj strzałki wskazujacej drogę ewakuacyjną i określ w ten sposób
                    pomieszczenie docelowe do którego należy przejść.
                    2. W zależności od
                    sanity określ ilość iteracji jakie botowi zajęłoby symulowane podejmowanie
                    decyzji i rozglądanie się.
               W przeciwnym wypadku:
                    1.Policz najkrótsze ścieżki do wszystkich drzwi w pomieszczeniu gdzie się znajduje bot:
                    Policzenie najkrótszych ścieżek do wyjścia z budynku przez pokoje, przyjmując
                    jako wagi krawędzi wcześniej policzone wartości dla
                    par drzwi, koniecznych do przejścia i wybierz w zależności od sanity
                    i wisdom drogę albo najkrótszą, albo którąś z kolei najlepszą prowadzącą do wyjścia
                    2. Ustal jako docelowy pokój do którego bot zmierza ten który jest pierwszy na obliczonej
                    ścieżce w punkcie wyżej.
            4. Oblicz najkrótszą ścieżkę do drzwi pokoju docelowego ustalonego w wyższych punktach
            5. Oblicz wektor kierunku do pierwszego wierzchołka ścieżki prowadzącej do drzwi pokoju docelowego z
            losowanym w zależności od sanity odchyleniem wektora kierunku od wektora optymalnego
            6. Oblicz w zależności od wykonywanego skrętu, możliwego przyspieszenia widoczności, ścieżki,
            zatłoczenia prędkość z jaką wykonywany będzie ruch tj. odległość o jaką bot przesunie się
            w kierunku wskazywanym przez wektor.
            7. Wykonaj przesunięcie.


            Kwestie nie uwzględnione w powyższym algorytmie:
                a) podążanie za tłumem
                b) korzystanie z całych szerokości korytarza zamiast biegnięcia tylko
                najkrótszymi drogami
                c) obrażenia i śmierć
                d) wypadki i niebezpieczeństwa
                e) tratowanie



            statysyka pewności co do tego jak biegnę


            Jakieś działania psychologii tłumu


            Cechy ludzika:
                przyspieszenie              float a;
                predkosc                    float v;
                predkoscMAx                 float vMax;
                Sanity                      float sanity; należące do przedziału [0;1]
                Wiedza na temat budynku     float wisdom;  należące do przedziału [0;1]
                Czy jest przewrócony        bool knocked;
                Hp                          int hp;




 */