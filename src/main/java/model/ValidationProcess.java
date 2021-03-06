package model;

import com.google.gson.GsonBuilder;
import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import org.mozilla.javascript.*;

/**
 * This class initiates the validation process. As an input it takes the
 * Rules.js where the rules are defined and an Invoice i, a plain java
 * object. Then it validates the values of the Invoice i based on the rules
 * in Rules.js. If there are any errors, they will be saved into an Object Array.
 * That way, wicket can take those errors and output them. This happens in the
 * InvoiceForm.java.
 *
 * @author Dave, e0726371
 */
public class ValidationProcess {

    Object[] errorMessages;
    Context cx = Context.enter();
    Scriptable scope = cx.initStandardObjects();

    public ValidationProcess() {
    }

    /**
     * The Method reads a JavaScript document, where rules for Validation are
     * definied. Then it validates the bean (Invoice i) according to the
     * rules in the JavaScript file.
     *
     * @param filename is the JavaScript document that contains the rules
     * @param i is the Invoice that will be validated
     */
    public void validate(String filename, Invoice i) {
        try {

            /*
             * 1. Read the javascript file
             */
            String inputFile = readFile(filename);

            /*
             * 2. Initialize Rhino and evaluate
             */
            cx = Context.enter(); //Creates and enters a Context. A Context stores information about the execution environment of a script.
            scope = cx.initStandardObjects(); //Initializes the standard objects (Object, Function, etc.). This must be done before scripts can be executed.
            cx.evaluateString(scope, inputFile, "<cmd>", 1, null); //evaluate: The inputFile will be associated with the scope

            /*
             * 3. set the local variables of the javascript file to the value of the invoice i
             */
            setVariables(i);

            /*
             * 4. Call the functions with the given input and saves errors from javascript into errorMessages Array.
             */
            callFunctions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * readFile() is an efficient Method to read a file into a String.
     *
     * @param path is the path of the file
     * @return String returns a String containing the characters of the given
     * file.
     */
    private String readFile(String filename) throws IOException {
        URL url = getClass().getResource("..\\rules\\" + filename);
        File file = new File(url.getPath());
        FileInputStream stream = new FileInputStream(file);
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            return Charset.defaultCharset().decode(bb).toString();
        } finally {
            stream.close();
        }
    }

    /**
     * setVariables() generates a json object from the given Invoice i and maps
     * it to an javascript "invoice" variable. Scope.get() gets the Objects from
     * the javascript file and scope.put() updates to objects according to the
     * values of your invoice.
     *
     * @param i is the Invoice
     */
    private void setVariables(Invoice i) throws IOException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchMethodException {
        invokeSetters(i);  //If you want to disable JSON HACK, please comment this line
        String json = new GsonBuilder().serializeNulls().create().toJson(i);
        System.out.println("JSON: " +json);
        scope.put("invoice", scope, json);
        Function eval = (Function) scope.get("evaluation", scope);
        eval.call(cx, scope, scope, null);
    }

    /**
     * callFunctions() calls the callFunctions() Method inside the javascript
     * file. Returning errors will be saved in the errorMessages String.
     */
    private void callFunctions() {
        Function callFunctions = (Function) scope.get("callFunctions", scope);
        Object result = callFunctions.call(cx, scope, scope, null);
        NativeArray arr = (NativeArray) result;
        errorMessages = new Object[(int) arr.getLength()];
        for (Object o : arr.getIds()) {
            int index = (Integer) o;
            errorMessages[index] = arr.get(index, null);
        }
    }

    /**
     * This method invoke all setter methods of user-defined classes. 
     * It iterates an object and if an attribute (e.g. Adresse) is user-defined, then it creates a new Instance of this class
     * and invokes the setter method. It is recursive and also check sub-classes.
     * @param o is the object
     */
    private void invokeSetters(Object o) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        System.out.println("INVOKING THE OBJECT " + o.getClass().toString());
        for (Field f : o.getClass().getDeclaredFields()) {
            System.out.println("NOW IN THE FIELD: " + f.getName());
            f.setAccessible(true);
            Object obj = null;
            if (isUserDefined(f.getType()) && f.get(o) == null) {
                System.out.println("FIELD is USER DEFINED AND NULL");
                Class c = Class.forName(f.getType().getName());
                System.out.println("CLASS IS " + c.getName());
                obj = c.getConstructor().newInstance();
                System.out.println("GOING INSIDE " + obj.toString() + " NOW.");
                invokeSetters(obj);
            }
            if (obj != null) {
                Method setter = getSetterMethod(o, f);
                System.out.println("IM IN PARENT OBJECT " + o + " AND INVOKING MTEHOD " + setter.getName() + " WITH PARAMETER " + obj.toString());
                setter.invoke(o, obj);
                System.out.println("METHOD WAS INVOKED");
            }
        }
    }

    public boolean isUserDefined(Class o) {
        if (o.isAssignableFrom(String.class)) {
            return false;
        }
        if (o.isAssignableFrom(Double.class)) {
            return false;
        }
        if (o.isAssignableFrom(Integer.class)) {
            return false;
        }
        if (o.isAssignableFrom(Boolean.class)) {
            return false;
        }
        if (o.isAssignableFrom(Short.class)) {
            return false;
        }
        if (o.isAssignableFrom(Float.class)) {
            return false;
        }
        if (o.isAssignableFrom(Long.class)) {
            return false;
        }
        return true;
    }

    /**
     * This method return a setter method of an object.
     * @param o is the object, of which the setter method should be returned.
     * @param f is a field of a class, e.g. Adresse
     * @return method if the method was found, null otherwise
     */
    private static Method getSetterMethod(Object o, Field f) {
        for (Method method : o.getClass().getMethods()) {
            String fieldname = "set" + f.getName();
            String methodname = method.getName().toLowerCase();
            if (fieldname.equals(methodname)) {
                return method;
            }
        }
        return null;
    }

    public Object[] getErrorMessages() {
        return errorMessages;
    }
}
