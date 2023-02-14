package com.zyz.Interview;

public class MyArrayList {

    private transient Object[] data = null;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList(int capability) {
        if (capability < 0) {
            throw new IllegalArgumentException("非法的集合初始化容量值 Illegal Capacity:" + capability);
        } else {
            //实例化数组
            this.data = new Object[capability];
        }
    }

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public void checkIncrease(int index, Object obj) {
        if (size > data.length) {
            Object[] newData = new Object[size * 2];
            if (index == -1 && obj == null) {
                System.arraycopy(data, 0, newData, 0, size);
            } else {
                //复制将要插入索引位置前面的对象
                System.arraycopy(data, index, newData, index + 1, size - index);
            }
            data = newData;
            newData = null;
        }
    }

    public int getSize() {
        return this.size;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < this.size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < this.size; i++) {
                if (o.equals(data[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 在尾部添加元素
     */
    public boolean add(Object obj) {
        checkIncrease(-1, null);
        data[size++] = obj;
        return true;
    }

    public boolean checkIndexOut(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("指定的索引越界，集合大小为:" + size + ",您指定的索引为:" + index);
        }
        return true;
    }

    public boolean add(int index, Object obj) {
        if (index == size) {
            add(obj);
        } else if (checkIndexOut(index)) {
            if (size < data.length) {
                System.arraycopy(data, index, data, index + 1, size - index);
                data[index] = obj;
            } else {
                checkIncrease(index, obj);
            }
            size++;
        }
        return true;
    }

    public Object get(int index) {
        checkIndexOut(index);
        return data[index];
    }

    public void removeAll() {
        for (int i = 0; i < this.size; i++) {
            data[i] = null;
        }
    }

    public Object remove(int index) {
        if (index == size + 1) {
            throw new IndexOutOfBoundsException("指定的索引越界，集合大小为:" + size + ",您指定的索引大小为:" + index);
        } else if (checkIndexOut(index)) {
            Object obj = data[index];
            if (index == size) {
                data[index] = null;
            } else {
                //将后边的数组向前移动一位
                System.arraycopy(data, index + 1, data, index, size - index);
            }
            size--;
            return obj;
        }
        return null;
    }

    public boolean remove(Object obj) {
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(data[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean contain(Object obj) {
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(data[i])) {
                return true;
            }
        }
        return false;
    }


}
