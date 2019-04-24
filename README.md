# RecipeCollection

Sovelluksen avulla käyttäjä voi pitää kirjaa pitäneistään resepteistä. 
Sovellusta voi käyttää usea käyttäjä, mutta sisältö on kaikille henkilökohtainen.


## Dokumentaatio

[Käyttöohje](https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/alustava_maarittelydokumentti.md)

[Tuntikirjanpito](https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuuri](https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Release](https://github.com/jennaran/ot-harjoitustyo/releases)

## Komentoriviltä suoritettavat toimenpiteet

### Testaus

```
mvn test
```

### Testikattavuusraportti

```     
mvn test jacoco:report
```

##### Rapotti avataan antamalla juuressa käsky:

```
open target/site/jacoco/index.html
```

### Checkstyle

```
mvn jxr:jxr checkstyle:checkstyle
```

##### Rapotti avataan antamalla juuressa käsky:

```
open target/site/checkstyle.html
```

### Jarin generointi

```
mvn package 

```

##### Jarin suorittaminen

Target hakemistosta löytyy jar-tiedosto RecipeCollection-1.0-SNAPSHOT.jar

Sen voi suorittaa käskyllä:

```
java -jar RecipeCollection-1.0-SNAPSHOT.jar
```

### JavaDocin generointi

```
mvn javadoc:javadoc
```

##### JavaDockin avaaminen

```
open target/site/apidocs/
```
