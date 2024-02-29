package daos;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import exception.CustomBankException;

public class DAOHelper {
	public <T> T mapResultSetToGivenClassObject(ResultSet resultSet, Class<T> clazz) throws CustomBankException {
        try {
            T givenClassInstance = clazz.getDeclaredConstructor().newInstance();

            Map<String, Method> settersMap = getSettersMap(clazz);

            for (String columnName : settersMap.keySet()) {
                if (columnExists(resultSet, columnName)) {
                    Method setterMethod = settersMap.get(columnName);
                    Class<?> parameterType = setterMethod.getParameterTypes()[0];

                    if (parameterType == int.class || parameterType == Integer.class) {
                        setterMethod.invoke(givenClassInstance, resultSet.getInt(columnName));
                    } else if (parameterType == long.class || parameterType == Long.class) {
                        setterMethod.invoke(givenClassInstance, resultSet.getLong(columnName));
                    } else if (parameterType == double.class || parameterType == Double.class) {
                        setterMethod.invoke(givenClassInstance, resultSet.getDouble(columnName));
                    } else if (parameterType == String.class) {
                        setterMethod.invoke(givenClassInstance, resultSet.getString(columnName));
                    } else {
                        throw new CustomBankException("Unsupported data type: " + parameterType.getSimpleName());
                    }
                }
            }

            return givenClassInstance;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new CustomBankException("Error mapping ResultSet to " + clazz.getSimpleName(), e);
        }
    }

    private Map<String, Method> getSettersMap(Class<?> clazz) {
        Map<String, Method> settersMap = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            if (isSetter(method)) {
                String fieldName = getFieldNameFromSetter(method.getName());
                settersMap.put(fieldName, method);
            }
        }
        return settersMap;
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set") && method.getParameterCount() == 1;
    }

    private String getFieldNameFromSetter(String setterName) {
        return Character.toLowerCase(setterName.charAt(3)) + setterName.substring(4);
    }

    private boolean columnExists(ResultSet resultSet, String columnName){
        try {
            resultSet.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
