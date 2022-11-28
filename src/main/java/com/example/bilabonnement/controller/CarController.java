package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Costumer;
import com.example.bilabonnement.model.Lease;
import com.example.bilabonnement.repository.CarRepository;
import com.example.bilabonnement.service.CarService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CarController {

  private CarService carService = new CarService();

  @GetMapping("/create-lease")
  public String createLease() {
    return "create-lease";
  }

  @PostMapping("/create-lease")
  public String postCreateLease(@RequestParam("costumer_first_name") String firstName, @RequestParam("costumer_last_name") String lastName, @RequestParam("costumer_email") String email, @RequestParam("costumer_phone") String phone, @RequestParam("lease_start") String leaseStart, @RequestParam("lease_end") String leaseEnd, HttpSession session) {
    Costumer costumer = new Costumer(firstName, lastName, email, phone);
    session.setAttribute("costumer", costumer);
    session.setAttribute("leaseStart", leaseStart);
    session.setAttribute("leaseEnd", leaseEnd);
    return "redirect:/car-list";
  }

  @GetMapping("/car-list")
  public String createList(Model carModel, HttpSession session) {
    Costumer costumer = (Costumer) session.getAttribute("costumer");
    carModel.addAttribute(costumer);
    carModel.addAttribute("fleet", carService.getCarRepository().getFleet());
    return "car-list";
  }

  @GetMapping("/new-lease/{carID}")
  public String newLease(HttpSession session, @PathVariable("carID") int carID) {
    Car car = carService.findCarByID(carID);
    Costumer costumer = (Costumer) session.getAttribute("costumer");
    Lease lease = new Lease(car, costumer, (String) session.getAttribute("leaseStart"), (String) session.getAttribute("leaseEnd"));
    carService.getCarRepository().createLease(lease);
    return "create-lease";
  }


}
