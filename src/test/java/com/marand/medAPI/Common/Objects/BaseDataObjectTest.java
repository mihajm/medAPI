package com.marand.medAPI.Common.Objects;

import com.marand.medAPI.Common.Entities.BaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseDataObjectTest {


    private long id = 10L;
    private BaseDataObject entity;

    protected BaseDataObject createEntity() {
        return new MockEntity(id);
    }

    @BeforeEach
    private void BaseDataObjectTestSetup() {
        entity = createEntity();
    }

    @Test
    void equals_ComparesClasses() {
        assertFalse(entity.equals(new BaseEntity(id)));
    }

    @Test
    void equals_ComparesFields_IfSameClass() {
        assertFalse(entity.equals(new MockEntity(5L)));
        assertTrue(entity.equals(createEntity()));
    }

    @Test
    void toString_returnsAllFieldsAndValues() {
        String entityString = entity.toString();
        assertTrue(entityString.contains("id"));
        assertTrue(entityString.contains(String.valueOf(entity.getId())));
    }

    @Test
    void hashCode_returnsHashBasedOnFieldValues() {
        int hashCode = entity.hashCode();
        entity.setId(5000);
        assertNotEquals(hashCode, entity.hashCode());
    }

    private static class MockEntity extends BaseDataObject {

        protected MockEntity() {}

        protected MockEntity(long id) {setId(id);}

        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

}