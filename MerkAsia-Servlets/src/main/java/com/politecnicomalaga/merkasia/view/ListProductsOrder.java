package com.politecnicomalaga.merkasia.view;

import com.politecnicomalaga.merkasia.controller.Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListProductsOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String dni = req.getParameter("dni");
        String code = req.getParameter("code");
        ServletUtils.writeJson(res, new Controller().listProductsOrder(dni, code));
    }
}
