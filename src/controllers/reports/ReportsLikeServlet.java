package controllers.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsLikeServlet
 */
@WebServlet("/reports/like")
public class ReportsLikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsLikeServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("report_id")));
        Employee e = (Employee) request.getSession().getAttribute("login_employee");

        List<Employee> employees_who_liked_report = new ArrayList<Employee>();
        employees_who_liked_report = r.getEmployees_who_liked_report();

        String status = request.getParameter("status");

        if (status.equals("like")) {
            employees_who_liked_report.add(e);
        }

        if (status.equals("cancel")) {
            //    employees_who_liked_report.remove(employees_who_liked_report.indexOf(e));
            for (int i = 0; i < employees_who_liked_report.size(); i++) {
                Employee e_of_list = employees_who_liked_report.get(i);
                if (e_of_list.getId().equals(e.getId())) {
                    employees_who_liked_report.remove(i);
                    break;
                }

            }


        }
        r.setEmployees_who_liked_report(employees_who_liked_report);

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());

    }
}
