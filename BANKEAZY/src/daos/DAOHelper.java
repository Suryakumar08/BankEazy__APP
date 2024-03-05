package daos;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.CustomBankException;

public class DAOHelper {
	public <T> T mapResultSetToGivenClassObject(ResultSet resultSet, Class<T> clazz, Map<String, Method> settersMap)
			throws CustomBankException {
		try {
			T givenClassInstance = clazz.getDeclaredConstructor().newInstance();

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

	public Map<String, Method> getSettersMap(Class<?> clazz) {
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

	private boolean columnExists(ResultSet resultSet, String columnName) {
		try {
			resultSet.findColumn(columnName);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public String generateUpdateQuery(Object pojo) throws CustomBankException {

		Class<?> clazz = pojo.getClass();
		String tableName = clazz.getSimpleName();
		StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");
		List<Field> fields = getAllFields(clazz);
		Collections.sort(fields, (a, b) -> a.getName().compareTo(b.getName()));
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				String fieldName = field.getName();
				Object fieldValue = field.get(pojo);
				if (fieldValue != null) {
					query.append(fieldName).append(" = ?, ");
				}
			} catch (IllegalAccessException e) {
				throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
			}
		}

		query.setLength(query.length() - 2);

		return query.toString();
	}

	private List<Field> getAllFields(Class<?> clazz) {
		List<Field> allFields = new ArrayList<>();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				allFields.add(field);
			}
			clazz = clazz.getSuperclass();
		}
		return allFields;
	}

	public <T> int setFields(PreparedStatement statement, T pojo) throws CustomBankException {

		Map<String, Method> getterMethodMap = getGettersMap(pojo);

		Class<?> clazz = pojo.getClass();
		int parameterIndex = 1;
		List<Field> fields = getAllFields(clazz);
		Collections.sort(fields, (a, b) -> a.getName().compareTo(b.getName()));

		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Method getterMethod = getterMethodMap.get(fieldName);
			if (getterMethod != null) {
				try {
					Object value = getterMethod.invoke(pojo);
					if (value != null) {
						statement.setObject(parameterIndex++, value);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| SQLException e) {
					throw new CustomBankException(CustomBankException.ERROR_OCCURRED, e);
				}
			}
		}
		return parameterIndex;
	}

	private <T> Map<String, Method> getGettersMap(T pojo) {
		Map<String, Method> getterMethodsMap = new HashMap<>();
		Class<?> clazz = pojo.getClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("get")) {
				String fieldName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
				getterMethodsMap.put(fieldName, method);
			}
		}
		return getterMethodsMap;
	}
}
