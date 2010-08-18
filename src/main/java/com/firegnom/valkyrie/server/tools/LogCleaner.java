/*******************************************************************************
 * Copyright (c) 2010 Maciej Kaniewski (mk@firegnom.com).
 *  
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 3 of the License, or
 *     (at your option) any later version.
 *  
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *  
 *     You should have received a copy of the GNU General Public License
 *     along with this program; if not, write to the Free Software Foundation,
 *     Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 *  
 *     Contributors:
 *      Maciej Kaniewski (mk@firegnom.com) - initial API and implementation
 ******************************************************************************/
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

// TODO: Auto-generated Javadoc
/**
 * The Class LogCleaner.
 * 
 * @author vincentr
 */
public class LogCleaner {

	/** The Constant SLEEP_TIME. */
	public final static long SLEEP_TIME = 5000;

	/**
	 * Creates a new instance of LogCleaner.
	 */
	public LogCleaner() {

	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		while (true) {
			String filedir;
			// Create a file object for your root directory
			File f1 = new File(args[1]);

			Hashtable<String, File> logFiles = new Hashtable<String, File>();

			// Get all the files and directory under your diretcory
			File[] strFilesDirs = f1.listFiles();

			for (int i = 0; i < strFilesDirs.length; i++) {
				if (strFilesDirs[i].isFile()) {
					// System.out.println( "File: " + strFilesDirs[i] + " (" +
					// strFilesDirs[i].length( ) + ")" ) ;
					// log file
					if (strFilesDirs[i].getName().contains("log.")) {
						logFiles.put(strFilesDirs[i].getName(), strFilesDirs[i]);
					}
				}
			}

			String[] logFileNames = (String[]) logFiles.keySet().toArray(
					new String[0]);
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
					System.out.println("Log file " + key + " to keep ");
				} else if (key.equals(lastLog2)) {
					System.out.println("Log file 2 " + key + " to keep ");
				} else {
					System.out.println("Log file " + key + " to delete ");
					// delete loag files
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
