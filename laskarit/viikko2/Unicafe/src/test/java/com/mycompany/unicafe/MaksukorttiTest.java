package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void konstruktoriAsettaaSaldonOikein() {  
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(90);
        assertEquals("saldo: 1.0", kortti.toString());
    }
    
    @Test   
    public void kortiltaVoiOttaaRahaa() {
        kortti.lataaRahaa(990);
        kortti.otaRahaa(200);
        
        assertEquals("saldo: 8.0", kortti.toString());

    }
    
    @Test   
    public void kortiltaEiVoiOttaaRahaaJosSaldoaEiOle() {
        kortti.lataaRahaa(990);
        kortti.otaRahaa(10000);
        
        assertEquals("saldo: 10.0", kortti.toString());

    }
    
    @Test
    public void palauttaaTrueKunRahatRiittaa() {
        kortti.lataaRahaa(990);
        
        assertEquals(true, kortti.otaRahaa(100));
    }
    
    @Test
    public void palauttaaFalseKunRahatEiRiita() {
        assertEquals(false, kortti.otaRahaa(100));
    }
}
