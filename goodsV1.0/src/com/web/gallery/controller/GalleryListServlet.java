package com.web.gallery.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.inquiry.model.service.AdminInquiryService;
import com.web.gallery.model.service.GalleryService;
import com.web.inquiry.model.vo.Inquiry;

/**
 * Servlet implementation class GalleryListServlet
 */
@WebServlet("/gallery/list")
public class GalleryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GalleryListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//페이지넘버, 카테고리, 상품명을 인자로 받아서 디비에서 리스트 불러오기
//		new GalleryService().getGalleryList();
		
		
		// DB에 저장된 문의내역을 페이징처리해서 가져온다
		int cPage;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {
			cPage = 1;
		}

		int numPerPage;
		try {
			numPerPage = Integer.parseInt(request.getParameter("numPerPage"));
		} catch (NumberFormatException e) {
			numPerPage = 10;
		}

//		List<Inquiry> list = new GalleryService().getGalleryList(cPage, numPerPage);

		// 페이지바 만들기
		int totalInquiry = new AdminInquiryService().inquiryCount();

		int totalPage = (int) Math.ceil((double) totalInquiry / numPerPage);

		int pageBarSize = 5; // 밑에 바는 5개씩만 보여준다

		// pageBar의 시작번호, 끝번호까지 출력해주는 변수
		int pageNo = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		int pageEnd = pageNo + pageBarSize - 1;

		String pageBar = "";

		// [<]버튼 만들기
		if (pageNo == 1) {
			pageBar += "<span><</span>";
		} else {			       
			pageBar += "<a href='" + request.getContextPath() + "/InquiryList?cPage=" + (pageNo - 1) + "&numPerPage="
					+ numPerPage + "'><</a>&nbsp ";
		}

		// 1 2 3 4 5
		// 5보다 크거나 10페이지보다 크지 않을때
		while (!(pageNo > pageEnd || pageNo > totalPage)) {
			if (pageNo == cPage) {
				pageBar += "<span class='cPage'>" + pageNo + "</span>&nbsp ";
			} else {
				pageBar += "<a href='" + request.getContextPath() + "/InquiryList?cPage=" + pageNo + "&numPerPage="
						+ numPerPage + "'>" + pageNo + "</a>&nbsp ";
			}
			pageNo++;
		}

		// [>]
		if (pageNo > totalPage) {
			pageBar += "<span>></span>&nbsp";
		} else {
			pageBar += "<a href='" + request.getContextPath() + "/InquiryList?cPage=" + pageNo + "&numPerPage="
					+ numPerPage + "'>></a>";
		}


//		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.setAttribute("cPage", cPage);
		
		request.getRequestDispatcher("/views/client/gallery/galleryList.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
