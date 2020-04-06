package com.web.admin.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.product.service.AdminProductService;
import com.web.product.model.vo.Product;


@WebServlet("/admin/productHeaderMenu")
public class ProductHeaderMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ProductHeaderMenuServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//헤더 메뉴 상품 가져오기
		
		
		List<Product> list =  new AdminProductService().productHeaderMenu();
		
		request.setAttribute("List", list);
		
		request.getRequestDispatcher("/views/client/common/header.jsp")
		.forward(request, response);
		
		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println(no);
		
		List<Product> listP  =  new AdminProductService().productInfo(no);
		
		request.setAttribute("ListP", listP);
		request.getRequestDispatcher("/views/client/product/productView.jsp")
		.forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
