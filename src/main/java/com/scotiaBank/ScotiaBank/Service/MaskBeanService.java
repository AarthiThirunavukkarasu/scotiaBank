package com.scotiaBank.ScotiaBank.Service;


public interface MaskBeanService {
	
	public static String maskCardNumber(String value, int visibleDigits) {
        int length = value.length();

        if (length <= visibleDigits) {
            // If the card number is shorter than or equal to the number of visible digits,
            // return the original card number without masking.
            return value;
        } else {
            // Mask all characters except the last 'visibleDigits' characters.
            StringBuilder maskedNumber = new StringBuilder();
            for (int i = 0; i < length - visibleDigits; i++) {
                maskedNumber.append('*');
            }
            maskedNumber.append(value.substring(length - visibleDigits));
            return maskedNumber.toString();
        }
    }

}
