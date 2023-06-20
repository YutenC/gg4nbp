package gg.nbp.core.util;

import static gg.nbp.core.util.Constants.GSON;
import static gg.nbp.core.util.Constants.JSON_MIME_TYPE;

import java.io.BufferedReader;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CommonUtil<V> {
	
	public static <V> V json2pojo(HttpServletRequest request, Class<V> classOfvo) {
		try (BufferedReader br = request.getReader()) {
			return GSON.fromJson(br, classOfvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <V> void writepojo2Json(HttpServletResponse response, V vo) {
		response.setContentType(JSON_MIME_TYPE);
		try (PrintWriter pw = response.getWriter()) {
			pw.print(GSON.toJson(vo));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
