package com.example.demo.controller;

import com.example.demo.pojo.School;
import com.example.demo.pojo.Student;
import com.example.demo.pojo.StudentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/save")
    public String save(){
        for(int i=0;i<100;i++){
            Student student=new Student();
            student.setId(i);
            student.setName("瓜田李下"+i);
            student.setAge(i%10+15);
            student.setSchoolId(i%3);

            mongoTemplate.save(student);
        }

        for (int i=0;i<3;i++){
            School school=new School();
            school.setId(i);
            school.setName("海贼王"+i);

            mongoTemplate.save(school);
        }

        return "success";
    }

    @RequestMapping("/get")
    public List<Map> get(){
        LookupOperation lookupOperation=LookupOperation.newLookup()
                .from("school")
                .localField("schoolId")
                .foreignField("_id")
                .as("school");

        Aggregation aggregation=Aggregation.newAggregation(lookupOperation);
        AggregationResults<Map> results=mongoTemplate.aggregate(aggregation,"student",Map.class);

        return results.getMappedResults();
    }

    @RequestMapping("/get2")
    public List<StudentMap> get2(){
        LookupOperation lookupOperation=LookupOperation.newLookup()
                .from("school")
                .localField("schoolId")
                .foreignField("_id")
                .as("school");

        Aggregation aggregation=Aggregation.newAggregation(lookupOperation);
        AggregationResults results=mongoTemplate.aggregate(aggregation,"student",StudentMap.class);

        return results.getMappedResults();
    }

    @RequestMapping("/get3")
    public List<Map> get3(){
        LookupOperation lookupOperation=LookupOperation.newLookup()
                .from("school")
                .localField("schoolId")
                .foreignField("_id")
                .as("school");

        Aggregation aggregation=Aggregation.newAggregation(lookupOperation,Aggregation.skip(10L),Aggregation.limit(10L),Aggregation.sort(Sort.by("school").descending()));
        AggregationResults<Map> results=mongoTemplate.aggregate(aggregation,"student",Map.class);

        return results.getMappedResults();
    }

    @RequestMapping("/get4")
    public List<StudentMap> get4(){
        LookupOperation lookupOperation=LookupOperation.newLookup()
                .from("school")
                .localField("schoolId")
                .foreignField("_id")
                .as("school");

        Aggregation aggregation=Aggregation.newAggregation(lookupOperation,Aggregation.skip(5L),Aggregation.limit(5L),Aggregation.sort(Sort.by("school").ascending()));
        AggregationResults results=mongoTemplate.aggregate(aggregation,"student",StudentMap.class);

        return results.getMappedResults();
    }
}