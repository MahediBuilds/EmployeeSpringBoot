package com.test.employee.controller;

import com.test.employee.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmployeeController {

    @GetMapping("/")
    @ResponseBody
    String showForm() {
        return "<html>" +
                "<body><div class='form-container'>" +
                "<h2 text-align: center>Employee Form</h2>" +
                "<form method='POST' action='/submit'>" +
                "First name:<br><input type='text' name='fname'><br>" +
                "Last name:<br><input type='text' name='lname'><br>" +
                "Gender:       <br>" +
                "<input type='radio' name='gender' value='Male'> Male<br>" +
                "<input type='radio' name='gender' value='Female'> Female<br>" +
                "Phone number:  <input type='text' name='phone'><br>" +
                "Age:   <input type='text' name='age'><br>" +
                "<input type='submit' value='Submit'> <br><br>" +
                "</form></div></body></html>";
    }

    @PostMapping("/submit")
    @ResponseBody
    public String handleForm(@ModelAttribute Employee employee) {
//        System.out.println(employee.getClass().getName());
//        System.out.println(employee);
//        System.out.println(employee.toString());
//        System.out.println(employee.toString().getClass());
//        System.out.println(employee.getAge());
        return "<html><body><h2>Employee Submitted</h2>" +
                "<p>First Name: " + employee.getFname() + "</p>" +
                "<p>Last Name: " + employee.getLname() + "</p>" +
                "<p>Gender: " + employee.getGender() + "</p>" +
                "<p>Phone: " + employee.getPhone() + "</p>" +
                "<p>Age: " + (employee.getAge() + 1)+ "</p>" +
                "<a href='/'>Go Back</a>" +
                "</body></html>";
    }
}
