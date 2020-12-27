package org.apache.openjpa.util;

import org.apache.openjpa.util.entity.ProxyManagerImplEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;


@RunWith(Parameterized.class)
public class CopyCustomTest {

    private ProxyManagerImpl proxyManager;
    private ProxyManagerImplEntity entity;
    private Object expectedResult;

    public CopyCustomTest(ProxyManagerImplEntity entity, Object expectedResult) {
        this.entity = entity;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameters(){
    	
    	/*CATEGORY PARTIOTION:
         * Object origin -->{Null, Valid, Invalid}
         * 
         * newCustomProxy(null)
         * newCustomProxy(new Date())
         * newCustomProxy(NonValid)
         * */
    	
    	Random r = new Random();
        
        
        ProxyManagerImplEntity entityNull = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityNonValid = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityDate = new ProxyManagerImplEntity();
        
        
        ProxyManagerImplEntity entityValid = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityMap = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityGregorian = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityProxyDate = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityList = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityManageable = new ProxyManagerImplEntity();
        

        return Arrays.asList(new Object[][] {
    		
        	//Suite minimale
        	{entityNull.initializeEntityNull(null), null},
        	/*TODO togliere il test sul NonValid.
        	 * ATTENZIONE: l'entity NonValid permette di essere un buon test 
        	 * anche se in futuro la classe venisse aggiornata in modo
        	 * da permettere al metodo di copiare il tipo di oggetto String.
        	 * Il fatto è che tanto la classe Valid va usata, tanto vale usare
        	 * anche la classe non Valid
        	 * */
            {entityNonValid.initializeEntityNonValid(), null}, 
            {entityDate.initializeEntityDate(new Date()), entityDate.getObject()},
            /*TODO gestisci l'entity per usare initializeGenricObject come predefinito
             * per gli altri metodi che richiedono solo un parametro di tipo Object*/
            //{entityNonValid.initializeGenricObject(new String()), null},

            //line, branch and mutation coverage
//            {entityManageable.initializeEntityManageable(), null},
//            {entityValid.initializeEntityValid(r.nextInt(), true), entityValid.getObject()}, 
//            {entityMap.initializeEntityMap(r.nextInt(), r.nextInt(), false), entityMap.getObject()},
//            {entityGregorian.initializeEntityGregorian(new GregorianCalendar()), entityGregorian.getObject()},
//            {entityProxyDate.initializeEntityProxyDate(new ProxyManagerImpl().newDateProxy(Date.class)), entityProxyDate.getObject()},
//            {entityList.initializeEntityList(r.nextInt()), entityList.getObject()}
    	});

    }

    

    @Before
    public void setUp(){
        this.proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void copyCustomTest() {
        Object result = this.proxyManager.copyCustom(this.entity.getObject());

       
        Assert.assertEquals(this.expectedResult, result);
      

    }

}
