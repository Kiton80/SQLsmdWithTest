package main.java.Sqlcmd.controller;

import java.util.Arrays;

/**
 * Created by Kirill on 09.07.2017.
 */
public class DataSet {


    static class Data {
        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

    public static int count=10;
    public static int maxSize = count;


    public Data[] data = new Data[maxSize];
    public int freeIndex = 0;


    public void put(String name, Object value) {

        for (int index = 0; index < freeIndex; index++) {
            if (data[index].getName().equals(name)) {
                data[index].value = value;
                return;
            }
        }

        increaseSize();
        data[freeIndex++] = new Data(name, value);
    }

    private boolean increaseSize() {
        if (freeIndex==maxSize) {
            maxSize=maxSize+count;
           Data[] newData= new Data[maxSize];

            for (int i = 0; i <data.length-1 ; i++) {
                newData[i]=data[i];
            }
            this.data=newData;
        return true;
        }
        return false;
    }    // Remade! To test!!

    public Object[] getValues() {
        Object[] result = new Object[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    public Object get(String name) {
        for (int i = 0; i < freeIndex; i++) {
            if (data[i].getName().equals(name)) {
                return data[i].getValue();
            }
        }
        return null;
    }

    public void updateFrom(DataSet newValue) {
        for (int index = 0; index < newValue.freeIndex; index++) {
            Data data = newValue.data[index];
            this.put(data.name, data.value);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "names:" + Arrays.toString(getNames()) + ", " +
                "values:" + Arrays.toString(getValues()) +
                "}";
    }
}
