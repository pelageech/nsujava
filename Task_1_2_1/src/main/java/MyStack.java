import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyStack<T> {

  private final List<T> stack;
  private int top;

  public MyStack() {
    stack = new ArrayList<>();
    top = 0;
  }

  public void push(T item) {
    stack.add(top++, item);
  }

  public T pop() {
    if (top <= 0) return null;

    return stack.remove(--top);
  }

  public MyStack<T> popStack(int n) {
    // New stack for return
    MyStack<T> stack2 = new MyStack<>();

    int s = n > top ? top : n;

    // Arr with size 's'
    List<T> arr = new ArrayList<>(s);
    for (int i = 0; i < s; i++) arr.add(null);

    // Fill arr new elements in correct sequence
    for (int i = s-1; i >= 0; i--) {
      arr.set(i, pop());
    }

    // push arr to stack2
    stack2.pushStack(arr);

    return stack2;
  }

  public void pushStack(List<T> arr) {
    for (T t : arr)
      push(t);
  }

  public void pushStack(T[] arr) {
    List<T> l = new ArrayList<>();
    Collections.addAll(l, arr);

    pushStack(l);
  }

  public int count() {
    return top;
  }
  
  public List<T> toList() {
    return new ArrayList<>(stack);
  }

}
