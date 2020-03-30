package com.web.review.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.web.review.model.service.ReviewService;
import com.web.review.model.vo.Review;

/**
 * Servlet implementation class ReviewView
 */
@WebServlet("/reviewView")
public class ReviewViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int reviewNo=Integer.parseInt(request.getParameter("rvNo"));
		Review review=new ReviewService().reviewView(reviewNo);
		Gson gson=new Gson();
		String jsonReview="";
		try {
			jsonReview=gson.toJson(review);
		}catch(Exception e) {
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(jsonReview);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
