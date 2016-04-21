package byCodeGame.game.entity.po;


public class Vector2D {

	private double x;
	
	private double y;
	
	
	public Vector2D(double x , double y){
		this.setX(x);
		this.setY(y);
	}
	
	public Vector2D clone(){
		return new Vector2D(getX(), getY());
	}
	
	public Vector2D zero(){
		setX(0);
		setY(0);
		return this;
	}
	
	public boolean isZero(){
		return getX()==0 && getY()==0;
	}
	
	public void setLength(double value){
		double a = this.getAngle(value);
		setX(Math.cos(a) * value);
		setY(Math.sin(a) * value);
	}
	
	/**
	 * 获取角度
	 * @param value
	 * @return
	 */
	public double getAngle(double value){
		return Math.atan2(getX(), getY());
	}
	
	/**
	 * 设置角度
	 * @param value
	 */
	public void setAngle(double value){
		double len = this.getLength();
		setX(Math.cos(value) * len);
		setY(Math.sin(value) * len);
	}
	
	/**
	 * 获取向量的模长
	 * @return
	 */
	public double getLength(){
		return Math.sqrt(lengthSQ());
	}
	
	/**
	 * 获取当前向量大小的平方
	 * @return
	 */
	public double lengthSQ(){
		return getX() * getX() + getY() * getY();
	}
	
	/**
	 * 将当前向量转化成单位向量   
	 * @return
	 */
	public Vector2D normalize(){
		if(this.getLength() == 0){
			setX(1);
			return this;
		}
		double len = this.getLength();
		setX(getX() / len);
		setY(getY() / len);
		return this;
	}
	
	/**
	 * 截取当前向量   
	 * @param max
	 * @return
	 */
	public Vector2D truncate(double max){
		this.setLength(Math.min(max, this.getLength()));
		return this;
	}
	
	public Vector2D reverse(){
		setX(-getX());
		setY(-getY());
		return this;
	}
	
	public boolean isNormalized(){
		return this.getLength() == 1.0;
	}
	
	/**
	 * 计算点积
	 * @param v2
	 * @return
	 */
	public double dotProd(Vector2D v2){
		return getX()*v2.getX() + getY() * v2.getY();
	}
	
	/**
	 * 计算差积
	 * @param v2
	 * @return
	 */
	public double crossProd(Vector2D v2){
		return getX()*v2.getY() - getY()*v2.getX();
	}
	
	public double angleBetween(Vector2D v1,Vector2D v2){
		if(!v1.isNormalized()) v1 = v1.clone().normalize();
		if(!v2.isNormalized()) v2 = v2.clone().normalize();
		
		return Math.acos(v1.dotProd(v2));
	}
	
	public int sign(Vector2D v2){
		return this.getPerp().dotProd(v2) <0 ? -1 : 1;
	}
	
	public Vector2D getPerp(){
		return new Vector2D(-getY(), getX());
	}
	
	public double dist(Vector2D v2){
		return Math.sqrt(this.distSQ(v2));
	}
	
	public double distSQ(Vector2D v2){
		double dx = v2.getX() - getX();
		double dy = v2.getY() - getY();
		return dx * dx + dy * dy;
	}
	
	public Vector2D add(Vector2D v2){
		return new Vector2D(getX() + v2.getX(), getY() + v2.getY());
	}
	
	public Vector2D subtract(Vector2D v2){
		return new Vector2D(getX() - v2.getX(),getY() - v2.getY());
	}
	
	/**
	 * 向量乘
	 * @param value
	 * @return
	 */
	public Vector2D multiply(double value){
		return new Vector2D(getX() * value, getY() * value);
	}
	
	public Vector2D divide(double value){
		return new Vector2D(getX()/value, getY()/value);
	}
	
	public boolean equals(Vector2D v2){
		return getX()==v2.getX() && getY() == v2.getY();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public String toString(){
		return "[Vector2D(x:"+ x + ",y:"+ y + ")]";
	}
}
