package com.autowrite.common.framework.entity;

import java.util.List;
import java.util.Map;

public class PaymentListEntity extends PageListWrapper {
	private List<PaymentMasterEntity> paymentMasterList;
	private List<PaymentLogEntity> paymentLogList;
	
	public List<PaymentMasterEntity> getPaymentMasterList() {
		return paymentMasterList;
	}
	public void setPaymentMasterList(List<PaymentMasterEntity> paymentMasterList) {
		this.paymentMasterList = paymentMasterList;
	}
	public List<PaymentLogEntity> getPaymentLogList() {
		return paymentLogList;
	}
	public void setPaymentLogList(List<PaymentLogEntity> paymentLogList) {
		this.paymentLogList = paymentLogList;
	}
}
