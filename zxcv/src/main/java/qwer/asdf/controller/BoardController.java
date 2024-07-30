package qwer.asdf.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @GetMapping
    public String listBoards(Model model) {
        List<Board> boards = boardService.getAllPosts();
        model.addAttribute("boards", boards);
        return "front/boards/list";
    }

    @GetMapping("/new")
    public String showBoardForm(Model model) {
        model.addAttribute("requestBoard", new RequestBoard());
        return "front/boards/create";
    }

    @PostMapping
    public String saveBoard(@Valid @ModelAttribute("requestBoard") RequestBoard requestBoard, Errors errors) {
        if (errors.hasErrors()) {
            return "front/boards/create";
        }
        if (requestBoard.getSeq() == null) {
            boardService.savePost(requestBoard);
        } else {
            boardService.updatePost(requestBoard);
        }
        return "redirect:/boards";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Board board = boardService.findPostById(id);
        RequestBoard requestBoard = modelMapper.map(board, RequestBoard.class); // 변환 추가
        model.addAttribute("requestBoard", requestBoard);
        return "front/boards/create";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        boardService.deletePostById(id);
        return "redirect:/boards";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model) {
        Board board = boardService.findPostById(id);
        RequestBoard requestBoard = modelMapper.map(board, RequestBoard.class); // 변환 추가
        model.addAttribute("board", requestBoard);
        return "front/boards/view";
    }
}
