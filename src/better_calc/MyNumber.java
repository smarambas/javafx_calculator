package better_calc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MyNumber {

	private BigDecimal num;
	
	public MyNumber(String s) {
		num = new BigDecimal(s);
	}
	
	public BigDecimal getBD() {
		return num;
	}
	
	public void setBD(BigDecimal n) {
		num = n;
	}
		
	public String getString() {
		return num.toString();
	}
	
	public String sum(MyNumber n) {
		BigDecimal ans = num.add(n.getBD());
		return ans.toString();
	}
	
	public String sub(MyNumber n) {
		BigDecimal ans = num.subtract(n.getBD());
		return ans.toString();
	}
	
	public String mult(MyNumber n) {
		BigDecimal ans = num.multiply(n.getBD());
		return ans.toString();
	}
	
	public String div(MyNumber n) {
		if(n.getBD().equals(BigDecimal.ZERO)) {
			return "Infinity";
		}
		else {
			int scale = 6;	//decimal numbers to show after the dot
			BigDecimal ans = num.divide(n.getBD(), scale, RoundingMode.CEILING);
			return ans.toString();
		}
	}
	
	public String pow(MyNumber n) {
		BigDecimal ans = num.pow(n.getBD().intValue());
		return ans.toString();
	}
	
	public String sqrt() {		
		if(num.compareTo(BigDecimal.ZERO) < 0) {
			return "nan";
		}
		else {
			MathContext mContext = new MathContext(6);
			BigDecimal ans = num.sqrt(mContext);
			return ans.toString();
		}
	}
}
