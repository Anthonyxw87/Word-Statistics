public class HashEntry implements Comparable<HashEntry>{
        private String key;
        private int value; 
        private int rankMostCommon;
        private boolean mostCommon = false;
        private HashTable rightTable = new HashTable(1);
        private HashTable leftTable = new HashTable(1);

        // put those in sorted array from least to most common
        public HashEntry(String key, int value){
                this.key = key;
                this.value = value;
        }

        // for mostCommonCollecs method (didn't finish)
        public HashTable getRightTable(){
                return rightTable;
        }

        // for mostCommonCollecs method (didn't finish)
        public HashTable getLeftTable(){
                return leftTable;
        }

        // accessor method for mostCommonCollecs method
        public void putInRight(String word){
                if (rightTable.get(word) == -1) {           // if the word does not exist in the table
                        rightTable.put(word, 1);
                }
                // if the word exists in the table already, increment the value by one
                else {                                                                  
                        rightTable.update(word, rightTable.get(word) + 1);
                }
        }

        // accessor method for mostCommonCollecs method
        public void putInLeft(String word){
                if (leftTable.get(word) == -1) {           // if the word does not exist in the table
                        leftTable.put(word, 1);
                }
                // if the word exists in the table already, increment the value by one
                else {                                                                  
                        leftTable.update(word, leftTable.get(word) + 1);
                }
        } 

        // setter method for mostCommon
        public void setMostCommon(boolean mostCommon){
                this.mostCommon = mostCommon;
        }

        // setter method for rankMostCommon
        public void setRankMostCommon(int rankMostCommon){
                this.rankMostCommon = rankMostCommon;
        }

        // getter method for mostCommon
        public int getRankMostCommon(){
                return rankMostCommon;
        }

        // getter method for key
        public String getKey(){
                return this.key;
        }

        // getter method for value 
        public int getValue(){
                return this.value;
        }
        
        // setter method for value
        public void setValue(int value){
                this.value = value;
        }

        // compares the values of the HashEntry, (comparison is different whether its froms most common to least common or least common to most)
        public int compareTo(HashEntry compare){
                if(mostCommon == false)
                        return this.getValue() - compare.getValue();
                
                else
                        return compare.getValue() - this.getValue();
        }
}
