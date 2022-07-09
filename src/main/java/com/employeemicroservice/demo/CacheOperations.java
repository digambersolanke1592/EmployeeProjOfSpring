package com.employeemicroservice.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CacheOperations {

    @Autowired
    EmployeeRepository employeeRepository;

    public static HashMap<Integer,Employee> cache = new HashMap<>();
    public List<Employee> employeeList;

    @Scheduled(cron = "0 */2 * ? * *") // here it means it will load every 2 minutes cache memory
                   //here we directly write cron value. there is another way it is taken from application.property file also
                                       //first we write value like cacheload = 0 */2 * ? * * in application file then
                       //make cron = "${cacheload}" it will also work properly
    public  void loadCache(){
        System.out.println("cache load started");
        employeeList = employeeRepository.findAll();
        if(!employeeList.isEmpty()){
            employeeList.forEach(employee -> cache.put(employee.getEmployeeId(),employee));

        }
        System.out.println("cache load end");
    }

}
