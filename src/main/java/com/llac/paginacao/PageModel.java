package com.llac.paginacao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class PageModel {
	
	private static int PAGE = 0;
	private static int SIZE = 5;
	private HttpServletRequest request;

	public PageModel(HttpServletRequest request) {
		this.request = request;
	}

	public void initPageAndSize() {
		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			PAGE = Integer.parseInt(request.getParameter("page")) - 1;
		}
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			PAGE = Integer.parseInt(request.getParameter("size"));
		}
	}

	public void setSIZE(int SIZE) {
		PageModel.SIZE = SIZE;
	}

	public int getPAGE() {
		return PAGE;
	}

	public int getSIZE() {
		return SIZE;
	}
}
