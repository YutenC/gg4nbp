package gg.nbp.web.SecondHand.buy.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/secondhand/selectimg")
public class SelectImg extends HttpServlet {
	private static final long serialVersionUID = 7319458959906666937L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		File src = new File("C:\\Users\\Tibame_T14\\Desktop\\AppImage\\" + req.getParameter("imgname"));
		resp.setContentType("image/gif");
		try (ServletOutputStream out = resp.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src))) {
			out.write(bis.readAllBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
