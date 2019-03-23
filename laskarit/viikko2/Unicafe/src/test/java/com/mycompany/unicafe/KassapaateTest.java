
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    Kassapaate kassa;
    Maksukortti kortti;
    Maksukortti kortti2;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
        kortti2 = new Maksukortti(100);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kassa!=null);      
    }
    
    @Test 
    public void konstruktoriAsettaaArvotOikein() {  
        assertEquals(100000, kassa.kassassaRahaa()); 
        assertEquals(0, kassa.edullisiaLounaitaMyyty()+kassa.maukkaitaLounaitaMyyty());      
    }

    @Test
    public void edullisestiSyodessaRahaSiirtyyOikein() {
        kassa.syoEdullisesti(500);
        
        assertEquals(100240, kassa.kassassaRahaa()); 
        assertEquals(260, kassa.syoEdullisesti(500)); 

    }
    
    @Test
    public void maukkaastiSyodessaRahaSiirtyyOikein() {
        kassa.syoMaukkaasti(500);
        
        assertEquals(100400, kassa.kassassaRahaa()); 
        assertEquals(100, kassa.syoMaukkaasti(500)); 

    }
    
    @Test
    public void edullistenLounaidenMaaraKasvaaOikein() {
        kassa.syoEdullisesti(500);
        kassa.syoEdullisesti(300);
        
        assertEquals(2, kassa.edullisiaLounaitaMyyty()); 
    }
    
    @Test
    public void maukkaidenLounaidenMaaraKasvaaOikein() {
        kassa.syoMaukkaasti(500);
        kassa.syoMaukkaasti(600);
        kassa.syoMaukkaasti(600);
        
        assertEquals(3, kassa.maukkaitaLounaitaMyyty()); 
    }
    
    @Test
    public void rahaaEiTarpeeksiEdulliseen() {
        kassa.syoEdullisesti(100);
        
        assertEquals(100000, kassa.kassassaRahaa()); 
        assertEquals(100, kassa.syoEdullisesti(100));
        assertEquals(0, kassa.edullisiaLounaitaMyyty()); 

    }
    
    @Test
    public void rahaaEiTarpeeksiMaukkaaseen() {
        kassa.syoMaukkaasti(100);
        
        assertEquals(100000, kassa.kassassaRahaa()); 
        assertEquals(100, kassa.syoMaukkaasti(100));
        assertEquals(0, kassa.maukkaitaLounaitaMyyty()); 

    }
    
    @Test
    public void edullisenKorttimaksuSiirtaaOikeanSummanRahaa() {
        assertEquals(true,kassa.syoEdullisesti(kortti));
    
    }
    
    @Test
    public void maukkaanKorttimaksuSiirtaaOikeanSummanRahaa() {
        assertEquals(true,kassa.syoMaukkaasti(kortti));
    
    }
    
    @Test
    public void edullistenLounaidenMaaraKasvaaOikeinKorttimaksussa() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        
        assertEquals(2, kassa.edullisiaLounaitaMyyty()); 
    }

    @Test
    public void maukkaidenLounaidenMaaraKasvaaOikeinKorttimaksussa() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        
        assertEquals(2, kassa.maukkaitaLounaitaMyyty()); 
    }
    
    @Test
    public void rahaaEiTarpeeksiEdulliseenKorttimaksuun() {
        kassa.syoEdullisesti(kortti2);
        
        assertEquals(100, kortti2.saldo()); 
        assertEquals(false, kassa.syoEdullisesti(kortti2));
        assertEquals(0, kassa.edullisiaLounaitaMyyty()); 

    }
    
    @Test
    public void rahaaEiTarpeeksiMaukkaaseenKorttimaksuun() {
        kassa.syoMaukkaasti(kortti2);

        assertEquals(100, kortti2.saldo()); 
        assertEquals(false, kassa.syoMaukkaasti(kortti2));
        assertEquals(0, kassa.maukkaitaLounaitaMyyty()); 

    }
    
    @Test
    public void rahaMaaraEiMuutuKassassaKortillaOstettaessa() {
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        
        assertEquals(100000, kassa.kassassaRahaa()); 
    }    
    
   
    @Test
    public void kortilleLadattaessaSaldoJaKassaSummaMuuttuuOikein() {
        kassa.lataaRahaaKortille(kortti, 100);
        
        assertEquals(100100, kassa.kassassaRahaa()); 
        assertEquals(1100, kortti.saldo()); 

    }
    
    @Test
    public void ladattaessaNegatiivinenArvoSaldoEiMuutu() {
        kassa.lataaRahaaKortille(kortti, -10);
        
        assertEquals(100000, kassa.kassassaRahaa()); 
        assertEquals(1000, kortti.saldo()); 

    }
}
