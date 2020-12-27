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
public class UnpinCacheMapTest {
	
	private CacheMap cacheMap;
	
    private CacheMapEntity entity;
    private boolean expectedResult;
	private int expectedSize;
	
	public UnpinCacheMapTest(CacheMapEntity entity, Boolean expectedResult, int expectedSize) { //, Object expectedResult
    	this.entity = entity;
    	this.expectedResult = expectedResult;
    	this.expectedSize = expectedSize;
    }
	
	@Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	
	@Parameterized.Parameters
    public static Collection<?> getParameters(){
		
		/*CATEGORY PARTITION:
		 * 
		 * Key --> {valid, NonValid, null}
		 * 
		 * L'oggetto nonValid non può essere considerato per via del fatto che 
		 * il paramentro è una classe di tipo Object.
		 * 
		 * unpin(valid)
		 * unpin(null)
		 * */
		
		/*MUTATION TESTING:
		 * 
		 * */
		Object obj = new Object();
	
	
	    return Arrays.asList(new Object[][] {
	    	//suite minimale
	    	{new CacheMapEntity(null, null, false, false), false, 0},
	    	{new CacheMapEntity(new Object(), obj, true, true), true, 1},
	    	
	    });
	}
	
	@Before
	public void setUp(){
		this.cacheMap = new CacheMap();        
        
        if (this.entity.isAlreadyExist()) {
            this.cacheMap.put(this.entity.getKey(), this.entity.getValue());
        }

        if (this.entity.isPinned()) {
            this.cacheMap.pin(this.entity.getKey());
        }

        this.cacheMap = spy(this.cacheMap);
	}

	
	@Test
	public void unpinTest() {
		
        boolean result = this.cacheMap.unpin(this.entity.getKey());
        
        Assert.assertEquals(this.expectedResult, result);
        
        //for mutation
        if (this.entity.isAlreadyExist()) {
        	verify(this.cacheMap, times(2)).writeLock();
        	verify(this.cacheMap, times(2)).writeUnlock();
        }else {
        	verify(this.cacheMap).writeLock();
        	verify(this.cacheMap).writeUnlock();
		}
        Assert.assertEquals(this.expectedSize, this.cacheMap.size());
        

	}

}
