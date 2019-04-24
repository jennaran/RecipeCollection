# Käyttöohje

Ohjelman jar-tiedoston voi ladata [täältä](https://github.com/jennaran/ot-harjoitustyo/releases)

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar RecipeCollection-1.0-SNAPSHOT.jar
```

### Aloitusnäkymä

<img src="">

Sisään voi kirjautua syöttämällä olemassa oleva käyttäjänimi ja siihen liittyvä salasana vasemmalla olevaan kirjautumislaatikkoon ja painamalla sitten _Sign in_ -nappia.

Jos käyttäjänimeä ei löydy tai salasana on väärä, ilmestyy ruutuun virheviesti.

<img src="">

Uuden käyttäjän voi luoda painamalla oikealla olevaa _Create An Account_ -nappia, jolla päästään käyttäjänluomisnäkymään.

### Käyttäjänluomisnäkymä

<img src="">

Käyttäjä voi luoda uuden käyttäjätunnuksen syöttämällä tekstikenttiin uniikin käyttäjänimen ja salasanan 
ja painamalla sitten _Sing up_ -nappulaa. 
Näkymä vaihtuu aloitusnäkymäksi, jos käyttäjä tunnus luodaan onnistuneesti.

Jos käyttäjätunnus on jo käytössä, ilmestyy ruutuun virheviesti.

<img src="">

Ylhäällä olevasta _Back_ -nappulasta pääsee takaisin aloitusnäkymään.

### Päänäkymä

<img src="">

Ylhäällä käyttäjävalikkoa klikkaamalla voidaan kirjautua ulos tai poistaa tili.

<img src="">

Uusi resepti voidaan luoda painamalla oikella olevaa _Add A New Recipe_ -nappulaa.

Vasemmalla on listattu kaikki käyttäjän lisäämät reseptit. 
Listan yläpuolella on tekstikenttä, jolla voidaan etsiä tiettyjä reseptejä/reseptiä 
kirjoittamalla siihen reseptin nimi tai osa nimestä ja painamalla _Search_ -nappulaa.
Kaikki reseptit saa taas näkyviin tyhjentämällä kenttä ja painamalla uudelleen _Search_.

<img src="">

Reseptin nimeä painalla päästään tutkimaan reseptiä.

### Reseptinuomisnäkymä

<img src="">

*Name:*

Ylhäällä olevaan tektikenttään kirjoitetaan uuden reseptin nimi. Nimen tulee olla uniikki.

*Ingredients*

Aineksia lisätään kirjoittamalla uusi aines ja sen määrä (esim. jauhoja 5dl) vasemmalla olevaan tekstikenttään ja painamalla _Add_ -nappulaa.
Aineksen saa poistettua kirjoittamalla se uudestaan tekstikenttään ja painamalla _Delete_ -nappia.

*Instructions*

Oikealla olevaan tekstikenttään voi lisätä ohjeet haluamassaan muodossa. 

*Napit*

_Back_ -napista pääsee takaisin päänäkymään.

_Save_ -napista resepti tallennetaan, jos mikään kenttä ei ole tyhjä ja nimi on uniikki. 

<img src="">

### Reseptinäkymä

<img src="">

_Back_ -nappulasta pääsee takaisin päänäkymään.

