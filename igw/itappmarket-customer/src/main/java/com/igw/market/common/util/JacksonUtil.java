package com.igw.market.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {

	private static final Log logger = LogFactory.getLog(JacksonUtil.class);

	public static String parseString(String body, String field) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		try {
			if (null == body || "".equals(body)) {
				return null;
			}
			node = mapper.readTree(body);
			JsonNode leaf = node.get(field);
			if (leaf != null)
				return leaf.asText();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static Integer parseInteger(String body, String field, Integer defValue) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		try {
			if (null == body || "".equals(body))
				return defValue;
			node = mapper.readTree(body);
			JsonNode leaf = node.get(field);
			if (leaf != null)
				return leaf.asInt();
			return defValue;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return defValue;
	}

	public static <T> T parseObject(String body, String field, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		try {
			if (null == body || "".equals(body))
				return null;
			node = mapper.readTree(body);
			node = node.get(field);
			return mapper.treeToValue(node, clazz);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T> T toObject(String body, Class<T> clazz) {
		if (body == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonNode node;
		try {
			if (null == body || "".equals(body))
				return null;
			node = mapper.readTree(body);
			return mapper.treeToValue(node, clazz);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public static <T> Map<String, T> toMap(String data, Class<T> clazz) {

		Map<String, T> result = new HashMap<String, T>();

		if (null == data || "".equals(data))
			return result;

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JsonNode node;
		Entry<String, JsonNode> jn;
		try {
			node = mapper.readTree(data);
			Iterator<Entry<String, JsonNode>> iterable = node.fields();
			while (iterable.hasNext()) {
				jn = iterable.next();
				result.put(jn.getKey(), mapper.treeToValue(jn.getValue(), clazz));
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public static <T> List<T> toList(String body, String field, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		try {
			node = mapper.readTree(body);
			JsonNode leaf = node.get(field);

			if (leaf.isArray()) {
				for (JsonNode jn : leaf) {
					result.add(mapper.treeToValue(jn, clazz));
				}
				return result;
			} else {
				return null;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String toJson(Object data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
