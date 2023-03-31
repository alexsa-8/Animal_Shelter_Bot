package pro.sky.animalshelterbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalshelterbot.constant.PetStatus;
import pro.sky.animalshelterbot.entity.Dog;
import pro.sky.animalshelterbot.service.DogService;

import java.util.Collection;

@RestController
@RequestMapping("dogs")
public class DogController {
    @Autowired
    private DogService service;

    @PostMapping
    public Dog createDog(@RequestBody Dog dog, @RequestParam PetStatus status) {
        return service.createDog(dog, status);
    }

    @GetMapping("{id}")
    public ResponseEntity<Dog> findDog(@PathVariable Long id) {
        Dog dog = service.findDog(id);
        if (dog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog);
    }

    @PutMapping
    public ResponseEntity<Dog> updateDog(@RequestBody Dog dog,
                                         @RequestParam PetStatus status) {
        Dog dog1 = service.updateDog(dog, status);
        if (dog1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable long id) {
        service.deleteDog(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Dog>> getAll() {
        return ResponseEntity.ok(service.getAllDog());
    }

}
