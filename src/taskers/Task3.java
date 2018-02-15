/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.application.Platform;

/**
 *
 * @author dalemusser
 * 
 * This example uses PropertyChangeSupport to implement
 * property change listeners.
 * 
 */
public class Task3 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    private boolean suspended = false;
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public Task3(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        doNotify("Task3 start.");
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task3: " + i);
            }
            
            if (exit) {
                return;
            }
            
            synchronized(this) {
                if (suspended) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
        }
        doNotify("Task3 done.");
    }
    
    public void end() {
        exit = true;
    }
    
    public void suspendTask3() {
       suspended = true;
    }
   
    synchronized public void resumeTask3() {
       suspended = false;
       notify();
    }
    
    // the following two methods allow property change listeners to be added
    // and removed
    public void addPropertyChangeListener(PropertyChangeListener listener) {
         pcs.addPropertyChangeListener(listener);
     }

     public void removePropertyChangeListener(PropertyChangeListener listener) {
         pcs.removePropertyChangeListener(listener);
     }
    
    private void doNotify(String message) {
        // this provides the notification through the property change listener
        Platform.runLater(() -> {
            // I'm choosing not to send the old value (second param).  Sending "" instead.
            pcs.firePropertyChange("message", "", message);
        });
    }
}