package com.test.employee.controller;

import com.test.employee.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    String file = "employees.txt";

    @GetMapping("/")
    @ResponseBody
    public String showForm() {
        return "<html><body><div class='form-container'>" +
                "<h2>Employee Form</h2>" +
                "<form method='POST' action='/add'>" +
                "First name:<br><input type='text' name='fname'><br>" +
                "Last name:<br><input type='text' name='lname'><br>" +
                "Gender:<br>" +
                "<input type='radio' name='gender' value='Male'> Male<br>" +
                "<input type='radio' name='gender' value='Female'> Female<br>" +
                "Phone:<br><input type='text' name='phone'><br>" +
                "Age:<br><input type='text' name='age'><br>" +
                "<input type='submit' value='Add'><br><br>" +
                "</form>" +

                "<h3>View All Employees:</h3><a href='/view'>/view</a><br>" +
                "<h3>Delete by Phone:</h3><form method='POST' action='/delete'>" +
                "Phone: <input type='text' name='phone'><br>" +
                "<input type='submit' value='Delete'>" +
                "</form>" +

                "<h3>Modify Employee:</h3><form method='POST' action='/modify'>" +
                "Phone (existing): <input type='text' name='phone'><br>" +
                "New First name: <input type='text' name='fname'><br>" +
                "New Last name: <input type='text' name='lname'><br>" +
                "New Gender: <input type='text' name='gender'><br>" +
                "New Age: <input type='text' name='age'><br>" +
                "<input type='submit' value='Modify'>" +
                "</form></div></body></html>";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addEmployee(@ModelAttribute Employee employee) {
        try {
            String line = String.join(",", employee.getFname(), employee.getLname(),
                    employee.getGender(), employee.getPhone(), employee.getAge()) + "\n";
            Files.write(Paths.get(file), line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return "<p>" + employee.getFname() + " has been added successfully!</p>" +
                    "<a href='/'>Back to menu</a>";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/view")
    @ResponseBody
    public String viewEmployees() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("employees.txt"));
            String html = "<h2>Employees:</h2><ul>";

            for (String line : lines) {
                String[] p = line.split(",");
                html += "<li>Name: " + p[0] + " " + p[1] + ", Gender: " + p[2] +
                        ", Phone: " + p[3] + ", Age: " + p[4] + "</li>";
            }

            return html + "</ul><a href='/'>Back</a>";
        } catch (Exception e) {
            return "<p>Error: " + e.getMessage() + "</p><a href='/'>Back</a>";
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteEmployee(@RequestParam String phone) {
        try {
            Path path = Paths.get("employees.txt");
            List<String> lines = Files.readAllLines(path);
            List<String> updated = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (!parts[3].equals(phone)) {
                    updated.add(line);
                } else {
                    System.out.println("No such user found!");
                }
            }

            Files.write(path, updated);
            return "<p>Employee with phone " + phone + " deleted.</p><a href='/'>Back</a>";
        } catch (Exception e) {
            return "<p>Error deleting employee: " + e.getMessage() + "</p><a href='/'>Back</a>";
        }
    }

    @PostMapping("/modify")
    @ResponseBody
    public String modifyEmployee(@RequestParam String phone,
                                 @RequestParam String fname,
                                 @RequestParam String lname,
                                 @RequestParam String gender,
                                 @RequestParam String age) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(file));
            List<String> updated = new ArrayList<>();
            boolean modified = false;
            for (String line : lines) {
                String[] data = line.split(",");
                if (data[3].equals(phone)) {
                    String newLine = String.join(",", fname, lname, gender, phone, age);
                    updated.add(newLine);
                    modified = true;
                } else {
                    updated.add(line);
                }
            }
            Files.write(Paths.get(file), updated);
            if (modified)
                return "<p>Employee updated.</p><a href='/'>Back</a>";
            else
                return "<p>No employee with that phone found.</p><a href='/'>Back</a>";
        } catch (Exception e) {
            return "Error modifying employee: " + e.getMessage();
        }
    }
}