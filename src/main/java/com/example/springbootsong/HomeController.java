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
    public String listCar(Model model){
        model.addAttribute("songs", songRepository.findAll());
        return "list";
    }
    public String index(Model model){
        //create album
        Album album = new Album();
        album.setName("Thanh xuan");
        album.setYear(2019);
        //create song
        Song song = new Song();
        song.setSongName("Em cua ngay hom wa");
        song.setAstirst("SonTung");
        song.setAuthors("Nguyen Hai Phong");
        //add song to empty list
        Set<Song> songs = new HashSet<>();
        songs.add(song);

        //add more song
        song.setSongName("Nguoi em yeu");
        song.setAstirst("Erik");
        song.setAuthors("Trinh Thanh Binh");
        songs.add(song);
        //add list of song to album
        album.setSongs(songs);
        //save album to database
        albumRepository.save(album);
        //Graf all the album from the database to the template
        model.addAttribute("albums",albumRepository.findAll());
        return "index";
    }
    @GetMapping("/add")
    public String listsong(Model model){
        model.addAttribute("song", new Song());
        return "songform";
    }
    @PostMapping("/process")
    public String processSong(@Valid Song song , BindingResult result){
        if(result.hasErrors()){
            return "songform";
        }
        songRepository.save(song);
        return "redirect:/";
    }
    @RequestMapping("/detail/{id}")
    public String showSong(@PathVariable("id") long id, Model model){
        model.addAttribute("song", songRepository.findById(id).get());
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateSong(@PathVariable("id") long id, Model model){
        model.addAttribute("song", songRepository.findById(id).get());
        return "index";

    }
    @RequestMapping("/delete/{id}")
    public  String delSong(@PathVariable("id") long id){
        songRepository.deleteById(id);
        return "redirect:/";
    }

}
