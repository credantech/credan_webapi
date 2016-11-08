package com.credan.webapi.comm.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CalculatorUtil {


	
	/**
	 * 等额本息计算
	 * 
	 * @param orderAmount
	 * @param instalment
	 * @param terms
	 * @return
	 */
	public static Map<String, Object> getACPI(BigDecimal installment) {

		List<Integer> termList = Lists.newArrayList();
		termList.add(6);
		termList.add(9);
		termList.add(12);

		Map<String, Object> repayments = Maps.newHashMap();
		repayments.put("term6", getMonthlyRepay(installment, new BigDecimal("0.02"), 6));
		repayments.put("term9", getMonthlyRepay(installment, new BigDecimal("0.02"), 9));
		repayments.put("term12", getMonthlyRepay(installment, new BigDecimal("0.02"), 12));

		return repayments;

	}

	public static double calculateACPI(double installment, int term, double monthlyRate) {
		if (monthlyRate == 0) {
			monthlyRate = 0.1;
		}
		double repayment = new BigDecimal(installment * (monthlyRate / 12) * (Math.pow((1 + (monthlyRate / 12)), term))
				/ (Math.pow((1 + (monthlyRate / 12)), (term)) - 1)).setScale(2, BigDecimal.ROUND_HALF_DOWN)
						.doubleValue();
		return repayment;
	}


	public static BigDecimal getMonthlyRepay(BigDecimal amount, BigDecimal monthRate, Integer terms){

		// 月本金
		BigDecimal repaymentPrincipal = Arith.div(amount, new BigDecimal(terms.toString()));
		repaymentPrincipal = subBigDecimal(repaymentPrincipal, 2);

		// 月利息
		BigDecimal repaymentInterest = Arith.mul(amount, monthRate);
		repaymentInterest = subBigDecimal(repaymentInterest, 2);
		
		//月还款总额
		BigDecimal monthlyRepay = Arith.add(repaymentPrincipal, repaymentInterest);
		monthlyRepay = subBigDecimal(monthlyRepay, 2);
		
		
		return monthlyRepay;
	}

	/**
	 * 等本等息
	 * 
	 * @param amount
	 * @param monthRate
	 * @param terms
	 * @return
	 */
	public static List<Map<String, Object>> CAI(BigDecimal amount, BigDecimal monthRate, Integer terms) {

		List<Map<String, Object>> payments = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();

		// 月本金
		BigDecimal repaymentPrincipal = Arith.div(amount, new BigDecimal(terms.toString()));
		repaymentPrincipal = subBigDecimal(repaymentPrincipal, 2);

		// 月利息
		BigDecimal repaymentInterest = Arith.mul(amount, monthRate);
		repaymentInterest = subBigDecimal(repaymentInterest, 2);
		
		//月还款总额
		BigDecimal monthlyRepay = Arith.add(repaymentPrincipal, repaymentInterest);
		monthlyRepay = subBigDecimal(monthlyRepay, 2);
		

		for (int i = 1; i <= terms; i++) {
			
			if (i == terms) {	//最后一月
				repaymentPrincipal = Arith.sub(amount, Arith.mul(repaymentPrincipal, new BigDecimal(terms-1)));
				repaymentInterest = Arith.sub(monthlyRepay, repaymentPrincipal);
				
			}
			
			map.put("repaymentInterest", repaymentInterest);
			map.put("repaymentPrincipal", repaymentPrincipal);
			map.put("monthlyRepay", monthlyRepay);
			System.out.println("还款: "+ monthlyRepay + ", 本金 : " + repaymentPrincipal + ", 利息" + repaymentInterest);
			payments.add(map);
		}
		return payments;

	}
	
	/**
	 * BigDecimal 数据截取小数点后N位 不四舍五入
	 * 
	 * @param decimalValue
	 * @return
	 */
	public static BigDecimal subBigDecimal(BigDecimal bigDecimal, int scale) {
		if (bigDecimal != null) {
			BigDecimal decimalValue = new BigDecimal(bigDecimal.toString());
			return decimalValue.setScale(scale, BigDecimal.ROUND_DOWN);
		}
		return new BigDecimal("0");
	}


	/**
	 * 计算现金贷
	 * @param loanAmount
	 * @param loanDays
	 * @param loanRate
	 * @return
	 */
	public static Map<String, Object> calculateLoanAmount(BigDecimal loanAmount, int loanDays, BigDecimal loanRate) {
		Map<String, Object> loanResult = Maps.newHashMap();
		String repayDate = DateHelper.getDateAfterDays(loanDays);
		BigDecimal loanInterest = Arith.round(Arith.mul(Arith.mul(loanAmount, loanRate), new BigDecimal(loanDays)), 0);
		
		BigDecimal finalAmount = Arith.round(Arith.sub(loanAmount, loanInterest), 0);
		loanResult.put("loanInterest", loanInterest);
		loanResult.put("finalAmount", finalAmount);
		loanResult.put("repayDate", repayDate);
		return loanResult;
	}
	
}
