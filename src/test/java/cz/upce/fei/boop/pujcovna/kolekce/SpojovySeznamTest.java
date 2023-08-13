package cz.upce.fei.boop.pujcovna.kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
public class SpojovySeznamTest {

    private static class TestClass {

        int a;

        public TestClass(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "T" + a;
        }

    }
    private final TestClass T1 = new TestClass(1);
    private final TestClass T2 = new TestClass(2);
    private final TestClass T3 = new TestClass(3);
    private final TestClass T4 = new TestClass(4);
    private final TestClass T5 = new TestClass(5);
    private final TestClass T6 = new TestClass(6);
    private final TestClass T7 = new TestClass(7);
    private final TestClass T8 = new TestClass(8);
    private final TestClass T9 = new TestClass(9);
    private SpojovySeznam<TestClass> seznam;

    public SpojovySeznamTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        seznam = new SpojovySeznam<>();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_01_01_VlozPrvni() {
        try {
            seznam.vlozPrvni(T1);
            TestClass expected = T1;
            TestClass result = seznam.dejPrvni();
            assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_01_02_VlozPrvni() {
        try {
            seznam.vlozPrvni(T1);
            seznam.vlozPrvni(T2);
            TestClass[] result = {seznam.dejPrvni(), seznam.dejPosledni()};
            TestClass[] expected = {T2, T1};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_01_03_VlozPrvni() {
        seznam.vlozPrvni(null);
        fail();
    }

    @Test
    public void test_02_01_VlozPosledni() {
        try {
            seznam.vlozPosledni(T1);
            TestClass expected = T1;
            TestClass result = seznam.dejPosledni();
            assertEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_02_02_VlozPosledni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            TestClass[] result = {seznam.dejPrvni(), seznam.dejPosledni()};
            TestClass[] expected = {T1, T2};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_02_03_VlozPosledni() {
        seznam.vlozPosledni(null);
        fail();
    }

    @Test
    public void test_03_01_VlozZaAktualni() {
        try {
            seznam.vlozPrvni(T1);
            seznam.nastavPosledni();
            seznam.vlozZaAktualni(T2);
            TestClass result = seznam.dejZaAktualnim();
            TestClass expected = T2;
            assertEquals(result, expected);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_03_02_VlozZaAktualni() {
        try {
            seznam.vlozPrvni(T1);
            seznam.vlozPrvni(T2);
            seznam.nastavPrvni();
            seznam.vlozZaAktualni(T3);
            TestClass[] result = {seznam.dejZaAktualnim(), seznam.dejPosledni()};
            TestClass[] expected = {T3, T1};
            assertArrayEquals(result, expected);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_03_03_VlozZaAktualni() throws KolekceException {
        seznam.vlozPrvni(T1);
        seznam.vlozZaAktualni(null);
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_03_04_VlozZaAktualni() throws KolekceException {
        seznam.vlozZaAktualni(T1);
        fail();
    }

    @Test
    public void test_03_05_VlozZaAktualni() {
        try {
            seznam.vlozPrvni(T1);
            seznam.nastavPosledni();
            seznam.vlozZaAktualni(T2);
            assertEquals(seznam.size(), 2);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_04_01_DejPrvni() throws KolekceException {
        seznam.dejPrvni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_05_01_DejPosledni() throws KolekceException {
        seznam.dejPosledni();
        fail();
    }

    @Test
    public void test_06_01_DejAktualni() {
        try {
            seznam.vlozPrvni(T1);
            seznam.vlozPrvni(T2);
            seznam.nastavPosledni();
            TestClass result = seznam.dejAktualni();
            TestClass expected = T1;
            assertEquals(result, expected);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_06_02_DejAktualni() throws KolekceException {
        seznam.vlozPrvni(T1);
        TestClass result = seznam.dejAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_06_03_DejAktualni() throws KolekceException {
        TestClass result = seznam.dejAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_07_01_DejZaAktualnim() throws KolekceException {
        seznam.vlozPosledni(T1);
        TestClass result = seznam.dejZaAktualnim();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_07_02_DejZaAktualnim() throws KolekceException {
        TestClass result = seznam.dejZaAktualnim();
        fail();
    }

    @Test
    public void test_08_01_odeberPrvni() {
        try {
            seznam.vlozPrvni(T1);
            seznam.vlozPrvni(T2);
            TestClass[] result = {seznam.odeberPrvni(), seznam.dejPrvni()};
            TestClass[] expected = {T2, T1};
            assertArrayEquals(result, expected);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_08_02_odeberPrvni() throws KolekceException {
        seznam.vlozPosledni(T1);
        seznam.vlozPosledni(T2);
        seznam.nastavPrvni();
        seznam.odeberPrvni();
        seznam.dejAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_08_03_odeberPrvni() throws KolekceException {
        seznam.odeberPrvni();
        fail();
    }

    @Test
    public void test_08_04_odeberPrvni() {
        try {
            seznam.vlozPrvni(T1);
            seznam.vlozPrvni(T2);
            seznam.odeberPrvni();
            assertEquals(seznam.size(), 1);
        } catch (Exception ex) {
            fail();
        }
    }
    
    @Test(expected=KolekceException.class)
    public void test_08_05_odeberPrvni() throws KolekceException{
        seznam.vlozPosledni(T1);
        seznam.odeberAktualni();
        fail();
    }

    @Test
    public void test_09_01_odeberPosledni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            TestClass[] result = {seznam.odeberPosledni(), seznam.dejPosledni()};
            TestClass[] expected = {T2, T1};
            assertArrayEquals(result, expected);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_09_02_odeberPosledni() throws KolekceException {
        seznam.vlozPosledni(T1);
        seznam.vlozPosledni(T2);
        seznam.nastavPosledni();
        seznam.odeberPosledni();
        seznam.dejAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_09_03_odeberPosledni() throws KolekceException {
        seznam.odeberPosledni();
        fail();
    }

    @Test
    public void test_09_04_odeberPosledni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.odeberPosledni();
            assertEquals(seznam.size(), 1);
        } catch (Exception ex) {
            fail();
        }
    }
    
    @Test(expected=KolekceException.class)
    public void test_09_05_odeberPosledni() throws KolekceException{
        seznam.vlozPosledni(T1);
        seznam.odeberAktualni();
        fail();
    }

    @Test
    public void test_10_01_odeberAktualni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.vlozPosledni(T3);
            seznam.nastavPrvni();
            seznam.dalsi();
            TestClass result = seznam.odeberAktualni();
            TestClass expected = T2;
            assertEquals(result, expected);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_10_02_odeberAktualni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.vlozPosledni(T3);
            seznam.nastavPrvni();
            TestClass result = seznam.odeberAktualni();
            TestClass expected = T1;
            assertEquals(result, expected);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_10_03_odeberAktualni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.vlozPosledni(T3);
            seznam.nastavPosledni();
            TestClass result = seznam.odeberAktualni();
            TestClass expected = T3;
            assertEquals(result, expected);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_10_04_odeberAktualni() throws KolekceException {
        seznam.vlozPosledni(T1);
        seznam.vlozPosledni(T2);
        seznam.nastavPosledni();
        TestClass result = seznam.odeberAktualni();
        seznam.dejAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_10_05_odeberAktualni() throws KolekceException {
        seznam.vlozPosledni(T1);
        seznam.odeberAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_10_06_odeberAktualni() throws KolekceException {
        seznam.odeberAktualni();
        fail();
    }

    @Test
    public void test_10_07_odeberAktualni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.vlozPosledni(T3);
            seznam.nastavPosledni();
            seznam.odeberAktualni();
            assertEquals(seznam.size(), 2);
        } catch (Exception e) {
            fail();
        }
    }
    
    @Test(expected=KolekceException.class)
    public void test_10_08_odeberAktualni() throws KolekceException{
        seznam.vlozPosledni(T1);
        seznam.nastavPosledni();
        seznam.odeberAktualni();
        seznam.dejPrvni();
        fail();
    }

    @Test
    public void test_11_01_odeberZaAktualnim() throws KolekceException {
        seznam.vlozPosledni(T1);
        seznam.vlozPosledni(T2);
        seznam.nastavPrvni();
        TestClass[] result = {seznam.odeberZaAktualnim(), seznam.dejPosledni()};
        TestClass[] expected = {T2, T1};
        assertArrayEquals(result, expected);
    }

    @Test
    public void test_11_02_odeberZaAktualnim() throws KolekceException {
        seznam.vlozPosledni(T1);
        seznam.vlozPosledni(T2);
        seznam.vlozPosledni(T3);
        seznam.nastavPrvni();
        TestClass[] result = {seznam.odeberZaAktualnim(), seznam.dejPosledni()};
        TestClass[] expected = {T2, T3};
        assertArrayEquals(result, expected);
    }

    @Test(expected = KolekceException.class)
    public void test_11_03_odeberZaAktualnim() throws KolekceException {
        seznam.odeberZaAktualnim();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_11_04_odeberZaAktualnim() throws KolekceException {
        seznam.vlozPrvni(T1);
        seznam.odeberZaAktualnim();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_11_05_odeberZaAktualnim() throws KolekceException {
        seznam.vlozPrvni(T1);
        seznam.nastavPrvni();
        seznam.odeberZaAktualnim();
        fail();
    }

    @Test
    public void test_11_06_odeberZaAktualnim() throws KolekceException {
        seznam.vlozPosledni(T1);
        seznam.vlozPosledni(T2);
        seznam.nastavPrvni();
        seznam.odeberZaAktualnim();
        assertEquals(seznam.size(), 1);
    }

    @Test
    public void test_12_01_size() {
        assertEquals(seznam.size(), 0);
    }

    @Test
    public void test_12_02_size() {
        seznam.vlozPosledni(T1);
        seznam.vlozPrvni(T2);
        assertEquals(seznam.size(), 2);
    }

    @Test
    public void test_13_01_zrus() {
        seznam.vlozPosledni(T1);
        seznam.zrus();
        assertEquals(seznam.size(), 0);
    }

    @Test
    public void test_14_01_nastavPrvni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.nastavPrvni();
            assertEquals(T1, seznam.dejAktualni());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_14_02_nastavPrvni() throws KolekceException {
        seznam.nastavPrvni();
        fail();
    }

    @Test
    public void test_15_01_nastavPosledni() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.nastavPosledni();
            assertEquals(T2, seznam.dejAktualni());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_15_02_nastavPosledni() throws KolekceException {
        seznam.nastavPosledni();
        fail();
    }

    @Test
    public void test_16_01_dalsi() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.nastavPrvni();
            seznam.dalsi();
            assertEquals(T2, seznam.dejAktualni());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_16_02_dalsi() throws KolekceException {
        seznam.vlozPosledni(T1);
        seznam.nastavPrvni();
        seznam.dalsi();
        fail();
    }

    @Test
    public void test_17_01_jeDalsi() {
        try {
            seznam.vlozPosledni(T1);
            seznam.nastavPrvni();
            assertEquals(false, seznam.jeDalsi());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_17_02_jeDalsi() {
        try {
            seznam.vlozPosledni(T1);
            seznam.vlozPosledni(T2);
            seznam.nastavPrvni();
            assertEquals(true, seznam.jeDalsi());
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_18_01_iterator() {
        seznam.vlozPosledni(T4);
        seznam.vlozPosledni(T6);
        Iterator<TestClass> it = seznam.iterator();
        TestClass[] expected = {T4, T6};
        TestClass[] result = {it.next(), it.next()};
        assertArrayEquals(expected, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_18_02_iterator() {
        seznam.vlozPosledni(T4);
        seznam.vlozPosledni(T6);
        Iterator<TestClass> it = seznam.iterator();
        it.next();
        it.next();
        it.next();
    }

    @Test
    public void test_18_03_iterator() {
        seznam.vlozPosledni(T4);
        seznam.vlozPosledni(T6);
        seznam.vlozPosledni(T8);
        Iterator<TestClass> it = seznam.iterator();
        int velikost = 0;
        while (it.hasNext()) {
            velikost++;
            it.next();
        }
        assertEquals(3, velikost);
    }
}
