package com.Greetings2App.Greeting2.Repository;

import com.Greetings2App.Greeting2.Models.Greetings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greetings, Long> {

}
