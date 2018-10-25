package com.vinfol.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.vinfol.model.UserModel;
import com.vinfol.model.enums.StatusEnums;

public interface UserRepository extends MongoRepository<UserModel, String> {

	/**
	 * 根据 developerId 查询 app
	 *
	 * @param developerId
	 * @param pageable
	 * @return
	 */

	UserModel findOneByName(String name);
	
	Page<UserModel> findAllByStatusNot(Pageable pageable,StatusEnums statusEnums);

	Page<UserModel> findAllByName(Pageable pageable, String name);

	UserModel findOneByEmail(String email);

	Page<UserModel> findAll(Pageable pageable2);

}
