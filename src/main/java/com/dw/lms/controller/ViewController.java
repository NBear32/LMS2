package com.dw.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // View 를 Controll
public class ViewController { // 디자인 패턴, MVC(Model[정보] View(=>Front 로 넘어감) Controll[로직(Controll+Service)]) 디자인 패턴

    @GetMapping("/lms/base.html")
    public String base(){return "base";}

    @GetMapping("/lms/cart.html")
    public String cart() {
        return "cart";
    }

    @GetMapping("/lms/course.html")
    public String course(){return "course";}

    @GetMapping("/lms/footer.html")
    public String footer() {
        return "footer";
    }

    @GetMapping("/lms/header.html")
    public String header() {
        return "header";
    }

    @GetMapping("/lms/lecture.html")
    public String lecture(){return "lecture";}

    @GetMapping("/lms/lectureDetail.html")
    public String lectureDetail(){return "lectureDetail";}

    @GetMapping("/lms/lms_events.html")
    public String lms_events(){return "lms_events";}

    @GetMapping("/lms/lms_notices.html")
    public String lms_notices(){return "lms_notices";}

    @GetMapping("/lms/lms_qa.html")
    public String lms_qa(){return "lms_qa";}

    @GetMapping("/lms/login.html")
    public String login() {
        return "login";
    } // login.html (.html 생략)

    @GetMapping("/lms/main.html")
    public String main(){return "main";}

    @GetMapping("/lms/mgmt_enrollment.html")
    public String mgmt_enrollment() {
        return "mgmt_enrollment";
    }

    @GetMapping("/lms/mgmt_lecture.html")
    public String mgmt_lecture() {
        return "mgmt_lecture";
    }

    @GetMapping("/lms/mgmt_user.html")
    public String mgmt_user() {
        return "mgmt_user";
    }

    @GetMapping("/lms/mypage.html")
    public String mypage() {
        return "mypage";
    }

    @GetMapping("/lms/mypageLecture.html")
    public String mypageLecture() {
        return "mypageLecture";
    }

    @GetMapping("/lms/mypageUserDelete.html")
    public String mypageUserDelete() {
        return "mypageUserDelete";
    }

    @GetMapping("/lms/reviewDetail.html")
    public String reviewDetail(){
    		return "reviewDetail";
		}

    @GetMapping("/lms/course/video.html")
    public String video(){return "popupVideo";}



}
