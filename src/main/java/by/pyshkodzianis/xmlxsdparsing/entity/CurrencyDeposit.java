package by.pyshkodzianis.xmlxsdparsing.entity;

import by.pyshkodzianis.xmlxsdparsing.entity.currency.Currency;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;


public class CurrencyDeposit extends Deposit {
    public Currency currency;


    public CurrencyDeposit(){

    }

    public CurrencyDeposit(String depositId, String depositorName, double amount
            , double profitability, LocalDateTime openDate, String timeConstraints){
        super (depositId, depositorName, amount, profitability, openDate, timeConstraints);
    }
    @XmlElement(name = "currency")
    public Currency getCurrency() {

        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MetalDeposit other = (MetalDeposit) obj;
        if ( currency == null) {
            if (other. metalCurrency != null)
                return false;
        } else if (!currency.equals(other.metalCurrency))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        return result;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append("[currency=");
        builder.append(currency);
        builder.append("]");
        return builder.toString();
    }
}


