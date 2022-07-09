package com.employeemicroservice.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

//this is the comment for checking github operation 

     @Autowired
     EmployeeRepository employeeRepository;

    @GetMapping ("/getMsg/{name}")
    public String sayHello(@PathVariable String name){
      //  return "Hi team we are creating employee microservices my name is";
        String temp = "Hi team we are creating employee microservices my name is ";
        return temp+name ;
    }
    //create operation
    //expected employee class entity class with the help of this we are passing employee request body
    //through postman and we are going to store that employee in db and cache

    //create Api
    @PostMapping("/createEmp")
    public Employee createEmployee(@RequestBody Employee employee){

        return employeeRepository.save(employee);
    }
//    @PutMapping("/updateEmp")
//    public Employee updatrEmployee (@RequestBody Employee employee){
//
//        Optional<Employee> oldemployee = employeeRepository.findById(employee.getEmployeeId()) ;//here aftergetemployeeId.get() we can write inplace of optional
//
//        //optional use for avoiding  nullpointer exception
//
//       if (oldemployee.isPresent()){
//
//          return employeeRepository.save(employee);
//
//       }
//       return null;
//    }

    @PutMapping("/updateEmp")
    public ResponseEntity<Employee> updatrEmployee (@RequestBody Employee employee){

        Optional<Employee> oldemployee = employeeRepository.findById(employee.getEmployeeId()) ;

        if (oldemployee.isPresent()){

            return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/searchEmp/{id}")
        public Employee getEmployee(@PathVariable int id){

        //return employeeRepository.findById(id);
        return CacheOperations.cache.get(id);
        }

        @GetMapping("/getAllEmp")
         public List<Employee> getAllEmployee(){

       // return employeeRepository.findAll();
            return  new ArrayList<>(CacheOperations.cache.values());
        }
       @DeleteMapping("/deleteEmpById/{id}")
       public String  deleteEmpById(@PathVariable int id) {

           if (employeeRepository.findById(id).isPresent()) {
               employeeRepository.deleteById(id);
               return "Employee deleted successful";
           } else {
               return "employee is not found";
           }
       }
}
