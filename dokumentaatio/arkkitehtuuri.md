# Arkkitehtuurikuvaus

## Rakenne

Tämä ohjelma noudattaa kolmekerroksista kerrosarkkitehtuuria. 
Pakkaus [ui](https://github.com/jennaran/ot-harjoitustyo/tree/master/RecipeCollection/src/main/java/ui) sisältää JavaFX:llä toteutetun graaffisen käyttöliittymän, 
pakkaus domain sisältää _Service_-luokan, joka toteuttaa ohjelman kannalta merkittäviä 
toimintoja. [Domain](https://github.com/jennaran/ot-harjoitustyo/tree/master/RecipeCollection/src/main/java/domain) sisältää myös luokat User ja Recipe. 
Ohjelmassa on lisäksi pakkaus [dao](https://github.com/jennaran/ot-harjoitustyo/tree/master/RecipeCollection/src/main/java/dao), joka sisältää _RecipeDAO_ ja _UserDAO_ -rajapinnat, 
sekä ne perivät luokat _DerbyRecipeDAO_ ja _DerbyUserDAO_.

## Käyttöliittymä

Käyttöliittymä sisältää viisi eri näkymää.
- kirjautumisnäkymä
- näkymä uuden käyttäjän luomiseen
- kotisivu sisäänkirjautuessa
- reseptin tarkastelu sivu
- uuden reseptin luomis sivu

Jokainen näkymä on toteutettu stage-oliona.
Näkymää vaihdetaan asettamalla uusi scene-olio stageen, joka löytyy luokan kostruktorista.

Käyttöliittymällä on myös konstruktori _Service_, jolle toimintojen suoritus on 
ulkoistettu. Konstruktori luodaan käyttöliittymän alussa _init()_ -metodissa.

## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostaa [User](https://github.com/jennaran/ot-harjoitustyo/blob/master/RecipeCollection/src/main/java/domain/User.java) -luokka, joka kuvaa käyttäjää ja 
[Recipe](https://github.com/jennaran/ot-harjoitustyo/blob/master/RecipeCollection/src/main/java/domain/Recipe.java)-luokka, joka kuvaa reseptejä. Käyttäjä voi omistaa monta yksityistä reseptiä.

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/taulujen_yhteydet.png">

Ohjelman toiminnallisuuksista vastaa [Service](https://github.com/jennaran/ot-harjoitustyo/blob/master/RecipeCollection/src/main/java/domain/Service.java) olio, jolla on metodit kaikille 
sovelluksen toiminnallisuuksille, kuten uuden käyttäjän luomiselle, 
käyttäjän poistamiselle ja reseptien listaamisella.
Service oliolla on konstruktorit _RecipeDAO_ ja _UserDAO_, joiden avulla se toteuttaa 
ohjelman toiminnallisuuksia. 

Servicen ja ohjelman muiden osien suhdetta kuvaava luokka- ja pakkauskaavio:

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/pakkauskaavio.png">

## Tietojen pysyväistallennus

Pakkauksen dao luokat [DerbyRecipeDao](https://github.com/jennaran/ot-harjoitustyo/blob/master/RecipeCollection/src/main/java/dao/DerbyRecipeDAO.java) ja [DerbyUserDao](https://github.com/jennaran/ot-harjoitustyo/blob/master/RecipeCollection/src/main/java/dao/DerbyUserDAO.java) huolehtivat tietojen tallettamisesta tiedostoihin.
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

Kirjautumis näkymän _Sign In_ -nappi laittaa liikkeelle seuraavan ketjun:

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/logIn.png">

Nappulan painaminen kutsuu Service luokan _login_-metodia antaen sille parametreina käyttäjän syöttämät arvot käyttäjänimi ja salasana. Käyttäen DerbyUserDAO:n metodia _searchByUsername_ antaen sille parametrina käyttäjänimi, etsitään löytyykö käyttäjien joukosta kyseisen nimistä käyttäjää. Jos käyttäjä löytyy palautetaan User-olio sitäkutsuneeseen metodiin. Muuten palautetaan null. Jos palautettu käyttäjä ei ole **null**, verrataan käyttäjän antamaa salasanaa palautetun käyttäjän salasanaan. Jos salasanat täsmää, palautetaan **true**. Muissa tapauksissa palautetaan **false**.

### Uuden käyttäjän luominen sekvenssikaaviona

Uudenkäyttäjän luomisnäkymän _Sign up_ -nappi laittaa liikkeelle seuraavan ketjun:

<img src="https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/newUser.png">

Nappulan painaminen kutsuu Service luokan _createNewUser_-metodia antaen sille parametreina käyttäjän syöttämät arvot käyttäjänimi ja salasana. Tutkitaan, että kumpikaan arvo ei ole tyhjä tai sisällä kiellettyä mertkkiä ";". Käyttäen DerbyUserDAO:n metodia _searchByUsername_ antaen sille parametrina käyttäjänimi, etsitään löytyykö käyttäjien joukosta kyseisen nimistä käyttäjää. Jos kysely palauttaa **null**, voidaan luoda uusi User-olio käyttäjän antamilla arvoilla. Tallennetaan juuri luotu käyttäjä tiedostoon kutsumalla DerbyUserDAO:n metodia _create_ antamalla sille parametriksi uusi käyttäjä. Jos tallennus onnistuu, palutetaan **true**. Muissa tapauksissa palautetaan **false**.

### Muut toiminallisuudet

Muut toiminnallisuudet toimivat samalla tavalla. Käyttäjä laittaa liikkeelle tapahtumaketjun jotain nappulaa painamalla, sen seurauksena kutsutaan sopivaa Service-luokan metodia, joka puolestaan kutsuu RecipeDAO:n tai UserDAO:n metodeja. Kontrollin palautuessa käyttöliittymälle, vaihdetaan tarvittaessa näkymää tai päivitetään nykyinen näkymä.

# Ohjelman rakenteeseen jääneet heikkoudet

[RecipeServiceUi](https://github.com/jennaran/ot-harjoitustyo/blob/master/RecipeCollection/src/main/java/ui/RecipeCollectionUi.java)-luokassa oleva koodi vaatisi siistimistä. Metodit ovat pistkiä ja sisältävät hieman kikkailua. 
