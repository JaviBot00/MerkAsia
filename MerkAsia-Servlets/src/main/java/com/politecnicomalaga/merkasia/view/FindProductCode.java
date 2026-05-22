package com.politecnicomalaga.merkasia.view;

import com.politecnicomalaga.merkasia.controller.Controller;

import javax.servlet.http.*;
import java.io.IOException;

public class FindProductCode extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String code = req.getParameter("code");
        ServletUtils.writeJson(res, (new Controller()).findProductCode(code));
    }
}
