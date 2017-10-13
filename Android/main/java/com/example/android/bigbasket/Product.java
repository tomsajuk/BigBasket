package com.example.android.bigbasket;

/**
 * Created by HP on 13-09-2017.
 */

public class Product {
   private String barcode;
    private char firstChar;
    private  String prodName;
    private float unitPrice;
    private int quant;
    private float amount;
    private String category;

    public Product(char f, String prod,String unit,String bar,String cat){
        firstChar = f;
        prodName = prod;
        amount = unitPrice = Float.valueOf(unit);
        quant = 1;
        barcode = bar;
        category = cat;
    }

    public String getFirstChar(){
        String ret = firstChar+"";
        return ret;}
    public String getProdName(){return prodName;}
    public String getAmount(){return String.valueOf(amount);}
    public String getQuant(){return String.valueOf(quant);}

    void setQuant(int q){
        if(q>=0)
            quant = q;
    }

    void setAmount(int q){
        if(q>=0)
        {
            amount = unitPrice*quant;
        }
    }

}
