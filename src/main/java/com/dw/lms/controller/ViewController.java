package com.dw.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Struct;

@Controller // View 를 Controll
public class ViewController { // 디자인 패턴, MVC(Model[정보] View(=>Front 로 넘어감) Controll[로직(Controll+Service)]) 디자인 패턴
    @GetMapping("/login_form")
    public String login_form() {
        return "login_form";
    } // login.html (.html 생략)

    @GetMapping("/articles")
    public String article() {
        return "article";
    } // article.html (.html 생략)

    @GetMapping("/lms/index.html")
    public String index() {
        return "index";
    }

    @GetMapping("/lms/singleProduct.html")
    public String singleProduct() {
        return "singleProduct";
    }

    @GetMapping("/lms/login.html")
    public String login() {
        return "login";
    }

    @GetMapping("/lms/cart.html")
    public String cart() {
        return "cart";
    }

    @GetMapping("/lms/mypage.html")
    public String mypage() {
        return "mypage";
    }

    @GetMapping("/lms/header.html")
    public String header() {
        return "header";
    }
    @GetMapping("/lms/footer.html")
    public String footer() {
        return "footer";
    }

    @GetMapping("/lms/main.html")
    public String main(){return "main";}

    @GetMapping("/lms/lecture.html")
    public String lecture(){return "lecture";}

    @GetMapping("/lms/lms_notices.html")
    public String lms_notices(){return "lms_notices";}

    @GetMapping("/lms/lms_qa.html")
    public String lms_qa(){return "lms_qa";}

    @GetMapping("/lms/lms_events.html")
    public String lms_events(){return "lms_events";}

}
