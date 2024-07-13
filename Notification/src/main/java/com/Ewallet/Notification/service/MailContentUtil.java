package com.Ewallet.Notification.service;

public class MailContentUtil {

	public static String getSuccessSenderEmailContent(String username, Double amount) {
		StringBuilder builder = new StringBuilder();
		builder.append("Dear " + username + ",\n\n");
		builder.append("Your account is debited with amount " + amount + " successfully.\n\n");
		builder.append("Thank you for using our services.");
		return builder.toString();
	}

	public static String getSuccessRecieverEmailContent(String username, Double amount) {
		StringBuilder builder = new StringBuilder();
		builder.append("Dear " + username + ",\n\n");
		builder.append("Your account is credited with amount " + amount + " successfully.\n\n");
		builder.append("Thank you for using our services.");
		return builder.toString();
	}


	public static String getFailureEmailContent(String username, Double amount) {
		StringBuilder builder = new StringBuilder();
		builder.append("Dear " + username + ",\n\n");
		builder.append("Your transaction of " + amount + " was unsuccessful.\n\n");
		builder.append("Thank you for using our services.");
		return builder.toString();
	}

	public static String getSubjectTransactionSuccessful(){
		return "Transaction Successful";
	}

	public static String getSubjectTransactionFailure(){
		return "Transaction Failure";
	}

}
