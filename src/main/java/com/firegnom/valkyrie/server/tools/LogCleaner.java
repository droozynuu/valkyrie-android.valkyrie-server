/*
 * LogCleaner.java
 *
 * Created on 31 August 2007, 11:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.firegnom.valkyrie.server.tools;

import java.io.File;
import java.util.Hashtable;

/**
 *
 * @author vincentr
 */
public class LogCleaner {
    
    public final static long SLEEP_TIME = 5000;
    
    /** Creates a new instance of LogCleaner */
    public LogCleaner() {
        
        
    }
    
    public static void main( String[] args ) {

        while (true) {
            String filedir ;
            // Create a file object for your root directory
            File f1 = new File( args[1] ) ;

            Hashtable<String, File> logFiles = new Hashtable<String, File>();

            // Get all the files and directory under your diretcory
            File[] strFilesDirs = f1.listFiles( );

            for ( int i = 0 ; i < strFilesDirs.length ; i ++ ) {
                if ( strFilesDirs[i].isFile( ) ) {
                    //System.out.println( "File: " + strFilesDirs[i] + " (" + strFilesDirs[i].length( ) + ")" ) ;
                    // log file
                    if (strFilesDirs[i].getName().contains("log.")) {
                        logFiles.put(strFilesDirs[i].getName(), strFilesDirs[i]);
                    }
                }
            }

            String [] logFileNames = (String[]) logFiles.keySet().toArray(new String[0]);
            java.util.Arrays.sort(logFileNames);
            String lastLog = logFileNames[logFileNames.length - 1];
            String lastLog2 = "";
            
            if (logFileNames.length - 2 >= 0) {
                lastLog2 = logFileNames[logFileNames.length - 2];
            }

            // clean logs
            for (String key : logFiles.keySet()) {
                File logFile = logFiles.get(key);
                // Don't delete last log file'
                if (key.equals(lastLog)) {
                    System.out.println( "Log file " + key + " to keep ") ;
                } 
                else if (key.equals(lastLog2)) {
                    System.out.println( "Log file 2 " + key + " to keep ") ;
                }
                else {
                    System.out.println( "Log file " + key + " to delete ") ;
                    //delete loag files
                    logFile.delete();
                }
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}