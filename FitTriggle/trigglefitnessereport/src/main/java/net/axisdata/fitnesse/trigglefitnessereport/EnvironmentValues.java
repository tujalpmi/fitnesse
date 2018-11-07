/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.axisdata.fitnesse.trigglefitnessereport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author jose.alvarez
 */
public class EnvironmentValues implements Serializable {

   private transient String environment;
   private transient String fitnessePort;
   //private String fitnessePort;
    
    public EnvironmentValues(String environment,String fitnessePort) {
        this.environment = environment;
        this.fitnessePort = fitnessePort;
    }

 
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    
    public String getFitnessePort() {
        return fitnessePort;
    }

    public void setFitnessePort(String fitnessePort) {
        this.fitnessePort = fitnessePort;
    }
 
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.environment);
        out.writeObject(this.fitnessePort);
    }

    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException {
        in.defaultReadObject();
        this.environment = (String)in.readObject();
        this.fitnessePort = (String)in.readObject();
    }
    
    
}
