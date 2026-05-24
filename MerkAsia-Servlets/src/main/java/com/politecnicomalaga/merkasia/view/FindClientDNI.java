package com.politecnicomalaga.merkasia.view;

import com.politecnicomalaga.merkasia.controller.Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindClientDNI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String dni = req.getParameter("dni");
        ServletUtils.writeJson(res, dni == null || dni.trim().isEmpty()
            ? (new Controller()).listClients()
            : (new Controller()).findClientDNI(dni));
    }
}
