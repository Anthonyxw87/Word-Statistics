import java.util.ArrayList;

public class HashTable {
        // an array that will be used to implement hashtable and store the hashentry
        private HashEntry[] table;

        // stores the maximum size of the HashTable
        private int maxSize;

        // stores the current number of elements in the HashTable
        private int numberOfElements;

        // stores the value that the load factor of the array should not be above
        private final double loadFactor = 0.75;

        // creates a HashTable with a default size of 100,000
        public HashTable() {
                table = new HashEntry[100000];
                setMaxSize(100000);
        }

        // creates a HashTable with a set size
        public HashTable(int i) {
                table = new HashEntry[i];
                setMaxSize(i);
        }

        // setter method for table
        private void setTable(HashEntry[] table) {
                this.table = table;
        }

        // getter method for table
        private HashEntry[] getTable() {
                return table;
        }

        // setter method for maxSize
        private void setMaxSize(int maxSize) {
                this.maxSize = maxSize;
        }

        // getter method for maxSize
        private int maxSize() {
                return maxSize;
        }

        // setter method for numberOfElements
        private void setNumberOfElements(int numberOfElements) {
                this.numberOfElements = numberOfElements;
        }

        // getter setter for numberOfElements
        private int getNumberOfElements() {
                return numberOfElements;
        }

        // getter method for loadFactor
        private double getLoadFactor() {
                return loadFactor;
        }

        // helper method to increment the number of elements by one
        private void incNumElements() {
                setNumberOfElements(numberOfElements + 1);
        }

        // helper method to rehash the hash table when the load factor gets above 0.75
        private void rehash() {
                int oldSize = maxSize();
                HashEntry[] oldTable = getTable();
                setTable(new HashEntry[2 * maxSize()]);
                setMaxSize(2 * maxSize());
                for (int i = 0; i < oldSize; i++) {
                    if (oldTable[i] != null)
                        put(oldTable[i].getKey(), oldTable[i].getValue());
                }
        }

        // helper method to find and insert into the next index using quadratic probing
        private int quadraticProbeIndex(int hashCode) {
                int code = hashCode;
                for (int i = 0; i < maxSize() && table[code] != null; i++) {        // uses quadratic probing to handle collisions
                        code = Math.abs((hashCode + i * i) % maxSize());
                        if (table[code] == null){
                                return code;
                        }
                }
                return -1;
        }

        // stores the key-value pair into the hashtable
        public void put(String key, int value) {
                incNumElements();
                if (getNumberOfElements() / maxSize() >= getLoadFactor()) 
                        rehash();                                               // rehash the hash table when the load factor gets above 0.75
                
                int hashCode = Math.abs(key.hashCode() % maxSize());            // uses java's abs() and hashcode() method to determine the hashCode
                if (table[hashCode] == null) {                                  // when the indicated index is empty
                        table[hashCode] = new HashEntry(key, value);            // inserts the key-value pair as a HashEntry into the HashTable
                } 
                else {
                        table[quadraticProbeIndex(hashCode)] = new HashEntry(key, value);
                }
        }

        // stores the key-value pair into the HashTable with a given hashCode
        public void put(String key, int value, int hashCode) {
                incNumElements();
                if (getNumberOfElements() / maxSize() >= getLoadFactor()) {
                        rehash(); // rehash the hash table when the load factor gets above 0.75
                }

                hashCode = Math.abs(hashCode % maxSize());
                if (table[hashCode] == null) {
                        table[hashCode] = new HashEntry(key, value);
                } else {
                        table[quadraticProbeIndex(hashCode)] = new HashEntry(key, value);
                }
        }

        // updates the value associated with the key (if any)
        public void update(String key, int value) {
                int hashCode = Math.abs(key.hashCode()) % maxSize();
                if(searchForIndex(key,hashCode) != -1){                         // if there a matching key in the table, updates the value
                        table[searchForIndex(key, hashCode)].setValue(value);
                }
                else{
                        put(key, value);                                        // if there is no matching key inserts the key-value pair in to the table
                }
            }

        // helper method to search the table for a index matching the key
        private int searchForIndex(String key, int hashCode){
                hashCode = hashCode % maxSize();
                int code = hashCode;
                // traverses through the table using quadratic probing until a matching key is found or reaches the max size of the table
                for(int i = 0; i < maxSize() && table[code] != null; i++){
                        if(table[code].getKey().equals(key)){
                                return code;
                        }
                        code = Math.abs((hashCode + i * i) % maxSize());    // quadratic probing
                }
                return -1;
        }

        // returns the value associated with the key
        public int get(String key) {
                int index = searchForIndex(key, Math.abs(key.hashCode()));
                if (index == -1) {
                    return index;
                }
                return table[index].getValue();
            }

        // returns the value associated with the key using a parameter hashCode
        public int get(String key, int hashCode) {
                hashCode = Math.abs(hashCode % maxSize());
                int index = searchForIndex(key, hashCode);
                if (index == -1)
                    return -1;
                return table[index].getValue();
            }


        // returns the rank associated with the key (if the key exists)
        public int getRankMostCommon(String key){
                int index = searchForIndex(key, Math.abs(key.hashCode()));
                if (index == -1) {
                    return -1;
                }

                return table[index].getRankMostCommon();
        }

        // returns the HashEntry in the table that matches the key
        public HashEntry getEntry(String key){
                int index = searchForIndex(key, Math.abs(key.hashCode()));
                if (index == -1) {
                    return null;
                }
                return getTable()[index];
            }

        // returns an new array list with all the words in the table
        public ArrayList<HashEntry> exportArray(){
                ArrayList<HashEntry> entries = new ArrayList<HashEntry>();
                for(HashEntry e : getTable()){
                        if (e != null) {
                                entries.add(e);
                        }
                }
                return entries;
            }
}