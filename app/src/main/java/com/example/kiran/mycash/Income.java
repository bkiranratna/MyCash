package com.example.kiran.mycash;

public class Income {

   private String amountsourcetype;
   private String id;
   private String transactiontype;
   private String amount;
   private String date;

   public String getAmountsourcetype() {
       return amountsourcetype;
   }

   public void setAmountsourcetype(String amountsourcetype) {
       this.amountsourcetype = amountsourcetype;
   }

   public String getId() {
       return id;
   }

   public void setId(String id) {
       this.id = id;
   }

   public String getTransactiontype() {
       return transactiontype;
   }

   public void setTransactiontype(String transactiontype) {
       this.transactiontype = transactiontype;
   }

   public String getAmount() {
       return amount;
   }

   public void setAmount(String amount) {
       this.amount = amount;
   }

   public String getDate() {
       return date;
   }

   public void setDate(String date) {
       this.date = date;
   }

}
