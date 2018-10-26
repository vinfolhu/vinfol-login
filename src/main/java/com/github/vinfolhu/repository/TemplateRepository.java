package com.github.vinfolhu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.vinfolhu.model.TemplateModel;
import com.github.vinfolhu.model.UserModel;
import com.github.vinfolhu.model.enums.StatusEnums;

public interface TemplateRepository extends MongoRepository<TemplateModel, String> {

	
	Page<TemplateModel> findAllByStatus(Pageable pageable,StatusEnums statusEnums);

	Page<TemplateModel> findAllByStatusNot(Pageable pageable,StatusEnums statusEnums);

	Page<TemplateModel> findAllByUserIdAndStatusNot(Pageable pageable,String userId, StatusEnums delete);
}
