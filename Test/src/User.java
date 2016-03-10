interface HouseHandler {
	public void buyHouse();
}

public class User implements HouseHandler {
	@Override
	public void buyHouse() {
		System.out.println("�������Լ�Ҫ������");
	}
	public static void main(String[] args) {
		User user = new User();
		HouseCompany hc = new HouseCompany(user);
		hc.buyHouse();
	}
}

class HouseCompany implements HouseHandler {
	public User user;
	public HouseCompany(){}

	public HouseCompany(User user){
		this.user = user;
	}
	public void before(){
		System.out.println("���ݹ�˾֮ǰ��Ҫ������");
	}
	public void after(){
		System.out.println("���ݹ�˾֮����Ҫ������");
	}
	@Override
	public void buyHouse() {
		before();
		user.buyHouse();
		after();
	}

}