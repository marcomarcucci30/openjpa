package org.apache.openjpa.util.entity;

public class CacheMapEntity {
	
	private Object key;
    private Object value;
    private boolean alreadyExist;
    private boolean pinned;
    private Integer cacheMaxMapSize;
    private Integer numObjectToInsert;

    public CacheMapEntity(Object key, Object value, boolean alreadyExist, boolean pinned) {
        this.key = key;
        this.value = value;
        this.alreadyExist = alreadyExist;
        this.pinned = pinned;
    }
    
    public CacheMapEntity(Object key, Object value, boolean alreadyExist, boolean pinned, Integer cacheMaxMapSize, Integer numObjectToInsert) {
        this.key = key;
        this.value = value;
        this.alreadyExist = alreadyExist;
        this.pinned = pinned;
        this.cacheMaxMapSize = cacheMaxMapSize;
        this.numObjectToInsert = numObjectToInsert;
    }
    

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public boolean isAlreadyExist() {
        return alreadyExist;
    }

    public boolean isPinned() {
        return pinned;
    }

    public Integer getCacheMaxMapSize() {
        return cacheMaxMapSize;
    }

    public Integer getNumObjectToInsert() {
        return numObjectToInsert;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setAlreadyExist(boolean alreadyExist) {
		this.alreadyExist = alreadyExist;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public void setCacheMaxMapSize(Integer cacheMaxMapSize) {
		this.cacheMaxMapSize = cacheMaxMapSize;
	}

	public void setNumObjectToInsert(Integer numObjectToInsert) {
		this.numObjectToInsert = numObjectToInsert;
	}

}
