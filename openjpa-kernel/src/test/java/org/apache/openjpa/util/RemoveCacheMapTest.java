package org.apache.openjpa.util;

import org.apache.openjpa.util.entity.CacheMapEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.Arrays;
import java.util.Collection;


import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class RemoveCacheMapTest {

	private CacheMap cacheMap;
    private CacheMapEntity entity;
    private Object expectedResult;
    private int expectedSize;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    public RemoveCacheMapTest(CacheMapEntity entity, Object expectedResult, int expectedSize) {
    	this.entity = entity;
    	this.expectedResult = expectedResult;
    	this.expectedSize = expectedSize;
    }


    @Parameterized.Parameters
    public static Collection<?> getParameters(){
        
        /*CATEGORY PARTITION:
    	 * 
    	 * Key --> {valid, NonValid, null}
    	 * 
    	 * L'oggetto nonValid non può essere considerato per via del fatto che 
    	 * il paramentro è una classe di tipo Object.
    	 * 
    	 * remove(valid)
    	 * remove(null)
    	 * */
    	
    	/*MUTATION TESTING:
    	 * -Line 457: mutation equivalent (NO Weak mutation, Strong mutation)  --> Forse è anche weak 
    	 *  
    	 */
    	
    	Object objValue = new Object();
    	
        
        return Arrays.asList(new Object[][] {
      		 //suite minimale
             {new CacheMapEntity(new Object(), null, false, true), null, 0},
      		 {new CacheMapEntity(null, null, false, false), null, 0},
      		 
      		 //coverage
      		 {new CacheMapEntity(new Object(), objValue, true, true), objValue, 0},
      		 {new CacheMapEntity(null, objValue, true, false), objValue, 0},
           });

    }

   
    @Before
    public void setUp() {
        this.cacheMap = new CacheMap(true);
        if (this.entity.isPinned()) {
            this.cacheMap.pin(this.entity.getKey());
        }

        if (this.entity.isAlreadyExist()) {
            this.cacheMap.put(this.entity.getKey(), this.entity.getValue());
        }
        
        this.cacheMap = spy(this.cacheMap);

    }

    @Test
    public void removeTest() {
    	
    	Object result = this.cacheMap.remove(this.entity.getKey());
        
        
        Assert.assertEquals(this.expectedResult, result);
        
        //for mutation
        Assert.assertEquals(this.expectedSize, this.cacheMap.size()); 
        if (entity.isAlreadyExist())
        	verify(this.cacheMap).entryRemoved(entity.getKey(), entity.getValue(), false);
        
        verify(this.cacheMap).writeLock();
        verify(this.cacheMap).writeUnlock();

    }
}
