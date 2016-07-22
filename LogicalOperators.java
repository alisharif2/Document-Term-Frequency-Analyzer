public class LogicalOperators {
  public static boolean conjunctionIter(boolean input[]) {
    for(int i = 0;i < input.length;++i) {
      input[0] = input[0] && input[i];
    }
    return input[0];
  }

  public static boolean disjunctionIter(boolean input[]) {
    for(int i = 0;i < input.length;++i) {
      input[0] = input[0] || input[i];
    }
    return input[0];
  }

  public static boolean conjunctionRec(boolean input[]) {
    if(input.length == 1) return input[0];
    input[1] = input[0] && input[1];
    boolean newInput[] = new boolean[input.length - 1];
    for(int i = 1;i < input.length;++i) {
      newInput[i - 1] = input[i];
    }
    return conjunctionRec(newInput);
  }

  public static boolean disjunctionRec(boolean input[]) {
    if(input.length == 1) return input[0];
    input[1] = input[0] || input[1];
    boolean newInput[] = new boolean[input.length - 1];
    for(int i = 1;i < input.length;++i) {
      newInput[i - 1] = input[i];
    }
    return disjunctionRec(newInput);
  }
}
