package yamlConvertor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import exception.CustomBankException;

public class YamlMapper{
	private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
	
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> mapToObject(String path) throws CustomBankException{
		Map<String, Map<String, String>> map;
		try {
			map = mapper.readValue(new File(path), Map.class);
		} catch (IOException e) {
			throw new CustomBankException("Error in mapping!", e);
		}
		return map;
	}
	
	
}
