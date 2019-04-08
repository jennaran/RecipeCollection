# Arkkitehtuurikuvaus

## Rakenne

Tämä ohjelma noudattaa kolmekerroksista kerrosarkkitehtuuria. 
Pakkaus ui sisältää JavaFX:llä toteutetun graaffisen käyttöliittymän, 
pakkaus domain sisältää service-luokan, joka toteuttaa ohjelman kannalta merkittäviä 
toimintoja. Domain sisältää myös luokat User ja Recipe. 
Ohjelmassa on lisäksi pakkaus dao, joka sisältää RecipeDAO ja UserDAO -rajapinnat, 
sekä ne perivät luokat DerbyRecipeDAO ja DerbyUserDAO.

## Käyttöliittymä

Käyttöliittymä sisältää viisi eri näkymää.
- kirjautumisnäkymä
- näkymä uuden käyttäjän luomiseen
- kotisivu sisäänkirjautuessa
- reseptin tarkastelu sivu
- uuden reseptin luomis sivu

Jokainen näkymä on toteutettu stage-oliona, joka luodaan kerran ohjelmaa suorittaessa 
ja tallennetaan sitten luokan konstruktoriksi. 
Näkymää vaihdetaan asettamalla uusi scene-olio stageen.

Käyttöliittymällä on myös konstruktori Service, jolle toimintojen suoritus on 
ulkoistettu. Konstruktori luodaan käyttöliittymän alussa init() -metodissa.

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostaa User -luokka, joka kuvaa käyttäjää ja 
Recipe-luokka, joka kuvaa reseptejä. Käyttäjä voi omistaa monta yksityistä reseptiä.

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/taulujen_yhteydet.png">

Ohjelman toiminnallisuuksista vastaa Service olio, jolla on metodit kaikille 
sovelluksen toiminnallisuuksille, kuten uuden käyttäjän luomiselle, 
käyttäjän poistamiselle ja reseptien listaamisella.
Service oliolla on konstruktorit RecipeDAO ja UserDAO, joiden avulla se toteuttaa 
ohjelman toiminnallisuuksia. 

TodoServicen ja ohjelman muiden osien suhdetta kuvaava luokka- ja pakkauskaavio:

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/pakkauskaavio.png">

## Tietojen pysyväistallennus

Pakkauksen dao luokat DerbyRecipeDao ja DerbyUserDao huolehtivat tietojen tallettamisesta tiedostoihin.



