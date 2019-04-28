# Vaatimusmäärittely

## Sovelluksen tarkoitus

Käyttäjä voi sovelluksen avulla pitää kirjaa lempiresepteistä. 
Sovellusta voi käyttää usea rekisteröitynyt käyttäjä, 
mutta sovelluksen sisältö on käyttäjäkohtainen.

## Käyttäjät

Koska sisältö on yksityistä on sovelluksessa vain normaali käyttäjätila.

<img src = "https://github.com/jennaran/ot-harjoitustyo/blob/master/dokumentaatio/Kuvat/RC_vaatimusmaare_kuva1.png">

## Toiminnallisuudet

Aloitussivu

- Nappi tunnuksen luomisnäkymään
- Voi kirjautua sisään
  - Täytä käyttäjätunnus ja salasana
  - Kirjaudu sisään painamalla nappia
  - Jos salasana tai käyttäjätunnus on väärin, saat virheviestin

Tunnuksenluomisnäkymä

- Täydennä uniikki uniikki käyttäjänimi ja salasana
- Luo tili painamalla nappia
- back-nappulaa klikkaamalla pääsee aloitussivulle

Sovellusnäkymä kirjauttumisen jälkeen

- Vasemmalla näet jo kirjaamiesi reseptien otsikot
- Otsikkoa klikkaamalla pääset reseptinäkymään
- Reseptejä voi etsiä ylhäältä löytyvästä hakukentästä
- Oikealla olevasta "Add A New Recipe" -nappulasta pääsee reseptinluomisnäkymään
- Ylhäällä vasemmalla lukee käyttäjänimi, jota klikkaamalla aukeaa valikko:
 - uloskirjautumiseen
 - tilin poistamiseen

Reseptinäkymä

- Ylhäällä lukee reseptin otsikko, vasemmalla ainekset ja oikealla ohje
- Otsikon vieressä on edit-nappi, jolla reseptiä pääsee muokkaamaan ja delete-nappi, jolla kyseinen resepti voidaan poistaa
- Muokkausnäkymä on sama kuin reseptin luomisnäkymä

Reseptin luomisnäkymä

- Samaan tyyliin kuin reseptinäkymän muokkaustila, mutta kaikki kohdat on tyhjiä
