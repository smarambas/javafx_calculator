package better_calc;

import java.math.BigDecimal;
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
			int scale = 6;
			BigDecimal ans = num.divide(n.getBD(), scale, RoundingMode.CEILING);
			return ans.toString();
		}
	}
}
