package com.web.review.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.web.product.model.service.ProductService;
import com.web.product.model.vo.Product;
import com.web.review.model.service.ReviewService;
import com.web.review.model.vo.Review;

/**
 * Servlet implementation class ReviewCategoryServlet
 */
@WebServlet("/reviewCategory")
public class ReviewCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewCategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject jsonObj=new JSONObject();
		List<Review> list;
		String reviewList="";
		String starpoint="";
		int count=0;
		if(request.getParameter("p_Category")==null) {
			list=new ReviewService().searchReview();
			count=new ReviewService().countReview();
		}else {
			String p_Category=request.getParameter("p_Category");
			if(p_Category.equals("전체")) {
				list=new ReviewService().searchReview();
				count=new ReviewService().countReview();
			}else {
				list=new ReviewService().searchReviewCategory(p_Category);
				count=new ReviewService().countReviewCategory(p_Category);
			}
		}
		//html 작성부분
		for(Review r:list) {
			starpoint="";
			for(int i=0;i<r.getRv_Star();i++) {
				starpoint+="<span class='fa fa-star checked'></span>";		
			}
			for(int i=0;i<5-(r.getRv_Star());i++) {
				starpoint+="<span class='fa fa-star'></span>";
			}
			Product p=new ProductService().getProduct(r.getP_No());
			reviewList+="<div style='border-bottom: 2px solid rgba(0, 0, 0, 0.2); width: 100%;'>"
					+ "<input id='RvNo' type='hidden' value='"+r.getRv_No()+"'>"
							+ "<table>"
								+ "<tr>"
									+ "<td class='reviewProductImg' rowspan='3'><a href='#'>"
									+ "<img class='review-product'" 
									+ "src='"+request.getContextPath()+"/images/product/thumbnail/"+p.getpThumbnail()+"'>"
									+ "</a></td>"
									+ "<td class='reviewTitle' colspan='3'>" + 
									"<a href='#'>"+p.getpName()+
									"</a></td></tr>"
									+ "<tr class='reviewTR'>"
									+ "<td class='reviewStar'>"
									+ starpoint+ "</td>"
									+ "<td class='reviewDate'>"+r.getRv_Date()+"</td>"
									+ "<td class='reviewWriter'>"+r.getM_nickName()+"</td>"
									+ "</tr><tr><td class='reviewContent' style='width:150px;' colspan='3'>"+r.getRv_Content()+"</td></tr></table>"
									+ "<div class='reviewImg'>"
									+ "<img"
									+" src='https://s3.marpple.co/files/u_1206533/2020/3/900/18296301f2293ae1ec778c915db20e7aab4de4adc53c16b.jpg'>"
									+"</div>"
									+"</div>";
		}
		jsonObj.put("reviewList", reviewList);
		jsonObj.put("count", count);
		response.setContentType("application/json; charset=UTF-8");
		new Gson().toJson(jsonObj,response.getWriter());
		
//		response.setContentType("text/html");
//		response.setCharacterEncoding("utf-8");
//		response.getWriter().write(reviewList);
		//request.getRequestDispatcher("views/client/review/reviewList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
