package by.pyshkodzianis.xmlxsdparsing.entity;

import by.pyshkodzianis.xmlxsdparsing.entity.currency.MetalCurrency;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;


public class MetalDeposit extends Deposit {

    public MetalCurrency metalCurrency;

    public MetalDeposit(){

    }

    public MetalDeposit(String depositId, String depositorName, double amount
    , double profitability, LocalDateTime openDate, String timeConstraints){
        super (depositId, depositorName, amount, profitability, openDate, timeConstraints);
    }
    @XmlElement(name = "metal-currency")
    public MetalCurrency getMetalCurrency() {
        return metalCurrency;
    }

    public void setMetalCurrency(MetalCurrency metalCurrency) {
        this.metalCurrency = metalCurrency;
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
        if ( metalCurrency == null) {
            if (other. metalCurrency != null)
                return false;
        } else if (!metalCurrency.equals(other.metalCurrency))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((metalCurrency == null) ? 0 : metalCurrency.hashCode());
        return result;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append("[metalCurrency=");
        builder.append(metalCurrency);
        builder.append("]");
        return builder.toString();
    }
}
