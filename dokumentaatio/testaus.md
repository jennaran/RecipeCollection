# Testausdokumentti

Domain pakkauksen luokat ja Dao pakkauksen luokat on testattu yksikkötestin
 ja Ui pakkauksen luokka manuaalisesti.

## Yksikkö- ja integraatiotestaus

### DOMAIN

Luokan Service metodeita, jotka liittyvät käyttäjiin on testattu testiluokassa ServiceUserTest.
Resepteihin liittyviä metodeita on testattu testiluokassa ServiceRecipeTest. 

Service-olion luominen vaatii UserDAO- ja RecipeDAO-olioita. Testasta varten on luotu luokat
FakeRecipeDAO ja FakeUserDAO, jotka simuloivat RecipeDAO ja UserDAO rajapintoja. 

User- ja Recipe-luokat on testattu testiluokissa UserTest ja RecipeTest.

### DAO

Daot on testattu testiluokissa DerbyRecipeDAOTest ja DerbyUserDAOTest. Tietojen tallentamiseen 
on käytetty JUnitin TemporaryFolderia.

### UI

RecipeCollectionUi on testattu manuaalisesti sijoittamalla tulostus-käskyjä koodin sekaan
varmistamaan ohjelman oikeanlaisen kulun. Lisäksi sovelluksen kaikki toiminnot on testattu
syöttämällä virheellistä tietoa ja varmistamalla ettei ongelmia synny.

## Testikattavuus

<img src = "https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/RC_uusiKayttajaVirhe2.png">

Testaamatta on jäänyt kaikki poikkeukset.

##
