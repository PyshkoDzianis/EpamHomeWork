package by.pyshkodzianis.xmlxsdparsing.entity;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement(name="deposit")
@XmlAccessorType(XmlAccessType.FIELD)
public class Deposit {
    private String depositId;
    private String depositor;
    private double amount;
    private double profitability;
    private LocalDateTime openDate;
    private String timeConstraints;

    public Deposit() {
    }

    public Deposit(String depositId, String depositor, double amount
            , double profitability, LocalDateTime openDate, String timeConstraints) {

        this.depositId=depositId;
        this.depositor=depositor;
        this.amount=amount;
        this.profitability=profitability;
        this.openDate=openDate;
        this.timeConstraints=timeConstraints;
    }

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getDepositor() {
        return depositor;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public  LocalDateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    public String getTimeConstraints() {
        return timeConstraints;
    }

    public void setTimeConstraints(String timeConstraints) {
        this.timeConstraints = timeConstraints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deposit that = (Deposit) o;

        if (Double.compare(that.profitability, profitability) != 0) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (depositId != null ? !depositId.equals(that.depositId) : that.depositId != null) return false;
        if (depositor != null ? !depositor.equals(that.depositor) : that.depositor != null) return false;
        if (openDate != null ? !openDate.equals(that.openDate) : that.openDate != null) return false;
        if (timeConstraints != null ? !timeConstraints.equals(that.timeConstraints) : that.timeConstraints != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((depositId == null) ? 0 : depositId.hashCode());
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((depositor == null) ? 0 : depositor.hashCode());
        result = prime * result + ((openDate == null) ? 0 : openDate.hashCode());
        temp = Double.doubleToLongBits(profitability);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((timeConstraints == null) ? 0 : timeConstraints.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName());
        builder.append(" [depositor=");
        builder.append(depositor);
        builder.append(", depositId=");
        builder.append(depositId);
        builder.append(", openDate=");
        builder.append(openDate);
        builder.append(", timeConstraints=");
        builder.append(timeConstraints);
        builder.append(", amount=");
        builder.append(amount);
        builder.append(", profitability=");
        builder.append(profitability);
        builder.append("]");
        return builder.toString();
    }



}