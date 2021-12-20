package com.example.mts_database.model.cards;


import com.example.mts_database.CommandLinApp;
import com.fasterxml.uuid.Generators;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class DebitCard implements PaymentCard {
    //private static UUID uuid = Generators.randomBasedGenerator().generate();;


    @Id
    @Column(nullable = false)
    private long id;
    @Temporal(TemporalType.DATE)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createDate;
    @Column(nullable = false)
    private String holderName;
    @Column(nullable = false)
    private String holderSurname;
    @Column(nullable = false, length = 16)
    private String cardNumber;
    @Column(nullable = false, length = 3)
    private String cardCVV;
    @Column(nullable = false, length = 5)
    private String cardValidTill;
    @Column(nullable = false)
    private CardAmount amount;

    /*@OneToMany(mappedBy = "cardFrom",
    fetch = FetchType.LAZY)
    private Set<Operation> myOperations = new HashSet<>();
*/
    public DebitCard(String holderName, String holderSurname, String cardNumber,
                     String cardCVV, String cardValidTill, CardAmount amount) {
        this.id = Generators.timeBasedGenerator().generate().timestamp();
        this.holderName = holderName;
        this.holderSurname = holderSurname;
        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
        this.cardValidTill = cardValidTill;
        this.amount = amount;
    }


    @Override
    public boolean checkCardCvv(String cardCVV) {
        System.out.println("BD - " + this.cardCVV + " CARD -" + cardCVV + "EQ - " + this.cardCVV.equals(cardCVV));
        return this.cardCVV.equals(cardCVV);
    }

    @Override
    public BigDecimal subtractAmount(int transferSum) {
        //ткущий баланс карты
        var balance = this.amount.value;
        //Вычисление комиссии за операцию
        var commission = CommandLinApp.percent
                .multiply(BigDecimal.valueOf(transferSum))
                .divide(BigDecimal.valueOf(100));
        //Вычисление суммы перевода + комиссия за операцию
        BigDecimal subtractSum = BigDecimal.valueOf(transferSum).add(commission);

        if (balance.compareTo(subtractSum) < 0) {
            return BigDecimal.valueOf(-1);
        } else {
            this.amount.subtractAmount(subtractSum);
            return commission;
        }
    }

    @Override
    public void addAmount(int adder) {
        this.amount.addAmount(BigDecimal.valueOf(adder));
    }
}
