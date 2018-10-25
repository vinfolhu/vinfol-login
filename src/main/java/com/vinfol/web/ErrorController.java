package com.vinfol.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vinfol.utils.BaseResponse;
import com.vinfol.utils.consts.IConstants;

import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
public class ErrorController
{
  Logger logger = LoggerFactory.getLogger(this.getClass());
  @ExceptionHandler(value = { Exception.class})
  public @ResponseBody ResponseEntity<BaseResponse> exceptionHandler(Exception ex, HttpServletRequest request)
  {
    logger.error("exceptionHandler error:{}", ex);
//    if(request!=null)
//    {
//      sb.append("URI:").append(request.getRequestURI()).append("\n");
//      sb.append("URL:").append(request.getRequestURL()).append("\n");
//      sb.append("RemoteAddr:").append(request.getRemoteAddr()).append("\n");
//      sb.append("RemoteHost:").append(request.getRemoteHost()).append("\n");
//      sb.append("RemotePort:").append(request.getRemotePort()).append("\n");
//      sb.append("method:").append(request.getMethod()).append("\n");
//      //add header
//      Enumeration<String> enumeration = request.getHeaderNames();
//      while (enumeration.hasMoreElements())
//      {
//        String header = enumeration.nextElement();
//        sb.append(header).append(":").append(request.getHeader(header)).append("\n");
//      }
//    }
//    String stacktrace = ExceptionUtils.getStackTrace(ex);
//    sb.append(stacktrace);
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_FAILED);
//    baseResponse.setStatusMsg(ex.getMessage());
    return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
  }
}
