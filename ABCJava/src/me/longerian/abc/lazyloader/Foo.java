package me.longerian.abc.lazyloader;

public class Foo {

	static {
		System.out.println("Foo loaded");
	}
	
	private static class LazyFoo {
		static {
			System.out.println("LazyFoo loaded");
		}
       public static Foo foo = new Foo();
    }
 
    public static Foo getInstance() {
       return LazyFoo.foo;
    }
}