package jasmin;

public class CodeGeneratorUtils {

  public static String getBetweenStrings(String text,String textFrom,String textTo) {
    String result = "";
    result = text.substring(text.indexOf(textFrom) + textFrom.length(),text.length());
    result =result.substring(0,result.indexOf(textTo));
    return result;
  }

  public static String getMethod(String code, String methodName) {
    return CodeGeneratorUtils.getBetweenStrings(code, ".method public " + methodName, ".end method");
  }

  public static String getLimit(String method, String limitType) {
    return CodeGeneratorUtils.getBetweenStrings(method, ".limit " + limitType + " ", "\n");
  }

  public static String getExprIndex(String method, String expression){
    String index = CodeGeneratorUtils.getBetweenStrings(method, expression, "\n");
    String[] tokens = index.split("_|\\s");
    if (tokens.length < 2)
      return "";
    return tokens[1];
  }

  public static int getMethodLimit(String code, String methodName, String limitType) {
    String method = "";
    String limit = "";
    method = CodeGeneratorUtils.getMethod(code, methodName);
    limit = CodeGeneratorUtils.getLimit(method, limitType);
    int limitValue;
    try {
        limitValue = Integer.parseInt(limit);
    }
    catch (NumberFormatException e){
        limitValue = -1;
    }
    return limitValue;
  }

  public static boolean codeContainsExpression(String code, String[] expressions) {
    boolean ret = false;
    for(int i = 0; i < expressions.length; i++){
        ret |= code.contains(expressions[i]);
    }
    return ret;
  }

  public static boolean methodContainsExpression(String code, String methodName, String[] expressions) {
    String method = "";
    method = CodeGeneratorUtils.getMethod(code, methodName);
    boolean ret = false;
    for(int i = 0; i < expressions.length; i++){
        ret |= method.contains(expressions[i]);
    }
    return ret;
  }

  public static int getMehtodExprIndex(String code, String methodName, String expression) {
    String method = "";
    String index = "";
    method = CodeGeneratorUtils.getMethod(code, methodName);
    index = CodeGeneratorUtils.getExprIndex(method, expression);
    int indexValue;
    try {
        indexValue = Integer.parseInt(index);
    }
    catch (NumberFormatException e){
        indexValue = -1;
    }
    return indexValue;
  }


}