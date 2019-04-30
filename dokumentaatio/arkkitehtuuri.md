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

Jokainen näkymä on toteutettu stage-oliona.
Näkymää vaihdetaan asettamalla uusi scene-olio stageen, joka löytyy luokan kostruktorista.

Käyttöliittymällä on myös konstruktori Service, jolle toimintojen suoritus on 
ulkoistettu. Konstruktori luodaan käyttöliittymän alussa _init()_ -metodissa.

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostaa User -luokka, joka kuvaa käyttäjää ja 
Recipe-luokka, joka kuvaa reseptejä. Käyttäjä voi omistaa monta yksityistä reseptiä.

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/taulujen_yhteydet.png">

Ohjelman toiminnallisuuksista vastaa Service olio, jolla on metodit kaikille 
sovelluksen toiminnallisuuksille, kuten uuden käyttäjän luomiselle, 
käyttäjän poistamiselle ja reseptien listaamisella.
Service oliolla on konstruktorit _RecipeDAO_ ja _UserDAO_, joiden avulla se toteuttaa 
ohjelman toiminnallisuuksia. 

TodoServicen ja ohjelman muiden osien suhdetta kuvaava luokka- ja pakkauskaavio:

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/pakkauskaavio.png">

## Tietojen pysyväistallennus

Pakkauksen dao luokat DerbyRecipeDao ja DerbyUserDao huolehtivat tietojen tallettamisesta tiedostoihin.
Sovelluksessa niitä käytetään niiden rajapintojen kautta.

### Tiedostot

Käyttäjien ja reseptien tiedot tallennetaan erillisiin tiedostoihin.  
Käyttäjät tallennetaan tiedostoon nimeltä **user.txt** ja reseptit tiedostoon **recipe.txt**.
Tiedostot on määritelty RecipeCollectionUi:n metodissa _init()_.

#### Käyttäjien tallennusmuoto

```
username;password
```

Käyttäjänimi ja salasana on erotettu puolipisteellä. Jokainen käyttäjä tallennetaan eri riveille.

### Reseptien tallennusmuoto

```
name;ingredient1_ingredient2;instruction1_instruction2;user's name
```

Reseptit tallennetaan eri riveille. Ensin tallennetaan reseptin nimi, siihen tarvittavat raaka-aineet, ohjeet ja reseptin omistajan käyttäjänimi. Kaikki erotetaan toisistaan puolipisteillä. 
Raaka-aineiden ja ohjeiden väliset alaviivat mahdollistavat niiden selkeämmän listauksen
 reseptintarkastelunäkymässä.

## Päätoiminnallisuudet

### Käyttäjän kirjautuminen sekvenssikaaviona

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/logIn.png">

Kirjautumis näkymän _Sign In_ -nappi laittaa liikkeelle seuraavan ketjun:

### Uuden käyttäjän luominen sekvenssikaaviona

Uudenkäyttäjän luomisnäkymän _Sign up_ -nappi laittaa liikkeelle seuraavan ketjun:

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/newUser.png">

# Ohjelman rakenteeseen jääneet heikkoudet


