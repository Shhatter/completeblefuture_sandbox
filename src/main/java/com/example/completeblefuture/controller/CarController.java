package com.example.completeblefuture.controller;

import com.example.completeblefuture.model.Car;
import com.example.completeblefuture.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity uploadFile(
        @RequestParam(value = "files") MultipartFile[] files) {
        try {
            for (final MultipartFile file : files) {
                carService.saveCars(file.getInputStream());
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    ResponseEntity<List<Car>> getAllCars() {
        try {

            var list = new ArrayList<CompletableFuture<List<Car>>>();


            for(int i = 0 ; i<2 ; i++) {

                list.add(carService.getAllCars()) ;
            }

//          var out =  list.stream().map(CompletableFuture::join).flatMap(Collection::stream).toList()  ;
//
//         var out =    CompletableFuture.allOf(list.toArray(new CompletableFuture[0]))
//                .thenApply(a -> list.stream().map(CompletableFuture::join).collect(Collectors.toList()));

            var out =  CompletableFuture.allOf(list.toArray(new CompletableFuture[0]))
                .thenApply(a -> list.stream().map(CompletableFuture::join).flatMap(Collection::stream).toList()).get();


            return ResponseEntity.ok()
                .body(out);

        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}