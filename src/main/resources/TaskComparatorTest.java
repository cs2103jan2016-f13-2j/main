
package main.resources;
 
import static org.junit.Assert.*;
 
import org.junit.Test;
 
//@@author A0125255L
public class TaskComparatorTest {
 
    @Test
    public void test() {
        TaskComparator comparator;
        Task task1 = new Task();
        Task task2 = new Task();
        int compareResult = 0;
        
        //Details test
        comparator = new TaskComparator(1);
        task1.setTaskDetails("abc");
        task2.setTaskDetails("xyz");
        compareResult = comparator.compare(task1,  task2);
        assertTrue(compareResult < 0);
        
        //Start Date test
        comparator = new TaskComparator(2);
        task1.setTaskStartDate(new Date(1,1,01));
        task2.setTaskStartDate(new Date(2,2,02));
        compareResult = comparator.compare(task1,  task2);
        assertTrue(compareResult < 0);
        
        //Start Time test
        comparator = new TaskComparator(3);
        task1.setTaskStartTime(new Time(12, 00));
        task2.setTaskStartTime(new Time(13, 00));
        compareResult = comparator.compare(task1,  task2);
        assertTrue(compareResult < 0);
        
        //Location test
        comparator = new TaskComparator(6);
        task1.setTaskLocation("abc");
        task2.setTaskLocation("xyz");
        compareResult = comparator.compare(task1,  task2);
        assertTrue(compareResult < 0);
        
        //Priority test
        comparator = new TaskComparator(7);
        task1.setPriority(1);
        task2.setPriority(3);
        compareResult = comparator.compare(task1,  task2);
        assertTrue(compareResult < 0);
        
        
    }
 
}