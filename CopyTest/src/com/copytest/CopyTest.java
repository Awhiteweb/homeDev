package com.copytest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.stopwatch.*;

public class CopyTest {

	public static void main(String[] args) throws IOException {
		File source = new File( "/Users/Work/Desktop/13 Assassins.m4v" );
		File dest1 = new File( "/Users/Work/Desktop/copy1/13 Assassins.m4v" );
		Path from = Paths.get( "/Users/Work/Desktop/13 Assassins.m4v" );
		Path dest2 = Paths.get( "/Users/Work/Desktop/copy2/13 Assassins.m4v" );
		File dest3 = new File( "/Users/Work/Desktop/copy3/13 Assassins.m4v" );

//		StopWatch t1 = new StopWatch();
//		
//		System.out.println( "Start timer 1");
//		
//		t1.start();
//		
//		copyFileUsingFileChannels( source, dest1 );
//		
//		t1.stop();
//		
//		System.out.println( "Stop timer 1");
//		System.out.println( "File channels" + t1.getTotalTimeMillis() + "ms" );
//		System.out.println( "File channels" + t1.getTotalTimeSeconds() + "s" );
//
//		System.out.println( "" );
//		StopWatch t2 = new StopWatch();
//		System.out.println( "Start timer 2");
//		
//		t2.start();
//		
//		moveFile( from, dest2 );
//		
//		t2.stop();
//		
//		System.out.println( "Stop timer 2");
//		System.out.println( "File copy" + t2.getTotalTimeMillis() + "ms" );
//		System.out.println( "File copy" + t2.getTotalTimeSeconds() + "s" );

		System.out.println( "" );
		StopWatch t3 = new StopWatch();
		System.out.println( "Start timer 3");
		
		t3.start();
		
		copyFileUsingStream( source, dest3 );
		
		t3.stop();
		
		System.out.println( "Stop timer 3");
		System.out.println( "File stream" + t3.getTotalTimeMillis() + "ms" );
		System.out.println( "File stream" + t3.getTotalTimeSeconds() + "s" );
		
//		int a = 50000;
//		int b = 2000;
//		double c = ( (double) b / a) * 100;
//		
//		System.out.println( "a = " + a + ". b = " + b + ". c = " + (int) c + "." );
		

	}
	
	private static void copyFileUsingFileChannels(File source, File dest) throws IOException
	{
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
	
	private static void moveFile( Path from, Path to ) throws IOException
	{
		Files.copy( from, to );			
	}		 

	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	    	int progress = 0;
	    	is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        int fileSize = (int) source.length();
	        int bufferSize = 1024;
	        
	        while ( (length = is.read(buffer) ) > 0) {
//	            os.write(buffer, 0, length);
	            progress += bufferSize;
	            double percent = ( (double) progress / fileSize ) * 100;
	            if ( (int) percent % 5 == 0 )
	            {
//		            System.out.println( "Long percentage output: " + (int) percent );
		            System.out.println( "Math output: " + Math.min( (int) percent , 100 ) );
	            }
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
}
