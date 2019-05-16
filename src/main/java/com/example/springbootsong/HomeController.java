package com.example.springbootsong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    SongRepository songRepository;

    @RequestMapping("/")
    public String listalbum(Model model){
        model.addAttribute("albums", albumRepository.findAll());
        return "list";
    }
    @GetMapping("/add")
    public String albumform(Model model){
        model.addAttribute("album", new Album());
        return "albumform";
    }
    @PostMapping("/processalbum")
    public String processform(@Valid Album album, BindingResult result){
        if(result.hasErrors()){
            return "albumform";
        }
        albumRepository.save(album);
        Set<Song> songs = new HashSet<>();
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showAlbum(@PathVariable("id") long id, Model model){
        model.addAttribute("album", albumRepository.findById(id).get());
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateAlbum(@PathVariable("id") long id, Model model){
        model.addAttribute("album", albumRepository.findById(id).get());
        return "albumform";

    }
    @RequestMapping("/delete/{id}")
    public  String delAlbum(@PathVariable("id") long id){
        albumRepository.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/addsongs/{id}")
    public String addSongs(@PathVariable("id") long id, Model model){
        model.addAttribute("album", albumRepository.findById(id).get() );
        return "redirect:/addsongs";
    }


    @GetMapping("/addsongs")
    public String songForm(Model model){
        model.addAttribute("albums", albumRepository.findAll());

        model.addAttribute("song", new Song());
        return "songform";
    }

    @PostMapping("/process_song")
    public String processForm(@Valid Song song, BindingResult result){
        if (result.hasErrors()){
            return "songform";
        }

        Set<Song> songs = new HashSet<Song>();



        return "redirect:/";
    }

}
