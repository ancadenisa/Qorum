# Qorum

Setup proiect:

1.Pasii de la [1] de la Local Installation
2.Install baza de date [2]
3.Install pgAdmin -- este un tool de adminstrare al bazei de date
4.Install python
4.Git clone la proiect
5.In root-ul proiectului rulati urmatoarele
	npm install
	*va instala toate dependentele listate in fisierul package.json
	bower install
	*va instala toate dependentele de frontend listate in fisierul bower.json
6.Uitandu-va in application-dev.yml o sa observati ca aveti acolo configurarile 
pentru baza de date(la datasource). 
	Url-ul ne spune ca baza va fi disponibila la portul 5432 si ca schema la care 
	se va contecta se numeste qorum. Asa ca din pgAdmin,"New database" & name it `qorum`.
	Asigurati-va ca username-ul si parola, din configurarile datasource-ului din yml corespund
	cu ce aveti voi
7. run com.qorum.Application.java & it should work.	


[1] https://jhipster.github.io/installation.html
[2] http://www.postgresql.org/download/
[3] http://www.pgadmin.org/download/