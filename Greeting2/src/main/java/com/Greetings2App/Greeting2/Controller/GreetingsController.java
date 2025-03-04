package com.Greetings2App.Greeting2.Controller;

import com.Greetings2App.Greeting2.Models.Greetings;
import com.Greetings2App.Greeting2.Service.GreetingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingsController {
    private GreetingService greetingService;

    public GreetingsController (GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    //UC1
    @GetMapping("/{name}")
    public Greetings getGreeting(@PathVariable String name) {
        return new Greetings("Hello " + name);
    }

    @PostMapping
    public Greetings postGreeting(@RequestBody String name) {
        return new Greetings("Hello " + name + " from POST");
    }

    @PutMapping("/{name}")
    public Greetings putGreeting(@PathVariable String name) {
        return new Greetings("Hello " + name + " from PUT");
    }

    @DeleteMapping("/{name}")
    public Greetings deleteGreeting(@PathVariable String name) {
        return new Greetings("Goodbye " + name + " fromDELETE");
    }
    //UC2
    @GetMapping("/empty")
    public String getGreeting(){
        return greetingService.getGreetingMessage();
}

//UC3
@GetMapping("/firstNameLastName")
public String getGreeting(@RequestParam(required = false)String firstName,@RequestParam(required = false)String lastName){
    return greetingService.getGreetingMessage(firstName, lastName);
}
    //UC4
    @PostMapping("/save")
    public Greetings  savesGreeting(@RequestBody Greetings greetings) {
        return greetingService.saveGreetingMessage(greetings.getMessage());
}
    //UC5
    @GetMapping("{id}")
    public Greetings getGreetingById(@PathVariable Long id) {
        return greetingService.getGreetingById(id);
}
//UC6
    @GetMapping("/all")
    public List<Greetings> getAllGreetings() {
        return greetingService.getAllGreetings();
}
//UC7
    @PutMapping("/id/{id}")
    public Greetings updateGreeting(@PathVariable Long id, @RequestBody Greetings greetings){
        return greetingService.updateGreeting(id,greetings.getMessage());
}
}

