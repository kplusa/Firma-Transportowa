# Firma-Transportowa
Projekt wykonywany w ramach zajęć z Podstaw Inżynierii Programowania

## Wstępne funkcjonalności: 
1.  Możliwość założenia konta oraz zalogowania jako spedytor, kierowca, klient.
2.  Możliwość wglądu do danych aktualnie zalogowanego użytkownika(adres email wraz z typem użytkownika).
3.  Możliwość wylogowania się oraz zalogowania na konto przypisane innemu użytkownikowi.
4.  Jako klient możliwość utworzenia zlecenia.
5.  Jako klient możliwość podglądu do istniejących zleceń, oraz ich edycji.
6.  Jako klient dodawanie przesyłek do dostarczenia oraz obserwacja stanu zleceń z przypisanym kurierem(przeznaczona do dostarczenia, odebrana od klienta, w trakcie transportu, dostarczona).
7.  Jako klient zaakceptowanie opłat wiążących się z nadaniem paczki w określonym zleceniu.
8.  Jako klient wyświetlenie cennika wraz z dopłatami.
9.  Jako spedytor możliwość edycji listy oddziałów (punktów przerzutowych przesyłek).
10. Jako spedytor możliwość przydzielenia przesyłki najbliższemu kierowcy uwzględniając odległość końca jego zlecenia w stosunku do początku kolejnego według uznania spedytora.
11. Jako spedytor możliwość obliczenia zapłaty dla kierowcy(procent od przyznanych paczek).
12. Jako spedytor możliwość wyświetlenia oraz edycji cennika wraz z dopłatami.
13. Jako kurier możliwość kontroli przeznaczonych przesyłek oraz szczegółowych informacji dotyczących przesyłek które nie zostały przez niego dostarczone, jednocześnie zostały przydzielone dla niego przez spedytora.
14. Jako kurier możliwośc zmiany stanu przydzielonej przesyłki.
15. Jako kurier możliwość zmiany preferowanego typu dostarczania przesyłek.

### Technologie:
Backend: Java<br/>
Frontend: JavaFX<br/>
DB: MSSQL

### Diagram ERD:
![Schemat_bazy_danych](https://user-images.githubusercontent.com/63128063/105821108-c3e43200-5fba-11eb-9196-fcdea7e5dfce.png)
### Diagram klas:
![ClassDiagram](https://user-images.githubusercontent.com/48603149/100749380-e277e380-33e4-11eb-9529-e77e3840c400.PNG)
### Diagram przypadków użycia:
![Diagram_przypadków_użycia](https://user-images.githubusercontent.com/48603149/100759532-004b4580-33f1-11eb-838c-5ce47d2cf8df.jpg)
### Diagram aktywności:
![Diagram_aktywności_1](https://user-images.githubusercontent.com/48603149/100757663-f0326680-33ee-11eb-9a80-70da7bbcd7c5.jpg)
### Wzorzec Facade:
![Facade](https://user-images.githubusercontent.com/63128063/105822957-e4ad8700-5fbc-11eb-852c-46652d8322ac.png)
### Wzorzec State:
![State](https://user-images.githubusercontent.com/63128063/105821647-600e3900-5fbb-11eb-9e76-ff5506af7a55.png)
### Wzorzec Factory:
![Factory](https://user-images.githubusercontent.com/63128063/105822201-14a85a80-5fbc-11eb-9be5-7f9483eebf08.png)
### Wzorzec Observer:
![image](https://user-images.githubusercontent.com/63128063/105822608-91d3cf80-5fbc-11eb-8ec5-4d3a55c82ff9.png)
### Wzorzec Adapter:
![image](https://user-images.githubusercontent.com/63128063/105830769-31e22680-5fc6-11eb-9aca-5fb5bd51b06a.png)
### Wzorzec Builder:
![image](https://user-images.githubusercontent.com/63128063/105841289-a7ed8a00-5fd4-11eb-9b2b-03d92b3851cd.png)
