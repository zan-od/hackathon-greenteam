package org.hillel.hackatongreenteam.controller;

import org.hillel.hackatongreenteam.controller.dto.UserDto;
import org.hillel.hackatongreenteam.model.User;
import org.hillel.hackatongreenteam.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://37.59.228.228")
    @GetMapping("user/{id}")
    public RestResponse getOne(@PathVariable("id") int id) {
        RestResponse response = new RestResponse();

        User user = userService.read(id);

        if (user == null) {
            response.setStatus(404);
            response.setMessage(String.format("User %d not found", id));
        } else {
            UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getAvatarPictureFilename());
            response.setStatus(200);
            response.setData(userDto);
        }
        return response;
    }


    @CrossOrigin(origins = "http://37.59.228.228")
    @PostMapping("admin/user")
    public RestResponse create(@RequestBody User user) {
        RestResponse response = new RestResponse();
        response.setStatus(201);
        response.setMessage("New user has created");
        response.setData(userService.save(user));
        return response;
    }

    @CrossOrigin(origins = "http://37.59.228.228")
    @PutMapping("admin/user/{id}")
    public RestResponse update(@PathVariable("id") User userFromDB,
                               @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDB, "id");

        RestResponse response = new RestResponse();
        response.setStatus(200);
        response.setMessage("The user has updated");
        response.setData(userService.save(userFromDB));

        return response;
    }

    @CrossOrigin(origins = "http://37.59.228.228")
    @DeleteMapping("admin/user/{id}")
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }

}
