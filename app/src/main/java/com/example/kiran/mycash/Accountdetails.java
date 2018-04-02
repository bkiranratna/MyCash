package com.example.kiran.mycash;
import java.util.List;
import java.util.ArrayList;

public class Accountdetails {

    private String currentmonthname;
    private String currentmonthexpenseamount;
    private String currentmonthincomeamount;
    private String totalexpenseamount;
    private String totalincomeamount;
    private String totalbalanceamount;
    private List<Expense> expense = new ArrayList<Expense>();
    private List<Income> income = new ArrayList<Income>();

    public String getCurrentmonthname() {
        return currentmonthname;
    }

    public void setCurrentmonthname(String currentmonthname) {
        this.currentmonthname = currentmonthname;
    }

    public String getCurrentmonthexpenseamount() {
        return currentmonthexpenseamount;
    }

    public void setCurrentmonthexpenseamount(String currentmonthexpenseamount) {
        this.currentmonthexpenseamount = currentmonthexpenseamount;
    }

    public String getCurrentmonthincomeamount() {
        return currentmonthincomeamount;
    }

    public void setCurrentmonthincomeamount(String currentmonthincomeamount) {
        this.currentmonthincomeamount = currentmonthincomeamount;
    }

    public String getTotalexpenseamount() {
        return totalexpenseamount;
    }

    public void setTotalexpenseamount(String totalexpenseamount) {
        this.totalexpenseamount = totalexpenseamount;
    }

    public String getTotalincomeamount() {
        return totalincomeamount;
    }

    public void setTotalincomeamount(String totalincomeamount) {
        this.totalincomeamount = totalincomeamount;
    }

    public String getTotalbalanceamount() {
        return totalbalanceamount;
    }

    public void setTotalbalanceamount(String totalbalanceamount) {
        this.totalbalanceamount = totalbalanceamount;
    }

    public List<Expense> getExpense() {
        return expense;
    }

    public void setExpense(List<Expense> expense) {
        this.expense = expense;
    }

    public List<Income> getIncome() {
        return income;
    }

    public void setIncome(List<Income> income) {
        this.income = income;
    }

}
