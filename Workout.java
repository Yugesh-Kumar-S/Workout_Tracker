import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.RandomAccessFile;
//Custom exception for invalid workout number
class Invalidworkout extends Exception
{
    Invalidworkout(String a)
    {
        super(a);
    }
}


class deletedate
{
     public static void delete(String filename, String date) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            String line;
            long truncatePoint = -1;

            // Read through the file to find the matching date line
            while ((line = raf.readLine()) != null) {
                if (line.equals(date)) {
                    truncatePoint = raf.getFilePointer(); // Position after the matching line
                    break;
                }
            }

            // If we found the date, truncate the file
            System.out.print(truncatePoint);
            if (truncatePoint != -1) {
                raf.setLength(truncatePoint - line.length() - 2); // Adjust for line break
            }
        }
    }
}


class Main
{
	public static void main(String[] args) 
	{
	    try 
	    {
	        //To access time and date
	        LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        String formattedDateTime = now.format(formatter);
	        String filename = "workout_tracker.txt";
	       
	        
	        
	        //create file to write , true makes it append mode
	        FileWriter file = new FileWriter(filename,true);
            file.write(formattedDateTime + System.lineSeparator());
            
            
            
            //array to store workouts
	        String[] workout = {"Bicep","Forearm","Tricep","Chest","Shoulder","ABS","Leg"};
		    System.out.println("Enter workout no");
		    
		    //create string builer to store workout in file
		    StringBuilder workoutsdone = new StringBuilder();
		    
		    //displays workout list
		    for(int i=0; i < workout.length ; i++)
		    {
		        System.out.println((i+1)+". "+workout[i]);
		    }
		    Scanner sc = new Scanner(System.in);
		    int number = sc.nextInt();
		    
		    //To check duplicate workout numbers
		    boolean[] duplicate = new boolean[7];
		    //writes workout into the stringbuilder
		    while(number > 0 )
		    {
		        int temp = number % 10;
		        
		        
		        //Handle invalid workout number
		        if(temp<1 || temp>workout.length) 
		        {
		            throw new Invalidworkout("You have entered invalid workout number");
		        }
		        
		        
		        
		        
		        //store workouts in stringbuilder only if not a duplicate 
		        
		        
		        
		        
		        
		        if(!duplicate[temp-1]) 
		        {
		            duplicate[temp-1]=true;
		            workoutsdone.append(workout[temp-1]).append(System.lineSeparator());
		        }
		        number /= 10;
		    }   
		    
		    
		    //delete content of current date if already present,placed here to first check for exception thrown before deleting previous content	        
		    deletedate.delete(filename,formattedDateTime);
		    //copy stringbuilder to File
		    file.write(workoutsdone.toString()+System.lineSeparator());
		    file.close();
		    
	    }
	    catch(Invalidworkout e)
	    {
	        System.out.println(e.getMessage());
	    }
	    catch(IOException e) 
	    {
	        System.out.println("File operation error");
	    }
	    
	}
}