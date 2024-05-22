package com.ECommerce.Tshirt.Repositories;

import com.ECommerce.Tshirt.Models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
