package com.politecnicomalaga.tienda.view;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** Shared utilities for all servlets in this package. */
final class ServletUtils {

    private ServletUtils() {}

    /**
     * Sets the response content type to JSON and writes {@code body}.
     *
     * @param res  servlet response
     * @param body JSON string to send
     */
    static void writeJson(HttpServletResponse res, String body) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = res.getWriter()) {
            out.print(body);
        }
    }
}
