package com.codegym.education.controller;

import com.codegym.education.model.Document;
import com.codegym.education.model.Lesson;
import com.codegym.education.repository.DocumentRepository;
import com.codegym.education.service.UserService;
import com.codegym.education.service.document.DocumentService;
import com.codegym.education.service.lesson.LessonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView home(@PageableDefault(size = 6) Pageable pageable) {
        Page<Lesson> listLessons = (Page<Lesson>) lessonService.findAll(pageable);
        Page<Document> listDocuments = (Page<Document>) documentService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("participant", userService.getCurrentUser());
        modelAndView.addObject("listLessons", listLessons);
        modelAndView.addObject("listDocuments", listDocuments);
        return modelAndView;
    }

    //xem chi tiet 1 phan
    @GetMapping("/showlesson/{id}")
    public ModelAndView showLesson(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("showlesson");
        Optional<Lesson> lesson = lessonService.findById(id);
        modelAndView.addObject("lesson", lesson);
        return modelAndView;
    }

    @GetMapping("/showDocument/{id}")
    public ModelAndView showDocument(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("showDocument");
        Optional<Document> document = documentService.findById(id);
        return modelAndView;
    }
    // vao trang tong bai viet + tim kiem

    @GetMapping("/showAllLesson")
    public ModelAndView showAllLesson(@PageableDefault(size = 10) Pageable pageable,
                                      @RequestParam("keyword") Optional<String> keyword) {
        Page<Lesson> listLesson;
        if (keyword.isPresent()) {
            listLesson = lessonService.findByNameLesson(pageable, keyword);
        } else {
            listLesson = lessonService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("alllesson");
        modelAndView.addObject("listLessons", listLesson);
        return modelAndView;
    }

    @GetMapping("/showAllDocument")
    public ModelAndView showAllDocument(@PageableDefault(size = 10) Pageable pageable,
                                        @RequestParam("keyword") Optional<String> keyword) {
        Page <Document> listDocuments;
        if(keyword.isPresent()){
            listDocuments = documentService.findByNameDocument(pageable,keyword);
        }else {
            listDocuments = documentService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("allDocument");
        modelAndView.addObject("listDocuments", listDocuments);
        return modelAndView;
    }

}

