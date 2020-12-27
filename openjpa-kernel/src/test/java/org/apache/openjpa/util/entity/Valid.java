package org.apache.openjpa.util.entity;

public class Valid {
    Integer value;

    public Valid() {}


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Valid bean = (Valid) obj;
            return this.value.equals(bean.getValue());
        } catch (Exception e) {
            return false;
        }
    }
}
