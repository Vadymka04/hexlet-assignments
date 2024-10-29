package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;
import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        Class<Address> addressClass = (Class<Address>) address.getClass();
        Method[] methods = addressClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Inspect.class)) {
                String methodName = method.getName();
                Class<?> returnType = method.getReturnType();
                System.out.println("Method " + methodName + " returns a value of type " + returnType.getSimpleName());
            }
        }
    }
}
