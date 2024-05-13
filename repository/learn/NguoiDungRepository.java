package vn.hoidanit.laptopshop.repository.learn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hoidanit.laptopshop.domain.learn.NguoiDung;

//crud: create, read, update, delete
@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {

}
