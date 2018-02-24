/**
 *
 */
package ubu.gii.dass.test.c01;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

import static org.junit.Assert.*;

/**
 * @author Iván Iglesias Cuesta
 */
public class ReusablePoolTest {

    private ReusablePool pool = null;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        pool = ReusablePool.getInstance();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        pool = null;
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        assertNotNull(pool);
        ReusablePool pool2 = ReusablePool.getInstance();
        assertNotNull(pool2);
        assertEquals(pool, pool2);
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
     */
    @Test
    public void testAcquireReusable() {
        Reusable reusable;
        Reusable reusable2;
        Reusable reusable3;
        int reusables = 0;
        try {
            reusable = pool.acquireReusable();
            assertNotNull(reusable);
            reusables++;
            reusable2 = pool.acquireReusable();
            assertNotNull(reusable2);
            reusables++;
            reusable3 = pool.acquireReusable();
            assertNotNull(reusable3);
            reusables++;
        } catch (NotFreeInstanceException e) {
            assertEquals(2, reusables);
        }
    }

    /**
     * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
     */
    @Test
    public void testReleaseReusable() {
        Reusable reusable = null;
        Reusable reusable2 = null;
        int releases = 0;
        try {
            reusable = pool.acquireReusable();
            reusable2 = pool.acquireReusable();
        } catch (NotFreeInstanceException e) {
            fail();
        }
        try {
            pool.releaseReusable(reusable);
            releases++;
            pool.releaseReusable(reusable2);
            releases++;
            pool.releaseReusable(reusable);
            releases++;
        } catch (DuplicatedInstanceException e) {
            assertEquals(2, releases);
        }
    }

}
