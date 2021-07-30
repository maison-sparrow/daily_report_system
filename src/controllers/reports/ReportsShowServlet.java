package controllers.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        //追加
        Employee e = (Employee) request.getSession().getAttribute("login_employee");


        //List<Employee> employees_who_liked_report = r.getEmployees_who_liked_report();
        List<Employee> employees_who_liked_report = new ArrayList<Employee>();
        employees_who_liked_report = r.getEmployees_who_liked_report();

        int like_count = employees_who_liked_report.size();


        boolean like_or_not = employees_who_liked_report.contains(e);



        //employees_who_liked_reportは別のEntityのListなので
        //closeするのはrをnewしてすぐでなく、Listを宣言してから。
        em.close();

        request.setAttribute("like_count", like_count);
        request.setAttribute("report", r);
        //追加
        request.setAttribute("like_or_not", like_or_not);

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
