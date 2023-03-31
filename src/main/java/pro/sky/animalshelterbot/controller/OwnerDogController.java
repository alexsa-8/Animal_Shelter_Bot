package pro.sky.animalshelterbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalshelterbot.constant.OwnerStatus;
import pro.sky.animalshelterbot.constant.PetStatus;import pro.sky.animalshelterbot.entity.OwnerDog;
import pro.sky.animalshelterbot.service.OwnerDogService;

import java.util.Collection;

@RestController
@RequestMapping("owners")
public class OwnerDogController {
    @Autowired
    private OwnerDogService service;

    @PostMapping
    public OwnerDog create(@RequestBody OwnerDog ownerDog, @RequestParam OwnerStatus status) {
        return service.create(ownerDog, status);
    }

    @GetMapping("{id}")
    public ResponseEntity<OwnerDog> find(@PathVariable Long id) {
        OwnerDog ownerDog = service.find(id);
        if (ownerDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerDog);
    }

    @PutMapping
    public ResponseEntity<OwnerDog> update(@RequestBody OwnerDog ownerDog,
                                           @RequestParam OwnerStatus status) {
        OwnerDog ownerDog1 = service.update(ownerDog, status);
        if (ownerDog1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerDog1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<OwnerDog>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

}
