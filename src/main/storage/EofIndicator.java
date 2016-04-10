package main.storage;

import java.io.Serializable;

import main.resources.Task;
//@@author A0124711U
/**
 * Empty class that is appended to the task file
 * to detect end of file when reading from the file.
 */
public class EofIndicator implements Serializable {
	//empty
}
