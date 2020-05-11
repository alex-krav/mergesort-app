#Merge sort application
##Idea

This is a demo application that allows to test 3 implementations of merge sort:
- binary merge sort (top-down implementation)
- natural merge sort
- multiway or k-way merge sort<p>

As input it accepts array of integers.<p>
On output it shows sorted array in asc/desc order and statistics for each implementation, collecting number of splits, merges and array copies.

##Building from sources
###Prerequisites
Before building application you need the following software installed on your machine:
1. Java 8 or higher
2. Maven build tool

###Building
Go to project directory and run command `mvn package` in terminal.<br>
In `target` directory JAR file named `mergesort-app-1.0.0.jar` will be created.

##Running
###Prerequisites
Before running application you need _Java 8_ installed on your machine.

###Start
In terminal run command `java -jar target/mergesort-app-1.0.0.jar`. Graphical window will be opened.

###Using
####Input
In graphical window there is an _Input panel_ with 3 tabs:
1. Text.<a name="input_text"></a><p>
    Consists of textarea. Example input:
    <pre>
    5 
    4 3 2 1 0</pre>
    First number is array size. All numbers are separated with _space_ character.
2. File.<p>
    Has a "Select" button that opens a window to choose a file with input array.<p>
    Format is the same as for [text](#input_text) variant.
3. Generate.<p>
   Has three input fields:
   - array size. Mandatory field
   - min value. Optional field (default: -2,147,483,648)
   - max value. Optional field (default: 2,147,483,647)<p>
   
    Will generate array of random integers.

Data from active tab will be used as input data for sorting.

You can select ascending or descending order in 'Order' panel.

After specifying input click 'Sort' button.

####Output
Output will be available in 3 resources.
1. Console. Will have all logs including input data, interim results, output array and statistics.<p>
For performance goals, only first 5 and last 5 elements of array are printed.
2. Screen. Will have full output array and statistical information.<p>
3. Text file. Will have full output array. Filename format: `output_date-time.txt`
