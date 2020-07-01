package com.testik.news.controllers;

import com.testik.news.models.Post;
import com.testik.news.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NewsController {
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/")
    public String newsMain(Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "home";
    }
    @GetMapping("/add")
    public String newsAdd(Model model)
    {
        return "news-add";
    }
    @PostMapping("/add")
    public String newsPostadd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post=  new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/";
    }
    @GetMapping("/{id}")
    public String newsDetails(@PathVariable(value = "id") long id, Model model){
        if (!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "news-details";
    }
    @GetMapping("/{id}/edit")
    public String newsEdit(@PathVariable(value = "id") long id, Model model){
        if (!postRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "news-edit";
    }
    @PostMapping("/{id}/edit")
    public String newsPostupdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post =postRepository.findById(id). orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/";
    }
    @PostMapping("/{id}/delete")
    public String newsPostdelete(@PathVariable(value = "id") long id, Model model){
        Post post =postRepository.findById(id). orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }
}
