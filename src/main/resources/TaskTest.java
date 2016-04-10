package main.resources;
 
import static org.junit.Assert.*;
 
import org.junit.Test;
 
public class TaskTest {
 
    @Test
    public void test() {
        Task testTask = new Task("Task Name", "Task Details", 1);
        if(testTask.getTaskDetails() != "Task Details"){
            fail();
        }
        if(testTask.getTaskType() != 1){
            fail();
        }
        if(testTask.getTaskName() != "Task Name"){
            fail();
        }
        if(testTask.getPriority() != 3){
            fail();
        }
        if(testTask.getTaskLocation() != null){
            fail();
        }
    }
}