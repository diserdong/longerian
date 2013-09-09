package me.longerian.abc.reference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.ref.WeakReference;

public class ReferenceIdiom {

	public static void main(String args[]) throws FileNotFoundException
	  {
	    broken();
	    correct();
	  }
	  public static FileReader recreateIt() throws FileNotFoundException
	  {
	    return new FileReader("NOTICE");
	  }
	  public static void broken() throws FileNotFoundException
	  {
	    System.out.println("Executing method broken");
	    FileReader obj = recreateIt();
	    WeakReference wr = new WeakReference(obj);
	    System.out.println("wr refers to object " + wr.get());
	    System.out.println("Now, clear the reference and run GC");
	    //Clear the strong reference, then run GC to collect obj.
	    obj = null;
	    System.gc();
	    System.out.println("wr refers to object " + wr.get());
	    //Now see if obj was collected and recreate it if it was.
	    obj = (FileReader)wr.get();
	    if (obj == null)
	    {
	      System.out.println("Now, recreate the object and wrap it in a WeakReference");
	      wr = new WeakReference(recreateIt());
	      System.gc();  //FileReader object is NOT pinned...there is no 
	                    //strong reference to it.  Therefore, the next 
	                    //line can return null.
	      obj = (FileReader)wr.get();
	    }
	    System.out.println("wr refers to object " + wr.get());
	  }
	  public static void correct() throws FileNotFoundException
	  {
	    System.out.println("");
	    System.out.println("Executing method correct");
	    FileReader obj = recreateIt();
	    WeakReference wr = new WeakReference(obj);
	    System.out.println("wr refers to object " + wr.get());
	    System.out.println("Now, clear the reference and run GC");
	    //Clear the strong reference, then run GC to collect obj
	    obj = null;
	    System.gc();
	    System.out.println("wr refers to object " + wr.get());
	    //Now see if obj was collected and recreate it if it was.
	    obj = (FileReader)wr.get();
	    if (obj == null)
	    {
	      System.out.println("Now, recreate the object and wrap it in a WeakReference");
	      obj = recreateIt();
	      System.gc();  //FileReader is pinned, this will not affect 
	                    //anything.
	      wr = new WeakReference(obj);
	    }
	    System.out.println("wr refers to object " + wr.get());
	  }
	  
}
