package prv.ak.testable;

//pastebin.com/Q7SYjiNM

import java.lang.reflect.*;

public class TestsGenerator {

    private final String testObject = "testObject";
    private static Integer testNr = 0;

    private String preamble(Class<?> c) {
        String result = """
    package prv.ak;
    
    import org.junit.*;
    import static org.junit.Assert.*;
    """;

        result += "import " + c.getPackageName() + "." + c.getSimpleName() + ";\n\n";

        result += "public class AppTest {\n";
        return result;
    }

    private String end() {
        return "}";
    }

    private String quote(Class<?> type, String string) {
        return switch (type.getSimpleName()) {
            case "String" -> "\"" + string + "\"";
            case "Character", "char" -> "'" + string + "'";
            default -> string;
        };
    }

    private String args(Parameter[] parameters, String[] args) {
        if (parameters.length != args.length) {
            throw new IllegalArgumentException("Wrong number of parameters");
        }
        String result = "";
        for (int i = 0; i < parameters.length; i++) {
            result += quote(parameters[i].getType(),args[i]);
            if (i < parameters.length - 1) {
                result += ",";
            }
        }
        return result;
    }

    private String testObject(Class<?> c) {
        String result = "";
        for (Constructor constructor : c.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(TestableConstructor.class)) {
                result += "    " + c.getSimpleName() + " " + testObject
                        + " = new " + c.getSimpleName()
                        + "(" + args(constructor.getParameters(), constructor.getDeclaredAnnotation(TestableConstructor.class).args()) + ");\n\n";
                break;
            }
        }
        return result;
    }

    private String generateTest(Method method) {
        String result = "";
        result += "    @Test\n";
        result += "    public void test_" + (++testNr) + "_" + method.getName() + "() {\n";
        result += "        assertEquals(";
        result += quote(method.getReturnType(),method.getDeclaredAnnotation(TestableMethod.class).expectedValue());
        result += ",";
        result += testObject + "." + method.getName() + "(";
        result += args(method.getParameters(), method.getDeclaredAnnotation(TestableMethod.class).args());
        result += ")";
        result += ");\n";
        result += "    }\n\n";
        return result;
    }

    private boolean isTestable(Method method) {
        return method.isAnnotationPresent(TestableMethod.class);
    }

    private String generateTests(Class<?> c) {
        String result = "";
        for (Method method : c.getDeclaredMethods()) {
            if (!method.getReturnType().getName().equals("void")) {
                if (isTestable(method)) {
                    result += generateTest(method);
                }
            }
        }
        return result;
    }

    public String generateFileWithAllTests(String className) {
        String result = "";
        try {
            Class<?> c = Class.forName(className);
            result += preamble(c);
            result += testObject(c);
            result += generateTests(c);
            result += end();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
