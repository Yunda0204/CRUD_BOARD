package qwer.asdf.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import qwer.asdf.entities.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
