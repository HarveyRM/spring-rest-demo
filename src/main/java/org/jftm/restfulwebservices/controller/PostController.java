package org.jftm.restfulwebservices.controller;

import org.jftm.restfulwebservices.exception.UserNotFoundException;
import org.jftm.restfulwebservices.model.Post;
import org.jftm.restfulwebservices.model.User;
import org.jftm.restfulwebservices.respository.PostRepository;
import org.jftm.restfulwebservices.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/users/{id}/posts")
    public List<Post> getUsersPosts(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }

        return userOptional.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post){
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }

        User user = userOptional.get();
        post.setUser(user);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(post.getId()).toUri();

		return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable int id){
        postRepository.deleteById(id);
    }

}
