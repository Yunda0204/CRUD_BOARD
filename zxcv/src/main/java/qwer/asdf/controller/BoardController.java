package qwer.asdf.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import qwer.asdf.entities.Board;
import qwer.asdf.services.BoardService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping // Spring Web
    public String listBoards(Model model) { // Spring Framework
        List<Board> boards = boardService.getAllPosts();
        model.addAttribute("boards", boards);
        return "front/boards/list";
    }

    @GetMapping("/new")
    public String showBoardForm(Model model) {
        model.addAttribute("requestBoard", new RequestBoard());
        return "front/boards/form";
    }

    @PostMapping
    public String saveBoard(@Valid @ModelAttribute("requestBoard") RequestBoard requestBoard, Errors errors) {
        if (errors.hasErrors()) {
            return "front/boards/form";
        }
        boardService.savePost(requestBoard);
        return "redirect:/boards";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model, Errors errors) {
        Optional<RequestBoard> requestBoard = boardService.getPostById(id);
        if (requestBoard.isEmpty()) {
            errors.reject("invalid.id", "Invalid post Id:" + id);
            return "redirect:/boards";
        }
        model.addAttribute("requestBoard", requestBoard.get());
        return "front/boards/form";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") long id) {
        boardService.deletePostById(id);
        return "redirect:/boards";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable("id") long id, Model model, Errors errors) {
        Optional<RequestBoard> requestBoard = boardService.getPostById(id);
        if (requestBoard.isEmpty()) {
            errors.reject("invalid.id", "Invalid board Id:" + id);
            return "redirect:/boards";
        }
        model.addAttribute("board", requestBoard.get());
        return "front/boards/view";
    }
}
