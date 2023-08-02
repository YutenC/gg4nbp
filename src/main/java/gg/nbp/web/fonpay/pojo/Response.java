package gg.nbp.web.fonpay.pojo;

import java.io.Serializable;

import lombok.Data;
@Data
public class Response implements Serializable {
	private static final long serialVersionUID = 4592809926853756847L;
	private Boolean success ;
	private String msg;
	private String debugMsg;
	private Integer apiLogId ;
	private String environment;
	private Integer errorCode;
}
