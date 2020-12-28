package org.apache.openjpa.util;

import org.apache.openjpa.util.entity.ProxyManagerImplEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Timestamp;
import java.util.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

@RunWith(Parameterized.class)
public class NewCustomProxyTest {
	
    private ProxyManagerImpl proxyManager;
    private ProxyManagerImplEntity entity;
    private Object expectedResult;
    
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    
    public NewCustomProxyTest(ProxyManagerImplEntity entity, Object expectedResult) {
        this.entity = entity;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameters(){
        Random r = new Random();
        
        /*CATEGORY PARTIOTION:
         * Object origin -->{Null, Valid, Invalid}
         * boolean autooff -->{True, False}
         * 
         * newCustomProxy(null, true)
         * newCustomProxy(new Date(), false)
         * newCustomProxy(NonValid, false)
         * */
        
        /*MUTATION TESTING:
         * 
         * Line 325: mutation equivalent (Weak mutation & Strong Mutation)*/
        
        ProxyManagerImplEntity entityNull = new ProxyManagerImplEntity(true);
        ProxyManagerImplEntity entityNonValid = new ProxyManagerImplEntity(false);
        ProxyManagerImplEntity entityDate = new ProxyManagerImplEntity(false);

        ProxyManagerImplEntity entityFinal = new ProxyManagerImplEntity(true);
        ProxyManagerImplEntity entityValid = new ProxyManagerImplEntity(true);
        ProxyManagerImplEntity entityMap = new ProxyManagerImplEntity(true);
        ProxyManagerImplEntity entitySortedMap = new ProxyManagerImplEntity(true);
        ProxyManagerImplEntity entitySortedSet = new ProxyManagerImplEntity(false);
        ProxyManagerImplEntity entityGregorian = new ProxyManagerImplEntity(false);
        ProxyManagerImplEntity entityProxyDate = new ProxyManagerImplEntity(true);
        ProxyManagerImplEntity entityList = new ProxyManagerImplEntity(true);
        ProxyManagerImplEntity entityTimestamp = new ProxyManagerImplEntity(false);
        
        
        ProxyManagerImplEntity entityManageable = new ProxyManagerImplEntity(false);
        
        boolean sortedMap = true;
        boolean notSortedMap = false;
        boolean proxyable = true;
        int initNanos = 1;       


        return Arrays.asList(new Object[][] {
    		
        	//Suite minimale
        	{entityNull.initializeEntityNull(null), null},
            {entityNonValid.initializeEntityNonValid(), null},
            {entityList.initializeEntityList(r.nextInt()), entityList.getObject()},

            
            //line coverage
            {entityManageable.initializeEntityManageable(), null},
            {entityDate.initializeEntityDate(new Date()), entityDate.getObject()},
            {entityMap.initializeEntityMap(r.nextInt(), r.nextInt(), notSortedMap), entityMap.getObject()},
            {entityGregorian.initializeEntityGregorian(new GregorianCalendar()), entityGregorian.getObject()},
            {entityProxyDate.initializeEntityProxyDate(new ProxyManagerImpl().newDateProxy(Date.class)), entityProxyDate.getObject()},
            {entityTimestamp.initializeEntityTimestamp(new Timestamp(initNanos), initNanos), entityTimestamp.getObject()},
            {entityFinal.initializeEntityFinal(), null},

                        
            //branch coverage
            {entitySortedMap.initializeEntityMap(r.nextInt(), r.nextInt(), sortedMap), entitySortedMap.getObject()},
            {entitySortedSet.initializeSortedSet(r.nextInt()), entitySortedSet.getObject()},
            {entityValid.initializeEntityValid(r.nextInt(), proxyable), entityValid.getObject()}, 
            
    	});

    }

    @Before
    public void setUp(){
        this.proxyManager = new ProxyManagerImpl();
        this.proxyManager.setUnproxyable(this.entity.getUnproxyable());
        if (this.entity.getObject() instanceof Timestamp)
        	this.entity.setObject(spy(this.entity.getObject()));
    }

    @Test
    public void newCustomProxyTest() {
        Object result = this.proxyManager.newCustomProxy(this.entity.getObject(), this.entity.isAutoOff());
        Assert.assertEquals(this.expectedResult, result);
        
        //TODO Perché non funziona per il mutation testing??
        if (this.entity.getObject() instanceof Timestamp)
        	verify((Timestamp) this.entity.getObject()).getNanos();
        
    }

}
