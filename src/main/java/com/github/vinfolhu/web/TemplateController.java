package com.github.vinfolhu.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.vinfolhu.model.TemplateModel;
import com.github.vinfolhu.model.UserModel;
import com.github.vinfolhu.model.enums.StatusEnums;
import com.github.vinfolhu.repository.TemplateRepository;
import com.github.vinfolhu.utils.DataResponse;
import com.github.vinfolhu.utils.consts.IConstants;

@RestController
@RequestMapping("/template")
public class TemplateController {
	private static final Logger log = LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	protected HttpServletRequest request;

	@PostMapping("/add")
	public TemplateModel add(@RequestBody TemplateModel templateModel) {
		System.out.println(
				"request.getAttribute(IConstants.USERMODELMSG):" + request.getAttribute(IConstants.USERMODELMSG));
		UserModel userModel = (UserModel) request.getAttribute(IConstants.USERMODELMSG);

		templateModel.setId(UUID.randomUUID().toString());
		templateModel.setStatus(StatusEnums.VALID);

		templateModel.setUserId(userModel.getId());

		log.info(templateModel.toString());

		return templateRepository.save(templateModel);

	}

	@GetMapping("/delete")
	public TemplateModel delete(@RequestParam String[] ids) {
		System.out.println(
				"request.getAttribute(IConstants.USERMODELMSG):" + request.getAttribute(IConstants.USERMODELMSG));
		UserModel userModel = (UserModel) request.getAttribute(IConstants.USERMODELMSG);
		for (String id : ids) {
			System.out.println(id);
		}

		return null;

	}

	@PostMapping("/update")
	public TemplateModel update(@RequestBody TemplateModel templateModel) {
		System.out.println(
				"request.getAttribute(IConstants.USERMODELMSG):" + request.getAttribute(IConstants.USERMODELMSG));
		UserModel userModel = (UserModel) request.getAttribute(IConstants.USERMODELMSG);

//		templateModel.setId(UUID.randomUUID().toString());
		templateModel.setStatus(StatusEnums.VALID);

		templateModel.setUserId(userModel.getId());

		log.info(templateModel.toString());

		return templateRepository.save(templateModel);

	}

	@GetMapping("/list")
	public DataResponse<Page<TemplateModel>> list(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "20") int size,
			@RequestParam(required = false, defaultValue = "") String name) {
		DataResponse<Page<TemplateModel>> dataResponse = new DataResponse<>();
		System.out.println(
				"request.getAttribute(IConstants.USERMODELMSG):" + request.getAttribute(IConstants.USERMODELMSG));
		UserModel userModel = (UserModel) request.getAttribute(IConstants.USERMODELMSG);
		page = page - 1;
		Pageable pageable = new PageRequest(page < 0 ? 0 : page, size);

		Page<TemplateModel> userModels = templateRepository.findAllByUserIdAndStatusNot(pageable, userModel.getId(),
				StatusEnums.DELETE);
		System.out.println(userModels);
		dataResponse.setObject(userModels);
		return dataResponse;
	}

	@GetMapping("/publish/list")
	public DataResponse<Page<TemplateModel>> publishList(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "20") int size,
			@RequestParam(required = false, defaultValue = "") String name) {
		DataResponse<Page<TemplateModel>> dataResponse = new DataResponse<>();
		System.out.println(
				"request.getAttribute(IConstants.USERMODELMSG):" + request.getAttribute(IConstants.USERMODELMSG));
		page = page - 1;
		Pageable pageable = new PageRequest(page < 0 ? 0 : page, size);

		Page<TemplateModel> userModels = templateRepository.findAllByStatusNot(pageable, StatusEnums.DELETE);
		System.out.println(userModels);
		dataResponse.setObject(userModels);
		return dataResponse;
	}

}
