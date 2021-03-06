/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button task1Button;
    
    @FXML 
    private Button task2Button;
    
    @FXML
    private Button task3Button;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    /**************************************************************************/
    /* Task 1 */
    @FXML
    public void startTask1(ActionEvent event) {
        if (task1Button.getText().equals("Start Task 1") && task1 != null) {
            /* Stop Task 1 Button */
            System.out.println("start task 1");
            task1.resumeTask1();
            task1Button.setText("Stop Task 1");
        } else if (task1Button.getText().equals("Stop Task 1") && task1 != null) {
            /* Start Task 1 Button */
            System.out.println("stop task 1");
            task1.suspendTask1();
            task1Button.setText("Start Task 1");
        }
        
        if (task1 == null) {
            task1Button.setText("Stop Task 1");
            System.out.println("start task 1");
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            task1Button.setText("Start Task 1");
        }
        textArea.appendText(message + "\n");
    }
    /**************************************************************************/
    /* Task 2 */
    @FXML
    public void startTask2(ActionEvent event) {
         if (task2Button.getText().equals("Start Task 2") && task2 != null) {
            /* Stop Task 2 Button */
            System.out.println("start task 2");
            task2.resumeTask2();
            task2Button.setText("Stop Task 2");
        } else if (task2Button.getText().equals("Stop Task 2") && task2 != null) {
            /* Start Task 2 Button */
            System.out.println("stop task 2");
            task2.suspendTask2();
            task2Button.setText("Start Task 2");
        }
         
        if (task2 == null) {
            System.out.println("start task 2");
            task2Button.setText("Stop Task 2");
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                if (message.equals("Task1 done.")) {
                    task1 = null;
                    task1Button.setText("Start Task 1");
                 }   
                textArea.appendText(message + "\n");
                
            });
            
            task2.start();
        }
    }
    /**************************************************************************/
    /* Task 3 */
    @FXML
    public void startTask3(ActionEvent event) {
           if (task3Button.getText().equals("Start Task 3") && task3 != null) {
            /* Stop Task 3 Button */
            System.out.println("start task 3");
            task3.resumeTask3();
            task3Button.setText("Stop Task 3");
        } else if (task3Button.getText().equals("Stop Task 3") && task3 != null) {
            /* Start Task 3 Button */
            System.out.println("stop task 3");
            task3.suspendTask3();
            task3Button.setText("Start Task 3");
        }
           
        if (task3 == null) {
            System.out.println("start task 3");
            task3Button.setText("Stop Task 3");
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
            });
            
            task3.start();
        }
    } 
}
