# Qorum

### Setup proiect      

1. Urmati pasii de pe pagina : [JHipster][1] din Local Installation  
2. Install baza de date : [PostgreSQL][2]  
3. Install [pgAdmin][3] -- este un tool de adminstrare al bazei de date  
4. Install python  
5. Git clone la proiect  
6. In root-ul proiectului rulati urmatoarele npm install *va instala toate dependentele listate in fisierul package.json bower install *va instala toate dependentele de frontend listate in fisierul bower.json  
7. Uitandu-va in application-dev.yml o sa observati ca aveti acolo configurarile pentru baza de date(la datasource). Url-ul ne spune ca baza va fi disponibila la portul 5432 si ca schema la care se va contecta se numeste qorum. Asa ca din pgAdmin,"New database" & name it qorum. Asigurati-va ca username-ul si parola, din configurarile datasource-ului din yml corespund cu ce aveti voi.  
8. Run com.qorum.Application.java & it should work.  

[1]: https://jhipster.github.io/installation.html "JHipster"
[2]: http://www.postgresql.org/download/ "PostgreSQL"
[3]: http://www.pgadmin.org/download/ "PGAdmin"

### Version
0.0.1

License
----

Private license 
