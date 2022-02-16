package com.revature;

public class DaoFactory {
    private static EmployeeDao employeeDao;
    private static CustomerDao customerDao;
    private static UserDao userDao;

    private DaoFactory() {

    }

    public static CustomerDao getCustomerDao() {
        if (customerDao == null) {
            customerDao = new CustomerDaoImp();
        }
        return customerDao;
    }

    public static EmployeeDao getEmployeeDao() {
        if (employeeDao == null) {
            employeeDao = new EmployeeDaoImp();
        }
        return employeeDao;
    }
    public static UserDao getUserDao(){
        if (userDao==null){
            userDao=new UserDaoImp();
        }
        return userDao;
    }
}
