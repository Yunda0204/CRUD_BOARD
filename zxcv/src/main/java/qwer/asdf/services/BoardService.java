package qwer.asdf.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import qwer.asdf.controller.RequestBoard;
import qwer.asdf.entities.Board;
import qwer.asdf.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    public List<Board> getAllPosts() {
        return boardRepository.findAll();
    }

    public Optional<RequestBoard> getPostById(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        return board.map(value -> modelMapper.map(value, RequestBoard.class));
    }

    public RequestBoard savePost(RequestBoard requestBoard) {
        Board board = modelMapper.map(requestBoard, Board.class);
        board = boardRepository.save(board);
        return modelMapper.map(board, RequestBoard.class);
    }

    public void deletePostById(Long id) {
        boardRepository.deleteById(id);
    }

    public Board findPostById(Long id) { // 반환 타입 변경
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + id));
    }
}