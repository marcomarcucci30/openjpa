package org.apache.openjpa.util;

import org.apache.openjpa.util.entity.CacheMapEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class GetCacheMapTest {
	
	private CacheMap cacheMap;
	private Object expectedResult;
    private CacheMapEntity entity;
	
	public GetCacheMapTest(CacheMapEntity entity, Object expectedResult) {
    	this.entity = entity;
    	this.expectedResult = expectedResult;
    }

	@Parameterized.Parameters
    public static Collection<?> getParameters(){
    	
    	/*CATEGORY PARTITION:
    	 * 
    	 * Key --> {valid, NonValid, null}
    	 * 
    	 * L'oggetto nonValid non può essere considerato per via del fatto che 
    	 * entrambi i paramentri sono classi di tipo Object.
    	 * 
    	 * get(valid)
    	 * get(null)
    	 * 
    	 * */
    	
    	/*MUTATION TESTING:
    	 * 
    	 * */
    	
		Object obj = new Object();
    	
    	
    	 return Arrays.asList(new Object[][] {
    		//suite minimale
    		 {new CacheMapEntity(null, null, false, false,1,0), null},
    		 {new CacheMapEntity(new Object(), obj, false, false,1,0), null},
    		 
    		 //branch coverage
    		 {new CacheMapEntity(new Object(), obj, true, false,1,0), obj},
    		 {new CacheMapEntity(new Object(), obj, false, false, 1, 1), obj},
    		 
         });
        

    }
	
	@Before
    public void setUp(){
        this.cacheMap = new CacheMap(true, entity.getCacheMaxMapSize(), entity.getCacheMaxMapSize() + 1, 1L, 1);

		
		if(this.entity.getNumObjectToInsert() != 0) {
			//controlla prima la soft map
			this.cacheMap.put(this.entity.getKey(), this.entity.getValue());
			this.cacheMap.put(new Object(), new Object());
		}
		if(this.entity.isAlreadyExist())
			this.cacheMap.put(this.entity.getKey(), this.entity.getValue());
		
		this.cacheMap = spy(this.cacheMap);

    }
	
	@Test
    public void getTest() {
		Object result = this.cacheMap.get(this.entity.getKey());
				
		Assert.assertEquals(this.expectedResult, result);
		
		
		//TODO inserire il nuovo test nel pom
		//TODO togli Badua
		//for mutation
		if (this.entity.getNumObjectToInsert() != 0) {
			/*Controllo che */
			Assert.assertNull(cacheMap.softMap.get(entity.getKey()));
		}
		verify(this.cacheMap).readUnlock();
    }
	

}
