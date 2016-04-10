package main.resources;
 
import static org.junit.Assert.*;
 
import org.junit.Test;
 
public class TaskComparatorTest {
 
    @Test
    public void test() {
        TaskComparator test = new TaskComparator(1);
        Task testTask1 = new Task("Task Name", "Task Details", 1);
        Task testTask2 = new Task("Task Name", "Task Details", 1);
        testTask2.setPriority(1);
        if(test.compare(testTask1, testTask2) != 2) {
            fail();
        }
        //Task nullTask = new Task("Task Name", null, 1);
       // if(test.compare(testTask1, nullTask) != -1) {
       //     fail();
       // }
    }
 
}