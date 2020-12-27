package org.apache.openjpa.util;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.apache.openjpa.util.entity.ProxyManagerImplEntity;

import java.util.*;


@RunWith(Parameterized.class)
public class CopyArrayTest {

    private ProxyManagerImpl proxyManager;
	private ProxyManagerImplEntity entity; 
	private Object expectedResult;

    public CopyArrayTest(ProxyManagerImplEntity entity, Object expectedResult) {
    	this.entity = entity;
    	this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameters(){
        
        Random r = new Random();
        
        /*CATEGORY PARTIOTION:
         * {Null, Valid, Invalid}
         * copyArray(null)
         * copyArray(Float[])
         * copyArray(Nonvalid)
         * */
        
        ProxyManagerImplEntity entityNull = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityNonValid = new ProxyManagerImplEntity();
        ProxyManagerImplEntity entityList = new ProxyManagerImplEntity();

        return Arrays.asList(new Object[][] {
        	
        	//suite minimale
        	{entityNull.initializeEntityNull(null) , null},
        	// TODO guarda il todo di copyArrayTest
        	{entityNonValid.initializeEntityNonValid(), UnsupportedException.class}, 
            {entityList.initializeEntityArray(r.nextFloat()), entityList.getObject()}
    	});

    }


    @Before
    public void setUp(){
        this.proxyManager = new ProxyManagerImpl();
    }

    @Test
    public void copyCustomTest() {
    	
    	Object result = null;
    	try {
    		result = this.proxyManager.copyArray(this.entity.getObject());
    		Assert.assertArrayEquals((Object[]) this.expectedResult, (Object[]) result);
    	}catch (UnsupportedException e) {
    		Assert.assertEquals(this.expectedResult, e.getClass());
		}
        
    }

}
