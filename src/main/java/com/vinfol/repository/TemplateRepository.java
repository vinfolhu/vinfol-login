package com.vinfol.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.vinfol.model.TemplateModel;
import com.vinfol.model.UserModel;
import com.vinfol.model.enums.StatusEnums;

public interface TemplateRepository extends MongoRepository<TemplateModel, String> {

	
	Page<TemplateModel> findAllByStatus(Pageable pageable,StatusEnums statusEnums);

	Page<TemplateModel> findAllByStatusNot(Pageable pageable,StatusEnums statusEnums);

	Page<TemplateModel> findAllByUserIdAndStatusNot(Pageable pageable,String userId, StatusEnums delete);
}
