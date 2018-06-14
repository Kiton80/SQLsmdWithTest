package ua.nikitchenko.Sqlcmd.web;


import ua.nikitchenko.Sqlcmd.model.DatabaseManager;
import ua.nikitchenko.Sqlcmd.service.Service;
import ua.nikitchenko.Sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class MainServlet extends HttpServlet {
    private Service service;

    @Override
    public void init() throws ServletException {
        super.init();
        service = new ServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("db_manager")==null){
            req.getRequestDispatcher("connect.jsp").forward(req, resp);
        }
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("db_manager");
        if (manager==null){
            resp.sendRedirect(resp.encodeRedirectURL("connect"));
        }

        String action = getAction(req);
        if (action.startsWith("/menu") || action.equals("/")) {
            req.setAttribute("items", service.commandsList());
            req.getRequestDispatcher("menu.jsp").forward(req, resp);

        } else if (action.startsWith("/help")) {
            req.getRequestDispatcher("help.jsp").forward(req, resp);

        } else if (action.startsWith("/list")){
            req.setAttribute("names", service.list((DatabaseManager) req.getSession().getAttribute("db_manager")));
            req.getRequestDispatcher("list.jsp").forward(req, resp);

        }else if (action.startsWith("/find")){
            req.getSession().setAttribute("table", "users");
            String tableName= (String) req.getSession().getAttribute("table");
            try {
                req.setAttribute("table", service.find(manager,tableName));
            } catch (Exception e) {
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
            req.getRequestDispatcher("find.jsp").forward(req, resp);
        }
        else {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length(), requestURI.length());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);

        if (req.getSession().getAttribute("db_manager")!=null){
            req.getRequestDispatcher("is_connected.jsp").forward(req, resp);
        }

        if (action.startsWith("/connect")) {
            String databaseName = req.getParameter("dbname");
            String userName = req.getParameter("username");
            String password = req.getParameter("password");

            try {
                service.connect(databaseName, userName, password);
                DatabaseManager manager = service.connect(databaseName, userName, password);
                req.getSession().setAttribute("db_manager", manager);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));
            } catch (Exception e) {
                req.setAttribute("message", e.getMessage());
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        } else if (action.startsWith("/add")){
            //todo

            }



    }

}
