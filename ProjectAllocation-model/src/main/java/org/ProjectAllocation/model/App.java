package org.ProjectAllocation.model;

import org.hibernate.annotations.Entity;

/**
 * Hello world!
 *
 */
@Entity
public class App 
{
	
	public static String play(String game)
	{
		return "play app " + game;
	}
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
