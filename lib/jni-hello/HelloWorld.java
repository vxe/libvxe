package be.vxe;

public class HelloWorld {
  private native void print();

  public static void main(String[] args) {
    // note here how we're calling an internal method print() which is not implemented.  This is
    // essentially a
    // header file.
    new HelloWorld().print();
  }

  static {
    System.loadLibrary("HelloWorld");
  }
}
